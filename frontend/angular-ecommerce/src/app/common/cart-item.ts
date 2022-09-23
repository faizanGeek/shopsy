import { Product } from "./product";

export class CartItem {
  cartId: string;
  product: any;
  quantity: number = 1;
  totalPrice:any = 0.0;
  totalDiscount:any = 0.0;
}
