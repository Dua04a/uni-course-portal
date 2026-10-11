import {
  Avatar,
  Box,
  Button,
  Card,
  CardContent,
  Chip,
  CircularProgress,
  Divider,
  LinearProgress,
  Stack,
  Typography,
} from "@mui/material";
import ArrowForwardRoundedIcon from "@mui/icons-material/ArrowForwardRounded";
import AutoStoriesRoundedIcon from "@mui/icons-material/AutoStoriesRounded";
import Groups2RoundedIcon from "@mui/icons-material/Groups2Rounded";
import HowToRegRoundedIcon from "@mui/icons-material/HowToRegRounded";
import SchoolRoundedIcon from "@mui/icons-material/SchoolRounded";
import { Link as RouterLink } from "react-router-dom";
import { useGetList, usePermissions, useRefresh } from "react-admin";
import { useTheme } from "@mui/material/styles";

type Course = {
  id: number | string;
  courseName?: string;
  courseCode?: string;
  instructorName?: string;
  seatLimit?: number;
  enrolledCount?: number;
};

const surfaceSx = (isDark: boolean) => ({
  border: "1px solid",
  borderColor: isDark ? "#343a46" : "#e8ebf0",
  borderRadius: 3,
  boxShadow: isDark ? "0 8px 24px rgba(0, 0, 0, 0.14)" : "0 8px 24px rgba(22, 34, 51, 0.04)",
  bgcolor: isDark ? "#222630" : "#fff",
});

const StatCard = ({
  label,
  value,
  caption,
  icon,
  tint,
  loading,
  error,
  isDark,
}: {
  label: string;
  value: number | string;
  caption: string;
  icon: React.ReactNode;
  tint: string;
  loading?: boolean;
  error?: boolean;
  isDark: boolean;
}) => (
  <Card sx={{ ...surfaceSx(isDark), height: "100%" }}>
    <CardContent sx={{ p: 2.5, "&:last-child": { pb: 2.5 } }}>
      <Stack direction="row" justifyContent="space-between" alignItems="flex-start">
        <Box>
          <Typography variant="body2" color="text.secondary" fontWeight={600}>
            {label}
          </Typography>
          <Typography variant="h4" fontWeight={750} sx={{ mt: 1, letterSpacing: "-0.04em" }}>
            {loading ? <CircularProgress size={25} /> : error ? "—" : value}
          </Typography>
        </Box>
        <Avatar sx={{ bgcolor: tint, color: "#25324a", width: 44, height: 44 }}>{icon}</Avatar>
      </Stack>
      <Typography variant="caption" color="text.secondary" sx={{ display: "block", mt: 1.5 }}>
        {error ? "Could not connect to the service" : caption}
      </Typography>
    </CardContent>
  </Card>
);

const CourseRow = ({ course }: { course: Course }) => {
  const limit = course.seatLimit ?? 30;
  const enrolled = course.enrolledCount ?? 0;
  const fill = limit > 0 ? Math.min((enrolled / limit) * 100, 100) : 0;

  return (
    <Stack
      direction={{ xs: "column", sm: "row" }}
      alignItems={{ xs: "stretch", sm: "center" }}
      spacing={2}
      sx={{ py: 2 }}
    >
      <Avatar variant="rounded" sx={{ width: 44, height: 44, bgcolor: "#eef2ff", color: "#4857bb" }}>
        <AutoStoriesRoundedIcon />
      </Avatar>
      <Box sx={{ flex: 1, minWidth: 0 }}>
        <Stack direction="row" spacing={1} alignItems="center" flexWrap="wrap">
          <Typography fontWeight={700}>{course.courseName || "Untitled course"}</Typography>
          {course.courseCode && <Chip size="small" label={course.courseCode} sx={{ bgcolor: "#f4f5f8", fontWeight: 600 }} />}
        </Stack>
        <Typography variant="body2" color="text.secondary" sx={{ mt: 0.35 }}>
          {course.instructorName || "Instructor not assigned"}
        </Typography>
      </Box>
      <Box sx={{ width: { xs: "100%", sm: 155 } }}>
        <Stack direction="row" justifyContent="space-between" sx={{ mb: 0.75 }}>
          <Typography variant="caption" color="text.secondary">Enrolled</Typography>
          <Typography variant="caption" fontWeight={700}>{enrolled} / {limit}</Typography>
        </Stack>
        <LinearProgress
          variant="determinate"
          value={fill}
          sx={{ height: 7, borderRadius: 9, bgcolor: "#edf0f5", "& .MuiLinearProgress-bar": { borderRadius: 9, bgcolor: fill >= 90 ? "#e79b4a" : "#5968c8" } }}
        />
      </Box>
    </Stack>
  );
};

export const Homepage = () => {
  const { permissions } = usePermissions();
  return <HomepageContent isAdmin={permissions === "ADMIN"} />;
};

const HomepageContent = ({ isAdmin }: { isAdmin: boolean }) => {
  const refresh = useRefresh();
  const theme = useTheme();
  const isDark = theme.palette.mode === "dark";
  const { data: courses, total: courseTotal, isPending: coursesPending, isError: coursesError } = useGetList<Course>("courses", {
    pagination: { page: 1, perPage: 5 },
    sort: { field: "id", order: "DESC" },
    filter: {},
  });
  const { total: studentTotal, isPending: studentsPending, isError: studentsError } = useGetList("students", {
    pagination: { page: 1, perPage: 1 },
    sort: { field: "id", order: "DESC" },
    filter: {},
  }, { enabled: isAdmin });

  const enrolledTotal = (courses ?? []).reduce((sum, course) => sum + (course.enrolledCount ?? 0), 0);
  const displayName = localStorage.getItem("username") || (isAdmin ? "Administrator" : "Student");

  return (
    <Box sx={{ minHeight: "100%", bgcolor: isDark ? "#171a21" : "#f7f8fc", color: isDark ? "#f3f4f6" : "#1d2636", px: { xs: 2, md: 4 }, py: { xs: 3, md: 4 } }}>
      <Box sx={{ maxWidth: 1240, mx: "auto" }}>
        <Stack direction={{ xs: "column", sm: "row" }} justifyContent="space-between" alignItems={{ xs: "stretch", sm: "center" }} spacing={2} sx={{ mb: 3.5 }}>
          <Box>
            <Typography variant="overline" sx={{ color: "#5968c8", fontWeight: 800, letterSpacing: ".12em" }}>
              LEARNING PORTAL
            </Typography>
            <Typography variant="h4" fontWeight={800} sx={{ letterSpacing: "-0.04em", mt: 0.25 }}>
              {isAdmin ? "Good to see you," : "Welcome back,"} {displayName}
            </Typography>
            <Typography color="text.secondary" sx={{ mt: 0.75 }}>
              {isAdmin ? "Here’s what’s happening across your learning community." : "Pick up where you left off or explore a new course."}
            </Typography>
          </Box>
          <Button
            component={RouterLink}
            to={isAdmin ? "/courses/create" : "/courses"}
            variant="contained"
            endIcon={<ArrowForwardRoundedIcon />}
            sx={{ alignSelf: { xs: "flex-start", sm: "center" }, px: 2.25, py: 1.15, borderRadius: 2, textTransform: "none", fontWeight: 700, boxShadow: "none", bgcolor: "#4f5fbd", "&:hover": { bgcolor: "#3e4da7", boxShadow: "none" } }}
          >
            {isAdmin ? "Create a course" : "Browse courses"}
          </Button>
        </Stack>

        <Box sx={{ display: "grid", gridTemplateColumns: { xs: "1fr", sm: "repeat(2, 1fr)", lg: "repeat(3, 1fr)" }, gap: 2, mb: 3 }}>
          <StatCard label="Courses" value={courseTotal ?? 0} caption="Available in the catalog" icon={<SchoolRoundedIcon />} tint="#eef2ff" loading={coursesPending} error={coursesError} isDark={isDark} />
          {isAdmin ? (
            <StatCard label="Students" value={studentTotal ?? 0} caption="Learners in your community" icon={<Groups2RoundedIcon />} tint="#e8f7f1" loading={studentsPending} error={studentsError} isDark={isDark} />
          ) : (
            <StatCard label="My learning" value="Ready when you are" caption="Your course registrations live here" icon={<HowToRegRoundedIcon />} tint="#e8f7f1" isDark={isDark} />
          )}
          <StatCard label={isAdmin ? "Active enrollments" : "Courses to explore"} value={isAdmin ? enrolledTotal : (courseTotal ?? 0)} caption={isAdmin ? "Across the courses shown below" : "Find a course that fits your goals"} icon={<AutoStoriesRoundedIcon />} tint="#fff3e5" loading={isAdmin && coursesPending} error={coursesError} isDark={isDark} />
        </Box>

        <Card sx={surfaceSx(isDark)}>
          <CardContent sx={{ p: { xs: 2, md: 3 }, "&:last-child": { pb: { xs: 2, md: 3 } } }}>
            <Stack direction={{ xs: "column", sm: "row" }} justifyContent="space-between" alignItems={{ xs: "flex-start", sm: "center" }} spacing={1} sx={{ mb: 1 }}>
              <Box>
                <Typography variant="h6" fontWeight={750}>Course overview</Typography>
                <Typography variant="body2" color="text.secondary" sx={{ mt: 0.4 }}>{isAdmin ? "A quick look at enrollment and capacity." : "A few courses from the catalog to get you started."}</Typography>
              </Box>
              <Button component={RouterLink} to="/courses" endIcon={<ArrowForwardRoundedIcon />} sx={{ textTransform: "none", fontWeight: 700 }}>View all courses</Button>
            </Stack>
            <Divider />
            {coursesPending ? (
              <Box sx={{ py: 5, display: "flex", justifyContent: "center" }}><CircularProgress size={28} /></Box>
            ) : coursesError ? (
              <Box sx={{ py: 5, textAlign: "center" }}>
                <Avatar sx={{ mx: "auto", mb: 1.5, bgcolor: "#fff3e5", color: "#a56725" }}><SchoolRoundedIcon /></Avatar>
                <Typography fontWeight={700}>Course service unavailable</Typography>
                <Typography variant="body2" color="text.secondary" sx={{ mt: 0.5, mb: 2 }}>
                  Start the course and student services, then try again.
                </Typography>
                <Button onClick={refresh} variant="outlined" sx={{ textTransform: "none", borderRadius: 2 }}>Retry</Button>
              </Box>
            ) : courses?.length ? (
              courses.map((course, index) => (
                <Box key={course.id}>
                  <CourseRow course={course} />
                  {index < courses.length - 1 && <Divider />}
                </Box>
              ))
            ) : (
              <Box sx={{ py: 5, textAlign: "center" }}>
                <Avatar sx={{ mx: "auto", mb: 1.5, bgcolor: "#eef2ff", color: "#5968c8" }}><AutoStoriesRoundedIcon /></Avatar>
                <Typography fontWeight={700}>No courses yet</Typography>
                <Typography variant="body2" color="text.secondary" sx={{ mt: 0.5 }}>Courses will appear here once they’re added to the catalog.</Typography>
              </Box>
            )}
          </CardContent>
        </Card>
        <Typography variant="caption" color="text.secondary" sx={{ display: "block", textAlign: "center", mt: 3 }}>
          Learning Portal · Keep growing, one course at a time
        </Typography>
      </Box>
    </Box>
  );
};
