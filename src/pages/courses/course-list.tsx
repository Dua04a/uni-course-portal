import {
  List,
  Datagrid,
  NumberField,
  TextField,
  EditButton,
  ShowButton,
  DeleteButton,
  Button,
  FunctionField,
  CreateButton,
  RefreshButton,
  TopToolbar,
  useNotify,
  useRefresh,
  useRecordContext,
} from "react-admin";

import HowToRegIcon from "@mui/icons-material/HowToReg";

//toolbar component
const CourseListActions = ({ isAdmin }: { isAdmin: boolean }) => (
  <TopToolbar>
    <RefreshButton />
    {isAdmin && <CreateButton />}
  </TopToolbar>
);

const RegisterButton = () => {
  const record = useRecordContext();
  const notify = useNotify();
  const refresh = useRefresh();

  const handleRegister = async () => {
    if (!record) {
      notify("Course not found", { type: "error" });
      return;
    }

    const courseId = record.id;
    const token = localStorage.getItem("token");
    if (!token) {
      notify("Not authenticated", { type: "error" });
      return;
    }

    try {
      const response = await fetch(
        `http://localhost:8080/api/v1/me/courses/${courseId}`,
        {
          method: "POST",
          headers: {
            Authorization: `Bearer ${token}`,
            Accept: "application/json",
          },
        },
      );
      const message = await response.text();
      if (!response.ok) {
        console.error("Registration failed:", response.status, message);
        notify(message || `Registration failed (${response.status})`, {
          type: "error",
        });
        return;
      }
      notify(message || "Student enrolled successfully", { type: "success" });
      refresh();
    } catch (error) {
      console.error("Registration error:", error);
      notify("Could not connect to Students Service", { type: "error" });
    }
  };

  const isFull =
    record != null && (record.enrolledCount ?? 0) >= (record.seatLimit ?? 30);

  return (
    <Button
      label={isFull ? "Full" : "Register"}
      onClick={handleRegister}
      disabled={isFull}
    >
      <HowToRegIcon />
    </Button>
  );
};

const CourseList = () => {
  const isAdmin = localStorage.getItem("role") === "ADMIN";
  return (
    <List actions={<CourseListActions isAdmin={isAdmin} />}>
      <Datagrid>
        <NumberField source="id" />
        <TextField source="courseName" label="Course name" />
        <TextField source="courseCode" label="Course code" />
        <TextField source="instructorName" label="Instructor name" />
        <TextField source="courseDescription" label="Description" />
        <NumberField source="seatLimit" label="Seat limit" />
        <NumberField source="enrolledCount" label="Enrolled" />
        <FunctionField
          label="Seats available"
          render={(record) =>
            Math.max((record.seatLimit ?? 30) - (record.enrolledCount ?? 0), 0)
          }
        />
        {isAdmin ? <ShowButton /> : null}
        {isAdmin ? <EditButton /> : null}
        {isAdmin ? <DeleteButton /> : null}
        {!isAdmin ? <RegisterButton /> : null}
      </Datagrid>
    </List>
  );
};

export default CourseList;
