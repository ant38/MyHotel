import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { OffreService, BookingService } from '../_services/index';
import { LoadChildren } from '@angular/router/src/config';

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
    this.id = this.route.snapshot.paramMap.get('id');
  }

}