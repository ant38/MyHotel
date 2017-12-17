import { Component, OnInit, Output, EventEmitter, Injectable } from '@angular/core';

@Component({
  selector: 'app-filtre-lieu',
  templateUrl: './filtre-lieu.component.html',
  styleUrls: ['./filtre-lieu.component.css']
})

@Injectable()
export class FiltreLieuComponent implements OnInit {

  lieuVar: string;
  @Output() lieuChange: EventEmitter<string> = new EventEmitter<string>();

  constructor() { }

  onChangeLieu(e){
    this.lieuChange.emit(this.lieuVar);
    localStorage.setItem("lieu", this.lieuVar);
  }    

  ngOnInit() {
    if (localStorage.getItem("lieu")!=null){
      this.lieuVar = localStorage.getItem("lieu");
      this.lieuChange.emit(this.lieuVar);
    }

  }

}
