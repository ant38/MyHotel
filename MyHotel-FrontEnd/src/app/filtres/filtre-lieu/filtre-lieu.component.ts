<<<<<<< HEAD
import { Component, OnInit, Injectable } from '@angular/core';
=======
import { Component, OnInit, Output, EventEmitter } from '@angular/core';
>>>>>>> c7d1969df2d44346d61dba9d385895e984687c2d

@Component({
  selector: 'app-filtre-lieu',
  templateUrl: './filtre-lieu.component.html',
  styleUrls: ['./filtre-lieu.component.css']
})

@Injectable()
export class FiltreLieuComponent implements OnInit {

  lieuVar: string;
  @Output() 
  lieuChange: EventEmitter<string> = new EventEmitter<string>();

  constructor() { }

<<<<<<< HEAD
  getLieu(): string {
    return this.lieuVar;
  }
  onKeyLieu(e){
    console.log(this.lieuVar);
  } 
=======
  onChangeLieu(e){
    //console.log(this.lieuVar);
    this.lieuChange.emit(this.lieuVar);
  }    
>>>>>>> c7d1969df2d44346d61dba9d385895e984687c2d

  ngOnInit() {  }

}
