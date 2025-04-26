import { MenuItem } from "@/lib/types";
import { fetchPost } from "./index";

// 주문
export const createOrder = (data: {
    email: string;
    address: string;
    zipCode: string;
    items: MenuItem[];
}) => fetchPost("/orders", data, false);
