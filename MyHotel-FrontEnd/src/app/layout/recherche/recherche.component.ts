import { Component, OnInit, NgModule } from '@angular/core';
import { FormControl } from "@angular/forms";
import { HotelService } from "../../_services/index";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { log } from 'util';

@Component ({
    selector: 'recherche',
    templateUrl: './recherche.component.html',
    //styleUrls: ['./confirmation.component.css']
})

export class RechercheComponent {
    allHotel: any = null;
    lieuForm: FormGroup; // <-- lieuForm is of type FormGroup
    ee: string;

    constructor(private hotelService: HotelService, private fb: FormBuilder) { // <-- inject FormBuilder
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

    getHotels(): void {
        this.hotelService.getAllHotel().subscribe(hotels => this.allHotel = hotels);
        console.log(this.allHotel);
    }

    ngOnInit() {
        this.getHotels();
    }
 }
