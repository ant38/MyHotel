import { Component, OnInit } from '@angular/core';
import { Hotel, Offer, Room } from '../../_models/index';
import { OffreService } from '../../_services/index';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-offre-reservation',
  templateUrl: './offre-reservation.component.html',
  styleUrls: ['./offre-reservation.component.css']
})
export class OffreReservationComponent implements OnInit {

  offre: Offer;
  room: Room[];
  hotel: Hotel;

  constructor(private route: ActivatedRoute, private offreService: OffreService) { }

  getOffre(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    console.log(id);
    this.offreService.getOffre(id).subscribe(offre => {this.offre = offre; this.room = this.offre.rooms; this.hotel = this.room[0].hotel; console.log(this.offre);});
  }

  ngOnInit() {
    this.getOffre();
  }

}
