import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-filtre-nombre-personne',
  templateUrl: './filtre-nombre-personne.component.html',
  styleUrls: ['./filtre-nombre-personne.component.css']
})
export class FiltreNombrePersonneComponent implements OnInit {

  nbPersVar: number;

  constructor() { }

  onKeyPers(e) {
    console.log(this.nbPersVar);
  }
  ngOnInit() {
  }

}
