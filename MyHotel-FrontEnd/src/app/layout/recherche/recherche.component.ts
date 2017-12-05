import { Component, OnInit, NgModule } from '@angular/core';
import { FormControl } from "@angular/forms";
import { HotelService } from "../../_services/index";

@Component ({
    selector: 'recherche',
    templateUrl: './recherche.component.html',
    //styleUrls: ['./confirmation.component.css']
})

export class RechercheComponent {
    name = new FormControl();

    allHotel: any = null;

    constructor(private hotelService: HotelService){}

    getHotels(): void {
        this.hotelService.getAllHotel().subscribe(hotels => this.allHotel = hotels);
        console.log(this.allHotel);
    }
    
    ngOnInit() {
        this.getHotels();
    }
}