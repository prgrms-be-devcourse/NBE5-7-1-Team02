import { Menu } from "@/lib/types/menu";
import AdminMenuCard from "./AdminMenuCard";

interface MenuListProps {
    menus: Menu[];
    onDelete: (menu: Menu) => void;
    onEdit: (menu: Menu) => void;
}

export default function AdminMenuList({
    menus,
    onDelete,
    onEdit,
}: MenuListProps) {
    return (
        <div className="grid gap-4 grid-cols-1 md:grid-cols-2 lg:grid-cols-3 px-30">
            {menus.map((menu) => (
                <AdminMenuCard
                    key={menu.id}
                    menu={menu}
                    onEdit={() => onEdit(menu)}
                    onDelete={() => onDelete(menu)}
                />
            ))}
        </div>
    );
}
