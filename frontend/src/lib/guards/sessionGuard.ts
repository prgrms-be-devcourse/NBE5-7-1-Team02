import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export function useSessionGuard() {
    const navigate = useNavigate();
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const check = async () => {
            try {
                const res = await fetch("http://localhost:8080/me", {
                    credentials: "include",
                });

                if (!res.ok) {
                    alert("로그인이 필요합니다.");

                    throw new Error("unauthenticated");
                }
            } catch (err) {
                console.error(err);

                navigate("/admin/login");
            } finally {
                setLoading(false);
            }
        };

        check();
    }, [navigate]);

    return { loading };
}
