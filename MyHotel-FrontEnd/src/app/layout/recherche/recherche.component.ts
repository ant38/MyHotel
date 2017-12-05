import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { log } from 'util';

@Component ({
    selector: 'recherche',
    templateUrl: './recherche.component.html',
    //styleUrls: ['./confirmation.component.css']
})

export class RechercheComponent {
    lieuForm: FormGroup; // <-- lieuForm is of type FormGroup
    ee: string;

    constructor(private fb: FormBuilder) { // <-- inject FormBuilder
        this.createForm();
        console.log("Form Submitted!");        
    }

    createForm() {
        this.lieuForm = this.fb.group({
            hotel: ['', Validators.required ], // <-- the FormControl called "hotel"
        })
    }
    
    onKey(event: any){
        console.log(this.ee);
    }
 }
