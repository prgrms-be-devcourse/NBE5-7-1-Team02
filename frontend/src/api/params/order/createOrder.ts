import { CartItem } from "@/lib/types/cartItem";

export interface CreateOrderRequest {
    email: string;
    addressdto: {
        address: string;
        zipCode: string;
    };
    items: CartItem[];
}
