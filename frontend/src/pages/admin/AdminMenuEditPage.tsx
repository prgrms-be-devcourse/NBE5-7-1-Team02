import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import AdminMenuForm, {
    MenuFormData,
} from "@/components/admin/menus/AdminMenuForm";
import { getMenuById } from "@/api/session/menu/getMenuById";
import { updateMenu } from "@/api/session/menu/updateMenu";
import { Menu } from "@/lib/types/menu";
import { useSessionGuard } from "../../lib/guards/sessionGuard";
import Loading from "../../components/common/Loading";

export default function AdminMenuEditPage() {
    const { loading: sessionLoading } = useSessionGuard();

    const { id } = useParams();
    const navigate = useNavigate();
    const [menu, setMenu] = useState<Menu | null>(null);
    const [, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        const fetch = async () => {
            try {
                console.log(id);

                const data = await getMenuById(Number(id));

                console.log("AdminMenuEditPage에서 받아온 data:", data);

                setMenu(data);
            } catch (err) {
                setError(
                    err.message || "메뉴 정보를 불러오는 데 실패했습니다."
                );
            } finally {
                setLoading(false);
            }
        };

        fetch();
    }, [id]);

    const handleSubmit = async (form: MenuFormData) => {
        try {
            await updateMenu(Number(id), {
                name: form.menuName,
                price: form.price,
                category: form.category,
                imageId: form.thumbnailId,
            });

            alert("수정되었습니다.");

            navigate("/admin/");
        } catch (err) {
            console.error(err);

            alert("수정에 실패했습니다.");
        }
    };

    if (sessionLoading) return <Loading />;
    if (error) return <p className="text-red-500">{error}</p>;
    if (!menu) return null;

    return (
        <div className="min-h-screen p-8 py-30 bg-gray-100">
            {/* <h1 className="text-2xl font-bold mb-4">메뉴 수정</h1> */}
            <AdminMenuForm
                onSubmit={handleSubmit}
                initialData={{
                    name: menu.name,
                    price: menu.price,
                    category: menu.category,
                    thumbnailId: menu.fileId,
                    thumbnailUrl: menu.thumbnailUrl,
                }}
            />
        </div>
    );
}
