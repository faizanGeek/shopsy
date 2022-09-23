import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class AddressService {
  deleteAddress(address: any) {
    return this.httpClient.put(`${this.baseUrl}/deleteAddress`, address, {
      responseType: "text",
    });
  }
  editAddress(address: {
    name: any;
    contactNumber: any;
    address: any;
    city: any;
    state: any;
    pinCode: any;
    customerPhoneNo: any;
    id: any;
  }) {
    return this.httpClient.put(`${this.baseUrl}/updateAddress`, address, {
      responseType: "text",
    });
  }

  private baseUrl = "http://localhost:8080/api";
  constructor(private httpClient: HttpClient) {}

  addAddress(address: {
    name: any;
    contactNumber: any;
    address: any;
    city: any;
    state: any;
    pinCode: any;
    customerPhoneNo: any;
  }) {
    return this.httpClient.post(`${this.baseUrl}/address`, address, {
      responseType: "text",
    });
  }

  getAddresses(customerEmailId: any) {
    return this.httpClient.get<any>(
      `${this.baseUrl}/address?emailId=${customerEmailId}`
    );
  }
}
