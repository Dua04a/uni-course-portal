import { Datagrid, List, NumberField, TextField } from "react-admin";

export const MyCourses = () => (
  <List title="My courses" resource="myCourses" pagination={false}>
    <Datagrid bulkActionButtons={false}>
      <NumberField source="courseId" label="Course ID" />
      <TextField source="courseName" label="Course name" />
      <TextField source="courseCode" label="Course code" />
      <TextField source="status" />
    </Datagrid>
  </List>
);
