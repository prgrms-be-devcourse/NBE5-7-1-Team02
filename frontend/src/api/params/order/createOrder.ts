import { MenuItem } from "@/lib/types/menuItem";

export interface CreateOrderRequest {
    email: string;
    addressdto: {
        address: string;
        zipCode: string;
    };
    items: MenuItem[];
}

export interface CreateOrderResponse {
    id: number;
    email: string;
    addressdto: {
        address: string;
        zipCode: string;
    };
    items: MenuItem[];
}
