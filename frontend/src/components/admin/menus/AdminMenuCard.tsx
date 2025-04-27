import { Menu } from "@/lib/types/menu";
import { CategoryLabels } from "@/lib/enums/Category";
// import { extractFilename } from "../../util/extractFilename";

const BASE = import.meta.env.VITE_API_URL;

interface Props {
    menu: Menu;
    onEdit: () => void;
    onDelete: () => void;
}

export default function AdminMenuCard({ menu, onEdit, onDelete }: Props) {
    const thumbnailKey = menu.thumbnailUrl ? menu.thumbnailUrl : "default.jpg";

    return (
        <div className="bg-white p-4 rounded shadow hover:shadow-md transition">
            <img
                src={`${BASE}/files/${thumbnailKey}`}
                alt={menu.name}
                className="w-full aspect-[4/3] object-cover object-center rounded mb-2"
            />
            <h2 className="text-lg font-semibold">{menu.name}</h2>
            <p className="text-sm text-gray-500">
                {CategoryLabels[menu.category] ?? menu.category}
            </p>
            <p className="text-sm mt-1">{menu.price.toLocaleString()}원</p>

            <div className="flex gap-2 mt-2">
                <button
                    onClick={onEdit}
                    className="px-3 py-1 bg-blue-500 text-white text-sm rounded hover:bg-blue-600"
                >
                    수정하기
                </button>
                <button
                    onClick={onDelete}
                    className="px-3 py-1 bg-red-500 text-white text-sm rounded hover:bg-red-600"
                >
                    삭제하기
                </button>
            </div>
        </div>
    );
}
