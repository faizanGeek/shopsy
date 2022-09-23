import { Component, OnInit } from "@angular/core";
import { OktaAuthService } from "@okta/okta-angular";
import { LoginService } from "src/app/services/login.service";

@Component({
  selector: "app-login-status",
  templateUrl: "./login-status.component.html",
  styleUrls: ["./login-status.component.scss"],
})
export class LoginStatusComponent implements OnInit {
  isAuthenticated: boolean = false;
  userFullName: string;
  customer: any;

  constructor(
    private oktaAuthService: OktaAuthService,
    private loginService: LoginService
  ) {}

  ngOnInit(): void {
    // Subscribe to authentication state changes
    this.customer = {
      emailId: "",
      name: "",
      phoneNo: "",
      password: "",
    };
    this.oktaAuthService.$authenticationState.subscribe((result) => {
      this.isAuthenticated = result;
      this.getUserDetails();
    });

    // this.saveUserToDB()
  }

  getUserDetails() {
    if (this.isAuthenticated) {
      this.oktaAuthService.getUser().then((res) => {
        this.userFullName = res.name;
        this.customer.emailId = res.email;
        this.customer.name = res.name;
        this.saveUserToDB(this.customer);
        console.log(this.customer);
      });
    }
  }

  logout() {
    // Terminates the session with Okta and removes current tokens.
    this.oktaAuthService.signOut();
  }

  saveUserToDB(customer: any) {
    this.loginService.saveToDB(customer).subscribe({
      next: (response) => {
        alert(`Registered`);
      },
      error: (err) => {
        console.log(`There was an error: ${err.message}`);
      },
    });
  }
}
