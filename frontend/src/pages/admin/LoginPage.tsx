import { useState } from "react";
import { APIException } from "../../lib/exceptions/ApiException";
import { sessionLogin } from "../../api/session/auth/login";

export default function LoginPage() {
    const [id, setId] = useState("");
    const [password, setPassword] = useState("");
    const [, setError] = useState("");

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        try {
            await sessionLogin({ id, password });
        } catch (err) {
            if (err instanceof APIException) {
                console.error(err.message);

                alert("아이디와 비밀번호가 틀렸습니다.");
            } else {
                setError(err.message || "로그인 실패");
            }
        }
    };

    return (
        <div className="min-h-screen flex items-center justify-center bg-gray-100">
            <form
                onSubmit={handleSubmit}
                className="bg-white p-8 rounded shadow-md w-full max-w-sm"
            >
                <h2 className="text-2xl font-bold mb-6 text-center">
                    어드민 로그인
                </h2>

                <div className="mb-4">
                    <label className="block text-gray-700 mb-1">아이디</label>
                    <input
                        type="text"
                        className="w-full border border-gray-300 p-2 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                        value={id}
                        onChange={(e) => setId(e.target.value)}
                        required
                    />
                </div>

                <div className="mb-6">
                    <label className="block text-gray-700 mb-1">비밀번호</label>
                    <input
                        type="password"
                        className="w-full border border-gray-300 p-2 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>

                <button
                    type="submit"
                    className="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700"
                >
                    로그인
                </button>
            </form>
        </div>
    );
}
