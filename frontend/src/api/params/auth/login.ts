export interface LoginRequest {
    id: string;
    password: string;
}

export interface LoginJwtResponse {
    accessToken: string;
    refreshToken: string;
}
