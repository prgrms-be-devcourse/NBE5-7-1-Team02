import { useState, ChangeEvent, FormEvent, useEffect } from "react";
import { uploadFile } from "@/api/session/file/uploadFile";
import { Category, CategoryLabels } from "../../../lib/enums/Category";
import { extractFilename } from "../../util/extractFilename";

export interface MenuFormData {
    menuName: string;
    price: number;
    category: Category;
    thumbnailId: number | null;
}

interface MenuFormProps {
    onSubmit: (req: MenuFormData) => void;
    initialData?: {
        name: string;
        price: number;
        category: Category;
        thumbnailId: number;
        thumbnailUrl?: string;
    };
}

export default function AdminMenuForm({
    onSubmit,
    initialData,
}: MenuFormProps) {
    const [name, setName] = useState("");
    const [price, setPrice] = useState("");
    const [category, setCategory] = useState<Category>(Category.COFFEE_BEAN);
    const [imagePreviewUrl, setImagePreviewUrl] = useState("");
    const [imageId, setImageId] = useState<number | null>(null);
    const [uploading, setUploading] = useState(false);

    useEffect(() => {
        if (initialData) {
            const thumbnailKey = initialData.thumbnailUrl
                ? extractFilename(initialData.thumbnailUrl)
                : "default.jpg";

            console.log(thumbnailKey);

            setName(initialData.name);
            setPrice(String(initialData.price));
            setCategory(initialData.category);
            setImageId(initialData.thumbnailId);
            setImagePreviewUrl(
                initialData.thumbnailUrl
                    ? `${import.meta.env.VITE_API_URL}/files/${thumbnailKey}`
                    : ""
            );
        }
    }, [initialData]);

    const handleImageChange = async (e: ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files?.[0];

        if (!file) return;

        setUploading(true);
        try {
            const res = await uploadFile(file); // { id, key }

            console.log(res);

            setImageId(res.id);

            console.log(res.id, res.key);
            console.log(`${import.meta.env.VITE_API_URL}${res.key}`);

            setImagePreviewUrl(
                `${import.meta.env.VITE_API_URL}/files/${res.key}`
            );
        } catch (err) {
            console.error(err);

            alert("이미지 업로드 실패");
        } finally {
            setUploading(false);
        }
    };

    const handleSubmit = (e: FormEvent) => {
        e.preventDefault();

        const numericPrice = Number(price);

        if (isNaN(numericPrice) || numericPrice > 1_000_000) {
            alert("가격은 1,000,000 이하의 숫자만 입력할 수 있습니다.");

            setPrice("");

            return;
        }

        if (isNaN(numericPrice) || numericPrice < 0) {
            alert("가격은 0 이상의 숫자만 입력할 수 있습니다.");

            setPrice("");

            return;
        }

        const data: MenuFormData = {
            menuName: name,
            price: Number(price),
            category,
            thumbnailId: imageId,
        };

        console.log(data);

        onSubmit(data);
    };

    return (
        <form onSubmit={handleSubmit} className="space-y-4 max-w-md mx-auto">
            <h2 className="text-xl font-bold mb-2">📋 메뉴 등록</h2>

            <input
                className="w-full border p-2"
                type="text"
                placeholder="메뉴명"
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
            />

            <input
                className="w-full border p-2"
                type="number"
                placeholder="가격"
                value={price}
                onChange={(e) => setPrice(e.target.value)}
                required
            />

            <select
                className="w-full border p-2"
                value={category}
                onChange={(e) => setCategory(e.target.value as Category)}
                required
            >
                <option value="">카테고리를 선택하세요</option>
                {Object.values(Category).map((cat) => (
                    <option key={cat} value={cat}>
                        {CategoryLabels[cat]}
                    </option>
                ))}
            </select>

            <input
                className="w-full border p-2"
                type="file"
                accept="image/*"
                onChange={handleImageChange}
            />

            {uploading && <p className="text-sm text-gray-500">업로드 중...</p>}

            {imagePreviewUrl && (
                <div className="mt-2">
                    <img
                        src={imagePreviewUrl}
                        alt="미리보기"
                        className="w-500"
                    />
                </div>
            )}

            <button type="submit" className="btn btn-dark w-full">
                메뉴 등록
            </button>
        </form>
    );
}
