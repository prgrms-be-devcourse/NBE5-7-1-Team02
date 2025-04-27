import { fetchPost } from "..";
import { CreateMenuRequest } from "../../params/menu/createMenu";

export const createMenu = (req: CreateMenuRequest) => {
    return fetchPost("/menus", req);
};
