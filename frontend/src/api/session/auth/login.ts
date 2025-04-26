import { APIException } from "../../../lib/exceptions/ApiException";
import { LoginRequest } from "../../params/auth/login";

const BASE = import.meta.env.VITE_API_URL;

export async function sessionLogin({
    id,
    password,
}: LoginRequest): Promise<void> {
    const res = await fetch(`${BASE}/admin/login`, {
        method: "POST",
        credentials: "include",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded",
        },
        body: new URLSearchParams({ id, password }),
    });

    if (res.ok) {
        window.location.href = "/admin";
    } else {
        const err = await res.json().catch(() => ({}));

        throw new APIException(res.status, err.message, err.errorCode);
    }
}
