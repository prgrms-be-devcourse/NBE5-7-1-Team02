import { fetchPostWithForm } from ".";

// 파일 업로드
export const uploadFile = (file: File) => {
    const form = new FormData();

    form.append("file", file);

    return fetchPostWithForm<{ id: number; key: string }>("/files", form);
};
