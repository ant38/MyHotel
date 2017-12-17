import { Component, OnInit } from '@angular/core';

import { User,Booking } from '../_models/index';
import { UserService } from '../_services/index';
import { BookingService } from '../_services/index';

@Component({
    moduleId: module.id,
    templateUrl: 'home.component.html'
})

export class HomeComponent implements OnInit {
    currentUser: User;
    users: User[] = [];
    books: Booking[]=[];

    constructor(private userService: UserService, private bookService: BookingService) {
        this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
        //console.log("user: "+this.currentUser);
    }

    ngOnInit() {
        this.loadAllUsers();
        this.loadAllBooks();
    }

    deleteUser(id: number) {
        this.userService.delete(id).subscribe(() => { this.loadAllUsers() });
    }

    private loadAllUsers() {
        this.userService.getAll().subscribe(users => { this.users = users; });
    }
    private loadAllBooks() {
        this.bookService.getAllBooking().subscribe(books => { this.books = books; });
    }
    show(id :number){
       this.bookService.book(id,this.currentUser.id,0);
}
}
