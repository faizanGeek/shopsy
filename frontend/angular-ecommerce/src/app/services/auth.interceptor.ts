import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HTTP_INTERCEPTORS,
} from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { AuthServicesService } from "./auth-services.service";
import { CustomerService } from "./customer.service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(
    private customerService: CustomerService,
    private authService: AuthServicesService
  ) {}
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    let newReq = req;
    let token = this.authService.getToken();
    if (token != null) {
      newReq = newReq.clone({
        setHeaders: { Authorization: `Bearer ${token}` },
      });
    }
    return next.handle(newReq);
  }
}
