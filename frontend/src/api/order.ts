import { CartItem } from "@/types";
import { fetchPost } from "./index";

// 주문
export const createOrder = (data: {
    email: string;
    address: string;
    zipCode: string;
    items: CartItem[];
}) => fetchPost("/orders", data);
