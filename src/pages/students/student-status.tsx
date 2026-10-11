import { useRecordContext } from "react-admin";
import { Chip, Stack } from "@mui/material";

const statusLabels: Record<string, string> = {
  ACTIVE: "Active",
  WITHDRAWN_FROM_UNIVERSITY: "Withdrawn",
  TERM_DEFERRED: "Term deferred",
  GRADUATED: "Graduated",
};

export const StudentStatusField = ({ label }: { label?: string }) => {
  const record = useRecordContext<{ status: string }>();
  return (
    <span aria-label={label}>
      {statusLabels[record?.status ?? ""] ?? record?.status}
    </span>
  );
};

export const StudentNameWithStatus = ({ label }: { label?: string }) => {
  const student = useRecordContext<{
    firstName: string;
    lastName: string;
    status: string;
  }>();
  if (!student) return null;

  return (
    <Stack direction="row" spacing={1} alignItems="center" aria-label={label}>
      <span>
        {student.firstName} {student.lastName}
      </span>
      <Chip
        size="small"
        label={statusLabels[student.status] ?? student.status}
        color={student.status === "ACTIVE" ? "default" : "primary"}
      />
    </Stack>
  );
};
