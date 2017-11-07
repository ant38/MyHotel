import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';

export class Hotel {
    id: number;
    name: string;
    lieu: string;
    dateIn: Date;
    dateOut: Date;
    offres: Offre[];
}

export class Offre {
    id_offre: number;
    name: string;
}

const OFFRES: Offre[] = [
    { id_offre: 1, name: 'offre1', },
    { id_offre: 2, name: 'offre2', },
    { id_offre: 3, name: 'offre3', },
]

const HOTELS: Hotel[] = [
    { id: 11, name: 'Hotel 1', lieu: 'ici', dateIn: new Date(2000, 8, 1), dateOut: new Date(2000, 9, 1), offres: [OFFRES[0], OFFRES[2]] },
    { id: 12, name: 'Hotel 2', lieu: 'ici', dateIn: new Date(2000, 8, 1), dateOut: new Date(2000, 9, 1), offres: [OFFRES[0]] },
    { id: 13, name: 'Hotel 3', lieu: 'ici', dateIn: new Date(2000, 8, 1), dateOut: new Date(2000, 9, 1), offres: [OFFRES[0], OFFRES[1]] },
    /*{ id: 14, name: 'Celeritas' },
    { id: 15, name: 'Magneta' },
    { id: 16, name: 'RubberMan' },
    { id: 17, name: 'Dynama' },
    { id: 18, name: 'Dr IQ' },
    { id: 19, name: 'Magma' },
    { id: 20, name: 'Tornado' }*/
  ];

 

@Component({
    selector: 'dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: [ './dashboard.component.css']
})

export class DashboardComponent {
    hotels = HOTELS;
    offres = OFFRES;
}