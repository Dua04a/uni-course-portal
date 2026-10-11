import {
  List,
  Datagrid,
  TextField,
  DeleteButton,
  useListContext,
  useRecordContext,
} from "react-admin";
import { StudentNameWithStatus } from "./student-status";

const RowNumber = ({ label }: { label?: string }) => {
  const record = useRecordContext<{ id: number }>();
  const {
    data = {},
    page = 1,
    perPage = 10,
  } = useListContext<{ id: number }>();
  const students = Object.values(data);
  const rowIndex = students.findIndex((student) => student.id === record?.id);

  return <span aria-label={label}>{(page - 1) * perPage + rowIndex + 1}</span>;
};

export const StudentList = () => (
  <List>
    <Datagrid rowClick="show">
      <RowNumber label="#" />
      <StudentNameWithStatus label="Student" />
      <TextField source="email" />
      <TextField source="major" />

      <DeleteButton />
    </Datagrid>
  </List>
);
