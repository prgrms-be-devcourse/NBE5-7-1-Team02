import { Menu } from "@/lib/types/menu";
import UserMenuCard from "./UserMenuCard";

interface Props {
    menus: Menu[];
    onAddToCart: (menu: Menu) => void;
}

export default function UserMenuList({ menus, onAddToCart }: Props) {
    return (
        <div className="flex flex-col gap-4">
            {menus.map((menu) => (
                <UserMenuCard
                    key={menu.id}
                    menu={menu}
                    onAddToCart={onAddToCart}
                />
            ))}
        </div>
    );
}
