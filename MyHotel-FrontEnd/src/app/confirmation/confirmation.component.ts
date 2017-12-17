import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'confirmation',
  templateUrl: './confirmation.component.html',
  styleUrls: ['./confirmation.component.css']
})
export class ConfirmationComponent implements OnInit {

  constructor(private route: ActivatedRoute) { }
  
  booking_id: number;

  ngOnInit() {
    this.booking_id = +this.route.snapshot.paramMap.get('id');
  }

}
