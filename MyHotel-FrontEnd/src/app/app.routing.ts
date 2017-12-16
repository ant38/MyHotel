import { NgModule }             from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthGuard } from './_guards/index';
// Pages à explorer
import { HomeComponent } from './home/index'; 
import { LoginComponent } from './login/index';
import { RegisterComponent } from './register/index';
import { DashboardComponent } from './dashboard/index';
import { RechercheComponent } from './recherche/index';
import { ReservationComponent } from './reservation/reservation.component';
import { ConfirmationComponent } from './confirmation/confirmation.component';
import { DescriptionComponent } from './description/description.component';

// Composants
import { RecapitulatifComponent } from './layout/recapitulatif/recapitulatif.component';
import { RecherchePlusComponent } from './layout/recherche-plus/recherche-plus.component';
import { RechercheBarreComponent } from './layout/recherche-barre/recherche-barre.component';
import { OffreDescComponent } from './layout/offre-desc/offre-desc.component';
import { OffreCompletComponent } from './layout/offre-complet/offre-complet.component';

import { PayComponent } from './layout/paiement/paiement.component';

const appRoutes: Routes = [

  
  { path: '', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'recherche', component: RechercheComponent },
  { path: 'paiement', component: PayComponent },
  { path: 'reservation/:id', component: ReservationComponent },
  { path: 'confirmation/:id', component: ConfirmationComponent },
  { path: 'description/:id', component: DescriptionComponent },
  
  // otherwise redirect to home(DashBoard)
  { path: '**', redirectTo: 'dashboard' }
];

export const routing = RouterModule.forRoot(appRoutes);
