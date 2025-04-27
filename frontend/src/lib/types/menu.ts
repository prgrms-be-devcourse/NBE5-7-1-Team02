import { Category } from "../enums/Category";
import { OrderStatus } from "../enums/OrderStatus";

export interface Menu {
    id: number;
    name: string;
    price: number;
    category: Category;
    fileId: number;
    status: OrderStatus;
    thumbnailUrl: string;
}
