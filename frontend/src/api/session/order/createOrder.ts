import { fetchPost } from "..";
import { CreateOrderRequest } from "../../params/order/createOrder";
import { GetOrderResponse } from "../../params/order/getOrderById";

export const createOrder = (data: CreateOrderRequest) =>
    fetchPost<GetOrderResponse>("/orders", data);
