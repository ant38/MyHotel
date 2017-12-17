import { Component, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-filtre-nombre-personne',
  templateUrl: './filtre-nombre-personne.component.html',
  styleUrls: ['./filtre-nombre-personne.component.css']
})
export class FiltreNombrePersonneComponent implements OnInit {

  nbPersVar: number;
  nbAdultsVar: number;
  nbChildrenVar: number;

  @Output() 
  nbAdultsChange: EventEmitter<number> = new EventEmitter<number>();
  
  @Output() 
  nbChildrenChange: EventEmitter<number> = new EventEmitter<number>();

  onChangeAdults(e){
    this.nbAdultsChange.emit(this.nbAdultsVar);
  }

  onChangeChildren(e){
    this.nbChildrenChange.emit(this.nbChildrenVar);
  } 
  constructor() { }

  onKeyPers(e) {
    console.log(this.nbPersVar);
  }
  ngOnInit() {
  }

}
