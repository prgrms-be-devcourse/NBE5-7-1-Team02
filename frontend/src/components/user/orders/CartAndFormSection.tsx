import { useState } from "react";
import { CartItem } from "@/lib/types";

interface OrderFormData {
    email: string;
    address: string;
    zipCode: string;
}

interface Props {
    cart: CartItem[];
    onAdd: (menuId: number) => void;
    onRemove: (menuId: number) => void;
    onSubmit: (form: OrderFormData) => void;
}

export default function CartAndFormSection({
    cart,
    onAdd,
    onRemove,
    onSubmit,
}: Props) {
    const [email, setEmail] = useState("");
    const [address, setAddress] = useState("");
    const [zipCode, setZipCode] = useState("");

    const totalPrice = cart.reduce(
        (sum, item) => sum + item.price * item.quantity,
        0
    );

    const handleOrder = (e: React.FormEvent) => {
        e.preventDefault();

        if (!email || !address || !zipCode) {
            alert("모든 정보를 입력해주세요.");
            return;
        }

        onSubmit({ email, address, zipCode });
    };

    return (
        <div className="bg-white rounded-lg shadow-md p-6">
            <h2 className="text-2xl font-bold mb-4">🛒 장바구니</h2>

            {/* 장바구니 리스트 */}
            <ul className="space-y-4 mb-6">
                {cart.map((item) => (
                    <li
                        key={item.menuId}
                        className="flex justify-between items-center"
                    >
                        <div>
                            <p className="font-semibold">{item.name}</p>
                            <p className="text-sm text-gray-500">
                                {item.price.toLocaleString()}원
                            </p>
                        </div>
                        <div className="flex items-center gap-2">
                            <button
                                onClick={() => onRemove(item.menuId)}
                                className="px-2 py-1 bg-gray-300 rounded"
                            >
                                -
                            </button>
                            <span>{item.quantity}</span>
                            <button
                                onClick={() => onAdd(item.menuId)}
                                className="px-2 py-1 bg-gray-300 rounded"
                            >
                                +
                            </button>
                        </div>
                    </li>
                ))}
            </ul>

            {/* 총 합계 */}
            <div className="mb-6 text-right font-bold text-lg">
                총 합계: {totalPrice.toLocaleString()}원
            </div>

            {/* 주문 폼 */}
            <form onSubmit={handleOrder} className="space-y-4">
                <input
                    type="email"
                    placeholder="이메일"
                    className="w-full border p-2 rounded"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />
                <input
                    type="text"
                    placeholder="주소"
                    className="w-full border p-2 rounded"
                    value={address}
                    onChange={(e) => setAddress(e.target.value)}
                    required
                />
                <input
                    type="text"
                    placeholder="우편번호"
                    className="w-full border p-2 rounded"
                    value={zipCode}
                    onChange={(e) => setZipCode(e.target.value)}
                    required
                />
                <button
                    type="submit"
                    className="w-full bg-green-600 text-white py-2 rounded hover:bg-green-700"
                >
                    주문하기
                </button>
            </form>
        </div>
    );
}
