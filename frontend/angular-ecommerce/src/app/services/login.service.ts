import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

@Injectable({
  providedIn: "root",
})
export class LoginService {
  private customerUrl = "http://localhost:8080/api/customer";

  constructor(private httpClient: HttpClient) {}

  saveToDB(customer: any) {
    return this.httpClient.post<any>(this.customerUrl, customer);
  }
}
