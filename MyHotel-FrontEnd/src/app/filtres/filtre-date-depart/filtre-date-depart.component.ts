import { Component, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-filtre-date-depart',
  templateUrl: './filtre-date-depart.component.html',
  styleUrls: ['./filtre-date-depart.component.css']
})
export class FiltreDateDepartComponent implements OnInit {
  dateOutVar: Date;

  @Output() 
  dateOutChange: EventEmitter<Date> = new EventEmitter<Date>();

  onChangeDateOut(e){
    console.log("onChangeDateOut");
    this.dateOutChange.emit(this.dateOutVar);
  } 

  ngOnInit() {
  }

}
