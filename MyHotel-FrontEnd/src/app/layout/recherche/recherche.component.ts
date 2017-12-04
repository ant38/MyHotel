import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";

@Component ({
    selector: 'recherche',
    templateUrl: './recherche.component.html',
    //styleUrls: ['./confirmation.component.css']
})

export class RechercheComponent {
    lieuForm: FormGroup; // <-- lieuForm is of type FormGroup
    
    constructor(private fb: FormBuilder) { // <-- inject FormBuilder
        this.createForm();
        console.log("Form Submitted!");        
    }

    createForm() {
        this.lieuForm = this.fb.group({
            hotel: ['', Validators.required ], // <-- the FormControl called "hotel"
        })
    }

    ngOnChanges() {
        console.log(this.lieuForm.valueChanges);
    }
 }
