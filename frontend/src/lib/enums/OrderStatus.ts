export enum OrderStatus {
    PENDING = "PENDING",
    SHIPPED = "SHIPPED",
}

export const OrderStatusLabels: Record<OrderStatus, string> = {
    [OrderStatus.PENDING]: "배송대기중",
    [OrderStatus.SHIPPED]: "발송완료",
};
