import { Component, OnInit } from '@angular/core';
import { Hotel, Offer, Room } from '../../_models/index';
import { OffreService } from '../../_services/index';
import { ActivatedRoute } from '@angular/router';
import { forEach } from '@angular/router/src/utils/collection';


@Component({
  selector: 'app-offre-reservation',
  templateUrl: './offre-reservation.component.html',
  styleUrls: ['./offre-reservation.component.css']
})
export class OffreReservationComponent implements OnInit {

  offre: Offer;
  room: Room[];
  hotel: Hotel;
  nbPers: Number;

  constructor(private route: ActivatedRoute, private offreService: OffreService) { }

  getOffre(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.offreService.getOffre(id).subscribe(offre => {this.offre = offre; this.room = this.offre.rooms; this.hotel = this.room[0].hotel;});
  }

  ngOnInit() {
    this.getOffre();
    console.log(this.offre);
  }

  getNbChambres(): Number {
    return this.room.length;
  }

  getNbPersonnes(): Number {
    this.nbPers = 0;
    this.room.forEach(function(element) {
      this.nbPers += element.places;
    }.bind(this));
    console.log(this.nbPers);
    return this.nbPers;
  }

}
