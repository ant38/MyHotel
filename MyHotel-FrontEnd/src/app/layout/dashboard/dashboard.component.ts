import { Component, OnInit, NgModule } from '@angular/core';
import { BrowserModule } from "@angular/platform-browser";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { HttpModule } from "@angular/http";
import { FormControl } from '@angular/forms';
import { PaiementComponent } from '../paiement/index';

import { Http, Response, Headers } from '@angular/http';
import 'rxjs/add/operator/map'


export class Hotel {
    id: number;
    name: string;
    lieu: string;
    etoiles: number;
    dateIn: Date;
    dateOut: Date;
}

export class Offre {
    id_offre: number;
    name: string;
    dateIn: Date;
    dateOut: Date;
    nbPersonne: number;
    hotel: Hotel;
    prix: number;
    description: string;
}

const HOTELS: Hotel[] = [
    { id: 10, name: 'Hotel 10', lieu: 'Paris', etoiles: 1, dateIn: new Date(2017, 8, 1), dateOut: new Date(2017, 9, 1) },
    { id: 11, name: 'Hotel 11', lieu: 'Paris', etoiles: 2, dateIn: new Date(2017, 8, 1), dateOut: new Date(2017, 9, 1) },
    { id: 12, name: 'Hotel 12', lieu: 'Lyon', etoiles: 3, dateIn: new Date(2017, 8, 1), dateOut: new Date(2017, 9, 1) },
    { id: 13, name: 'Hotel 13', lieu: 'Lyon', etoiles: 4, dateIn: new Date(2017, 8, 1), dateOut: new Date(2017, 9, 1) },
    { id: 14, name: 'Hotel 14', lieu: 'Grenoble', etoiles: 5, dateIn: new Date(2017, 8, 1), dateOut: new Date(2017, 9, 1) },
    { id: 15, name: 'Hotel 15', lieu: 'Grenoble', etoiles: 0, dateIn: new Date(2017, 8, 1), dateOut: new Date(2017, 9, 1) },
    { id: 16, name: 'Hotel 16', lieu: 'Grenoble', etoiles: 1, dateIn: new Date(2017, 8, 1), dateOut: new Date(2017, 9, 1) },
    { id: 17, name: 'Hotel 17', lieu: 'Grenoble', etoiles: 2, dateIn: new Date(2017, 8, 1), dateOut: new Date(2017, 9, 1) },
    { id: 18, name: 'Hotel 18', lieu: 'Grenoble', etoiles: 2, dateIn: new Date(2017, 8, 1), dateOut: new Date(2017, 9, 1) },
    { id: 19, name: 'Hotel 19', lieu: 'Grenoble', etoiles: 3, dateIn: new Date(2017, 8, 1), dateOut: new Date(2017, 9, 1) },
];

const OFFRES: Offre[] = [
    { id_offre: 0,  name: 'offre00', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 2, hotel: HOTELS[0], prix: 100, description: "1 chambre avec lits doubles"},
    { id_offre: 1,  name: 'offre01', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 2, hotel: HOTELS[1], prix: 100, description: "1 chambre avec lits doubles"},
    { id_offre: 2,  name: 'offre02', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 2, hotel: HOTELS[2], prix: 100, description: "1 chambre avec lits doubles"},
    { id_offre: 3,  name: 'offre03', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 2, hotel: HOTELS[3], prix: 100, description: "1 chambre avec lits doubles"},
    { id_offre: 4,  name: 'offre04', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 2, hotel: HOTELS[4], prix: 100, description: "1 chambre avec lits doubles"},
    { id_offre: 5,  name: 'offre05', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 2, hotel: HOTELS[5], prix: 100, description: "1 chambre avec lits doubles"},
    { id_offre: 6,  name: 'offre06', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 2, hotel: HOTELS[6], prix: 100, description: "1 chambre avec lits doubles"},
    { id_offre: 7,  name: 'offre07', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 2, hotel: HOTELS[7], prix: 100, description: "1 chambre avec lits doubles"},
    { id_offre: 8,  name: 'offre08', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 2, hotel: HOTELS[8], prix: 100, description: "1 chambre avec lits doubles"},
    { id_offre: 9,  name: 'offre09', dateIn:  new Date(2017, 11, 19), dateOut: new Date(2017, 12, 31), nbPersonne: 2, hotel: HOTELS[9], prix: 100, description: "1 chambre avec lits doubles"},
    { id_offre: 10, name: 'offre10', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 3, hotel: HOTELS[0], prix: 100, description: "1 chambre avec 1 lit double et 1 lit simple"},
    { id_offre: 11, name: 'offre11', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 3, hotel: HOTELS[1], prix: 100, description: "1 chambre avec 1 lit double et 1 lit simple"},
    { id_offre: 12, name: 'offre12', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 3, hotel: HOTELS[2], prix: 100, description: "1 chambre avec 1 lit double et 1 lit simple"},
    { id_offre: 13, name: 'offre13', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 3, hotel: HOTELS[3], prix: 100, description: "1 chambre avec 1 lit double et 1 lit simple"},
    { id_offre: 14, name: 'offre14', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 3, hotel: HOTELS[4], prix: 100, description: "1 chambre avec 1 lit double et 1 lit simple"},
    { id_offre: 15, name: 'offre15', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 3, hotel: HOTELS[5], prix: 100, description: "1 chambre avec 1 lit double et 1 lit simple"},
    { id_offre: 16, name: 'offre16', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 3, hotel: HOTELS[6], prix: 100, description: "1 chambre avec 1 lit double et 1 lit simple"},
    { id_offre: 17, name: 'offre17', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 3, hotel: HOTELS[7], prix: 100, description: "1 chambre avec 1 lit double et 1 lit simple"},
    { id_offre: 18, name: 'offre18', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 3, hotel: HOTELS[8], prix: 100, description: "1 chambre avec 1 lit double et 1 lit simple"},
    { id_offre: 19, name: 'offre19', dateIn:  new Date(2017, 11, 19), dateOut: new Date(2017, 12, 31), nbPersonne: 3, hotel: HOTELS[9], prix: 100, description: "1 chambre avec 1 lit double et 1 lit simple"},
    { id_offre: 20, name: 'offre20', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 4, hotel: HOTELS[0], prix: 100, description: "1 chambre avec 2 lits doubles"},
    { id_offre: 21, name: 'offre21', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 4, hotel: HOTELS[1], prix: 100, description: "1 chambre avec 2 lits doubles"},
    { id_offre: 22, name: 'offre22', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 4, hotel: HOTELS[2], prix: 100, description: "1 chambre avec 2 lits doubles"},
    { id_offre: 23, name: 'offre23', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 4, hotel: HOTELS[3], prix: 100, description: "1 chambre avec 2 lits doubles"},
    { id_offre: 24, name: 'offre24', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 4, hotel: HOTELS[4], prix: 100, description: "1 chambre avec 2 lits doubles"},
    { id_offre: 25, name: 'offre25', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 4, hotel: HOTELS[5], prix: 100, description: "1 chambre avec 2 lits doubles"},
    { id_offre: 26, name: 'offre26', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 4, hotel: HOTELS[6], prix: 100, description: "1 chambre avec 2 lits doubles"},
    { id_offre: 27, name: 'offre27', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 4, hotel: HOTELS[7], prix: 100, description: "1 chambre avec 2 lits doubles"},
    { id_offre: 28, name: 'offre28', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 4, hotel: HOTELS[8], prix: 100, description: "1 chambre avec 2 lits doubles"},
    { id_offre: 29, name: 'offre29', dateIn:  new Date(2017, 11, 19), dateOut: new Date(2017, 12, 31), nbPersonne: 4, hotel: HOTELS[9], prix: 100, description: "1 chambre avec 2 lits doubles"},
    { id_offre: 30, name: 'offre30', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 5, hotel: HOTELS[0], prix: 100, description: "1 chambre avec 1 lit double et 1 chambre avecs 3 lits simples"},
    { id_offre: 31, name: 'offre31', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 5, hotel: HOTELS[1], prix: 100, description: "1 chambre avec 1 lit double et 1 chambre avecs 3 lits simples"},
    { id_offre: 32, name: 'offre32', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 5, hotel: HOTELS[2], prix: 100, description: "1 chambre avec 1 lit double et 1 chambre avecs 3 lits simples"},
    { id_offre: 33, name: 'offre33', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 5, hotel: HOTELS[3], prix: 100, description: "1 chambre avec 1 lit double et 1 chambre avecs 3 lits simples"},
    { id_offre: 34, name: 'offre34', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 5, hotel: HOTELS[4], prix: 100, description: "1 chambre avec 1 lit double et 1 chambre avecs 3 lits simples"},
    { id_offre: 35, name: 'offre35', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 5, hotel: HOTELS[5], prix: 100, description: "1 chambre avec 1 lit double et 1 chambre avecs 3 lits simples"},
    { id_offre: 36, name: 'offre36', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 5, hotel: HOTELS[6], prix: 100, description: "1 chambre avec 1 lit double et 1 chambre avecs 3 lits simples"},
    { id_offre: 37, name: 'offre37', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 5, hotel: HOTELS[7], prix: 100, description: "1 chambre avec 1 lit double et 1 chambre avecs 3 lits simples"},
    { id_offre: 38, name: 'offre38', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 5, hotel: HOTELS[8], prix: 100, description: "1 chambre avec 1 lit double et 1 chambre avecs 3 lits simples"},
    { id_offre: 39, name: 'offre39', dateIn:  new Date(2017, 11, 19), dateOut: new Date(2017, 12, 31), nbPersonne: 5, hotel: HOTELS[9], prix: 100, description: "1 chambre avec 1 lit double et 1 chambre avecs 3 lits simples"},
    { id_offre: 40, name: 'offre40', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 6, hotel: HOTELS[0], prix: 100, description: "3 chambre avec 1 lit double"},
    { id_offre: 41, name: 'offre41', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 6, hotel: HOTELS[1], prix: 100, description: "3 chambre avec 1 lit double"},
    { id_offre: 42, name: 'offre42', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 6, hotel: HOTELS[2], prix: 100, description: "3 chambre avec 1 lit double"},
    { id_offre: 43, name: 'offre43', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 6, hotel: HOTELS[3], prix: 100, description: "3 chambre avec 1 lit double"},
    { id_offre: 44, name: 'offre44', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 6, hotel: HOTELS[4], prix: 100, description: "3 chambre avec 1 lit double"},
    { id_offre: 45, name: 'offre45', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 6, hotel: HOTELS[5], prix: 100, description: "3 chambre avec 1 lit double"},
    { id_offre: 46, name: 'offre46', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 6, hotel: HOTELS[6], prix: 100, description: "3 chambre avec 1 lit double"},
    { id_offre: 47, name: 'offre47', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 6, hotel: HOTELS[7], prix: 100, description: "3 chambre avec 1 lit double"},
    { id_offre: 48, name: 'offre48', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 6, hotel: HOTELS[8], prix: 100, description: "3 chambre avec 1 lit double"},
    { id_offre: 49, name: 'offre49', dateIn:  new Date(2017, 11, 19), dateOut: new Date(2017, 12, 31), nbPersonne: 6, hotel: HOTELS[9], prix: 100, description: "3 chambre avec 1 lit double"},
    { id_offre: 50, name: 'offre50', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 2, hotel: HOTELS[0], prix: 100, description: "1 chambre avec 2 lits simples"},
    { id_offre: 51, name: 'offre51', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 2, hotel: HOTELS[1], prix: 100, description: "1 chambre avec 2 lits simples"},
    { id_offre: 52, name: 'offre52', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 2, hotel: HOTELS[2], prix: 100, description: "1 chambre avec 2 lits simples"},
    { id_offre: 53, name: 'offre53', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 2, hotel: HOTELS[3], prix: 100, description: "1 chambre avec 2 lits simples"},
    { id_offre: 54, name: 'offre54', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 2, hotel: HOTELS[4], prix: 100, description: "1 chambre avec 2 lits simples"},
    { id_offre: 55, name: 'offre55', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 2, hotel: HOTELS[5], prix: 100, description: "1 chambre avec 2 lits simples"},
    { id_offre: 56, name: 'offre56', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 2, hotel: HOTELS[6], prix: 100, description: "1 chambre avec 2 lits simples"},
    { id_offre: 57, name: 'offre57', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 2, hotel: HOTELS[7], prix: 100, description: "1 chambre avec 2 lits simples"},
    { id_offre: 58, name: 'offre58', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 2, hotel: HOTELS[8], prix: 100, description: "1 chambre avec 2 lits simples"},
    { id_offre: 59, name: 'offre59', dateIn:  new Date(2017, 11, 19), dateOut: new Date(2017, 12, 31), nbPersonne: 2, hotel: HOTELS[9], prix: 100, description: "1 chambre avec 2 lits simples"},
    { id_offre: 60, name: 'offre60', dateIn:  new Date(2017, 11, 20), dateOut: new Date(2017, 12, 31), nbPersonne: 2, hotel: HOTELS[0], prix: 100, description: "2 chambres avec 1 lit"},
];

@Component({

    selector: 'dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: [ './dashboard.component.css']
})

export class DashboardComponent {
    data: any = null;

  constructor(private _http: Http) {
    this.getMyBlog();
  }

  private getMyBlog() {
    return this._http.get('http://18.216.255.59/MyHotel/rest/hotels')
                .map((res: Response) => res.json())
                 .subscribe(data => {
                        this.data = data;
                        console.log(this.data[0]['id']);
                });
  }

}


