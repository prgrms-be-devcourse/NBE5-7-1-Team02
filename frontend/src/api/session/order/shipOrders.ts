import { fetchPatch } from "..";
import { ShipOrderResponse } from "../../params/order/shipOrder";
import { ShipOrdersRequest } from "../../params/order/shipOrders";

export const shipOrders = (data: ShipOrdersRequest) =>
    fetchPatch<ShipOrderResponse>(`/orders/ship`, data);
