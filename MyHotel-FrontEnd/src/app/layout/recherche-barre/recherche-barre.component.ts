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
  days: number;

  constructor() { }

  setLieu(city) {
    this.city=city;
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
  setnbNuit(days){
    this.days=days;
    this.search();
  }
  
  search(){
    this.parametre.emit(
      { city: this.city,
        adults: this.adults,
        children: this.children,
        dateIn: this.dateIn,
        dateOut: this.dateOut,
        days: this.days
      });
  }
  ngOnInit() {
  }

  

}
