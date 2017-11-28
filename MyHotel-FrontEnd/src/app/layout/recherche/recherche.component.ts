import { Component, OnInit } from '@angular/core';
import { FormControl } from "@angular/forms";

@Component ({
    selector: 'recherche',
    templateUrl: './recherche.component.html',
    //styleUrls: ['./confirmation.component.css']
})

export class RechercheComponent {
    name = new FormControl();
    
}