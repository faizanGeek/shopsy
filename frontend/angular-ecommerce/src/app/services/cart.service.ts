import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { BehaviorSubject, Subject } from "rxjs";
import { CartItem } from "../common/cart-item";

@Injectable({
  providedIn: "root",
})
export class CartService {

  // theCartItem.quantity = 1;
  // console.log(theCartItem);

  cartItems: any = [];
  totalPrice: Subject<any> = new BehaviorSubject<any>(0);
  totalQuantity: Subject<any> = new BehaviorSubject<any>(0);
  cartItemUpdate: any = {
    cartItemId: 0,
    quantity: 0,
  };
  deletedCartItemProductId: any;
  deletedCartItemQuantity: any;
  constructor(private httpClient: HttpClient) {}

  private baseUrl = "http://localhost:8080/api";

  editCartItem(theCartItem: CartItem, quantity: number, phoneId:any) {
    // check if we already have the item in our cart
    // theCartItem.quantity = 1;
    // console.log(theCartItem);
    this.deletedCartItemProductId = theCartItem.product.id;
    this.getCartList(theCartItem.cartId).subscribe((data) => {
      this.cartItems = data;
      // console.log(data);

      this.computeCartTotals(quantity);

      if (quantity !== 0) {
        // 0 quantity means we have to delete this Item
        this.saveAndUpdate(theCartItem, quantity,phoneId);
      } else {
        this.deleteItem(theCartItem);
      }
    });

    // compute cart total price and total quantity
  }

  computeCartTotals(quantity: number) {
    //let totalPriceValue: number = 0;
    console.log(quantity);
    let totalQuantityValue: number = 0;
    console.log(this.cartItems);
    for (let currentCartItem of this.cartItems) {
      //  totalPriceValue += currentCartItem.quantity * currentCartItem.unitPrice;
      console.log(currentCartItem.product);
      totalQuantityValue += currentCartItem.quantity;
      if (currentCartItem.product.id === this.deletedCartItemProductId)
        this.deletedCartItemQuantity = currentCartItem.quantity;
    }
    console.log(totalQuantityValue);

    // publish the new values ... all subscribers will receive the new data
    //this.totalPrice.next(totalPriceValue);
    if (quantity !== 0) {
      this.totalQuantity.next(totalQuantityValue + quantity);
    } else {
      this.totalQuantity.next(
        totalQuantityValue - this.deletedCartItemQuantity
      );
    }

    // log cart data just for debugging purposes
  }

  saveAndUpdate(theCartItem: any, quantity: number,phoneId:any) {
    let alreadyExistsInCart: boolean = false;
    let existingCartItem: any = undefined;
    if (this.cartItems.length > 0) {
      // find the item in the cart based on item id

      existingCartItem = this.cartItems.find(
        (tempCartItem) => tempCartItem.product.id === theCartItem.product.id
      );

      // check if we found it
      alreadyExistsInCart = existingCartItem != undefined;
    }
    if (alreadyExistsInCart) {
      // increment the quantity
      //  console.log(  existingCartItem.quantity);
      // this
      console.log("asdsd");
      existingCartItem.quantity++;

      this.updateCartItemQuantity(existingCartItem.id, quantity,phoneId).subscribe(
        (response) => {
          console.log("cart Updated");
        },
        (error) => {
          console.log(error);
        }
      );
    } else {
      //this.cartItems.push(theCartItem);
      this.addCartItem(theCartItem,phoneId).subscribe(
        (response) => {
          console.log(response);
        },
        (error) => {
          console.log(error);
        }
      );
    }
  }

  deleteItem(theCartItem: any) {
    let existingCartItem: any = undefined;

    existingCartItem = this.cartItems.find(
      (tempCartItem) => tempCartItem.product.id === theCartItem.product.id
    );

    this.deleteCartItem(existingCartItem).subscribe(
      (response) => {
        console.log(response);
      },
      (error) => {
        console.log(error);
      }
    );
  }

  getCartList(cartId: any): Observable<any> {
    return this.httpClient.get<any>(
      `${this.baseUrl}/getCartItems?cartId=${cartId}`
    );
  }
  addCartItem(theCartItem: CartItem,phoneId:any): Observable<any> {
    return this.httpClient.post(`${this.baseUrl}/cartItem?phoneId=${phoneId}`, theCartItem, {
      responseType: "text",
    });
  }
  updateCartItemQuantity(cartId: number, quantity: number,phoneId:any) {
    console.log("cId " + cartId);
    console.log("q " + quantity);
    this.cartItemUpdate.cartItemId = cartId;
    this.cartItemUpdate.quantity = quantity;
    console.log(this.cartItemUpdate);

    return this.httpClient.put(
      `${this.baseUrl}/updateCartQuantity?phoneId=${phoneId}`,
      this.cartItemUpdate,
      {
        responseType: "text",
      }
    );
  }

  deleteCartItem(theCartItem: any) {
    return this.httpClient.delete<any>(
      `${this.baseUrl}/deleteCartItem?cartItemId=${theCartItem.id}`
    );
  }

  // remove(theCartItem: any) {
  //   get index of item in the array
  //   const itemIndex = this.cartItems.findIndex(
  //     (tempCartItem) => tempCartItem.id === theCartItem.id
  //   );

  //   if found, remove the item from the array at the given index
  //   if (itemIndex > -1) {
  //     this.cartItems.splice(itemIndex, 1);

  //     this.computeCartTotals();
  //   }
  // }

  applyCode(discountCodeField: string, token: any): Observable<any> {
    return this.httpClient.get<any>(
      `${this.baseUrl}/applyCode?code=${discountCodeField}&token=${token}`
    );
  }

  getCart(emailId:any): Observable<any> {
    return this.httpClient.get<any>(
      `${this.baseUrl}/cart?emailId=${emailId}`
    );
  }

  updateCart(phoneNo: any) {
    return this.httpClient.put(
      `${this.baseUrl}/updateCart`,this.cartItemUpdate,
      {
        responseType: "text",
      }
    );
  }
}
