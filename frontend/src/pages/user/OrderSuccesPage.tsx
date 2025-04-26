import { useNavigate, useParams } from "react-router-dom";

export default function OrderSuccessPage() {
    const { orderId } = useParams();
    const navigate = useNavigate();

    //TODO CreatOrder Response 프롭으로 받고 ui에 뿌리기
    return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-gray-50 p-8">
            <div className="bg-white p-8 rounded shadow-md text-center">
                <h1 className="text-2xl font-bold mb-4">주문 완료</h1>
                <p className="mb-6">
                    주문번호: <span className="font-semibold">{orderId}</span>
                </p>
                <button
                    onClick={() => navigate("/")}
                    className="bg-green-600 text-white px-6 py-2 rounded hover:bg-green-700"
                >
                    확인
                </button>
            </div>
        </div>
    );
}
