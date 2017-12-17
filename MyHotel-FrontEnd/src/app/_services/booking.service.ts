import { Injectable } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';
import { HttpClient } from "@angular/common/http";
import { Observable } from 'rxjs/Observable';
import { Booking, Room, User} from '../_models/index';

@Injectable()
export class BookingService {

  constructor(private http: HttpClient) { }
  private BookingsURL = 'http://18.216.255.59/MyHotel/rest/bookings';
  
  getAllBooking(): Observable<Booking[]> {
    return this.http.get<Booking[]>(this.BookingsURL);
  }

  getBooking(id): Observable<Booking> {
    return this.http.get<Booking>(this.BookingsURL+'/'+id);
  }

  book(offreId, clientId, paid): Observable<Booking[]> {
    return this.http.get<Booking[]>(this.BookingsURL+"/book?offerId="+offreId+"&clientId="+clientId+"&paid="+paid);
  }
}
/*
import { Room } from "./room";
import { User } from "./user";
export class Booking {
    id_booking: number;
    version: number;
    dateIn: Date;
    dateOut: Date;
    paid: boolean;
    roomlist: Room[];
    clientList: User[];
}

http://18.216.255.59/MyHotel/rest/bookings/book?offerId=138&clientId=6&paid=0
*/