import { Component, OnInit, Input } from '@angular/core';
import { Offer, Hotel, Room } from '../../_models/index';
import { HotelService } from '../../_services/index';

@Component({
  selector: 'app-offre-desc',
  templateUrl: './offre-desc.component.html',
  styleUrls: ['./offre-desc.component.css']
})
export class OffreDescComponent implements OnInit {

  @Input() offre: Offer;
  image: any;

  constructor(private hotelService: HotelService) { }

  ngOnInit() {
    this.getImage(this.offre.rooms[0].hotel.id);
  }
  getImage(id){
    this.hotelService.getImageHotel(id).subscribe(img => {this.image = img.content});
  }

}
