import { fetchGet } from "..";
import { GetOrdersResponse } from "../../params/order/getOrders";

export const getOrders = () => fetchGet<GetOrdersResponse[]>(`/orders`);
