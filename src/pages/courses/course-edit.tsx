import {
  Edit,
  NumberInput,
  SimpleForm,
  TextInput,
  minValue,
  required,
} from "react-admin";

const CourseEdit = () => (
  <Edit>
    <SimpleForm>
      <TextInput source="courseName" label="Course name" />
      <TextInput source="courseCode" label="Course code" />
      <TextInput source="courseDescription" label="Description" multiline />
      <TextInput source="instructorName" label="Instructor name" />
      <NumberInput
        source="seatLimit"
        label="Seat limit"
        validate={[required(), minValue(1)]}
      />
    </SimpleForm>
  </Edit>
);

export default CourseEdit;
