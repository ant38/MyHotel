"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var Hotel = /** @class */ (function () {
    function Hotel() {
    }
    return Hotel;
}());
exports.Hotel = Hotel;
var Offre = /** @class */ (function () {
    function Offre() {
    }
    return Offre;
}());
exports.Offre = Offre;
var OFFRES = [
    { id_offre: 1, name: 'offre1', },
    { id_offre: 2, name: 'offre2', },
    { id_offre: 3, name: 'offre3', },
];
var HOTELS = [
    { id: 11, name: 'Hotel 1', lieu: 'ici', dateIn: new Date(2000, 8, 1), dateOut: new Date(2000, 9, 1), offres: [OFFRES[0], OFFRES[2]] },
    { id: 12, name: 'Hotel 2', lieu: 'ici', dateIn: new Date(2000, 8, 1), dateOut: new Date(2000, 9, 1), offres: [OFFRES[0]] },
    { id: 13, name: 'Hotel 3', lieu: 'ici', dateIn: new Date(2000, 8, 1), dateOut: new Date(2000, 9, 1), offres: [OFFRES[0], OFFRES[1]] },
];
var DashboardComponent = /** @class */ (function () {
    function DashboardComponent() {
        this.hotels = HOTELS;
        this.offres = OFFRES;
    }
    DashboardComponent = __decorate([
        core_1.Component({
            selector: 'dashboard',
            templateUrl: './dashboard.component.html',
            styleUrls: ['./dashboard.component.css']
        })
    ], DashboardComponent);
    return DashboardComponent;
}());
exports.DashboardComponent = DashboardComponent;
//# sourceMappingURL=dashboard.component.js.map