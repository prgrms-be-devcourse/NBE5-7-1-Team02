import { APIException } from "../../../lib/exceptions/ApiException";
import { RefreshResponse } from "../../params/auth/refresh";
import { fetchPost } from "./../";

export async function refreshToken(): Promise<RefreshResponse> {
    const refreshToken = localStorage.getItem("refreshToken");

    if (!refreshToken) {
        throw new APIException(401, "리프레시 토큰이 없습니다.");
    }

    const res = await fetchPost<RefreshResponse>("/auth/refresh", {
        refreshToken,
    });

    localStorage.setItem("accessToken", res.accessToken);

    return res;
}
