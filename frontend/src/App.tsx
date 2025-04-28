import { BrowserRouter, Routes, Route } from "react-router-dom";
import {
    OrderPage,
    OrderLookupPage,
    OrderSuccessPage,
    AdminMenuEditPage,
    AdminHomePage,
    AdminMenuCreatePage,
    LoginPage,
} from "./pages";
import FileUploadTestPage from "./pages/admin/FileUploadTestPage";

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
                    element={<AdminMenuEditPage />}
                />
                <Route path="/upload" element={<FileUploadTestPage />} />

                {/* 유저 */}
                <Route path="/" element={<OrderPage />} />
                <Route path="/orders/success" element={<OrderSuccessPage />} />
                <Route path="/orders/lookup" element={<OrderLookupPage />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
