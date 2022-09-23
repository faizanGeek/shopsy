import { DoCheck } from "@angular/core";
import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { AuthServicesService } from "src/app/services/auth-services.service";
import { CartService } from "src/app/services/cart.service";
// import { ToastrService } from 'ngx-toastr';
import { CustomerService } from "src/app/services/customer.service";

@Component({
  selector: "app-header",
  templateUrl: "./header.component.html",
  styleUrls: ["./header.component.scss"],
})
export class HeaderComponent implements OnInit, DoCheck {
  isCollapsed = true;
  isLoggedIn: boolean = false;
  customerName: any;
  customerEmail: any;
  cartId: any;
  totalQuantity: number = 0;
  intialStage: boolean = true;
  token: any;
  constructor(
    private router: Router,
    private customerService: CustomerService,
    private cartService: CartService,
    private authService: AuthServicesService
  ) {
    this.authService.currentUser.subscribe((token) => {
      if (token == undefined || token == "" || token == null) {
        this.isLoggedIn = false;
      } else {
        this.isLoggedIn = true;
        console.log("1 " + this.isLoggedIn);
        this.totalQuantity = 0;
        this.updateCartStatus();
      }
    });
  }

  ngOnInit() {}
  updateCartStatus() {
    this.cartService.totalQuantity.subscribe((data) => {
      this.totalQuantity = data;
      console.log("2 " + this.totalQuantity);
      //console.log(this.totalQuantity + " " + this.isLoggedIn )
      if (this.totalQuantity === 0) {
        this.getCustomerDetail();
      }
    });
  }

  ngDoCheck() {
    //  this.intialStage =false;
  }

  searchProduct(search: any) {
    if (search.value.trim().length) {
      this.router.navigateByUrl(`/products/search/${search.value}`);
    }
  }
  signOut() {
    this.authService.logout();
    this.isLoggedIn = false;
    this.intialStage = true;
    this.totalQuantity = 0;
    //  window.location.reload();
    this.router.navigateByUrl("/products");
    console.log("hi");
  }
  getCustomerDetail() {
    let token = this.authService.getToken();
    console.log("3 " + token);
    this.customerService.getCustomerDetail(token).subscribe((data) => {
      //  console.log(data);
      this.cartId = data.customerCart.id;
      // console.log(this.cartId);
      // console.log("cartId: "+ this.cartId)
      console.log("4 " + this.cartId);

      this.getCartQantity(this.cartId);
    });
  }
  getCartQantity(cartId: any) {
    this.cartService.getCartList(cartId).subscribe((data) => {
      //    console.log(data);
      console.log("5 " + data);

      this.getTotalQuantity(data);
      // this.totalQuantity = data.length;
    });
  }

  getTotalQuantity(cartItems: any) {
    // this.totalQuantity = data.length;

    for (let currentCartItem of cartItems) {
      this.totalQuantity += currentCartItem.quantity;
    }
    console.log("6 " + this.totalQuantity);
  }
}
