import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Product } from "../common/product";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { ProductCategory } from "../common/product-category";

@Injectable({
  providedIn: "root",
})
export class ProductService {
  private baseUrl = "http://localhost:8080/api";

  constructor(private httpClient: HttpClient) {}

  getProductList(
    theCategoryId: any,
    minPrice: any,
    maxPrice: any,
    selectedSort: any,
    theKeyword: any
  ): Observable<any> {
    // alert(selectedSort);
    return this.httpClient
      .get<any>(`${this.baseUrl}/products?category_id=${theCategoryId}
    &min_price=${minPrice}&max_price=${maxPrice}&sort_by_price=${selectedSort}&name=${theKeyword}`);
  }

  getColorsList(): Observable<any> {
    return this.httpClient.get<any>(`${this.baseUrl}/colors`);
  }

  getProductListByCategory(theCategoryId: number): Observable<any> {
    //console.log(this.httpClient.get<Product[]>(`${this.baseUrl}?category=${theCategoryId}`));
    return this.httpClient.get<any>(
      `${this.baseUrl}/products?category=${theCategoryId}`
    );
  }

  getProductCategories(): Observable<any> {
    return this.httpClient.get<any>(`${this.baseUrl}/product-category`);
  }

  getProductCategory(categoryId: any): Observable<any> {
    return this.httpClient.get<any>(
      `${this.baseUrl}/productCategory?categoryId=${categoryId}`
    );
  }

  searchProducts(theKeyword: string, page: any): Observable<any> {
    return this.httpClient.get<any>(
      `${this.baseUrl}/products?name=${theKeyword}&page=${page}`
    );
  }

  getProduct(theProductId: number): Observable<any> {
    return this.httpClient.get<any>(
      `${this.baseUrl}/product?id=${theProductId}`
    );
  }
}
