import { useLocation, useNavigate } from "react-router-dom";
import { GetOrderResponse } from "@/api/params/order/getOrderById";
import { OrderStatusLabels } from "../../lib/enums/OrderStatus";

export default function OrderSuccessPage() {
    const { state } = useLocation();
    const navigate = useNavigate();

    const order = state as GetOrderResponse;

    if (!order) {
        return (
            <div className="flex items-center justify-center min-h-screen">
                <p>주문 정보를 불러올 수 없습니다.</p>
            </div>
        );
    }

    return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-gray-50 p-8">
            <div className="bg-white p-8 rounded shadow-md w-full max-w-md">
                <h1 className="text-2xl font-bold mb-6 text-center">
                    🎉 주문이 완료되었습니다!
                </h1>

                <div className="space-y-2 text-left">
                    <p>
                        <span className="font-bold">주문번호:</span> {order.id}
                    </p>
                    <p>
                        <span className="font-bold">주문일시:</span>{" "}
                        {new Date(order.createdAt).toLocaleString("ko-KR")}
                    </p>
                    <p>
                        <span className="font-bold">이메일:</span> {order.email}
                    </p>
                    <p>
                        <span className="font-bold">주소:</span> {order.address}{" "}
                        ({order.zipCode})
                    </p>
                    <p>
                        <span className="font-bold">상태:</span>{" "}
                        {OrderStatusLabels[order.status]}
                    </p>
                </div>

                <div className="mt-6">
                    <h2 className="text-lg font-bold mb-2">주문한 메뉴</h2>
                    <ul className="space-y-2">
                        {order.menus.map((menu) => (
                            <li key={menu.id} className="flex justify-between">
                                <div>
                                    {menu.name} x {menu.quantity}
                                </div>
                                <div>
                                    {(
                                        menu.price * menu.quantity
                                    ).toLocaleString()}
                                    원
                                </div>
                            </li>
                        ))}
                    </ul>
                </div>

                <div className="mt-4 font-bold text-right">
                    총 합계: {order.totalPrice.toLocaleString()}원
                </div>

                <button
                    onClick={() => navigate("/")}
                    className="mt-8 w-full bg-green-600 text-white py-2 rounded hover:bg-green-700 transition cursor-pointer"
                >
                    확인
                </button>
            </div>
        </div>
    );
}
