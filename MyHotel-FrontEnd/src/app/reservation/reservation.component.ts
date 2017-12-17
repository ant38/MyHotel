import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
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

  constructor(private route: ActivatedRoute, private offreService: OffreService, private bookingService: BookingService, private location: Location, public router: Router) { }

  id: any;
  clientId: number;
  logged: boolean = false;
  prix: number;
  offre: Offer;
  
  goBack(): void {
    this.location.back();
  }

  book(paid){
    console.log("in book");
    this.bookingService.book(this.id, this.clientId, paid).subscribe(book=>{this.router.navigate(['confirmation/'+book[0].id_booking]);});

  }

  ngOnInit() {
    this.id = +this.route.snapshot.paramMap.get('id');
    if (localStorage.getItem("currentUser") != null) {
      this.logged = true;
      var currentUser = JSON.parse(localStorage.getItem('currentUser'));
      this.clientId = currentUser.id;
    }
    this.offreService.getOffre(this.id).subscribe(offre => {this.offre = offre});
  }


  openCheckout() {
    this.prix = this.offre.price;
    var handler = (<any>window).StripeCheckout.configure({
      key: 'pk_test_oi0sKPJYLGjdvOXOM8tE8cMa',
      locale: 'french',
      currency: 'EUR',
      token: function (token: any) {
      }
    });

    handler.open({
      name: 'Paiement par carte',
      description: 'saisir votre email:',
      amount: this.prix*100 //le prix est divisé par 100 !?
    });
  }
}
