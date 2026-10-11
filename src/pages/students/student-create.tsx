import { Create, SimpleForm, TextInput, SelectInput } from "react-admin";

export const StudentCreate = () => (
  <Create>
    <SimpleForm>
      <TextInput source="firstName" label="First Name" />
      <TextInput source="lastName" label="Last Name" />
      <TextInput source="email" label="Email" />
      <TextInput source="major" label="Major" />
      <SelectInput
        source="status"
        label="Status"
        choices={[
          { id: "ACTIVE", name: "Active" },
          { id: "INACTIVE", name: "Inactive" },
        ]}
      />
    </SimpleForm>
  </Create>
);
