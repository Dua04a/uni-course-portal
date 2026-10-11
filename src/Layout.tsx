import type { ComponentType, ReactNode } from "react";
import { Avatar, Box, Chip, Stack, Typography } from "@mui/material";
import SchoolRoundedIcon from "@mui/icons-material/SchoolRounded";
import { AppBar as RAAppBar, Layout as RALayout, CheckForApplicationUpdate, usePermissions } from "react-admin";

const PortalAppBar = () => {
  const { permissions } = usePermissions();
  const isAdmin = permissions === "ADMIN";

  return (
    <RAAppBar color="default" sx={{ zIndex: (theme) => theme.zIndex.drawer + 1 }}>
      <Stack direction="row" alignItems="center" spacing={1.5} sx={{ width: "100%", minWidth: 0 }}>
        <Avatar variant="rounded" sx={{ width: 38, height: 38, bgcolor: "primary.main", color: "primary.contrastText" }}>
          <SchoolRoundedIcon fontSize="small" />
        </Avatar>
        <Box sx={{ minWidth: 0 }}>
          <Typography fontWeight={800} lineHeight={1.1} noWrap>Campus Portal</Typography>
          <Typography variant="caption" color="text.secondary" noWrap>University learning management</Typography>
        </Box>
        <Box sx={{ flex: 1 }} />
        <Chip
          size="small"
          label={isAdmin ? "Administrator" : "Student"}
          sx={{ bgcolor: "action.hover", fontWeight: 700, display: { xs: "none", sm: "inline-flex" } }}
        />
      </Stack>
    </RAAppBar>
  );
};

export const Layout = ({ children, menu }: { children: ReactNode; menu?: ComponentType<any> }) => (
  <RALayout menu={menu} appBar={PortalAppBar}>
    {children}
    <CheckForApplicationUpdate />
  </RALayout>
);
