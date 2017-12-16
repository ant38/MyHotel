import { Component, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-recherche-barre',
  templateUrl: './recherche-barre.component.html',
  styleUrls: ['./recherche-barre.component.css']
})
export class RechercheBarreComponent implements OnInit {


  @Output()
  parametre: EventEmitter<any>= new EventEmitter<any>();

  city: string;
  adults: number;
  children: number;
  dateIn: Date;
  dateOut: Date;
  nbNuit: number;

  constructor() { }

  setLieu(city) {
    this.city=city;
    console.log("setLieu dans barre component");
    this.search();
  }
  setAdults(adults) {
    this.adults=adults;
    this.search();
  }
  setChildren(children) {
    this.children=children;
    this.search();
  }
  setDateIn(dateIn) {
    this.dateIn=dateIn;
    this.search();
  }
  setDateOut(dateOut) {
    this.dateOut=dateOut;
    this.search();
  }
  setnbNuit(nbNuit){
    this.nbNuit=nbNuit;
    this.search();
  }
  
  search(){
    console.log("dans search de barre component");
    this.parametre.emit(
      { city: this.city,
        adults: this.adults,
        children: this.children,
        dateIn: this.dateIn,
        dateOut: this.dateOut
      });
  }
  ngOnInit() {
  }

}
