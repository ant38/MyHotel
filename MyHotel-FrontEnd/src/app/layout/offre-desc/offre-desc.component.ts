import { Component, OnInit, Input } from '@angular/core';
import { Offer } from '../../_models/index';

@Component({
  selector: 'app-offre-desc',
  templateUrl: './offre-desc.component.html',
  styleUrls: ['./offre-desc.component.css']
})
export class OffreDescComponent implements OnInit {

  @Input() offre: Offer;

  constructor() { }

  ngOnInit() {
  }

}
