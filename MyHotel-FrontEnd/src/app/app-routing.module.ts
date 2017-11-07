import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent }     from './login.component';
import { SigninComponent }    from './signin.component';
import { DashboardComponent } from './dashboard.component';
import { AuthGuard } from './_guards/index';

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full', canActivate: [AuthGuard] },
  { path: 'login',  component: LoginComponent },
  { path: 'signin', component: SigninComponent },
  { path: 'dashboard', component: DashboardComponent }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}


