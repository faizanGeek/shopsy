import { Component, OnInit } from "@angular/core";
import { ProductService } from "src/app/services/product.service";
import { Product } from "src/app/common/product";
import { ActivatedRoute, Router } from "@angular/router";
import { CartService } from "src/app/services/cart.service";
import { Input } from "@angular/core";
import { CartItem } from "src/app/common/cart-item";
import { CustomerService } from "src/app/services/customer.service";
import { FlashMessagesService } from "angular2-flash-messages";
import { NotificationsService } from "angular2-notifications";
import { AuthServicesService } from "src/app/services/auth-services.service";

//import { Router } from '@angular/router';

@Component({
  selector: "app-product-list",
  templateUrl: "./product-list-grid.component.html",
  styleUrls: ["./product-list.component.scss"],
})
export class ProductListComponent implements OnInit {
  @Input() pageSize = 12;
  canFetch = false;
  selectedPage = 0;
  selectedSort = "any";
  selectedCategory = "any";
  selectedId = 0;
  selectedColor = "any";
  isLoggedIn: boolean;
  minPrice = "0";
  maxPrice = "undefined";
  totalNoOfItems: any;
  totalNoOfItemsCopy: any;
  colors: any;
  productCategory: any;
  pageDetail: any;
  products: any;
  currentCategoryId: number;
  searchMode: boolean;
  page: any = 0;
  cartStatus: string = "notAdded";
  items: any;
  cartId: any;
  itemsCopy: any;
  phoneNo:any;
  sortBy = [
    {
      display: "Any",
      value: "any",
    },
    {
      display: "Lowest Price",
      value: "lowest",
    },
    {
      display: "Highest Price",
      value: "highest",
    },
  ];

  constructor(
    private productService: ProductService,
    private route: ActivatedRoute,
    private cartService: CartService,
    private customerService: CustomerService,
    private flashMessage: FlashMessagesService,
    private notiService: NotificationsService,
    private authService: AuthServicesService,
    private router: Router
  ) {
    this.authService.currentUser.subscribe((token) => {
      if (token == undefined || token == "" || token == null) {
        this.isLoggedIn = false;
        this.logOut();
      } else {
        this.isLoggedIn = true;
      }
    });
  }

  ngOnInit() {
    this.phoneNo = this.authService.getMailId();
    this.handleListProducts(
      this.selectedId,
      this.minPrice,
      this.maxPrice,
      this.selectedSort
    );

    this.listProductCategory();
    this.getColorsList();
  }
  logOut() {
    if (this.items != undefined) {
      for (let item of this.items) {
        item.cartStatus = "notAdded";
      }
    }
  }
  getCustomerDetail(cartId: any, products: any) {
    let token = this.authService.getToken();
    this.customerService.getCustomerDetail(token).subscribe((data) => {
      this.cartId = data.customerCart.id;
      this.getCartStatus(this.cartId, products);
      //    console.log(data);
    });
  }

  listProductCategory() {
    this.productService.getProductCategories().subscribe((data) => {
      this.productCategory = data;
    });
  }

  handleListProducts(
    selectedId: any,
    minPrice: any,
    maxPrice: any,
    selectedSort: any
  ) {
    let theKeyword: string = "";

    if (this.route.snapshot.paramMap.has("keyword")) {
      theKeyword = this.route.snapshot.paramMap.get("keyword");
    }
    this.productService
      .getProductList(selectedId, minPrice, maxPrice, selectedSort, theKeyword)
      .subscribe((data) => {
        //  this.items=data.productList;
        //  console.log(data);
        if (this.isLoggedIn == true) {
          this.getCustomerDetail(this.cartId, data);
        } else {
          this.items = data.productList;
          if (this.items != undefined) {
            for (let item of this.items) {
              item.cartStatus = "notAdded";
            }
          }
        }
        //  this.itemsCopy = this.items;
        this.totalNoOfItems = data.pageDetail.totalElements;
        this.totalNoOfItemsCopy = this.totalNoOfItems;
      });
  }
  getCartStatus(cartId: any, products: any) {
    this.cartService.getCartList(cartId).subscribe((data) => {
      console.log(data);
      this.setCartStatus(data, products);
      // this.totalQuantity = data.length;
    });
  }

  setCartStatus(cartItems: any, products: any) {
    // this.totalQuantity = data.length;
    //console.log(cartItems);
    for (let product of products.productList) {
      product.cartStatus = "notAdded";
    }
    for (let currentCartItem of cartItems) {
      for (let product of products.productList) {
        if (product.id === currentCartItem.product.id) {
          //  console.log("p " + product.id + " it " + currentCartItem.product.id);
          product.cartStatus = "added";
          // console.log(product);
        }
      }
      //  //  this.totalQuantity += currentCartItem.quantity;
    }
    this.items = products.productList;
    // console.log(this.totalQuantity);
  }
  getColorsList() {
    this.productService.getColorsList().subscribe((data) => {
      this.colors = data;
    });
    // throw new Error('Method not implemented.');
  }

  pagination(inc: any) {
    this.page = this.page + inc;
    console.log(this.page);
    this.ngOnInit();
  }

  addToCart(theProduct: any) {
    this.showFlash();
    const theCartItem = new CartItem();
    theCartItem.cartId = this.cartId;
    theCartItem.product = theProduct;
    console.log("p: ");
    console.log(this.items);
    for (let item of this.items) {
      if (item.id === theProduct.id) {
        item.cartStatus = "added";
      }
    }

    this.cartService.editCartItem(theCartItem, 1,this.phoneNo);
  }

  onChangePage(products: Array<any>) {
    this.products = products;
  }

  selectCategory(cId: any, cName: any) {
    this.selectedCategory = cName;
    this.selectedId = cId;
    this.handleListProducts(
      this.selectedId,
      this.minPrice,
      this.maxPrice,
      this.selectedSort
    );
  }

  clearCategory() {
    this.selectedCategory = "any";
    this.selectedId = 0;
    this.handleListProducts(
      this.selectedId,
      this.minPrice,
      this.maxPrice,
      this.selectedSort
    );
  }

  selectMin(minPrice: any) {
    this.minPrice = minPrice.trim().length === 0 ? "0" : minPrice.trim();
    this.handleListProducts(
      this.selectedId,
      this.minPrice,
      this.maxPrice,
      this.selectedSort
    );
    console.log(minPrice);
  }
  selectMax(maxPrice: any) {
    this.maxPrice = maxPrice.trim().length === 0 ? "0" : maxPrice.trim();
    this.handleListProducts(
      this.selectedId,
      this.minPrice,
      this.maxPrice,
      this.selectedSort
    );
  }

  clearPrice() {
    this.minPrice = "0";
    this.maxPrice = "undefined";
    this.handleListProducts(
      this.selectedId,
      this.minPrice,
      this.maxPrice,
      this.selectedSort
    );
  }

  clearColor() {}

  selectColor(cName: any) {}

  selectSort(sort: any) {
    this.selectedSort = sort;
    this.handleListProducts(
      this.selectedId,
      this.minPrice,
      this.maxPrice,
      this.selectedSort
    );
  }

  showFlash() {
    // 1st parameter is a flash message text
    // 2nd parameter is optional. You can pass object with options.
    // this.flashMessage.show('Welcome To TheRichPost.com', { cssClass: 'alert-success', timeout: 2000 });
    this.notiService.success("Product is Added", "", {
      position: ["middle", "top"],
      timeOut: 1000,
      animate: "fade",
      maxLength: 5,
      showProgressBar: true,
    });
  }

  goToItem(productId) {
    this.router.navigate(["/detail/", productId], { relativeTo: this.route });
  }
}
