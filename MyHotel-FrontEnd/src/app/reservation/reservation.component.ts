import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { OffreService } from '../_services/index';
import { LoadChildren } from '@angular/router/src/config';

@Component({
  selector: 'reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css']
})
export class ReservationComponent {

  constructor(private route: ActivatedRoute, private offreService: OffreService, private location: Location) { }
  id: any;
  
  goBack(): void {
      this.location.back();
  }

  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get('id');
  }

}
