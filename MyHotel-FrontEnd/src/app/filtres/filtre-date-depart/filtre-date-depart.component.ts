import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-filtre-date-depart',
  templateUrl: './filtre-date-depart.component.html',
  styleUrls: ['./filtre-date-depart.component.css']
})
export class FiltreDateDepartComponent implements OnInit {
  dateOutVar: string;

  ngOnInit() {
    const myInput2 = document.querySelector('#myInput2');
    myInput2.addEventListener("change", function() {
        let input = this.value;
        let dateQuit = new Date(input);
        console.log(input);
        console.log(dateQuit);

        this.dateOutVar = this.value;
        console.log(this.dateOutVar);
    });
  }

}
