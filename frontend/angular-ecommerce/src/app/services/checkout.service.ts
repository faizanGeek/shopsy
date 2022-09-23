import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Order } from "../common/order";
import { Purchase } from "../common/purchase";

@Injectable({
  providedIn: "root",
})
export class CheckoutService {
  private purchaseUrl = "http://localhost:8080/api";
  constructor(private httpClient: HttpClient) {}

  placeOrder(order: Order): Observable<any> {
    return this.httpClient.post(`${this.purchaseUrl}/order`, order,{responseType: "text"});
  }

  proceedPayment(paymentPayload:any): Observable<any>{
    return this.httpClient.post<any>(`${this.purchaseUrl}/payment-details`, paymentPayload)
  }

  payuCallback(paymentResponse:any):Observable<any>{
    return this.httpClient.post(`${this.purchaseUrl}/payment-response`,paymentResponse, {responseType: "text"})
  }
}
