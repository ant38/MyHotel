import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-filtre-lieu',
  templateUrl: './filtre-lieu.component.html',
  styleUrls: ['./filtre-lieu.component.css']
})
export class FiltreLieuComponent implements OnInit {

  lieuVar: string;

  constructor() { }

  onKeyLieu(e){
    console.log(this.lieuVar);
  }    

  ngOnInit() {
  }

}
