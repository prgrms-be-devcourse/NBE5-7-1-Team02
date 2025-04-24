import { fetchPostWithForm } from "./index";

// 파일 업로드
export const uploadFile = (file: File) => {
    const form = new FormData();

    form.append("file", file);

    return fetchPostWithForm<{ id: number; url: string }>("/files", form);
};
