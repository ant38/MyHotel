import { Component, OnInit } from '@angular/core';
import { NouisliderModule } from 'ng2-nouislider';

@Component({
  selector: 'app-filtre-avis',
  templateUrl: './filtre-avis.component.html',
  styleUrls: ['./filtre-avis.component.css']
})
export class FiltreAvisComponent implements OnInit {

  avisVar: string;

  constructor() { }

  onKeyAvis(e) {
    console.log(this.avisVar);
  }
  ngOnInit() {
  }

}
