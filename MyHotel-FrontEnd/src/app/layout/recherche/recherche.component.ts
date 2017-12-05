import { Component, OnChanges, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { log } from 'util';

@Component ({
    selector: 'recherche',
    templateUrl: './recherche.component.html',
    //styleUrls: ['./confirmation.component.css']
})

export class RechercheComponent {
    


    hotelForm: FormGroup; // <-- hotelForm is of type FormGroup
    lieuVar: string;
    petitDejVar: boolean = false;

    constructor(private fb: FormBuilder) { // <-- inject FormBuilder
        this.createForm();       
    }

    createForm() {
        this.hotelForm = this.fb.group({
            lieu: '', //Validators.required ], // <-- the FormControl called "lieu"
            petitDej: '',
        })
    }
    
    handleChange(e) {
        console.log(this.petitDejVar)
    }

    onKeyLieu(event: any){
        console.log(this.lieuVar);
    }    
 }
