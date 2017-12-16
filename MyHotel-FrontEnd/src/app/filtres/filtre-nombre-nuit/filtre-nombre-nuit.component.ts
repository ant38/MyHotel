import { Component, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-filtre-nombre-nuit',
  templateUrl: './filtre-nombre-nuit.component.html',
  styleUrls: ['./filtre-nombre-nuit.component.css']
})
export class FiltreNombreNuitComponent implements OnInit {

  nbNuitVar: number;
  @Output() 
  nbNuitChange: EventEmitter<number> = new EventEmitter<number>();

  onChangeNbNuit(e){
    this.nbNuitChange.emit(this.nbNuitVar);
  } 

  constructor() { }

  onKeyNuit(e) {
    console.log(this.nbNuitVar);
  }
  ngOnInit() {
  }

}
