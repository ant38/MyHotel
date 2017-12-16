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
    //console.log(this.lieuVar);
    this.lieuChange.emit(this.lieuVar);
  }    

  ngOnInit() {  }

}
