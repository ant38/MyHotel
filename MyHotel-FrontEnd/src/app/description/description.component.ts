import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { OffreService } from "../_services/index";
import { Offer } from "../_models/index";
import { Room } from '../_models/room';
import { Hotel } from '../_models/hotel';

@Component ({
    selector: 'description',
    templateUrl: './description.component.html'
    //styleUrls: ['./description.component.css']
})

export class DescriptionComponent {
    constructor(private route: ActivatedRoute, private offreService: OffreService, private location: Location) {}
    id: any;

    goBack(): void {
        this.location.back();
    }

    ngOnInit() {
        this.id = +this.route.snapshot.paramMap.get('id');
    }

}