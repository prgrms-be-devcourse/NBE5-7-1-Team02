/**
 * 주문 생성할 때 필요한 장바구니
 */
export interface CartItem {
    menuId: number;
    name: string;
    price: number;
    quantity: number;
}
