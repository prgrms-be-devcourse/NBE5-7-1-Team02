import { Category } from "../../../lib/enums/Category";

export interface UpdateMenuRequest {
    name: string;
    price: number;
    category: Category;
    imageId: number | null;
}
