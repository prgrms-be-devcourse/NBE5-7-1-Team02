import { BrowserRouter, Routes, Route } from "react-router-dom";
import { OrderPage, AdminMenuEdit } from "./pages";
import FileUploadTestPage from "./pages/admin/FileUploadTestPage";
import LoginPage from "./pages/admin/LoginPage";
import AdminHomePage from "./pages/admin/AdminHomePage";
import AdminMenuCreatePage from "./pages/admin/AdminMenuCreatePage";
import OrderLookupPage from "./pages/user/OrderLookUpPage";
import OrderSuccessPage from "./pages/user/OrderSuccesPage";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                {/* 어드민 */}
                <Route path="/admin/login" element={<LoginPage />} />
                <Route path="/admin/" element={<AdminHomePage />} />
                <Route
                    path="/admin/menus/new"
                    element={<AdminMenuCreatePage />}
                />
                <Route
                    path="/admin/menus/:id/edit"
                    element={<AdminMenuEdit />}
                />
                <Route path="/upload" element={<FileUploadTestPage />} />

                {/* 유저 */}
                {/* <Route path="/" element={<Home />} /> */}
                <Route path="/" element={<OrderPage />} />
                <Route
                    path="/order/success/:orderId"
                    element={<OrderSuccessPage />}
                />
                <Route path="/order/lookup" element={<OrderLookupPage />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
