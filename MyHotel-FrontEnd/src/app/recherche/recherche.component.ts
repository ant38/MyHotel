import { Component, OnInit, NgModule } from '@angular/core';
import { FormControl } from "@angular/forms";
import { HotelService } from "../_services/index";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { log } from 'util';

//Filtres utilisés
import { FiltreAvisComponent, FiltreDateArriveComponent, FiltreDateDepartComponent, FiltreLieuComponent, FiltreNombreNuitComponent, FiltreNombrePersonneComponent, FiltrePetitDejeunerComponent, FiltrePrixComponent } from "../filtres/index";

@Component ({
    selector: 'recherche',
    templateUrl: './recherche.component.html',
    //styleUrls: ['./confirmation.component.css']
})

export class RechercheComponent {
    allHotel: any = null;
    hotelForm: FormGroup; // <-- hotelForm is of type FormGroup
    lieuVar: string;
    petitDejVar: boolean = false;
    dateInVar: string;

    constructor(private hotelService: HotelService, private fb: FormBuilder) { // <-- inject FormBuilder
        this.createForm();       
    }

    createForm() {
        this.hotelForm = this.fb.group({
            lieu: '', //Validators.required ], // <-- the FormControl called "lieu"
            petitDej: '',
            dateIn: '',
        })
    }
    
    handleChange(e) {
        console.log(this.petitDejVar)
    }

    onKeyLieu(event: any){
        console.log(this.lieuVar);
    }    

    getHotels(): void {
        this.hotelService.getAllHotel().subscribe(hotels => this.allHotel = hotels);
        console.log(this.allHotel);
    }

    ngOnInit() {
        this.getHotels();
    }
    
    onDateIn(e) {
        console.log(this.dateInVar);
    }
    // ('#datetimepicker6').datetimepicker();
 }