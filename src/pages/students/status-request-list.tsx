import {
  Button,
  Datagrid,
  FunctionField,
  List,
  NumberField,
  TextField,
  useNotify,
  useRecordContext,
  useRefresh,
} from "react-admin";

const statusLabels: Record<string, string> = {
  WITHDRAWN_FROM_UNIVERSITY: "Withdraw from university",
  TERM_DEFERRED: "Defer term",
  GRADUATED: "Graduation",
};

const ReviewButtons = () => {
  const request = useRecordContext<{ id: number; studentName: string }>();
  const notify = useNotify();
  const refresh = useRefresh();

  const review = async (decision: "approve" | "reject") => {
    if (!request) return;
    if (
      decision === "approve" &&
      !window.confirm(
        `Approve the request for ${request.studentName}? The student will be removed from all active courses.`,
      )
    ) {
      return;
    }

    const token = localStorage.getItem("token");
    try {
      const response = await fetch(
        `http://localhost:8080/api/v1/admin/status-requests/${request.id}/${decision}`,
        {
          method: "PATCH",
          headers: { Authorization: `Bearer ${token}` },
        },
      );
      const message = await response.text();
      if (!response.ok) {
        notify(message || `Could not ${decision} request`, { type: "error" });
        return;
      }

      notify(decision === "approve" ? "Request approved" : "Request rejected", {
        type: "success",
      });
      refresh();
    } catch (error) {
      console.error("Could not review status request:", error);
      notify("Could not connect to Students Service", { type: "error" });
    }
  };

  return (
    <>
      <Button label="Approve" onClick={() => review("approve")} />
      <Button label="Reject" onClick={() => review("reject")} />
    </>
  );
};

export const StatusRequestList = () => (
  <List title="Pending status requests" queryOptions={{ staleTime: 0 }}>
    <Datagrid rowClick={false} bulkActionButtons={false}>
      <NumberField source="id" />
      <TextField source="studentName" label="Student" />
      <FunctionField
        source="requestedStatus"
        label="Requested status"
        render={(record) =>
          statusLabels[record.requestedStatus] ?? record.requestedStatus
        }
      />
      <ReviewButtons />
    </Datagrid>
  </List>
);
