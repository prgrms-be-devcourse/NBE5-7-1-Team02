import { LoginJwtResponse, LoginRequest } from "../../params/auth/login";
import { fetchPost } from "./../";

export async function login(req: LoginRequest): Promise<LoginJwtResponse> {
    const res = await fetchPost<LoginJwtResponse>("/auth/login", req);

    localStorage.setItem("accessToken", res.accessToken);
    localStorage.setItem("refreshToken", res.refreshToken);

    return res;
}
