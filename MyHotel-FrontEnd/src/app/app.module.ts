import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { LoginForm } from './app.login-form.component'//first test

import { ReactiveFormsModule,FormsModule } from '@angular/forms';//Reac
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { LoginComponent }      from './login.component';
import { SigninComponent }          from './signin.component';

import { AppRoutingModule }     from './app-routing.module';

@NgModule({
  declarations: [
    AppComponent,
    LoginForm,
    LoginComponent,
    SigninComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule, //first test
    FormsModule,//////
    HttpModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [
    AppComponent,
    LoginForm
  ]
})
export class AppModule { }
