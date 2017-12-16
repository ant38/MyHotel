import { Component} from '@angular/core';
import { FiltreLieuComponent } from '../filtres/filtre-lieu/filtre-lieu.component';
import { FiltrePetitDejeunerComponent } from '../filtres/filtre-petit-dejeuner/filtre-petit-dejeuner.component';
import { FiltreDateArriveComponent } from '../filtres/filtre-date-arrive/filtre-date-arrive.component';
import { OffreService } from '../_services/index';
import { Offer } from '../_models/index';

@Component ({
    selector: 'recherche',
    templateUrl: './recherche.component.html',
    //styleUrls: ['./confirmation.component.css']
})

export class RechercheComponent {

    offres: Offer[];
    
    constructor(private offreService: OffreService) { }

    search(object): void {
        if (object.city!=null && object.adults!= null && object.children!=null && object.dateIn!=null && object.dateOut!=null){
            var dateIn = new Date(object.dateIn);
            var dateOut = new Date(object.dateOut);
            var dateInString = (dateIn.getMonth()+1)+"/"+(dateIn.getDate())+"/"+(dateIn.getFullYear());
            var dateOutString = (dateOut.getMonth()+1)+"/"+(dateOut.getDate())+"/"+(dateOut.getFullYear());
           
            this.offreService.search(object.city, object.adults, object.children, dateInString, dateOutString)
                .subscribe(offres => {this.offres = offres});
        }
    }
}