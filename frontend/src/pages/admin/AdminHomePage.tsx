import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Menu } from "@/lib/types/menu";
import AdminMenuList from "../../components/admin/menus/AdminMenuList";
import { getMenus } from "../../api/session/menu/getMenus";
import { deleteMenu } from "../../api/session/menu/deleteMenu";
import { sessionLogout } from "../../api/session/auth/logout";
import { useSessionGuard } from "../../lib/guards/sessionGuard";
import Loading from "../../components/common/Loading";
import AdminOrderList from "../../components/admin/orders/AdminOrderList";
import { getOrders } from "../../api/session/order/getOrders";
import { GetOrdersResponse } from "../../api/params/order/getOrders";
import { shipOrder } from "../../api/session/order/shipOrder";
import { shipOrders } from "../../api/session/order/shipOrders";

export default function AdminHomePage() {
    const { loading: sessionLoading } = useSessionGuard();

    const navigate = useNavigate();

    const [menus, setMenus] = useState<Menu[]>([]);
    const [ordersData, setOrdersData] = useState<GetOrdersResponse[]>([]);
    const [, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [tab, setTab] = useState<"menu" | "order">("menu");

    const fetchMenus = async () => {
        try {
            const menus = await getMenus();
            const orders = await getOrders();

            console.log(menus);

            setMenus(menus);
            setOrdersData(orders);
        } catch (err) {
            setError(err.message || "에러가 발생했습니다.");
        } finally {
            setLoading(false);
        }
    };

    const fetchOrders = async () => {
        try {
            const orders = await getOrders();

            console.log("주문 목록:", orders);

            setOrdersData(orders);
        } catch (err) {
            setError(err.message || "주문 불러오기 실패");
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

    // 단건 배송 처리
    const handleShip = async (orderId: number) => {
        try {
            await shipOrder(orderId);

            alert("배송 완료 처리되었습니다.");

            fetchOrders();
        } catch (err) {
            console.error(err);

            alert("배송 처리 실패");
        }
    };

    // 복수 배송 처리
    const handleBulkShip = async (orderIds: number[]) => {
        try {
            await shipOrders({ ids: orderIds });

            alert("일괄 배송 완료되었습니다.");

            fetchOrders();
        } catch (err) {
            console.error(err);

            alert("일괄 배송 처리 실패");
        }
    };

    function isToday(date: Date | string) {
        const today = new Date();
        const d = typeof date === "string" ? new Date(date) : date;
        return (
            d.getFullYear() === today.getFullYear() &&
            d.getMonth() === today.getMonth() &&
            d.getDate() === today.getDate()
        );
    }

    const todayOrders = ordersData
        .flatMap((dateRange) => dateRange.orders)
        .filter((order) => isToday(order.createdAt));

    const todayOrderCount = todayOrders.length;
    const todaySalesTotal = todayOrders.reduce(
        (sum, order) => sum + order.totalPrice,
        0
    );

    useEffect(() => {
        setLoading(true);

        if (tab === "menu") {
            fetchMenus();
        } else if (tab === "order") {
            fetchOrders();
        }
    }, [tab]);

    return sessionLoading ? (
        <Loading />
    ) : (
        <div className="min-h-screen bg-gray-50">
            {/* 상단 헤더 */}
            <header className="bg-green-600 text-white p-4 flex justify-between items-center shadow-md">
                <h1 className="text-2xl font-bold">
                    ☕ Grid & Circle 관리 대시보드
                </h1>
                <button
                    onClick={handleLogout}
                    className="bg-white text-green-600 px-4 py-2 rounded hover:bg-gray-100"
                >
                    로그아웃
                </button>
            </header>

            {/* 탭 메뉴 */}
            <div className="flex justify-center gap-8 py-4 bg-white shadow">
                <button
                    onClick={() => setTab("menu")}
                    className={`px-4 py-2 text-lg font-semibold ${
                        tab === "menu"
                            ? "border-b-4 border-green-600 text-green-600"
                            : "text-gray-500"
                    }`}
                >
                    메뉴 관리
                </button>
                <button
                    onClick={() => setTab("order")}
                    className={`px-4 py-2 text-lg font-semibold ${
                        tab === "order"
                            ? "border-b-4 border-green-600 text-green-600"
                            : "text-gray-500"
                    }`}
                >
                    주문 관리
                </button>
            </div>

            {/* 대시보드 통계 */}
            <div className="flex justify-center gap-12 py-6 bg-gray-50 text-center">
                <div>
                    <h4 className="text-lg font-semibold text-gray-700">
                        메뉴 수
                    </h4>
                    <p className="text-2xl font-bold text-green-600">
                        {menus.length}
                    </p>
                </div>
                <div>
                    <h4 className="text-lg font-semibold text-gray-700">
                        오늘 주문 건수
                    </h4>
                    <p className="text-2xl font-bold text-green-600">
                        {todayOrderCount}
                    </p>
                </div>
                <div>
                    <h4 className="text-lg font-semibold text-gray-700">
                        오늘 매출 합계
                    </h4>
                    <p className="text-2xl font-bold text-green-600">
                        {todaySalesTotal.toLocaleString()}원
                    </p>
                </div>
            </div>

            {/* 메인 컨텐츠 */}
            <main className="p-8">
                {tab === "menu" && (
                    <div>
                        <div className="flex justify-between items-center mb-6 px-32">
                            <h2 className="text-2xl font-bold">메뉴 관리</h2>
                            <button
                                onClick={handleCreate}
                                className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
                            >
                                메뉴 추가
                            </button>
                        </div>

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

                {tab === "order" && (
                    <div>
                        <h2 className="text-2xl font-bold mb-6 px-32">
                            주문 관리
                        </h2>
                        {error && <p className="text-red-500">{error}</p>}
                        <AdminOrderList
                            ordersData={ordersData}
                            onShip={handleShip}
                            onBulkShip={handleBulkShip}
                        />
                    </div>
                )}
            </main>
        </div>
    );
}
