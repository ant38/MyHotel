import { Component, OnInit, Injectable } from '@angular/core';

@Component({
  selector: 'app-filtre-lieu',
  templateUrl: './filtre-lieu.component.html',
  styleUrls: ['./filtre-lieu.component.css']
})

@Injectable()
export class FiltreLieuComponent implements OnInit {

  lieuVar: string;

  constructor() { }

  getLieu(): string {
    return this.lieuVar;
  }
  onKeyLieu(e){
    console.log(this.lieuVar);
  } 

  ngOnInit() {  }

}
