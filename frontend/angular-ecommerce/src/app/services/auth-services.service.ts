import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable } from "rxjs";
import * as CryptoJS from "crypto-js";

@Injectable({
  providedIn: "root",
})
export class AuthServicesService {
  private secretKey = "qazwsxedcrfv0abc";
  private encryptedTokens: any;
  private currentUserSubject: BehaviorSubject<any>;
  public currentUser: Observable<any>;
  private customerUrl = "http://localhost:8080/api";
  constructor(private httpClient: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<any>(
      localStorage.getItem("token")
    );
    this.currentUser = this.currentUserSubject.asObservable();
    // this.encryptUsingAES256();
  }
  sendOtp(smsDTO:any): Observable<any>{
    console.log(smsDTO);
    return this.httpClient.post(`${this.customerUrl}/sendOtp`, smsDTO ,{
      responseType: "text",
    });
  }
  doLogin(customer: any): Observable<any> {
    console.log(customer);
    return this.httpClient.post<any>(`${this.customerUrl}/token`, customer);
    //return null;
  }

  loginUser(tokenWithEmail: any) {
    let _key = CryptoJS.enc.Utf8.parse(this.secretKey);
    let _iv = CryptoJS.enc.Utf8.parse(this.secretKey);
    let encrypted = CryptoJS.AES.encrypt(JSON.stringify(tokenWithEmail), _key, {
      keySize: 16,
      iv: _iv,
      mode: CryptoJS.mode.ECB,
      padding: CryptoJS.pad.Pkcs7,
    });
    console.log(encrypted.toString());
    localStorage.setItem("token", encrypted.toString());
    let decrypted = this.decryptUsingAES256(localStorage.getItem("token"));
    this.currentUserSubject.next(decrypted.token);
  }

  decryptUsingAES256(encrypted: string) {
    let _key = CryptoJS.enc.Utf8.parse(this.secretKey);
    let _iv = CryptoJS.enc.Utf8.parse(this.secretKey);

    let decrypted = CryptoJS.AES.decrypt(encrypted, _key, {
      keySize: 16,
      iv: _iv,
      mode: CryptoJS.mode.ECB,
      padding: CryptoJS.pad.Pkcs7,
    }).toString(CryptoJS.enc.Utf8);
    decrypted = JSON.parse(decrypted);
    return decrypted;
  }

  isLoggedIn() {
    let token = localStorage.getItem("token");
    if (token == undefined || token == "" || token == null) {
      return false;
    } else {
      return true;
    }
  }

  logout() {
    // remove user from local storage and set current user to null
    localStorage.removeItem("token");
    this.currentUserSubject.next(0);
    //  console.log(this.currentUser)
  }

  getToken() {
    console.log("hi");
    let token: string = localStorage.getItem("token");
    let decrypted;
    if (token != "" && token != null) {
      decrypted = this.decryptUsingAES256(token);
      return decrypted.token;
    }
    return token;
  }
  getMailId() {
    let token: string = localStorage.getItem("token");
    let decrypted = this.decryptUsingAES256(token);
    return decrypted.phoneNo;
  }
}
