import { Link } from "react-router-dom";

export default function Home() {
    return (
        <div>
            <h1>커피메뉴 관리 시스템</h1>

            <h2>임시 유저</h2>
            <ul>
                <li>
                    <Link to="/order">주문하기</Link>
                </li>
            </ul>

            <h2>어드민</h2>
            <ul>
                <li>
                    <Link to="/admin/login">로그인(임시)</Link>
                </li>
                <li>
                    <Link to="/admin/">어드민 인덱스페이지</Link>
                </li>
                <li>
                    <Link to="/admin/menus/new">메뉴 추가</Link>
                </li>
            </ul>

            <h2>api 테스트</h2>
            <ul>
                <li>
                    <Link to="/upload">파일 업로드</Link>
                </li>
            </ul>
        </div>
    );
}
