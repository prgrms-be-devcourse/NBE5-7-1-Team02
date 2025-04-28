import { fetchPatch } from "..";
import { ShipOrderResponse } from "../../params/order/shipOrder";

export const shipOrder = (id: number) =>
    fetchPatch<ShipOrderResponse>(`/orders/${id}/ship`);
