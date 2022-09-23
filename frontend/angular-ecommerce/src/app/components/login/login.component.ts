import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Customer } from 'src/app/common/customer';
import { AuthServicesService } from 'src/app/services/auth-services.service';
import { CustomerService } from 'src/app/services/customer.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  signInForm: FormGroup;
  signInWithPhone:FormGroup;
  emailPattern = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
  loginWithPhone:boolean = false;
  customer: Customer = new Customer();
 

  constructor(
    private customerService: CustomerService,
    private router: Router,
    private authService: AuthServicesService
  ) {
  
  }
 
  ngOnInit() {
   
    this.signInForm = new FormGroup({
      email: new FormControl(null, [
        Validators.required,
        Validators.pattern(this.emailPattern),
      ]),
      password: new FormControl(null, [
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(52),
      ]),
    });
   
    // this.authState = this.store.select('auth');
  }
 
  onSubmitted() {
 
    this.customer.emailId = this.signInForm.value.email;
    this.customer.password = this.signInForm.value.password;
    //  console.log("asd"+this.signInForm.value.email);
    this.authService.doLogin(this.customer).subscribe(
      (response: any) => {
        console.log(response);
        this.authService.loginUser(response);
        this.router.navigate(["products"]);
      },
      (error) => {
        console.log(error);
      }
    );
  }


}
