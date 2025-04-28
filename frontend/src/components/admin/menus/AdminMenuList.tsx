import { Menu } from "@/lib/types/menu";
import AdminMenuCard from "./AdminMenuCard";
import { Category, CategoryLabels } from "../../../lib/enums/Category";

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
    const groupedMenus = menus.reduce<Record<string, Menu[]>>((acc, menu) => {
        if (!acc[menu.category]) {
            acc[menu.category] = [];
        }
        acc[menu.category].push(menu);
        return acc;
    }, {});

    return (
        <div className="space-y-12 px-30">
            {Object.entries(groupedMenus).map(([category, menus]) => (
                <div key={category}>
                    {/* 카테고리 타이틀 */}
                    <h3 className="text-2xl font-bold mb-4 text-gray-700">
                        {CategoryLabels[category as Category] ?? category}
                    </h3>

                    {/* 메뉴 카드 리스트 */}
                    <div className="grid gap-4 grid-cols-1 md:grid-cols-2 lg:grid-cols-3">
                        {menus.map((menu) => (
                            <AdminMenuCard
                                key={menu.id}
                                menu={menu}
                                onEdit={() => onEdit(menu)}
                                onDelete={() => onDelete(menu)}
                            />
                        ))}
                    </div>
                </div>
            ))}
        </div>
    );
}
