import { useState } from "react";
import { uploadFile } from "../../api/jwt/file";

export default function FileUploadTestPage() {
    const [file, setFile] = useState<File | null>(null);
    const [previewUrl, setPreviewUrl] = useState("");
    const [uploadedUrl, setUploadedUrl] = useState("");

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const selected = e.target.files?.[0];
        if (selected) {
            setFile(selected);
            setPreviewUrl(URL.createObjectURL(selected)); // 미리보기
        }
    };

    const handleUpload = async () => {
        if (!file) return;
        try {
            const res = await uploadFile(file);
            const url = `http://localhost:8080/files/${res.key}`;

            setUploadedUrl(url);

            alert(`업로드 성공: 이미지 URL = ${url}`);
        } catch (err) {
            alert("업로드 실패");

            console.error(err);
        }
    };

    return (
        <div className="container mt-5">
            <h2>이미지 업로드 테스트</h2>
            <input type="file" accept="image/*" onChange={handleChange} />
            <button onClick={handleUpload} className="btn btn-dark mt-2">
                업로드
            </button>

            {previewUrl && (
                <div className="mt-3">
                    <h5>선택한 이미지 미리보기</h5>
                    <img src={previewUrl} alt="preview" width={200} />
                </div>
            )}

            {uploadedUrl && (
                <div className="mt-3">
                    <h5>서버에서 생성하고 반환한 이미지</h5>
                    <img src={uploadedUrl} alt="uploaded" width={200} />
                </div>
            )}

            <div>
                <div>로컬 스토리지의 기본이미지 서빙 테스트</div>
                <img
                    src={`${import.meta.env.VITE_API_URL}/files/default.jpg`}
                    alt="default"
                    width={200}
                />
            </div>
        </div>
    );
}
