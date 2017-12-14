import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OffreService } from "../../_services/index";
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
  
  constructor(private route: ActivatedRoute, private offreService: OffreService) { }

  getOffre(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    console.log(id);
    this.offreService.getOffre(id).subscribe(offre => {this.offre = offre; this.chambres = this.offre.rooms; this.hotel = this.chambres[0].hotel; console.log(this.offre);});
  }
  ngOnInit() {
    this.getOffre();
    
  }

}
