import {
  Create,
  PasswordInput,
  SelectInput,
  SimpleForm,
  TextInput,
} from "react-admin";
import { useWatch } from "react-hook-form";

const StudentFields = () => {
  const role = useWatch({ name: "role", defaultValue: "STUDENT" });

  if (role !== "STUDENT") {
    return null;
  }

  return (
    <>
      <TextInput source="firstName" label="First name" />
      <TextInput source="lastName" label="Last name" />
      <TextInput source="email" label="Email" />
      <TextInput source="major" label="Major" />
    </>
  );
};

export const AccountCreate = () => (
  <Create redirect={false}>
    <SimpleForm shouldUnregister>
      <TextInput source="username" />
      <PasswordInput source="password" />
      <SelectInput
        source="role"
        defaultValue="STUDENT"
        choices={[
          { id: "STUDENT", name: "Student" },
          { id: "ADMIN", name: "Admin" },
        ]}
      />
      <StudentFields />
    </SimpleForm>
  </Create>
);
