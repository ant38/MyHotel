import { Component, OnInit } from '@angular/core';
import { NouisliderModule } from 'ng2-nouislider';

@Component({
  selector: 'app-filtre-prix',
  templateUrl: './filtre-prix.component.html',
  styleUrls: ['./filtre-prix.component.css']
})
export class FiltrePrixComponent implements OnInit {

  prixVar: any = [0,500];

  constructor() { }

  onKeyPrix(e) {
    console.log(this.prixVar);
  }
  ngOnInit() {
  }

}
