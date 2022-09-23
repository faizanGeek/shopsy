import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Customer } from "../common/customer";
import { HttpClientModule } from "@angular/common/http";
import { Observable } from "rxjs";
import { Subject } from "rxjs";
import { BehaviorSubject } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class CustomerService {
  // loggedIn:boolean= false;
  loggedIn: Subject<boolean> = new BehaviorSubject<boolean>(false);

  private customerUrl = "http://localhost:8080/api";
  data: any;
  constructor(private httpClient: HttpClient) {}

  signUp(userDetails: any): Observable<any> {
    return this.httpClient.post(`${this.customerUrl}/customer`, userDetails, {
      responseType: "text",
    });
  }

  getCustomerDetail(token: any): Observable<any> {
    return this.httpClient.get<any>(
      `${this.customerUrl}/customerDetail?token=${token}`
    );
  }

  getEmailId() {
    return localStorage.getItem("emailId");
  }

  // getToken() {
  //   return localStorage.getItem("token");
  // }
}
