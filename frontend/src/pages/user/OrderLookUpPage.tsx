import { useState } from "react";
import { getOrderById } from "@/api/session/order/getOrderById";
import { GetOrderResponse } from "@/api/params/order/getOrderById";
import { useNavigate } from "react-router-dom";
import { OrderStatusLabels } from "../../lib/enums/OrderStatus";

export default function OrderLookupPage() {
    const navigate = useNavigate();

    const [orderId, setOrderId] = useState("");
    const [email, setEmail] = useState("");
    const [order, setOrder] = useState<GetOrderResponse | null>(null);
    const [error, setError] = useState("");

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        if (!orderId || !email) {
            alert("주문번호와 이메일을 모두 입력해주세요.");

            return;
        }

        try {
            const orderData = await getOrderById({
                id: Number(orderId),
                email,
            });

            setOrder(orderData);

            setError("");
        } catch (err) {
            console.error(err);

            setError("주문을 찾을 수 없습니다.");

            setOrder(null);
        }
    };

    const handleConfirm = () => {
        navigate("/");
    };

    return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-gray-50 p-8">
            <div className="bg-white p-8 rounded shadow-md w-full max-w-md">
                {order ? (
                    <></>
                ) : (
                    <h1 className="text-2xl font-bold mb-6 text-center">
                        주문 조회
                    </h1>
                )}

                {error && (
                    <p className="text-center text-red-500 mb-4">{error}</p>
                )}

                {order ? (
                    <div className="space-y-2 text-left">
                        {" "}
                        <h1 className="text-2xl font-bold mb-6 text-center">
                            주문 상세
                        </h1>
                        <div className="space-y-2 text-left">
                            <p>
                                <span className="font-bold">주문번호:</span>{" "}
                                {order.id}
                            </p>
                            <p>
                                <span className="font-bold">주문일시:</span>{" "}
                                {new Date(order.createdAt).toLocaleString(
                                    "ko-KR"
                                )}
                            </p>
                            <p>
                                <span className="font-bold">이메일:</span>{" "}
                                {order.email}
                            </p>
                            <p>
                                <span className="font-bold">주소:</span>{" "}
                                {order.address} ({order.zipCode})
                            </p>
                            <p>
                                <span className="font-bold">상태:</span>{" "}
                                {OrderStatusLabels[order.status]}
                            </p>
                        </div>
                        <h3 className="text-lg font-semibold mt-4 mb-2">
                            주문한 메뉴
                        </h3>
                        <ul className="space-y-1">
                            {order.menus.map((menu) => (
                                <li
                                    key={menu.id}
                                    className="flex justify-between"
                                >
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
                        <div className="mt-4 font-bold text-right">
                            총 합계: {order.totalPrice.toLocaleString()}원
                        </div>
                        <button
                            type="button"
                            onClick={handleConfirm}
                            className="w-full mt-6 bg-green-600 text-white py-2 rounded hover:bg-green-700 cursor-pointer"
                        >
                            확인
                        </button>
                    </div>
                ) : (
                    <form onSubmit={handleSubmit} className="space-y-4 mb-6">
                        <input
                            type="text"
                            placeholder="주문번호를 입력하세요"
                            value={orderId}
                            onChange={(e) => setOrderId(e.target.value)}
                            className="w-full border p-2 rounded"
                            required
                        />
                        <input
                            type="email"
                            placeholder="이메일을 입력하세요"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            className="w-full border p-2 rounded"
                            required
                        />
                        <button
                            type="submit"
                            className="w-full bg-green-600 text-white py-2 rounded hover:bg-green-700 cursor-pointer"
                        >
                            조회하기
                        </button>
                    </form>
                )}
            </div>
        </div>
    );
}
