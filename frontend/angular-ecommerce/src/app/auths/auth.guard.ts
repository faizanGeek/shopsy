import { Injectable } from "@angular/core";
import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  UrlTree,
} from "@angular/router";
import { Observable } from "rxjs";
import { AuthServicesService } from "../services/auth-services.service";
import { CustomerService } from "../services/customer.service";

@Injectable({
  providedIn: "root",
})
export class AuthGuard implements CanActivate {
  isLoggedIn: boolean;
  constructor(
    private customerService: CustomerService,
    private authService: AuthServicesService
  ) {}
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    if (this.authService.isLoggedIn()) {
      return true;
    } else {
      return false;
    }
  }
}
