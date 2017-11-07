import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { AlertService, UserService } from './_services/index';

@Component({
    selector: 'USERsignin',
    templateUrl: 'signin.component.html'
})

export class SigninComponent {
    model: any = {};
    loading = false;

    constructor(
        private router: Router,
        private userService: UserService,
        private alertService: AlertService) { }

    signin() {
        this.loading = true;
        console.log("sign in successfully~");
        this.userService.create(this.model)
            .subscribe(
                data => {
                    this.alertService.success('Registration successful', true);
                    this.router.navigate(['/login']);
                },
                error => {
                    this.alertService.error(error);
                    this.loading = false;
                });
    }
}
