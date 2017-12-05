import { Injectable } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';
import { HttpClient } from "@angular/common/http";
import { Hotel } from '../_models/hotel';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class HotelService {

  constructor(private http: HttpClient) { }

  private hotelURL = 'http://18.216.255.59/MyHotel/rest/hotels';

  getAllHotel(): Observable<Hotel[]> {
    return this.http.get<Hotel[]>(this.hotelURL);
  }

}
