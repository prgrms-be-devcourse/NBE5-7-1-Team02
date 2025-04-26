import { Category } from "../enums/Category";

export interface Menu {
    id: number;
    name: string;
    price: number;
    category: Category;
    fileId: number;
    thumbnailUrl: string;
}
