import { Component, OnInit } from "@angular/core";
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from "@angular/forms";

@Component({
  selector: "app-checkout",
  templateUrl: "./checkout.component.html",
  styleUrls: ["./checkout.component.scss"],
})
export class CheckoutComponent implements OnInit {
  constructor(private _formBuilder: FormBuilder) {}
  isEditable = true;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  personalForm: FormGroup;

  ngOnInit(): void {}
}
