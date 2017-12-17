import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule,FormsModule }    from '@angular/forms';
import { HttpModule, Headers } from '@angular/http';
import { HttpClientModule, HttpClient, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NouisliderModule } from 'ng2-nouislider';
import { MyDatePickerModule } from 'mydatepicker';


// used to create fake backend
import { fakeBackendProvider } from './_helpers/index';
import { MockBackend, MockConnection } from '@angular/http/testing';
import { BaseRequestOptions } from '@angular/http';

import { AppComponent }  from './app.component';
import { routing }        from './app.routing';

import { AlertComponent } from './_directives/index';
import { AuthGuard } from './_guards/index';

import { JwtInterceptor } from './_helpers/index';
import { AlertService, AuthenticationService, UserService, HotelService, OffreService, BookingService } from './_services/index';

import { HomeComponent } from './home/index';
import { LoginComponent } from './login/index';
import { RegisterComponent } from './register/index';
import { DashboardComponent } from './dashboard/index';
import { RechercheComponent } from './recherche/index';
import { ReservationComponent } from './reservation/reservation.component';
import { ConfirmationComponent } from './confirmation/confirmation.component';
import { DescriptionComponent } from './description/description.component';

import { OffreCompletComponent } from './layout/offre-complet/offre-complet.component';
import { OffreDescComponent } from './layout/offre-desc/offre-desc.component';
import { PayComponent } from './layout/paiement/paiement.component';
import { RecapitulatifComponent } from './layout/recapitulatif/recapitulatif.component';
import { RechercheBarreComponent } from './layout/recherche-barre/recherche-barre.component';
import { RecherchePlusComponent } from './layout/recherche-plus/recherche-plus.component';

import { FiltreLieuComponent } from './filtres/filtre-lieu/filtre-lieu.component';
import { FiltreDateArriveComponent } from './filtres/filtre-date-arrive/filtre-date-arrive.component';
import { FiltreDateDepartComponent } from './filtres/filtre-date-depart/filtre-date-depart.component';
import { FiltreNombrePersonneComponent } from './filtres/filtre-nombre-personne/filtre-nombre-personne.component';
import { FiltreNombreNuitComponent } from './filtres/filtre-nombre-nuit/filtre-nombre-nuit.component';
import { FiltrePrixComponent } from './filtres/filtre-prix/filtre-prix.component';
import { FiltreAvisComponent } from './filtres/filtre-avis/filtre-avis.component';
import { FiltrePetitDejeunerComponent } from './filtres/filtre-petit-dejeuner/filtre-petit-dejeuner.component';
import { OffreReservationComponent } from './layout/offre-reservation/offre-reservation.component';


@NgModule({
    imports: [
        BrowserModule,
        ReactiveFormsModule, //first test
        FormsModule,
        HttpModule,
        HttpClientModule,
        NouisliderModule,
        MyDatePickerModule,
        routing
    ],
    declarations: [
        AppComponent,
        AlertComponent,
        HomeComponent,
        LoginComponent,
        DashboardComponent,
        ReservationComponent,
        ConfirmationComponent,
        RegisterComponent,
        DescriptionComponent,
        RechercheComponent,
        RecapitulatifComponent,
        RecherchePlusComponent,
        RechercheBarreComponent,
        OffreDescComponent,
        OffreCompletComponent,
        FiltreLieuComponent,
        FiltreDateArriveComponent,
        FiltreDateDepartComponent,
        FiltreNombrePersonneComponent,
        FiltreNombreNuitComponent,
        FiltrePrixComponent,
        FiltreAvisComponent,
        FiltrePetitDejeunerComponent,
        OffreReservationComponent,
        PayComponent
    ],
    providers: [
        AuthGuard,
        AlertService,
        AuthenticationService,
        UserService,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: JwtInterceptor,
            multi: true
        },

        HotelService,
        OffreService,
        BookingService,
        HttpClientModule,
    
        // providers used to create fake backend
        fakeBackendProvider,
        MockBackend,
        BaseRequestOptions
    ],
    bootstrap: [AppComponent]
})

export class AppModule { }

