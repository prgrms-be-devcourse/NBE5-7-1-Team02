import { APIException } from "../lib/exceptions/ApiException";

const BASE = import.meta.env.VITE_API_URL;

export async function fetchGet<T>(url: string): Promise<T> {
    const res = await fetch(`${BASE}${url}`);

    if (!res.ok) await catchException(res);

    return await res.json();
}

export async function fetchPost<T>(url: string, body: unknown): Promise<T> {
    const res = await fetch(`${BASE}${url}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body),
    });

    if (!res.ok) await catchException(res);

    return await res.json();
}

export async function fetchPut<T>(url: string, body: unknown): Promise<T> {
    const res = await fetch(`${BASE}${url}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body),
    });

    if (!res.ok) await catchException(res);

    return await res.json();
}

export async function fetchDelete<T>(url: string): Promise<T> {
    const res = await fetch(`${BASE}${url}`, { method: "DELETE" });

    if (!res.ok) await catchException(res);

    return await res.json();
}

export async function fetchPostWithForm<T>(
    url: string,
    formData: FormData
): Promise<T> {
    const res = await fetch(`${BASE}${url}`, {
        method: "POST",
        body: formData,
    });

    if (!res.ok) throw new Error(await res.text());

    return await res.json();
}

async function catchException(res: Response) {
    const err = await res.json().catch(() => ({}));

    throw new APIException(res.status, err.message, err.errorCode);
}
