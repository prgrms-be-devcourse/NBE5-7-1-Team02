import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Menu } from "@/lib/types/menu";
import { getMenus } from "@/api/session/menu/getMenus";
import CartAndFormSection from "../../components/user/orders/CartAndFormSection";
import { CartItem } from "../../lib/types";
import MenuSection from "../../components/user/orders/MenuSection";
import { createOrder } from "../../api/session/order/createOrder";

export default function OrderPage() {
    const navigate = useNavigate();

    const [menus, setMenus] = useState<Menu[]>([]);
    const [cart, setCart] = useState<CartItem[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchMenus = async () => {
            try {
                const fetchedMenus = await getMenus();
                setMenus(fetchedMenus);
            } catch (error) {
                console.error(error);
            } finally {
                setLoading(false);
            }
        };

        fetchMenus();
    }, []);

    const handleAddMenuToCart = (menu: Menu) => {
        setCart((prevCart) => {
            const existing = prevCart.find((item) => item.menuId === menu.id);
            if (existing) {
                return prevCart.map((item) =>
                    item.menuId === menu.id
                        ? { ...item, quantity: item.quantity + 1 }
                        : item
                );
            } else {
                return [
                    ...prevCart,
                    {
                        menuId: menu.id,
                        name: menu.name,
                        price: menu.price,
                        quantity: 1,
                    },
                ];
            }
        });
    };

    const handleIncreaseQuantity = (menuId: number) => {
        setCart((prevCart) =>
            prevCart.map((item) =>
                item.menuId === menuId
                    ? { ...item, quantity: item.quantity + 1 }
                    : item
            )
        );
    };

    const handleRemoveFromCart = (menuId: number) => {
        setCart((prevCart) =>
            prevCart
                .map((item) =>
                    item.menuId === menuId
                        ? { ...item, quantity: item.quantity - 1 }
                        : item
                )
                .filter((item) => item.quantity > 0)
        );
    };

    const handleOrderSubmit = async (form: {
        email: string;
        address: string;
        zipCode: string;
    }) => {
        console.log("주문 정보:", form);
        console.log("장바구니:", cart);

        try {
            const res = await createOrder({
                email: form.email,
                addressdto: {
                    address: form.address,
                    zipCode: form.zipCode,
                },
                items: cart.map((item) => ({
                    menuId: item.menuId,
                    name: item.name,
                    price: item.price,
                    quantity: item.quantity,
                })),
            });

            console.log("createOrder response => ", res);

            alert("주문이 완료되었습니다!");

            const orderId = res.id;

            setCart([]);

            navigate(`/order/success/${orderId}`);
        } catch (err) {
            console.error(err);

            alert("주문에 실패했습니다.");
        }
    };

    if (loading)
        return (
            <div className="flex items-center justify-center h-screen">
                로딩 중...
            </div>
        );

    return (
        <div className="flex flex-col min-h-screen">
            {/* 상단 헤더 */}
            <header className="bg-green-600 text-white py-4 px-35 shadow-md flex justify-between items-center">
                <h1 className="text-2xl font-bold">☕ Grid & Coffee 주문</h1>
                <button
                    onClick={() => navigate("/order/lookup")}
                    className="bg-white text-green-600 px-4 py-2 rounded hover:bg-gray-100"
                >
                    내 주문 확인
                </button>
            </header>

            {/* 메인 컨텐츠 */}
            <main className="flex flex-1 p-8 px-30 gap-6 bg-gray-50">
                {/* 왼쪽: 메뉴 영역 */}
                <div className="w-2/3 p-6 bg-white rounded-lg shadow overflow-y-auto">
                    <MenuSection
                        menus={menus}
                        onAddToCart={handleAddMenuToCart}
                    />
                </div>

                {/* 오른쪽: 장바구니 + 주문 폼 */}
                <div className="w-1/3 p-6 bg-white rounded-lg shadow overflow-y-auto">
                    <CartAndFormSection
                        cart={cart}
                        onAdd={handleIncreaseQuantity}
                        onRemove={handleRemoveFromCart}
                        onSubmit={handleOrderSubmit}
                    />
                </div>
            </main>
        </div>
    );
}
