import { GetOrderResponse } from "./getOrderById";

export interface GetOrdersResponse {
    from: Date;
    to: Date;
    orders: GetOrderResponse[];
}
