import { Component, OnInit } from "@angular/core";
import { FormGroup } from "@angular/forms";
import { Observable } from "rxjs";

@Component({
  selector: "app-progress-bar",
  templateUrl: "./progress-bar.component.html",
  styleUrls: ["./progress-bar.component.scss"],
})
export class ProgressBarComponent implements OnInit {
  activeStep = 0;
  activeValue = 25;
  orderState: Observable<any>;
  isEditable = false;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  constructor() {}

  ngOnInit(): void {}
}
