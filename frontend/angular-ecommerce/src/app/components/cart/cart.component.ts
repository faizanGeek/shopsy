import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { Subscription } from "rxjs";
import { CartItem } from "src/app/common/cart-item";
import { AuthServicesService } from "src/app/services/auth-services.service";
import { CartService } from "src/app/services/cart.service";
import { CustomerService } from "src/app/services/customer.service";

@Component({
  selector: "app-cart",
  templateUrl: "./cart.component.html",
  styleUrls: ["./cart.component.scss"],
})
export class CartComponent implements OnInit {
  // cartState: Observable<CartState>;
  cartItemCountSubscription: Subscription;
  showDiscountInput = false;
  cartItems: any;
  applyCodeShow = false;
  cartItemCount = 0;
  cart: any;
  totalCartPrice: any;
  totalCargoPrice: any = 50;
  totalDiscountPer: any;
  totalDiscountedPrice: any = 0.0;
  discountPercent: number;
  interestedItems: any;
  coupanCodeDiscount: any;
  isCodeValid: any;
  totalPrice: number;
  isLoggedIn: boolean = false;
  cartId: any;
  interestedCards: any;
  phoneNo:any;
  coupanCode:any = null;

  constructor(
    private cartService: CartService,
    private customerService: CustomerService,
    private authService: AuthServicesService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.authService.currentUser.subscribe((token) => {
      if (token == undefined || token == "" || token == null) {
        this.isLoggedIn = false;
      } else {
        this.isLoggedIn = true;
        this.cartService.totalQuantity.subscribe(
          (data) => (this.cartItemCount = data)
        );
        this.getCustomerDetail();
      }
    });
  }

  ngOnInit(): void {
    this.phoneNo = this.authService.getMailId();
  }

  getCustomerDetail() {
    let token = this.authService.getToken();
    this.customerService.getCustomerDetail(token).subscribe((data) => {
      this.cartId = data.customerCart.id;
      this.cartItemCountSubscription = this.cartService
        .getCartList(this.cartId)
        .subscribe((data) => {
          this.cartItems = data;
          console.log(data);
          this.countCartItems(data);
        });
      console.log(this.cartItemCountSubscription);
    });
  }
  countCartItems(cartItems: any) {
    let total: any = 0;
    let totalPrice: any = 0;
    let totalDiscountPer: any = 0.0;
    let totalDiscountedPrice: any = 0.0;
    for (let item of cartItems) {
      total = total + item.quantity;
      totalPrice = totalPrice + item.product.unitPrice * item.quantity;
      totalDiscountPer =
        totalDiscountPer + item.product.productDiscount * item.quantity;
    }
    if (this.cartItemCount === 0) this.cartItemCount = total;
    this.totalCartPrice = totalPrice;

    this.totalDiscountPer = totalDiscountPer / this.cartItemCount;
    this.totalDiscountedPrice =
      this.totalCartPrice - (this.totalCartPrice * this.totalDiscountPer) / 100;
    console.log(this.totalDiscountPer);
    console.log(total);
  }

  goToItem(productId: any) {
    this.router.navigate(["/detail/", productId], { relativeTo: this.route });
  }

  amountDecrement(cartItem: any) {
    let quantityIsGreaterThanOne: boolean = true;
    for (let item of this.cartItems) {
      if (item.id === cartItem.id) {
        if (item.quantity > 1) {
          item.quantity = item.quantity - 1;
          this.totalCartPrice = this.totalCartPrice - item.product.unitPrice;
          this.totalDiscountPer =
            (this.totalDiscountPer * item.quantity -
              item.product.productDiscount) /
            item.quantity;
          this.totalDiscountedPrice =
            this.totalCartPrice -
            (this.totalCartPrice * this.totalDiscountPer) / 100;
        } else {
          quantityIsGreaterThanOne = false;
        }
      }
    }
    let theCartItem = new CartItem();
    theCartItem = cartItem;
    if (quantityIsGreaterThanOne) {
      this.cartService.editCartItem(theCartItem, -1,this.phoneNo);
    } else {
      this.removeFromCart(theCartItem);
    }
  }

  amountIncrement(cartItem: any) {
    let quantityIsLessThanEqualToFive: boolean = true;
    for (let item of this.cartItems) {
      if (item.id === cartItem.id) {
        if (item.quantity <= 4) {
          console.log("hi",this.totalDiscountPer,this.cartItemCount,item.product.productDiscount)
          this.totalDiscountPer =
            ((this.totalDiscountPer * this.cartItemCount) +
              item.product.productDiscount) /
              (this.cartItemCount+1);
          console.log(this.totalDiscountPer);    
          
          this.totalCartPrice = this.totalCartPrice + item.product.unitPrice;
          //  this.totalDiscountPer=this.totalDiscountPer*2
          this.totalDiscountedPrice =
            this.totalCartPrice -
            (this.totalCartPrice * this.totalDiscountPer) / 100;
            item.quantity = item.quantity + 1;
        } else {
          quantityIsLessThanEqualToFive = false;
        }
      }
    }
    if (quantityIsLessThanEqualToFive) {
      let theCartItem = new CartItem();
      theCartItem = cartItem;
      console.log(theCartItem);
      this.cartService.editCartItem(theCartItem, 1,this.phoneNo);
    }
  }

  removeFromCart(cartItem: any) {
    for (let item of this.cartItems) {
      if (item.id === cartItem.id) {
        item.quantity = item.quantity + 1;
        this.totalCartPrice =
          this.totalCartPrice - item.product.unitPrice * item.quantity;
        this.totalDiscountPer =
          (this.totalDiscountPer * item.quantity +
            item.product.productDiscount * item.quantity) /
          item.quantity;
        this.totalDiscountedPrice =
          this.totalCartPrice -
          (this.totalCartPrice * this.totalDiscountPer) / 100;
      }
    }
    this.cartItems = this.cartItems.filter((item) => item != cartItem);
    let theCartItem = new CartItem();
    theCartItem = cartItem;
    this.cartService.editCartItem(theCartItem, 0,this.phoneNo);
  }

  activatePurchase() {
    let token = this.authService.getToken();
   
    this.cartService.applyCode(this.coupanCode , token).subscribe(
      (data) => {
        //  console.log(data.discount);
      },
      (error) => {
        console.log(error);
        this.isCodeValid = "false";
      }
    );

    this.router.navigate(["/checkout"], { relativeTo: this.route });

  }

  applyCode(discountCodeField) {
    let token = this.authService.getToken();
    console.log(discountCodeField.value);
    this.coupanCode = discountCodeField.value
    this.coupanCodeDiscount = 0.0;
    this.isCodeValid = " ";
    this.cartService.applyCode(this.coupanCode , token).subscribe(
      (data) => {
        //  console.log(data.discount);
        this.coupanCodeDiscount = data.discount;
        this.isCodeValid = "true";
        this.totalDiscountedPrice =
          this.totalDiscountedPrice -
          (this.totalDiscountedPrice * this.coupanCodeDiscount) / 100;
          this.totalDiscountPer = this.totalDiscountPer +  this.coupanCodeDiscount; 
      },
      (error) => {
        console.log(error);
        this.isCodeValid = "false";
      }
    );
  }

  scrollLeft() {
    this.interestedCards.nativeElement.scrollLeft -= 250;
  }
  scrollRight() {
    this.interestedCards.nativeElement.scrollLeft -= 250;
  }
}
