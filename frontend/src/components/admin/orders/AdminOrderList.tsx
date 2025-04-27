import { GetOrdersResponse } from "../../../api/params/order/getOrders";
import { OrderStatusLabels } from "../../../lib/enums/OrderStatus";

interface Props {
    ordersData: GetOrdersResponse[];
    onShip: (orderId: number) => void;
    onBulkShip: (orderIds: number[]) => void;
}

export default function AdminOrderList({
    ordersData,
    onShip,
    onBulkShip,
}: Props) {
    return (
        <div className="space-y-8 px-30">
            {ordersData.map((dateRange, idx) => {
                const pendingOrderIds = dateRange.orders
                    .filter((order) => order.status === "PENDING")
                    .map((order) => order.id);

                return (
                    <div key={idx} className="bg-white p-6 rounded shadow">
                        {/* 날짜 범위 */}
                        <div className="flex justify-between items-center mb-4">
                            <h2 className="text-xl font-bold">
                                {formatDate(dateRange.from)} 14시 ~{" "}
                                {formatDate(dateRange.to)} 14시
                            </h2>
                            {pendingOrderIds.length > 0 && (
                                <button
                                    onClick={() => onBulkShip(pendingOrderIds)}
                                    className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
                                >
                                    배송 일괄 처리
                                </button>
                            )}
                        </div>

                        {/* 주문 리스트 */}
                        <div className="space-y-4">
                            {dateRange.orders.map((order) => (
                                <div
                                    key={order.id}
                                    className="border p-4 rounded hover:bg-gray-50 transition"
                                >
                                    <p className="font-semibold">
                                        주문번호: {order.id}
                                    </p>
                                    <p>이메일: {order.email}</p>
                                    <p>
                                        주소: {order.address} ({order.zipCode})
                                    </p>
                                    <p>
                                        상태: {OrderStatusLabels[order.status]}
                                    </p>
                                    <p className="text-sm text-gray-500">
                                        주문일시:{" "}
                                        {formatDateTime(order.createdAt)}
                                    </p>

                                    {/* 메뉴 리스트 */}
                                    <div className="mt-2 space-y-1">
                                        {order.menus.map((menu, idx) => (
                                            <p
                                                key={idx}
                                                className="text-sm text-gray-600"
                                            >
                                                - {menu.name} (x{menu.quantity}){" "}
                                                {menu.price * menu.quantity}원
                                            </p>
                                        ))}
                                    </div>

                                    <p className="font-bold mt-5">
                                        총 합계:{" "}
                                        {order.totalPrice.toLocaleString()}원
                                    </p>

                                    {/* 배송 버튼 */}
                                    {order.status === "PENDING" && (
                                        <button
                                            onClick={() => onShip(order.id)}
                                            className="mt-4 px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700"
                                        >
                                            배송 시작
                                        </button>
                                    )}
                                </div>
                            ))}
                        </div>
                    </div>
                );
            })}
        </div>
    );
}

function formatDate(date: Date | string) {
    if (typeof date === "string") date = new Date(date);
    return date.toLocaleDateString("ko-KR");
}

function formatDateTime(date: Date | string) {
    if (typeof date === "string") date = new Date(date);
    return date.toLocaleString("ko-KR");
}
