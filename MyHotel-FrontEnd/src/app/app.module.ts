import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { LoginForm } from './app.login-form.component'//first test

import { ReactiveFormsModule,FormsModule } from '@angular/forms';//Reac
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginForm
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule, //first test
    FormsModule,//////
    HttpModule
  ],
  providers: [],
  bootstrap: [
    AppComponent,
    LoginForm
  ]
})
export class AppModule { }
