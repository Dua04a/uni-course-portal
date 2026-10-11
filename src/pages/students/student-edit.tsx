import { Edit, SimpleForm, TextInput } from "react-admin";

export const StudentEdit = () => (
  <Edit>
    <SimpleForm>
      <TextInput source="firstName" />
      <TextInput source="lastName" />
      <TextInput source="email" />
      <TextInput source="major" />
    </SimpleForm>
  </Edit>
);
