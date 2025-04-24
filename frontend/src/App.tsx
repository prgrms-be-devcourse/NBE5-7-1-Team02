import { BrowserRouter, Routes, Route } from "react-router-dom";
import {
    Home,
    OrderPage,
    AdminMenuList,
    AdminMenuCreate,
    AdminMenuEdit,
} from "./pages";
import FileUploadTestPage from "./pages/FileUploadTestPage";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/order" element={<OrderPage />} />
                <Route path="/admin/menus" element={<AdminMenuList />} />
                <Route path="/admin/menus/new" element={<AdminMenuCreate />} />
                <Route
                    path="/admin/menus/:menuId/edit"
                    element={<AdminMenuEdit />}
                />
                <Route path="/upload" element={<FileUploadTestPage />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
