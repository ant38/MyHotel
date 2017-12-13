import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-filtre-prix',
  templateUrl: './filtre-prix.component.html',
  styleUrls: ['./filtre-prix.component.css']
})
export class FiltrePrixComponent implements OnInit {

  prixVar: number;

  constructor() { }

  onKeyPrix(e) {
    console.log(this.prixVar);
  }
  ngOnInit() {
  }

}
