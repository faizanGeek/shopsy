import { HttpErrorResponse } from "@angular/common/http";
import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Params, Router } from "@angular/router";
import { Observable, Subscription } from "rxjs";
import { CartItem } from "src/app/common/cart-item";
import { AuthServicesService } from "src/app/services/auth-services.service";
import { CartService } from "src/app/services/cart.service";
import { CustomerService } from "src/app/services/customer.service";
import { ProductService } from "src/app/services/product.service";

@Component({
  selector: "app-product-detail",
  templateUrl: "./product-detail.component.html",
  styleUrls: ["./product-detail.component.scss"],
})
export class ProductDetailComponent implements OnInit {
  paramSubscription: Subscription;

  product: any;
  activeProductVariant: any;

  cartState: Observable<any>;
  innerLoading = true;

  productId: number;
  variant: number;
  categoryName: any;
  cartId: any;
  isItemAdded: boolean;
  phoneNo:any;

  isPopState = false;
  fetchError: HttpErrorResponse = null;
  routerSubscription: Subscription;

  activeTab = 1;

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private authService: AuthServicesService,
    private customerService: CustomerService,
    private cartService: CartService,
    private router: Router
  ) {}

  ngOnInit(): void {
  this.phoneNo =   this.authService.getMailId();
    this.paramSubscription = this.route.params.subscribe((params: Params) => {
      this.productId = params.productId;

      this.productService.getProduct(this.productId).subscribe(
        (data) => {
          this.product = data;
          let token = this.authService.getToken();
          this.customerService.getCustomerDetail(token).subscribe((data) => {
            this.cartId = data.customerCart.id;
            this.getCartStatus(this.cartId);
          });
          console.log(data);
          this.variant = params.variant ? params.variant : this.product.id;
          //   this.activeProductVariant = data.find(p => p.id === Number(this.variant));
          if (!this.activeProductVariant) {
            this.activeProductVariant = data;
          }
        },
        (error) => {
          console.log("error");
        }
      );
    });
  }
  getCartStatus(cartId: any) {
    this.cartService.getCartList(cartId).subscribe((data) => {
      console.log(data);
      console.log(this.product);
      for (let currentCartItem of data) {
        if (this.product.id === currentCartItem.product.id) {
          this.isItemAdded = true;
        } else {
          this.isItemAdded = false;
        }
      }
      console.log(this.isItemAdded);
    });
  }

  addToCart(product: any) {
    this.isItemAdded = false;
    const theCartItem = new CartItem();
    theCartItem.cartId = this.cartId;
    theCartItem.product = product;
    this.isItemAdded = true;
    this.cartService.editCartItem(theCartItem, 1,this.phoneNo);
  }

  setActiveTab(active: any) {
    this.activeTab = active;
  }

  setActiveVariant(vId: any) {}
  goToTheCart() {
    this.router.navigateByUrl("/cart");
  }
}
