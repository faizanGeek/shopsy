import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";

import { AppComponent } from "./app.component";
import { ProductListComponent } from "./components/product-list/product-list.component";
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { ProductService } from "./services/product.service";
import { Routes, RouterModule, Router } from "@angular/router";
import { ProductCategoryMenuComponent } from "./components/product-category-menu/product-category-menu.component";
import { SearchComponent } from "./components/search/search.component";
import { ProductDetailsComponent } from "./components/product-details/product-details.component";
import { CartStatusComponent } from "./components/cart-status/cart-status.component";
import { CartDetailsComponent } from "./components/cart-details/cart-details.component";

import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { LoginStatusComponent } from "./components/login-status/login-status.component";

import { MatSidenavModule } from "@angular/material/sidenav";
// import {ToastrModule} from 'ngx-toastr';
//import {NgbModule} from '@ng-bootstrap/ng-bootstrap'
import {
  OKTA_CONFIG,
  OktaAuthModule,
  OktaCallbackComponent,
  OktaAuthGuard,
} from "@okta/okta-angular";

import myAppConfig from "./config/my-app-config";
import { MembersPageComponent } from "./components/members-page/members-page.component";
import { SignupComponent } from "./components/signup/signup.component";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { JwPaginationModule } from "jw-angular-pagination";
import { NgxPaginationModule } from "ngx-pagination";
import { HeaderComponent } from "./components/header/header.component";
import { AuthGuard } from "./auths/auth.guard";
import { AuthInterceptor } from "./services/auth.interceptor";
import { MatStepperModule } from "@angular/material/stepper";
import { MatButtonModule } from "@angular/material/button";
import { FlashMessagesModule } from "angular2-flash-messages";
import { SimpleNotificationsModule } from "angular2-notifications";
import { CartComponent } from "./components/cart/cart.component";
import { ProductDetailComponent } from "./components/product-detail/product-detail.component";
import { ProductDetailModule } from "./components/product-detail/product-detail.module";
import { AddressesComponent } from "./components/addresses/addresses.component";
import { MatFormField, MatFormFieldModule, MatLabel } from "@angular/material/form-field";
import { A11yModule } from "@angular/cdk/a11y";
import { ClipboardModule } from "@angular/cdk/clipboard";
import { DragDropModule } from "@angular/cdk/drag-drop";
import { PortalModule } from "@angular/cdk/portal";
import { ScrollingModule } from "@angular/cdk/scrolling";
import { CdkStepperModule } from "@angular/cdk/stepper";
import { CdkTableModule } from "@angular/cdk/table";
import { CdkTreeModule } from "@angular/cdk/tree";
import { MatAutocompleteModule } from "@angular/material/autocomplete";
import { MatBadgeModule } from "@angular/material/badge";
import { MatBottomSheetModule } from "@angular/material/bottom-sheet";
import { MatButtonToggleModule } from "@angular/material/button-toggle";
import { MatCardModule } from "@angular/material/card";
import { MatCheckboxModule } from "@angular/material/checkbox";
import { MatChipsModule } from "@angular/material/chips";
import { MatNativeDateModule, MatRippleModule } from "@angular/material/core";
import { MatDatepickerModule } from "@angular/material/datepicker";
import { MatDialogModule } from "@angular/material/dialog";
import { MatDividerModule } from "@angular/material/divider";
import { MatExpansionModule } from "@angular/material/expansion";
import { MatGridListModule } from "@angular/material/grid-list";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { MatListModule } from "@angular/material/list";
import { MatMenuModule } from "@angular/material/menu";
import { MatPaginatorModule } from "@angular/material/paginator";
import { MatProgressBarModule } from "@angular/material/progress-bar";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { MatRadioModule } from "@angular/material/radio";
import { MatSelectModule } from "@angular/material/select";
import { MatSlideToggleModule } from "@angular/material/slide-toggle";
import { MatSliderModule } from "@angular/material/slider";
import { MatSnackBarModule } from "@angular/material/snack-bar";
import { MatSortModule } from "@angular/material/sort";
import { MatTableModule } from "@angular/material/table";
import { MatTabsModule } from "@angular/material/tabs";
import { MatToolbarModule } from "@angular/material/toolbar";
import { MatTooltipModule } from "@angular/material/tooltip";
import { MatTreeModule } from "@angular/material/tree";
import { SignInComponent } from './components/sign-in/sign-in.component';

const oktaConfig = Object.assign(
  {
    onAuthRequired: (oktaAuth, injector) => {
      const router = injector.get(Router);

      // Redirect the user to your custom login page
      router.navigate(["/login"]);
    },
  },
  myAppConfig.oidc
);

const routes: Routes = [
  {
    path: "members",
    component: MembersPageComponent,
    canActivate: [OktaAuthGuard],
  },
  { path: "user/address", component: AddressesComponent },
  { path: "signin", component: SignInComponent },
  { path: "signup", component: SignupComponent },
  { path: "login/callback", component: OktaCallbackComponent },
  { path: "cart", component: CartComponent },
  { path: "products/:id", component: ProductDetailsComponent },
  { path: "products/search/:keyword", component: ProductListComponent },
  { path: "products/category/:id", component: ProductListComponent },
  { path: "category", component: ProductListComponent },
  { path: "products", component: ProductListComponent },
  { path: "", redirectTo: "/products", pathMatch: "full" },
  {
    path: "checkout", 
    loadChildren: () =>
      import("./components/checkout/checkout.module").then(
        (m) => m.CheckoutModule
      ),
  },
  { path: 'login', loadChildren: () => import('./components/login/login.module').then(m => m.LoginModule) },
  { path: "**", redirectTo: "/products", pathMatch: "full" },
];

@NgModule({
  declarations: [
    AppComponent,
    ProductListComponent,
    ProductCategoryMenuComponent,
    SearchComponent,
    ProductDetailsComponent,
    CartStatusComponent,
    CartDetailsComponent,
    LoginStatusComponent,
    MembersPageComponent,
    SignupComponent,
    HeaderComponent,
    CartComponent,
    AddressesComponent,
    SignInComponent,
  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    OktaAuthModule,
    BrowserAnimationsModule,
    NgbModule,
    JwPaginationModule,
    NgxPaginationModule,
    FlashMessagesModule.forRoot(),
    SimpleNotificationsModule.forRoot(),
    ProductDetailModule,
    A11yModule,
    ClipboardModule,
    CdkStepperModule,
    CdkTableModule,
    CdkTreeModule,
    DragDropModule,
    MatAutocompleteModule,
    MatBadgeModule,
    MatBottomSheetModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatCardModule,
    MatCheckboxModule,
    MatChipsModule,
    MatStepperModule,
    MatDatepickerModule,
    MatDialogModule,
    MatDividerModule,
    MatExpansionModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatNativeDateModule,
    MatPaginatorModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatRadioModule,
    MatRippleModule,
    MatSelectModule,
    MatSidenavModule,
    MatSliderModule,
    MatSlideToggleModule,
    MatSnackBarModule,
    MatSortModule,
    MatTableModule,
    MatTabsModule,
    MatToolbarModule,
    MatTooltipModule,
    MatTreeModule,
    PortalModule,
    ScrollingModule,
    
  ],
  providers: [
    ProductService,
    [{ provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }],
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
