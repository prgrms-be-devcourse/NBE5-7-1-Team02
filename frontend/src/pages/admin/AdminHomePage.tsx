import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Menu } from "@/lib/types/menu";
import AdminMenuList from "../../components/admin/menus/AdminMenuList";
import { getMenus } from "../../api/session/menu/getMenus";
import { deleteMenu } from "../../api/session/menu/deleteMenu";
import { sessionLogout } from "../../api/session/auth/logout";
import { useSessionGuard } from "../../lib/guards/sessionGuard";
import Loading from "../../components/common/Loading";

export default function AdminHomePage() {
    const { loading: sessionLoading } = useSessionGuard();

    const navigate = useNavigate();

    const [menus, setMenus] = useState<Menu[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    const fetchMenus = async () => {
        try {
            const menus = await getMenus();

            console.log(menus);

            setMenus(menus);
        } catch (err) {
            setError(err.message || "에러가 발생했습니다.");
        } finally {
            setLoading(false);
        }
    };

    const handleLogout = async () => {
        try {
            await sessionLogout();

            window.location.href = "/admin/login";
        } catch (e) {
            console.error("서버 로그아웃 실패:", e);
        }
    };

    const handleCreate = () => {
        navigate("/admin/menus/new");
    };

    const handleDelete = async (menu: Menu) => {
        const confirmed = window.confirm(
            `${menu.name}을(를) 삭제하시겠습니까?`
        );
        if (!confirmed) return;

        try {
            await deleteMenu(menu.id);

            alert("삭제되었습니다.");

            fetchMenus();
        } catch (err) {
            alert("삭제 실패: " + err.message);
        }
    };

    useEffect(() => {
        fetchMenus();
    }, []);

    return (
        <div className="min-h-screen bg-gray-50 p-8">
            {loading && sessionLoading ? (
                <Loading />
            ) : (
                <div>
                    <header className="flex justify-between items-center mb-6">
                        <div>
                            <h1 className="text-3xl font-bold">
                                ☕ Grid & Circle 카페 관리자 페이지
                            </h1>
                            <p className="text-sm text-gray-600 mt-1">
                                메뉴 관리
                            </p>
                        </div>
                        <div className="flex gap-2">
                            <button
                                onClick={handleCreate}
                                className="text-sm bg-blue-600 text-white px-3 py-1 rounded hover:bg-blue-700"
                            >
                                메뉴 추가
                            </button>
                            <button
                                onClick={handleLogout}
                                className="text-sm border border-gray-400 px-3 py-1 rounded hover:bg-gray-100"
                            >
                                로그아웃
                            </button>
                        </div>
                    </header>

                    {error && <p className="text-red-500">{error}</p>}
                    <AdminMenuList
                        menus={menus}
                        onEdit={(menu) =>
                            navigate(`/admin/menus/${menu.id}/edit`)
                        }
                        onDelete={handleDelete}
                    />
                </div>
            )}
        </div>
    );
}
