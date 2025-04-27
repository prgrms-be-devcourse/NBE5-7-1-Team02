import { fetchPut } from "..";
import { UpdateMenuRequest } from "../../params/menu/updateMenu";

export const updateMenu = (id: number, data: UpdateMenuRequest) =>
    fetchPut(`/menus/${id}`, data);
