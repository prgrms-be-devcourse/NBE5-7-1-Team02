import { useState } from "react";

export default function OrderLookupPage() {
    const [orderId, setOrderId] = useState("");

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();

        console.log("조회할 주문번호:", orderId);

        // TODO 나중에 주문 조회 API 호출
    };

    return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-gray-50 p-8">
            <div className="bg-white p-8 rounded shadow-md w-full max-w-md">
                <h1 className="text-2xl font-bold mb-6 text-center">
                    주문 조회
                </h1>
                <form onSubmit={handleSubmit} className="space-y-4">
                    <input
                        type="text"
                        placeholder="주문번호를 입력하세요"
                        value={orderId}
                        onChange={(e) => setOrderId(e.target.value)}
                        className="w-full border p-2 rounded"
                        required
                    />
                    <button
                        type="submit"
                        className="w-full bg-green-600 text-white py-2 rounded hover:bg-green-700"
                    >
                        조회하기
                    </button>
                </form>
            </div>
        </div>
    );
}
