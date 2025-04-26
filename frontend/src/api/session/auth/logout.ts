import { APIException } from "../../../lib/exceptions/ApiException";

const BASE = import.meta.env.VITE_API_URL;

export async function sessionLogout(): Promise<void> {
    const res = await fetch(`${BASE}/admin/logout`, {
        method: "POST",
        credentials: "include",
    });

    if (!res.ok) {
        const err = await res.json().catch(() => ({}));

        throw new APIException(res.status, err.message, err.errorCode);
    }
}
