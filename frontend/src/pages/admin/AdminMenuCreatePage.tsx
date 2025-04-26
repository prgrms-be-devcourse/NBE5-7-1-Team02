import { useNavigate } from "react-router-dom";
import AdminMenuForm from "@/components/admin/menus/AdminMenuForm";
import { createMenu } from "../../api/session/menu/createMenu";
import { CreateMenuRequest } from "../../api/params/menu/createMenu";
import { useSessionGuard } from "../../lib/guards/sessionGuard";
import Loading from "../../components/common/Loading";

export default function AdminMenuCreatePage() {
    const { loading } = useSessionGuard();

    const navigate = useNavigate();

    const handleSubmit = async (form: CreateMenuRequest) => {
        try {
            await createMenu(form);

            alert("메뉴가 등록되었습니다.");

            navigate("/admin/");
        } catch (err) {
            console.log(err);

            alert("등록 실패: " + err.message);
        }
    };

    return loading ? (
        <Loading />
    ) : (
        <div className="min-h-screen p-8 py-30 bg-gray-100">
            <AdminMenuForm onSubmit={handleSubmit} />
        </div>
    );
}
