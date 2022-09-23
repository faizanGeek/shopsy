import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ReactiveFormsModule } from "@angular/forms";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { ProductDetailComponent } from "./product-detail.component";
import { RouterModule, Routes } from "@angular/router";
import { RelatedComponent } from "./related/related.component";

export const ProductDetailRoutes: Routes = [
  { path: "detail/:productId", component: ProductDetailComponent },
  { path: "detail/:productId/:variant", component: ProductDetailComponent },
];

@NgModule({
  declarations: [ProductDetailComponent, RelatedComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(ProductDetailRoutes),
    ReactiveFormsModule,
    NgbModule,
  ],
})
export class ProductDetailModule {}
