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
    localStorage.setItem("nbAdults", String(this.nbAdultsVar));
  }

  onChangeChildren(e){
    this.nbChildrenChange.emit(this.nbChildrenVar);
    localStorage.setItem("nbChildren", String(this.nbChildrenVar));    
  } 
  constructor() { }

  onKeyPers(e) {
    console.log(this.nbPersVar);
  }
  ngOnInit() {
    if (localStorage.getItem("nbAdults")!= null){
      this.nbAdultsVar = Number(localStorage.getItem("nbAdults"));
    }
    else {
      this.nbAdultsVar = 1;
    }
    if (localStorage.getItem("nbChildren")!=null){
      this.nbChildrenVar = Number(localStorage.getItem("nbChildren"));      
    }
    else {
    this.nbChildrenVar = 0;    
    }
    this.nbAdultsChange.emit(this.nbAdultsVar);
    this.nbChildrenChange.emit(this.nbChildrenVar);
    
  }

}
