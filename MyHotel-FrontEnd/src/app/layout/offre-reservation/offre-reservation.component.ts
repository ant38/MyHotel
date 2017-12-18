import { Component, OnInit } from '@angular/core';
import { Hotel, Offer, Room } from '../../_models/index';
import { OffreService, HotelService } from '../../_services/index';
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
  image: any;

  constructor(private route: ActivatedRoute, private offreService: OffreService, private hotelService: HotelService) { }

  getOffre(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.offreService.getOffre(id).subscribe(offre => {this.offre = offre; this.room = this.offre.rooms; this.hotel = this.room[0].hotel; this.getImage(this.hotel.id);});
  }
  getImage(id){
    this.hotelService.getImageHotel(id).subscribe(img => {this.image = img.content});
  }

  ngOnInit() {
    this.getOffre();
    console.log(this.offre);
  }

  getNbChambres(): Number {
    return this.room.length;
  }

  getNbPersonnes(): Number {
    return Number(localStorage.getItem("nbAdults"))+Number(localStorage.getItem("nbChildren"));

  }

}
