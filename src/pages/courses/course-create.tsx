import {
  Create,
  NumberInput,
  SimpleForm,
  TextInput,
  minValue,
  required,
} from "react-admin";

const CourseCreate = () => (
  <Create>
    <SimpleForm>
      <TextInput source="courseName" label="Course name" />
      <TextInput source="courseCode" label="Course code" />
      <TextInput source="courseDescription" label="Description" multiline />
      <TextInput source="instructorName" label="Instructor name" />
      <NumberInput
        source="seatLimit"
        label="Seat limit"
        defaultValue={30}
        validate={[required(), minValue(1)]}
      />
    </SimpleForm>
  </Create>
);

export default CourseCreate;
