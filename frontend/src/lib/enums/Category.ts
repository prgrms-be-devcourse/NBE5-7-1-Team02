export enum Category {
    COFFEE_BEAN = "COFFEE_BEAN",
    DRINK = "DRINK",
    DESSERT = "DESSERT",
}

export const CategoryLabels: Record<Category, string> = {
    [Category.COFFEE_BEAN]: "원두",
    [Category.DRINK]: "음료",
    [Category.DESSERT]: "디저트",
};
