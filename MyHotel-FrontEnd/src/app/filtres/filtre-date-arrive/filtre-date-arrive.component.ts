import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import {IMyDpOptions, IMyDateModel, IMyInputFieldChanged, IMyCalendarViewChanged, IMyInputFocusBlur, IMyMarkedDate, IMyDate, IMySelector} from 'mydatepicker';


@Component({
  selector: 'app-filtre-date-arrive',
  templateUrl: './filtre-date-arrive.component.html',
  styleUrls: ['./filtre-date-arrive.component.css']
})
export class FiltreDateArriveComponent implements OnInit {
  @Output() 
  dateInChange: EventEmitter<Date> = new EventEmitter<Date>();
  
  public myDatePickerOptions: IMyDpOptions = {
    // other options...
    dateFormat: 'dd.mm.yyyy',
  };
  private dateInVar: IMyDate = {year: 0, month: 0, day: 0};

  constructor() {
    // set today to selDate attribute
    /*let d: Date = new Date();
    this.dateInVar = {year: d.getFullYear(), 
                    month: d.getMonth() + 1, 
                    day: d.getDate()};*/
  }

  onDateChanged(event: IMyDateModel) {
    // Update value of selDate variable
    this.dateInVar = event.date;
    this.dateInChange.emit(event.jsdate);
    localStorage.setItem("dateIn",event.formatted);
  }

  // Calling this function clears the selected date
  clearDate() {
    this.dateInVar = {year: 0, month: 0, day: 0};
  }
  ngOnInit(){
    if (localStorage.getItem("dateIn")!=null){
      var mydate = new Date(localStorage.getItem("dateIn"));
      this.dateInVar = { year: mydate.getFullYear(), 
                          month: mydate.getMonth() + 1, 
                          day: mydate.getDate()};

      this.dateInChange.emit(mydate);      
    }
  }
}