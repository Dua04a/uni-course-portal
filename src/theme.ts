import { createTheme, type PaletteMode } from "@mui/material/styles";

export const createPortalTheme = (mode: PaletteMode) => {
  const dark = mode === "dark";
  const primary = dark ? "#9ba9ff" : "#4556b8";
  const background = dark ? "#171a22" : "#f4f6fb";
  const paper = dark ? "#222631" : "#ffffff";
  const border = dark ? "#343a48" : "#e5e9f2";

  return createTheme({
    palette: {
      mode,
      primary: { main: primary, contrastText: dark ? "#1b2030" : "#fff" },
      secondary: { main: dark ? "#79cdb4" : "#198b72" },
      background: { default: background, paper },
      divider: border,
      text: { primary: dark ? "#f0f2f7" : "#20283a", secondary: dark ? "#a9b0c0" : "#6b7487" },
    },
    shape: { borderRadius: 12 },
    typography: {
      fontFamily: 'Inter, "Segoe UI", Arial, sans-serif',
      h4: { fontWeight: 750, letterSpacing: "-0.035em" },
      h5: { fontWeight: 700, letterSpacing: "-0.025em" },
      h6: { fontWeight: 700 },
      button: { fontWeight: 650, textTransform: "none" },
    },
    components: {
      MuiCssBaseline: {
        styleOverrides: {
          body: { backgroundColor: background },
          "*::-webkit-scrollbar": { width: 8, height: 8 },
          "*::-webkit-scrollbar-thumb": { background: dark ? "#454c5d" : "#cbd1de", borderRadius: 8 },
        },
      },
      MuiButton: {
        styleOverrides: {
          root: { borderRadius: 10, paddingInline: 16 },
          contained: { boxShadow: "none", "&:hover": { boxShadow: "none" } },
        },
      },
      MuiCard: { styleOverrides: { root: { border: `1px solid ${border}`, boxShadow: dark ? "0 10px 28px rgba(0,0,0,.12)" : "0 10px 28px rgba(28,39,67,.045)" } } },
      MuiPaper: { styleOverrides: { root: { backgroundImage: "none" } } },
      MuiTableCell: {
        styleOverrides: {
          head: { color: dark ? "#c6ccda" : "#667087", fontWeight: 700, backgroundColor: dark ? "#292e3a" : "#f7f8fc", borderBottomColor: border },
          body: { borderBottomColor: border },
        },
      },
      MuiOutlinedInput: { styleOverrides: { root: { borderRadius: 10 } } },
      MuiTextField: { defaultProps: { fullWidth: true, size: "small", margin: "none", variant: "outlined" } },
      MuiFormControl: { styleOverrides: { root: { width: "100%", maxWidth: "100%" } } },
      MuiInputBase: { styleOverrides: { root: { borderRadius: 10, minHeight: 50 }, input: { fontSize: "0.98rem" } } },
      MuiInputLabel: { styleOverrides: { root: { fontWeight: 550 } } },
      MuiFormHelperText: { styleOverrides: { root: { marginLeft: 2, marginTop: 6 } } },
      RaSimpleForm: {
        styleOverrides: {
          root: {
            width: "100%",
            maxWidth: 1080,
            marginInline: "auto",
            padding: "32px !important",
            "& > .MuiStack-root": {
              display: "grid",
              width: "100%",
              gridTemplateColumns: "repeat(auto-fit, minmax(min(100%, 340px), 1fr))",
              gap: "20px 24px",
              alignItems: "start !important",
            },
            "& .MuiFormControl-root": { width: "100%", maxWidth: "100%", margin: 0 },
            "& .MuiInputBase-root": { minHeight: 54 },
          },
        },
      },
      RaLayout: { styleOverrides: { content: { backgroundColor: background } } },
      RaAppBar: {
        styleOverrides: {
          appBar: { backgroundColor: paper, color: dark ? "#f0f2f7" : "#20283a", boxShadow: "none", borderBottom: `1px solid ${border}` },
          toolbar: { minHeight: "72px !important", paddingInline: "20px !important" },
          menuButton: { marginRight: 12 },
        },
      },
      RaSidebar: { styleOverrides: { paper: { backgroundColor: paper, borderRight: `1px solid ${border}`, padding: "14px 12px" } } },
      RaMenuItemLink: {
        styleOverrides: {
          root: {
            minHeight: 46,
            margin: "3px 0",
            borderRadius: 10,
            color: dark ? "#c2c8d5" : "#596276",
            transition: "background-color .18s ease, color .18s ease",
            "&:hover": { backgroundColor: dark ? "#2e3442" : "#f0f2fa" },
            "&.RaMenuItemLink-active": { color: primary, backgroundColor: dark ? "rgba(155,169,255,.13)" : "#edf0ff", fontWeight: 700 },
            "& .RaMenuItemLink-icon": { minWidth: 40, color: "inherit" },
          },
        },
      },
    },
  });
};
