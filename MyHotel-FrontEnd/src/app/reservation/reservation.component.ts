import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { OffreService, BookingService } from '../_services/index';
import { LoadChildren } from '@angular/router/src/config';
import { Offer } from '../_models';


@Component({
  selector: 'reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css']
})
export class ReservationComponent {

  offreId: number;
  clientId: number;
  paid: number;

  constructor(private route: ActivatedRoute, private offreService: OffreService, private bookingService: BookingService, private location: Location) { }
  id: any;
  logged: boolean = false;
  prix: number;
  offre: Offer;
  
  goBack(): void {
    this.location.back();
  }

    book(object){
    var offreId = object.offre.id;
    var clientId = object.client.id;
    var paid = object.paid;
    this.bookingService.book(offreId, clientId, paid).subscribe(book=>{});
  }

  ngOnInit() {
    const idBis = +this.route.snapshot.paramMap.get('id');
    this.id = +this.route.snapshot.paramMap.get('id');
    if (localStorage.getItem("currentUser") != null) {
      this.logged = true;
    }
    this.offreService.getOffre(idBis).subscribe(offre => {this.offre = offre});
  }


  openCheckout() {
    this.prix = this.offre.price;
    console.log(this.prix);
    var handler = (<any>window).StripeCheckout.configure({
      key: 'pk_test_oi0sKPJYLGjdvOXOM8tE8cMa',
      locale: 'auto',
      token: function (token: any) {
        // You can access the token ID with `token.id`.
        // Get the token ID to your server-side code for use.
      }
    });

    handler.open({
      name: 'Paiement par carte',
      description: 'saisir votre email:',
      amount: this.prix*100
    });
  }
}
