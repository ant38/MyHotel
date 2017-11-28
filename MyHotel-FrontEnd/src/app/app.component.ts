import { Component } from '@angular/core';


@Component({
    moduleId: module.id,
    selector: 'app',
    templateUrl: 'app.component.html'
})

export class AppComponent {

    getUser() {
        let user =  JSON.parse(localStorage.getItem('currentUser'));
        console.log("user: "+user);
        return user;
    }
 }