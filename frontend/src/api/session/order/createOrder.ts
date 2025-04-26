import { fetchPost } from "..";
import {
    CreateOrderRequest,
    CreateOrderResponse,
} from "../../params/order/createOrder";

export const createOrder = (data: CreateOrderRequest) =>
    fetchPost<CreateOrderResponse>("/orders", data);
