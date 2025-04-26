import { APIException } from "../../lib/exceptions/ApiException";
import { refreshToken } from "./auth/refresh";

const BASE = import.meta.env.VITE_API_URL;

function getAuthHeaders(): HeadersInit {
    const token = localStorage.getItem("accessToken");

    return token ? { Authorization: `Bearer ${token}` } : {};
}

async function handleResponse<T>(res: Response): Promise<T> {
    if (!res.ok) {
        const err = await res.json().catch(() => ({}));

        throw new APIException(res.status, err.message, err.errorCode);
    }

    return await res.json();
}

export async function fetchGet<T>(
    url: string,
    withAuth: boolean = true
): Promise<T> {
    const headers: HeadersInit = withAuth ? getAuthHeaders() : {};

    const fetchFn = () =>
        fetch(`${BASE}${url}`, {
            headers,
        }).then(handleResponse<T>);

    return withAuth ? withTokenRetry(fetchFn) : fetchFn();
}

export async function fetchPost<T>(
    url: string,
    body: unknown,
    withAuth: boolean = true
): Promise<T> {
    const headers: HeadersInit = {
        "Content-Type": "application/json",
        ...(withAuth ? getAuthHeaders() : {}),
    };

    const fetchFn = () =>
        fetch(`${BASE}${url}`, {
            method: "POST",
            headers,
            body: JSON.stringify(body),
        }).then(handleResponse<T>);

    return withAuth ? withTokenRetry(fetchFn) : fetchFn();
}

export async function fetchPut<T>(
    url: string,
    body: unknown,
    withAuth: boolean = true
): Promise<T> {
    const headers: HeadersInit = {
        "Content-Type": "application/json",
        ...(withAuth ? getAuthHeaders() : {}),
    };

    const fetchFn = () =>
        fetch(`${BASE}${url}`, {
            method: "PUT",
            headers,
            body: JSON.stringify(body),
        }).then(handleResponse<T>);

    return withAuth ? withTokenRetry(fetchFn) : fetchFn();
}

export async function fetchDelete<T>(
    url: string,
    withAuth: boolean = true
): Promise<T> {
    const headers: HeadersInit = withAuth ? getAuthHeaders() : {};

    const fetchFn = () =>
        fetch(`${BASE}${url}`, {
            method: "DELETE",
            headers,
        }).then(handleResponse<T>);

    return withAuth ? withTokenRetry(fetchFn) : fetchFn();
}

export async function fetchPostWithForm<T>(
    url: string,
    formData: FormData,
    withAuth: boolean = true
): Promise<T> {
    const headers: HeadersInit = withAuth ? getAuthHeaders() : {};

    const fetchFn = () =>
        fetch(`${BASE}${url}`, {
            method: "POST",
            body: formData,
            headers,
        }).then(handleResponse<T>);

    return withAuth ? withTokenRetry(fetchFn) : fetchFn();
}

// refresh 래퍼
export async function withTokenRetry<T>(
    requestFn: () => Promise<T>
): Promise<T> {
    try {
        return await requestFn();
    } catch (err) {
        if (err instanceof APIException && err.statusCode === 401) {
            await refreshToken();

            return await requestFn();
        }
        throw err;
    }
}
