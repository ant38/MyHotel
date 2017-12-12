import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-filtre-date-arrive',
  templateUrl: './filtre-date-arrive.component.html',
  styleUrls: ['./filtre-date-arrive.component.css']
})
export class FiltreDateArriveComponent implements OnInit {
  dateInVar: string;

  constructor() { }
  

  ngOnInit() {
    const myInput = document.querySelector('#myInput');
    myInput.addEventListener("change", function() {
        let input = this.value;
        let dateEntered = new Date(input);
        console.log(input);
        console.log(dateEntered);

        this.dateInVar = this.value;
        console.log(this.dateInVar);
    });
  }

}
