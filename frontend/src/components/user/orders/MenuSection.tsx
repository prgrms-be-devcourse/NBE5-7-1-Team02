import { Menu } from "@/lib/types/menu";
import UserMenuList from "./../menus/UserMenuList";

interface Props {
    menus: Menu[];
    onAddToCart: (menu: Menu) => void;
}

export default function MenuSection({ menus, onAddToCart }: Props) {
    return (
        <div className="mb-6">
            <div className="bg-white rounded-lg shadow-md p-6">
                <h2 className="text-2xl font-bold mb-4">메뉴</h2>
                <UserMenuList menus={menus} onAddToCart={onAddToCart} />
            </div>
        </div>
    );
}
