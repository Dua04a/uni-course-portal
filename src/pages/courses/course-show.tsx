import {
  Show,
  SimpleShowLayout,
  NumberField,
  TextField,
  useShowController,
  useDataProvider,
} from "react-admin";
import { useEffect, useState } from "react";

const CourseShow = () => {
  const { record } = useShowController();
  const dataProvider = useDataProvider();
  const [students, setStudents] = useState<any[]>([]);

  useEffect(() => {
    if (!record?.id) return;
    dataProvider
      .getList("courseStudents", {
        pagination: { page: 1, perPage: 100 },
        sort: { field: "id", order: "ASC" },
        filter: { courseId: record.id },
      })
      .then(({ data }) => setStudents(data));
  }, [record?.id, dataProvider]);

  return (
    <Show>
      <SimpleShowLayout>
        <NumberField source="id" />
        <TextField source="courseName" label="Course name" />
        <TextField source="courseCode" label="Course code" />
        <TextField source="instructorName" label="Instructor name" />
        <TextField source="courseDescription" label="Description" />
        <NumberField source="seatLimit" label="Seat limit" />
        <NumberField source="enrolledCount" label="Enrolled" />
        <h3>Registered Students</h3>
        {students.map((student) => (
          <div key={student.id}>{student.studentName}</div>
        ))}
      </SimpleShowLayout>
    </Show>
  );
};

export default CourseShow;
