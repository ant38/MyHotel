import { Component, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-filtre-nombre-nuit',
  templateUrl: './filtre-nombre-nuit.component.html',
  styleUrls: ['./filtre-nombre-nuit.component.css']
})
export class FiltreNombreNuitComponent implements OnInit {
  
  constructor() { }

  nbNuitVar: number;
  @Output() 
  nbNuitChange: EventEmitter<number> = new EventEmitter<number>();

  onChangeNbNuit(e){
    this.nbNuitChange.emit(this.nbNuitVar);
    localStorage.setItem("nbNuit", String(this.nbNuitVar));
  }

  ngOnInit() {
    if (localStorage.getItem("nbNuit")!= null){
      this.nbNuitVar = Number(localStorage.getItem("nbNuit"));
      this.nbNuitChange.emit(this.nbNuitVar);
    }
  }

}
