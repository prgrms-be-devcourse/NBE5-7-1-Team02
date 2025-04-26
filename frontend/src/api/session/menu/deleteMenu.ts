import { fetchDelete } from "..";

export const deleteMenu = (id: number) => fetchDelete(`/menus/${id}`);
