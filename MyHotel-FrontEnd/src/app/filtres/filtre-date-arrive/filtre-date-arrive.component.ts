import { Component, OnInit, Output, EventEmitter } from '@angular/core';


@Component({
  selector: 'app-filtre-date-arrive',
  templateUrl: './filtre-date-arrive.component.html',
  styleUrls: ['./filtre-date-arrive.component.css']
})
export class FiltreDateArriveComponent implements OnInit {
  dateInVar: Date;

  @Output() 
  dateInChange: EventEmitter<Date> = new EventEmitter<Date>();

  constructor() { }

  onChangeDateIn(e) {
    console.log("onChangeDateIn");
    this.dateInChange.emit(this.dateInVar);
  } 

  ngOnInit() {
  }
}
