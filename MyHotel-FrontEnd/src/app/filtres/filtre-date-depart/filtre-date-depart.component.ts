import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import {IMyDpOptions, IMyDateModel, IMyInputFieldChanged, IMyCalendarViewChanged, IMyInputFocusBlur, IMyMarkedDate, IMyDate, IMySelector} from 'mydatepicker';

@Component({
  selector: 'app-filtre-date-depart',
  templateUrl: './filtre-date-depart.component.html',
  styleUrls: ['./filtre-date-depart.component.css']
})
export class FiltreDateDepartComponent implements OnInit {

  @Output() 
  dateOutChange: EventEmitter<Date> = new EventEmitter<Date>();
  
  public myDatePickerOptions: IMyDpOptions = {
    // other options...
    dateFormat: 'dd.mm.yyyy',
  };
  private dateOutVar: IMyDate = {year: 0, month: 0, day: 0};

  constructor() {
    // set today to selDate attribute
    /*let d: Date = new Date();
    this.dateOutVar = {year: d.getFullYear(), 
                    month: d.getMonth() + 1, 
                    day: d.getDate()+1};*/
  }

  onDateChanged(event: IMyDateModel) {
    // Update value of selDate variable
    this.dateOutVar = event.date;
    this.dateOutChange.emit(event.jsdate);
    localStorage.setItem("dateOut",event.formatted);
  }

  // Calling this function clears the selected date
  clearDate() {
    this.dateOutVar = {year: 0, month: 0, day: 0};
  }
  ngOnInit(){
    if (localStorage.getItem("dateOut")!=null){
      var mydate = new Date(localStorage.getItem("dateOut"));
      this.dateOutVar = { year: mydate.getFullYear(), 
                          month: mydate.getMonth() + 1, 
                          day: mydate.getDate()};

      this.dateOutChange.emit(mydate);  
    }
  }
}