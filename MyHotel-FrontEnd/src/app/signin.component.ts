import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
    selector: 'USERsignin',
    templateUrl: 'signin.component.html'
})

export class SigninComponent {
    model: any = {};
    loading = false;

    constructor(
        private router: Router,){}

    signin() {
        this.loading = true;
        console.log("sign in successfully~");}
}
