import { OrderStatusLabels } from "@/lib/enums/OrderStatus";
import { GetOrdersResponse } from "../../../api/params/order/getOrders";

interface Props {
    ordersData: GetOrdersResponse[];
    onShip: (orderId: number) => void;
}

export default function AdminOrderList({ ordersData, onShip }: Props) {
    return (
        <div className="space-y-8 px-100">
            {ordersData.map((dateRange, idx) => (
                <div key={idx} className="bg-white p-6 rounded shadow">
                    {/* 날짜 범위 */}
                    <h2 className="text-xl font-bold mb-4">
                        {formatDate(dateRange.from)} 14시 ~{" "}
                        {formatDate(dateRange.to)} 14시
                    </h2>

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
                                <p>상태: {OrderStatusLabels[order.status]}</p>

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
                                    총 합계: {order.totalPrice.toLocaleString()}
                                    원
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
            ))}
        </div>
    );
}

function formatDate(date: Date | string) {
    if (typeof date === "string") date = new Date(date);

    return date.toLocaleDateString("ko-KR");
}
