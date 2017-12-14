import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-filtre-nombre-nuit',
  templateUrl: './filtre-nombre-nuit.component.html',
  styleUrls: ['./filtre-nombre-nuit.component.css']
})
export class FiltreNombreNuitComponent implements OnInit {

  nbNuitVar: number;

  constructor() { }

  onKeyNuit(e) {
    console.log(this.nbNuitVar);
  }
  ngOnInit() {
  }

}
