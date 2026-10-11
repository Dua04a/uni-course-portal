import { Show, SimpleShowLayout, TextField, NumberField } from "react-admin";
import { StudentStatusField } from "./student-status";

export const StudentShow = () => (
  <Show>
    <SimpleShowLayout>
      <NumberField source="id" />
      <TextField source="firstName" />
      <TextField source="lastName" />
      <TextField source="email" />
      <TextField source="major" />
      <StudentStatusField label="Status" />
    </SimpleShowLayout>
  </Show>
);
