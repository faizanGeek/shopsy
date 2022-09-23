import { Component, OnInit } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { Customer } from "src/app/common/customer";
import { CustomerService } from "src/app/services/customer.service";
import { EcommerceValidators } from "src/app/validators/ecommerce-validators";

@Component({
  selector: "app-signup",
  templateUrl: "./signup.component.html",
  styleUrls: ["./signup.component.scss"],
})
export class SignupComponent implements OnInit {
  constructor(
    private customerService: CustomerService,
    private router: Router
  ) {}
  signUpForm: FormGroup;
  emailPattern = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
  namePattern = "^[a-zA-Z ]+$";
  userDetails: Customer = new Customer();

  // authState: Observable<AuthState>;
  ngOnInit(): void {
    this.signUpForm = new FormGroup({
      email: new FormControl(null, [
        Validators.required,
        Validators.pattern(this.emailPattern),
      ]),
      name: new FormControl(null, [
        Validators.required,
        Validators.pattern(this.namePattern),
      ]),
      passwordGroup: new FormGroup(
        {
          newPassword: new FormControl(null, [
            Validators.required,
            Validators.minLength(6),
            Validators.maxLength(52),
          ]),
          newPasswordConfirm: new FormControl(null, [
            Validators.required,
            Validators.minLength(6),
            Validators.maxLength(52),
          ]),
        },
        EcommerceValidators.passwordMatchCheckValidator.bind(this)
      ),
    });

    //this.authState = this.store.select('auth');
  }

  onSubmitted() {
    console.log(this.signUpForm.value.email);
    this.userDetails.emailId = this.signUpForm.value.email;
    this.userDetails.name = this.signUpForm.value.name;
    this.userDetails.password = this.signUpForm.value.passwordGroup.newPassword;
    this.userDetails.password =
      this.signUpForm.value.passwordGroup.newPasswordConfirm;

    this.customerService.signUp(this.userDetails).subscribe({
      next: (response) => {
        console.log("successfully registered");
        this.router.navigateByUrl("/products");
      },
      error: (err) => {
        console.log("error " + err.message);
      },
    });
  }
}
