import {
  Admin,
  Menu,
  MenuItemLink,
  Resource,
  usePermissions,
} from "react-admin";
import type { ComponentType } from "react";
import ArticleIcon from "@mui/icons-material/Article";
import SchoolIcon from "@mui/icons-material/School";
import PeopleIcon from "@mui/icons-material/People";
import PersonAddIcon from "@mui/icons-material/PersonAdd";
import AdminPanelSettingsIcon from "@mui/icons-material/AdminPanelSettings";
import AssignmentTurnedInIcon from "@mui/icons-material/AssignmentTurnedIn";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import { Box, Typography } from "@mui/material";

import { Layout } from "./Layout";
import { authProvider } from "./authProvider";
import { dataProvider } from "./dataProvider";
import { Homepage } from "./pages/homepage";
import CourseCreate from "./pages/courses/course-create";
import CourseEdit from "./pages/courses/course-edit";
import CourseList from "./pages/courses/course-list";
import CourseShow from "./pages/courses/course-show";
import { StudentList } from "./pages/students/student-list";
import { StudentShow } from "./pages/students/student-show";
import { StudentEdit } from "./pages/students/student-edit";
import { AccountCreate } from "./pages/users/account-create";
import { AdminList } from "./pages/users/admin-list";
import { StatusRequestList } from "./pages/students/status-request-list";
import { StudentProfile } from "./pages/students/student-profile";
import { MyCourses } from "./pages/courses/my-courses";
import { createPortalTheme } from "./theme";

const AppMenu = () => {
  const { permissions } = usePermissions();
  const isAdmin = permissions === "ADMIN";
  return (
    <Menu>
      <Box sx={{ px: 1.5, pt: 0.5, pb: 1.25 }}>
        <Typography variant="overline" color="text.secondary" fontWeight={800} letterSpacing=".12em">WORKSPACE</Typography>
      </Box>
      <MenuItemLink to="/" primaryText="Home" leftIcon={<ArticleIcon />} />
      <MenuItemLink
        to="/courses"
        primaryText="Courses"
        leftIcon={<SchoolIcon />}
      />
      {isAdmin && (
        <Box sx={{ px: 1.5, pt: 2, pb: 0.75 }}>
          <Typography variant="overline" color="text.secondary" fontWeight={800} letterSpacing=".12em">ADMINISTRATION</Typography>
        </Box>
      )}
      {isAdmin && (
        <MenuItemLink
          to="/students"
          primaryText="Students"
          leftIcon={<PeopleIcon />}
        />
      )}
      {isAdmin && (
        <MenuItemLink
          to="/admins"
          primaryText="Secondary admins"
          leftIcon={<AdminPanelSettingsIcon />}
        />
      )}
      {isAdmin && (
        <MenuItemLink
          to="/statusRequests"
          primaryText="Status requests"
          leftIcon={<AssignmentTurnedInIcon />}
        />
      )}
      {isAdmin && (
        <MenuItemLink
          to="/users/create"
          primaryText="Create account"
          leftIcon={<PersonAddIcon />}
        />
      )}
      {!isAdmin && (
        <Box sx={{ px: 1.5, pt: 2, pb: 0.75 }}>
          <Typography variant="overline" color="text.secondary" fontWeight={800} letterSpacing=".12em">MY LEARNING</Typography>
        </Box>
      )}
      {!isAdmin && (
        <MenuItemLink
          to="/myCourses"
          primaryText="My courses"
          leftIcon={<SchoolIcon />}
        />
      )}
      {!isAdmin && (
        <MenuItemLink
          to="/myProfile"
          primaryText="My profile"
          leftIcon={<AccountCircleIcon />}
        />
      )}
    </Menu>
  );
};

const AdminPage = ({ page: Page }: { page: ComponentType<any> }) => {
  const { permissions } = usePermissions();
  return permissions === "ADMIN" ? <Page /> : null;
};

const AdminCourseCreate = () => <AdminPage page={CourseCreate} />;
const AdminCourseEdit = () => <AdminPage page={CourseEdit} />;
const AdminCourseShow = () => <AdminPage page={CourseShow} />;
const AdminStudentList = () => <AdminPage page={StudentList} />;
const AdminStudentShow = () => <AdminPage page={StudentShow} />;
const AdminStudentEdit = () => <AdminPage page={StudentEdit} />;
const AdminAccountCreate = () => <AdminPage page={AccountCreate} />;
const AdminAccountList = () => <AdminPage page={AdminList} />;
const AdminStatusRequestList = () => <AdminPage page={StatusRequestList} />;
const AppLayout = (props: any) => <Layout {...props} menu={AppMenu} />;

export const App = () => (
  <Admin
    layout={AppLayout}
    dataProvider={dataProvider}
    dashboard={Homepage}
    authProvider={authProvider}
    theme={createPortalTheme("light")}
    darkTheme={createPortalTheme("dark")}
  >
    <Resource
      name="courses"
      icon={ArticleIcon}
      list={CourseList}
      show={AdminCourseShow}
      create={AdminCourseCreate}
      edit={AdminCourseEdit}
    />
    <Resource
      name="students"
      icon={PeopleIcon}
      list={AdminStudentList}
      show={AdminStudentShow}
      edit={AdminStudentEdit}
    />
    <Resource name="users" icon={PersonAddIcon} create={AdminAccountCreate} />
    <Resource
      name="admins"
      icon={AdminPanelSettingsIcon}
      list={AdminAccountList}
    />
    <Resource
      name="statusRequests"
      icon={AssignmentTurnedInIcon}
      list={AdminStatusRequestList}
    />
    <Resource name="myCourses" icon={SchoolIcon} list={MyCourses} />
    <Resource name="myProfile" icon={AccountCircleIcon} list={StudentProfile} />
  </Admin>
);
