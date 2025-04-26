import { Menu } from "@/lib/types/menu";
import { fetchGet } from "..";

export async function getMenus(): Promise<Menu[]> {
    const res = await fetchGet<{ menus: Menu[] }>("/menus");

    return res.menus;
}
