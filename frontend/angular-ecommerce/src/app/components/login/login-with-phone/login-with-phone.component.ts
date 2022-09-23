import { Component, OnInit, Pipe, PipeTransform } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgxOtpInputConfig } from "ngx-otp-input";
import { Subscription, timer } from 'rxjs';
import { AuthServicesService } from 'src/app/services/auth-services.service';
import { EcommerceValidators } from 'src/app/validators/ecommerce-validators';


export class MyService {
  getCounter(tick) {
    return timer(0, tick);
  }
}
@Component({
  selector: 'app-login-with-phone',
  templateUrl: './login-with-phone.component.html',
  styleUrls: ['./login-with-phone.component.scss'],
  providers:[MyService]
})
export class LoginWithPhoneComponent implements OnInit {

  //validate:EcommerceValidators;
  otpSent:boolean=false;
  isOtpExpired= false;
  isOtpEntered:boolean = false;
  otp:any;
  temp:boolean = true;
  countDown: Subscription;
  phoneNo=""
  name=""
  counter = 600;
  tick = 1000;
  otpInputConfig: NgxOtpInputConfig = {
    otpLength: 6,
    autofocus: true,
    classList: {
      inputBox: "my-super-box-class",
      input: "my-super-class",
      inputFilled: "my-super-filled-class",
      inputDisabled: "my-super-disable-class",
      inputSuccess: "my-super-success-class",
      inputError: "my-super-error-class"
    }
  };

  signInWithPhone:FormGroup;
  customer = {
    phoneNo:"",
    name : "",
    otp: ""
  };
  sms= {
    to:""
  }

  constructor(private myService: MyService,   private authService: AuthServicesService,  private router: Router,) { }

  ngOnInit(): void {
    this.countDown = this.myService
    .getCounter(this.tick)
    .subscribe(() => {
      if(this.counter>=1)
        this.counter--
      else
       this.isOtpExpired = true
    });
    this.signInWithPhone = new FormGroup({
      phoneNo: new FormControl(null, [
        Validators.required,
        Validators.minLength(10),Validators.maxLength(10),EcommerceValidators.notBlankValidator
      ]),
      name: new FormControl(null, [
        Validators.required,  Validators.pattern("^[a-zA-Z\\s]+$"),EcommerceValidators.notBlankValidator
      ]),
    });
  }

  onSubmitFromPhone(){
    this.counter = 600;
    this.otpSent = !this.otpSent
    this.customer.phoneNo = this.signInWithPhone.value.phoneNo;
    this.customer.name = this.signInWithPhone.value.name;
    this.sms.to = this.signInWithPhone.value.phoneNo;
    this.authService.sendOtp(this.sms).subscribe(
      (response: any) => {
        console.log(response);
       // this.authService.loginUser(response);
      },
      (error) => {
        console.log(error);
      }
    );
   console.log(this.customer);
  }

  handeOtpChange(value: string[]): void {
    // console.log(value);
    // let bool = value.every(element => element != null)
    // console.log(bool);
  }

  handleFillEvent(value: string): void {
    if(this.temp){
      this.customer.otp = value;
      this.temp = !this.temp;
      this.isOtpEntered = true;
      this.otp = value;
      console.log(this.isOtpEntered,this.otp);
    }
  
    
  }
  changeNo(){
    this.otpSent = !this.otpSent
  }
  verifyOtp(){
    this.authService.doLogin(this.customer).subscribe(
      (response: any) => {
        console.log("hi")
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


@Pipe({
  name: "formatTime"
})
export class FormatTimePipe implements PipeTransform {
  transform(value: number): string {
    //MM:SS format
    if(value>0){
    const minutes: number = Math.floor(value / 60);
    return (
      ("00" + minutes).slice(-2) +
      ":" +
      ("00" + Math.floor(value - minutes * 60)).slice(-2)
    );
    }
    else{

    }

    // for HH:MM:SS
    //const hours: number = Math.floor(value / 3600);
    //const minutes: number = Math.floor((value % 3600) / 60);
    //return ('00' + hours).slice(-2) + ':' + ('00' + minutes).slice(-2) + ':' + ('00' + Math.floor(value - minutes * 60)).slice(-2);
  }

}
