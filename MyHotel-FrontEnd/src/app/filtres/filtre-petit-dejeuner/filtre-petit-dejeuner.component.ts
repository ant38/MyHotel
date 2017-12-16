import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-filtre-petit-dejeuner',
  templateUrl: './filtre-petit-dejeuner.component.html',
  styleUrls: ['./filtre-petit-dejeuner.component.css']
})
export class FiltrePetitDejeunerComponent implements OnInit {

  petitDejVar: boolean = false;
  
  constructor() { }
  handleChange(e) {
    console.log(this.petitDejVar)
}

  ngOnInit() {  }

}
