import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { LoginForm } from './app.login-form.component'//first test

import { ReactiveFormsModule,FormsModule } from '@angular/forms';//Reac
import { HttpModule } from '@angular/http';
// used to create fake backend
import { fakeBackendProvider } from './_helpers/index';
import { MockBackend, MockConnection } from '@angular/http/testing';
import { BaseRequestOptions } from '@angular/http';/////

import { AppComponent }       from './app.component';
import { LoginComponent }     from './login.component';
import { SigninComponent }    from './signin.component';
import { DashboardComponent } from './dashboard.component';

<<<<<<< HEAD
import { AlertService, AuthenticationService, UserService } from './_services/index';
import { AlertComponent } from './_directives/index';
import { AppRoutingModule }     from './app-routing.module';
import { AuthGuard } from './_guards/index';
=======
import { AppRoutingModule } from './app-routing.module';
>>>>>>> 78d204a4c8a0b4e5b95e0ea827c58e7b5f2b2e77

@NgModule({
  declarations: [
    AppComponent,

    AlertComponent,

    DashboardComponent,

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
<<<<<<< HEAD
  providers: [
    AuthGuard,
    AlertService,/////
        AuthenticationService,
        UserService,
    // providers used to create fake backend
        fakeBackendProvider,
        MockBackend,
        BaseRequestOptions
  ],
  bootstrap: [
    AppComponent//,
    //LoginForm/////only in declarations

  ]
=======
  providers: [],
  bootstrap: [ AppComponent ]
>>>>>>> 78d204a4c8a0b4e5b95e0ea827c58e7b5f2b2e77
})
export class AppModule { }
