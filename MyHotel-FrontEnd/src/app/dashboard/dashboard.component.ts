import { Component, OnInit, NgModule } from '@angular/core';
import { BrowserModule } from "@angular/platform-browser";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { HttpModule } from "@angular/http";
import { FormControl } from '@angular/forms';
import { PayComponent } from '../layout/paiement/paiement.component';

@Component({

    selector: 'dashboard',
    templateUrl: './dashboard.component.html',
})

export class DashboardComponent {
  constructor() {}
  ngOnInit() {
      localStorage.removeItem("lieu");
      localStorage.removeItem("dateIn");
      localStorage.removeItem("dateOut");
      localStorage.removeItem("nbNuit");
      localStorage.removeItem("nbAdults");
      localStorage.removeItem("nbChildren");
    }

}


