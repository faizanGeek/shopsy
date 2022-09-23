import { Component, OnInit } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { AddressService } from "src/app/services/address.service";
import { AuthServicesService } from "src/app/services/auth-services.service";
import { EcommerceValidators } from "src/app/validators/ecommerce-validators";

@Component({
  selector: "app-addresses",
  templateUrl: "./addresses.component.html",
  styleUrls: ["./addresses.component.scss"],
})
export class AddressesComponent implements OnInit {
  ind: any;
  customerEmailId: any;
  addressForm: FormGroup;
  addresses: any;
  isForm: boolean = false;
  constructor(
    private addressService: AddressService,
    private authService: AuthServicesService
  ) {}

  ngOnInit(): void {
    this.customerEmailId = this.authService.getMailId();

    this.addressService.getAddresses(this.customerEmailId).subscribe(
      (data) => {
        console.log(data);
        this.addresses = data;
      },
      (error) => {}
    );

    this.addressForm = new FormGroup({
      name: new FormControl(null, [
        EcommerceValidators.notBlankValidator,
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(100),
      ]),
      contactNo: new FormControl(null, [
        EcommerceValidators.notBlankValidator,
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(100),
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
      zip: new FormControl(null, [
        EcommerceValidators.notBlankValidator,
        Validators.required,
        Validators.maxLength(6),
        Validators.minLength(5),
      ]),
    });
  }

  onSubmitAddressForm() {
    //  this.innerLoading = true;

    const address = {
      name: this.addressForm.value?.name?.trim()?.length
        ? this.addressForm.value.name.trim()
        : null,
      contactNumber: this.addressForm.value?.contactNo?.trim()?.length
        ? this.addressForm.value.contactNo.trim()
        : null,
      address: this.addressForm.value?.address?.trim()?.length
        ? this.addressForm.value.address.trim()
        : null,
      city: this.addressForm.value?.city?.trim()?.length
        ? this.addressForm.value.city.trim()
        : null,
      state: this.addressForm.value?.state?.trim()?.length
        ? this.addressForm.value.state.trim()
        : null,
      pinCode: this.addressForm.value?.zip?.trim()?.length
        ? this.addressForm.value.zip.trim()
        : null,
      customerPhoneNo: this.customerEmailId,
      id: this.addressForm.value.id ? this.addressForm.value.id : null,
    };

    console.log(address.id);
    if (address.id == null) {
      ///  for addition
      this.addresses.push(address);
      this.addressService.addAddress(address).subscribe(
        (data) => {
          console.log("added");
        },
        (error) => {
          console.log(error);
        }
      );
    } else {
      console.log(address);

      this.addresses.splice(this.ind, 1, address);
      this.addressService.editAddress(address).subscribe(
        (data) => {
          console.log("added");
        },
        (error) => {
          console.log(error);
        }
      );
    }
  }

  edit(address: any) {
    this.ind = this.addresses.indexOf(address);
    this.isForm = true;
    this.addressForm = new FormGroup({
      name: new FormControl(address.name, [
        EcommerceValidators.notBlankValidator,
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(100),
      ]),
      contactNo: new FormControl(address.contactNumber, [
        EcommerceValidators.notBlankValidator,
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(100),
      ]),
      address: new FormControl(address.address, [
        EcommerceValidators.notBlankValidator,
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(240),
      ]),
      city: new FormControl(address.city, [
        EcommerceValidators.notBlankValidator,
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(100),
      ]),
      state: new FormControl(address.state, [
        EcommerceValidators.notBlankValidator,
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(40),
      ]),
      zip: new FormControl(address.pinCode, [
        EcommerceValidators.notBlankValidator,
        Validators.required,
        Validators.maxLength(6),
        Validators.minLength(5),
      ]),
      id: new FormControl(address.id),
    });
  }

  delete(address: any) {
    let ind = this.addresses.indexOf(address);
    this.addresses.splice(ind, 1);
    this.addressService.deleteAddress(address).subscribe(
      (data) => {},
      (error) => {}
    );
  }
}
