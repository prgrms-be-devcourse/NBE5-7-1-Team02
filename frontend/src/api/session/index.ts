import { APIException } from "@/lib/exceptions/ApiException";

const BASE = import.meta.env.VITE_API_URL;

async function handleResponse<T>(res: Response): Promise<T> {
    if (!res.ok) {
        if (res.status === 401) {
            window.location.href = "/admin/login";

            throw new Error("인증되지 않은 사용자입니다.");
        } else {
            const err = await res.json().catch(() => ({}));

            throw new APIException(res.status, err.message, err.errorCode);
        }
    }

    if (res.status === 204) {
        return undefined as unknown as T;
    }

    return await res.json();
}

export async function fetchGet<T>(url: string): Promise<T> {
    const res = await fetch(`${BASE}${url}`, {
        method: "GET",
        credentials: "include",
    });

    return await handleResponse<T>(res);
}

export async function fetchPost<T>(url: string, body: unknown): Promise<T> {
    const res = await fetch(`${BASE}${url}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify(body),
    });

    return await handleResponse<T>(res);
}

export async function fetchPostWithForm<T>(
    url: string,
    formData: FormData
): Promise<T> {
    const res = await fetch(`${BASE}${url}`, {
        method: "POST",
        body: formData,
        credentials: "include",
    });

    return await handleResponse<T>(res);
}

export async function fetchPut<T>(url: string, body: unknown): Promise<T> {
    const res = await fetch(`${BASE}${url}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify(body),
    });

    return await handleResponse<T>(res);
}

export async function fetchPatch<T>(url: string, body?: unknown): Promise<T> {
    const res = await fetch(`${BASE}${url}`, {
        method: "PATCH",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: body ? JSON.stringify(body) : undefined,
    });

    return await handleResponse<T>(res);
}

export async function fetchDelete<T>(url: string): Promise<T> {
    const res = await fetch(`${BASE}${url}`, {
        method: "DELETE",
        credentials: "include",
    });

    return await handleResponse<T>(res);
}
