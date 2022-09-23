import { BOOL_TYPE } from "@angular/compiler/src/output/output_ast";
import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"],
})
export class AppComponent implements OnInit {
  constructor(private router: Router) {
 
    }
  isCollapsed = true;
  ngOnInit(): void {
    
    // throw new Error('Method not implemented.');
  }
  title = "angular-ecommerce";
}


