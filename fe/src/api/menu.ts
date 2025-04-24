import { Menu } from "@/types/menu";
import { fetchPost, fetchPut, fetchDelete, fetchGet } from ".";

// 모든 메뉴 가져오기
export const getMenus = () => fetchGet<Menu[]>("/menus");

// 메뉴 생성
export const createMenu = (data: {
    name: string;
    price: number;
    category: string;
    imageId: number;
}) => fetchPost("/menus", data);

// 메뉴 수정
export const updateMenu = (id: number, data: unknown) =>
    fetchPut(`/menus/${id}`, data);

// 메뉴 삭제
export const deleteMenu = (id: number) => fetchDelete(`/menus/${id}`);
