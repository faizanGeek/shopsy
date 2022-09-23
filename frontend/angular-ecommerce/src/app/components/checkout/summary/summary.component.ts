import { Component, OnInit , Output ,EventEmitter } from "@angular/core";
import { Observable, Subscription } from "rxjs";
import { AuthServicesService } from "src/app/services/auth-services.service";
import { CartService } from "src/app/services/cart.service";

@Component({
  selector: "app-summary",
  templateUrl: "./summary.component.html",
  styleUrls: ["./summary.component.scss"],
})
export class SummaryComponent implements OnInit {
  @Output() parentFunctionForPrice:EventEmitter<any> =  new EventEmitter();
  @Output() parentFunctionForCartId:EventEmitter<any> =  new EventEmitter();

  cartState: Observable<any>;
 // cartItems:any;
  cart:any;
  orderState: Observable<any>;
  termsAccepted = false;
  cartItems:any;
  color:any = "green"
  emailId:any;
  totalCartPrice
  totalCargoPrice = 50
  totalDiscountPer
  showDiscountInput
  totalDiscountedPrice = 0.0
  cartItemCount;

  routerSubscription: Subscription;
  constructor(private authService:AuthServicesService,
    private cartService:CartService) {}

  ngOnInit(): void {
    this.emailId = this.authService.getMailId();
    this.cartService.getCart(this.emailId).subscribe(
      data=>{
           this.cart = data;
           console.log(this.cart);
           this.cartItems = this.cart.cartItem;
           this.computeCartItems();
           console.log(this.cartItems);
      },
      (error)=>{

      }
    )
  }
  computeCartItems() {

    this.cartItemCount = this.cart.totalQuantity;
    this.totalCartPrice = this.cart.cartPrice;
    this.totalDiscountPer = this.cart.totalDiscountPer;
    this.totalDiscountedPrice = this.cart.totalPrice +this.cart.coupanDiscountedPrice+50;
    this.parentFunctionForPrice.emit(this.totalDiscountedPrice);
    this.parentFunctionForCartId.emit(this.cart.id);
  }

  openTermsModel(){

  }

  openBankModal(){
    
  }
}
