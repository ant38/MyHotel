import { Component, OnInit, Input } from '@angular/core';
import { Hotel, Offer, Room } from '../../_models/index';

@Component({
  selector: 'app-offre-complet',
  templateUrl: './offre-complet.component.html',
  styleUrls: ['./offre-complet.component.css']
})
export class OffreCompletComponent implements OnInit {
  
  offre: Offer;
  //chambres: Room[] = this.offre.rooms;
  //hotel: Hotel = this.chambres[0].hotel;
  
  constructor() { }

  ngOnInit() {
  }

}
