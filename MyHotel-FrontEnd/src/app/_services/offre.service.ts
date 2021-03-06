import { Injectable } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';
import { HttpClient } from "@angular/common/http";
import { Observable } from 'rxjs/Observable';
import { Offer,Hotel } from '../_models/index';


@Injectable()
export class OffreService {

  constructor(private http: HttpClient) { }
  
  private offresURL = 'http://18.216.255.59/MyHotel/rest/offers';

  getAllOffres(): Observable<Offer[]> {
    return this.http.get<Offer[]>(this.offresURL);
  }

  getOffre(id): Observable<Offer> {
    return this.http.get<Offer>(this.offresURL+'/'+id);
  }

  search(city, adults, children, dateIn, dateOut, days=0): Observable<Offer[]> {
    return this.http.get<Offer[]>(this.offresURL+"/search?city="+city+"&adults="+adults+"&children="+children+"&dateIn="+dateIn+"&dateOut="+dateOut+"&days="+days);
  }
}
