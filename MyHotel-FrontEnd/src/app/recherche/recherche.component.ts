import { Component, OnInit, NgModule } from '@angular/core';
import { FormControl } from "@angular/forms";
import { HotelService } from "../_services/index";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { log } from 'util';

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

    onClick() {
        console.log('OK CLIC')
    }

    
    // function () {
    //     ('#datetimepicker6').datetimepicker();
    //     ('#datetimepicker7').datetimepicker({
    //         useCurrent: false //Important! See issue #1075
    //     });
    //     ("#datetimepicker6").on("dp.change", function (e) {
    //         ('#datetimepicker7').data("DateTimePicker").minDate(e.date);
    //     });
    //     ("#datetimepicker7").on("dp.change", function (e) {
    //         ('#datetimepicker6').data("DateTimePicker").maxDate(e.date);
    //     });
//};
 }
