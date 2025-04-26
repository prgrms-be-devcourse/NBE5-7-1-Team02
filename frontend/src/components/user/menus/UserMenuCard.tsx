import { Menu } from "@/lib/types/menu";
import { CategoryLabels } from "@/lib/enums/Category";
import { extractFilename } from "../../util/extractFilename";

const BASE = import.meta.env.VITE_API_URL;

interface Props {
    menu: Menu;
    onAddToCart: (menu: Menu) => void;
}

export default function UserMenuCard({ menu, onAddToCart }: Props) {
    const thumbnailKey = menu.thumbnailUrl
        ? extractFilename(menu.thumbnailUrl)
        : "default.jpg";

    return (
        <div className="flex items-center bg-white p-4 rounded shadow hover:shadow-md transition">
            {/* 왼쪽: 이미지 */}
            <img
                src={`${BASE}/files/${thumbnailKey}`}
                alt={menu.name}
                className="w-24 h-24 object-cover rounded mr-4"
            />

            {/* 가운데: 정보 */}
            <div className="flex-1">
                <h2 className="text-lg font-semibold">{menu.name}</h2>
                <p className="text-sm text-gray-500">
                    {CategoryLabels[menu.category] ?? menu.category}
                </p>
                <p className="text-sm mt-1">{menu.price.toLocaleString()}원</p>
            </div>

            {/* 오른쪽: 추가 버튼 */}
            <button
                onClick={() => onAddToCart(menu)}
                className="ml-4 px-3 py-1 bg-green-500 text-white text-sm rounded hover:bg-green-600"
            >
                추가
            </button>
        </div>
    );
}
