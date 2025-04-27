import { fetchGet } from "..";
import {
    GetOrderRequest as GetOrderByIdRequest,
    GetOrderResponse as GetOrderByIdResponse,
} from "../../params/order/getOrderById";

export const getOrderById = ({ id, email }: GetOrderByIdRequest) =>
    fetchGet<GetOrderByIdResponse>(`/orders/${id}?email=${email}`);
