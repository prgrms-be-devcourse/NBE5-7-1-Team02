import { OrderStatus } from "../../../lib/enums/OrderStatus";
import { OrderMenu } from "../../../lib/types/orderMenu";

export interface GetOrderRequest {
    id: number;
    email: string;
}

export interface GetOrderResponse {
    id: number;
    email: string;
    address: string;
    zipCode: string;
    status: OrderStatus;
    totalPrice: number;
    menus: OrderMenu[];
    createdAt: Date;
}
