import { Component, OnInit } from "@angular/core";
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from "@angular/forms";
import { AddressService } from "src/app/services/address.service";
import { AuthServicesService } from "src/app/services/auth-services.service";
import { EcommerceValidators } from "src/app/validators/ecommerce-validators";
import { ThemePalette } from "@angular/material/core";
import { CartService } from "src/app/services/cart.service";
import { HttpClient } from "@angular/common/http";

import { BOOL_TYPE } from "@angular/compiler/src/output/output_ast";
import { WindowNativeService } from "src/app/services/window-native.service";
import { Router } from "@angular/router";
import { CheckoutService } from "src/app/services/checkout.service";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { TermsComponent } from "../terms/terms.component";
import { Order } from "src/app/common/order";

//declare var Razorpay:any;
//declare var bolt:any;
@Component({
  selector: "app-personal",
  templateUrl: "./personal.component.html",
  styleUrls: ["./personal.component.scss"],
})
export class PersonalComponent implements OnInit {

  txnId:string
  isEditable = true;
  phoneNo: any;
  addresses: any;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  personalForm: FormGroup;
  defaultAddress: any;
  isNewAddress: boolean = false;
  disablePaymentButton:boolean = true;
  totalOrderPrice:number;
  termsAccepted:boolean = false;
  payOnDelivery:boolean = false;
  payNow:boolean = false;
  isSaveAddress = true;
  shippingAddress:any;
  public payuform: any = {};
  cartId:any;
   modifiedAddress:any;
  addressOptions: any = [
    {
      id: Number,
      address: String,
    },
  ];
   paymentCallback = {
    txnId: "",
    payId: "",
    mode: "",
    status: "",
    email: "",
  }
  color: ThemePalette = "primary";
  // selectedAddress:any = "thisOne"
  constructor(
    private _formBuilder: FormBuilder,
    private addressService: AddressService,
    private authService: AuthServicesService,
    private cartService: CartService,
    private http: HttpClient,
    private window:WindowNativeService,
    private routerNav: Router,
    private checkoutService:CheckoutService,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    console.log(this.isSaveAddress)
    this.phoneNo = this.authService.getMailId();
    this.addressService.getAddresses(this.phoneNo).subscribe(
      (data) => {
        console.log(data);
        this.addresses = data;
      },
      (error) => {}
    );

    this.firstFormGroup = this._formBuilder.group({
      firstCtrl: ["", Validators.required],
    });
    this.secondFormGroup = this._formBuilder.group({
      secondCtrl: ["", Validators.required],
    });

    console.log(this.defaultAddress);

    this.personalForm = new FormGroup({
      addressId: new FormControl(null, [Validators.required]),
      shipName: new FormControl(null),
      phone: new FormControl(null),
      address: new FormControl(null),
      city: new FormControl(null),
      state: new FormControl(null),
      pin: new FormControl(null),
      isSaveAddress:new FormControl(true),
    });
    this.payuform = {
      productinfo: "",
      firstname: "",
      email: "",
      phone: "",
      amount: "",
      surl: "",
      furl: "",
      key: "",
      hash: "",
      txnid:"",
    }
  }

  onSubmitOrderForm(event: any) {
    console.log(event);
     this.shippingAddress = {
      addressId: this.personalForm.value.addressId,
      shipName: this.personalForm.value.shipName,
      phone: this.personalForm.value.phone,
      address: this.personalForm.value.address,
      city: this.personalForm.value.city,
      pin: this.personalForm.value.pin,
      state: this.personalForm.value.state,
      isSaveAddress: this.personalForm.value.isSaveAddress,
    };

    console.log(this.shippingAddress);
  }

  newShippingAddress() {
    this.isNewAddress = true;
    this.personalForm = new FormGroup({
      addressId: new FormControl(null),
      shipName: new FormControl(null, [
        Validators.pattern("^[a-zA-Z\\s]+$"),
        Validators.required,
        ,
        Validators.minLength(3),
        Validators.maxLength(104),
      ]),
      phone: new FormControl(null, [
        Validators.required,
        Validators.pattern("[0-9]+"),
        Validators.minLength(10),
        Validators.maxLength(12),
      ]),
      address: new FormControl(null, [
        EcommerceValidators.notBlankValidator,
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(240),
      ]),
      city: new FormControl(null, [
        EcommerceValidators.notBlankValidator,
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(100),
      ]),
      state: new FormControl(null, [
        EcommerceValidators.notBlankValidator,
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(40),
      ]),
      pin: new FormControl(null, [
        EcommerceValidators.notBlankValidator,
        Validators.required,
        Validators.maxLength(6),
        Validators.minLength(5),
      ]),
      isSaveAddress : new FormControl(true)
    });
  }

  confirmPayment() {
    this.isSaveAddress =  this.shippingAddress.isSaveAddress;
    console.log(this.isSaveAddress);
    if(this.shippingAddress.addressId == null){
      this.modifiedAddress = {
        name: this.shippingAddress.shipName,
        contactNumber: this.shippingAddress.phone,
        address: this.shippingAddress.address,
        city: this.shippingAddress.city,
        pin: this.shippingAddress.pin,
        customerPhoneNo: this.phoneNo,
        state: this.shippingAddress.state
      };
    }
    else{
      this.modifiedAddress = this.addresses.find(address =>address.id === this.shippingAddress.addressId)
    }
    console.log(this.modifiedAddress);

    let paymentPayload = {
      email: "",
      name: this.modifiedAddress.name,
      phoneNo: this.modifiedAddress.customerPhoneNo,
      contactNo: this.modifiedAddress.contactNumber,
      productInfo: this.cartId,
      amount: this.totalOrderPrice
      }

    console.log(paymentPayload)
    if(this.payNow){
       this.payOnline(paymentPayload)
    }
    else{
      console.log("PayOnDeliver")
      this.payOffline(paymentPayload)
    }     
  }

  payOnline(paymentPayload:any){
    this.checkoutService.proceedPayment(paymentPayload).subscribe(
      (data:any) => {
      console.log(data);
     this.window.nativeWindow.bolt.launch({
        key: data.key,
        hash:data.hash,
        txnid:data.txnId,
        amount:data.amount,
        firstname:data.name,
        email:data.email,
        phone:data.phoneNo,
        productinfo:data.productInfo,
        surl :data.surl,
        furl :data.furl ,
      }, {
        responseHandler:(response) => {
        // your payment response Code goes here
        console.log(response);
        if(response.response.txnStatus !== "CANCEL")
            this.orderInfo(response.response)
      },
      catchException: function (response) {
     console.log(response)
      }
    });
       
    }, error1 => {
        console.log(error1);
      })
  }

  payOffline(paymentPayload:any){
     
    this.checkoutService.proceedPayment(paymentPayload).subscribe(
      data=>{
        paymentPayload.txnid = data.txnId;
        paymentPayload.txnStatus = "SUCCESS";
        paymentPayload.mode = "PAY_ON_DELIVERY"
        
        this.orderInfo(paymentPayload)
      },
      error=>{

      }
    )
  }
  orderInfo(response: any) {
   this.paymentCallback.txnId = response.txnid;
   this.paymentCallback.status = response.txnStatus;
   this.paymentCallback.email = response.email;
   this.paymentCallback.mode = response.mode;
   this.paymentCallback.payId = response.payuMoneyId;
   this.checkoutService.payuCallback(this.paymentCallback).subscribe(
     (data)=>{
       console.log(data);
       let order:Order = new Order();
       if(response.txnStatus === "SUCCESS"){
        order.orderedCartId = this.cartId;
        order.shippingAddress = this.modifiedAddress;
        order.paymentThrough = "PAY_ON_DELIVERY"
        order.customerPhoneNo = this.phoneNo;
        order.txnId = response.txnid;
       this.checkoutService.placeOrder(order).subscribe(
         data=>{

         },
         error=>{

         }
       )
        //this.routerNav.navigate(["/checkout/success"]);
      }
     },
     (error)=>{
      console.log(error);
     }
   )

  }
  parentFunctionForPrice($event){
    console.log($event);
    this.totalOrderPrice = $event
  }
  parentFunctionForCartId($event){
    console.log($event);
    this.cartId = $event
  }
  openTermsModel(){

    this.modalService.open(TermsComponent, {
      backdrop: 'static',
      keyboard: false,
      centered: true
    });
  }  
  successPayOnDelivery(){
    console.log("hi")
    console.log(this.payOnDelivery,this.payNow)
    this.payOnDelivery = !this.payOnDelivery
    if(this.payNow == true){
      this.payNow = false;
    }
    
  }
  successPayNow(){
    console.log(this.payOnDelivery,this.payNow)
    this.payNow = !this.payNow
    if(this.payOnDelivery){
      this.payOnDelivery = false;
    }
  }
}
