import { useEffect, useState } from "react";
import {
  Button,
  Datagrid,
  List,
  TextField,
  useNotify,
  useRecordContext,
} from "react-admin";
import { Stack } from "@mui/material";
import { StudentStatusField } from "./student-status";

type StatusRequest = {
  id: number;
  requestedStatus: string;
  status: string;
};

const statusOptions = [
  { value: "WITHDRAWN_FROM_UNIVERSITY", label: "Withdraw from university" },
  { value: "TERM_DEFERRED", label: "Defer term" },
  { value: "GRADUATED", label: "Graduation" },
];

const statusLabels: Record<string, string> = {
  WITHDRAWN_FROM_UNIVERSITY: "Withdrawn",
  TERM_DEFERRED: "Term deferred",
  GRADUATED: "Graduation",
  PENDING: "Pending admin approval",
  APPROVED: "Approved",
  REJECTED: "Rejected",
};

const StudentStatusRequestPanel = ({ label }: { label?: string }) => {
  const student = useRecordContext<{ id: number; status: string }>();
  const notify = useNotify();
  const [requests, setRequests] = useState<StatusRequest[]>([]);

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) return;

    fetch("http://localhost:8080/api/v1/me/status-requests", {
      headers: { Authorization: `Bearer ${token}` },
    })
      .then((response) => (response.ok ? response.json() : []))
      .then((data) => setRequests(data))
      .catch((error) =>
        console.error("Could not load status requests:", error),
      );
  }, [student?.id]);

  const pending = requests.find((request) => request.status === "PENDING");
  const latest = requests[0];
  const canRequest = student?.status === "ACTIVE" && !pending;

  const submitRequest = async (
    requestedStatus: string,
    requestLabel: string,
  ) => {
    if (
      !window.confirm(
        `Send a request for ${requestLabel}? It will take effect only after admin approval.`,
      )
    ) {
      return;
    }

    const token = localStorage.getItem("token");
    try {
      const response = await fetch(
        "http://localhost:8080/api/v1/me/status-requests",
        {
          method: "POST",
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ requestedStatus }),
        },
      );
      const text = await response.text();
      let result: any = text;
      try {
        result = JSON.parse(text);
      } catch {
        // The API returns plain text for validation errors.
      }

      if (!response.ok) {
        notify(
          typeof result === "string" ? result : "Could not submit request",
          { type: "error" },
        );
        return;
      }

      setRequests((current) => [result, ...current]);
      notify("Request sent. It will take effect after admin approval.", {
        type: "success",
      });
    } catch (error) {
      console.error("Could not submit status request:", error);
      notify("Could not connect to Students Service", { type: "error" });
    }
  };

  return (
    <Stack aria-label={label} spacing={1}>
      {latest && (
        <span>
          Latest request:{" "}
          {statusLabels[latest.requestedStatus] ?? latest.requestedStatus} —{" "}
          {statusLabels[latest.status] ?? latest.status}
        </span>
      )}
      {student?.status === "ACTIVE" &&
        statusOptions.map((option) => (
          <Button
            key={option.value}
            label={option.label}
            disabled={!canRequest}
            onClick={() => submitRequest(option.value, option.label)}
          />
        ))}
      {pending && <span>A request is waiting for admin approval.</span>}
    </Stack>
  );
};

export const StudentProfile = () => (
  <List title="My profile" actions={false} pagination={false} perPage={1}>
    <Datagrid rowClick={false} bulkActionButtons={false}>
      <TextField source="firstName" />
      <TextField source="lastName" />
      <TextField source="email" />
      <TextField source="major" />
      <StudentStatusField label="Current status" />
      <StudentStatusRequestPanel label="Request a status change" />
    </Datagrid>
  </List>
);
