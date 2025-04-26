import { UploadFileResponse } from "../../params/file/uploadFile";
import { fetchPostWithForm } from "../../session";

// 파일 업로드
export const uploadFile = (file: File) => {
    const form = new FormData();

    form.append("file", file);

    return fetchPostWithForm<UploadFileResponse>("/files", form);
};
