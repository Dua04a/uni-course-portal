import {
  Datagrid,
  List,
  RefreshButton,
  TextField,
  TopToolbar,
  useListContext,
  useRecordContext,
} from "react-admin";

const RowNumber = ({ label }: { label?: string }) => {
  const record = useRecordContext<{ id: number }>();
  const {
    data = {},
    page = 1,
    perPage = 10,
  } = useListContext<{ id: number }>();
  const admins = Object.values(data);
  const rowIndex = admins.findIndex((admin) => admin.id === record?.id);

  return <span aria-label={label}>{(page - 1) * perPage + rowIndex + 1}</span>;
};

const AdminListActions = () => (
  <TopToolbar>
    <RefreshButton />
  </TopToolbar>
);

export const AdminList = () => (
  <List
    title="Secondary admins"
    actions={<AdminListActions />}
    queryOptions={{ staleTime: 0 }}
  >
    <Datagrid rowClick={false}>
      <RowNumber label="#" />
      <TextField source="username" />
      <TextField source="role" />
    </Datagrid>
  </List>
);
