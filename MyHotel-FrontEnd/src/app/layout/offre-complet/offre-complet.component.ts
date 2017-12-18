import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OffreService, HotelService } from "../../_services/index";
import { Hotel, Offer, Room } from '../../_models/index';

@Component({
  selector: 'app-offre-complet',
  templateUrl: './offre-complet.component.html',
  styleUrls: ['./offre-complet.component.css'] 
})
export class OffreCompletComponent implements OnInit {
  
  offre: Offer;
  chambres: Room[];
  hotel: Hotel;
  image: any;
  
  constructor(private route: ActivatedRoute, private offreService: OffreService, private hotelService: HotelService) { }

  getOffre(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    console.log(id);
    this.offreService.getOffre(id).subscribe(offre => {console.log("into service");this.offre = offre; this.chambres = this.offre.rooms; this.hotel = this.chambres[0].hotel; this.getImage(this.hotel.id);});
  }
  getImage(id){
    this.hotelService.getImageHotel(id).subscribe(img => {this.image = img.content});
  }
  ngOnInit() {
    this.getOffre();    
  }

}
