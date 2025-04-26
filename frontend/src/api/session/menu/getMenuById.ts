import { fetchGet } from "..";
import { Menu } from "../../../lib/types";

export const getMenuById = (id: number) => fetchGet<Menu>(`/menus/${id}`);
