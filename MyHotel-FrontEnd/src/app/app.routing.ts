import { NgModule }             from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from './home/index'; 
import { LoginComponent } from './login/index';
import { RegisterComponent } from './register/index';
import { DashboardComponent } from './layout/dashboard/index';
import { AuthGuard } from './_guards/index';
import { OffreComponent } from './layout/offre/index';
import { PaiementComponent } from './layout/paiement/index';
import { ConfirmationResaComponent } from './layout/confirmationResa/index';

const appRoutes: Routes = [

  { path: 'dashboard', component: DashboardComponent },
  { path: '', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'offre', component: OffreComponent },
  { path: 'paiement', component: PaiementComponent, canActivate: [AuthGuard] },
  { path: 'confirmationResa', component: ConfirmationResaComponent, canActivate: [AuthGuard] },  
];

export const routing = RouterModule.forRoot(appRoutes);
