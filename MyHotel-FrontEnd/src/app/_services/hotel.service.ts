import { Injectable } from '@angular/core';
import { Http, Headers, Response, ResponseContentType } from '@angular/http';
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


  /*getImage(imageUrl: string): Observable<File> {
    return this.http
        .get(imageUrl, { responseType: ResponseContentType.Blob })
        .map((res: Response) => res.blob());
  }

  /*imageToShow: any;
  createImage(image: Blob) {
    let reader = new FileReader();
    reader.addEventListener("load", () => {
      this.imageToShow = reader.result;
    }, false);
    if(image) {
      reader.readAsDataURL(image);
    }
  }

  getImageFromBlob() {
    
  }*/

  getImage(id: Number): Observable<any> {
    return this.http.get(this.hotelURL + String(id) + "/getImage");
  }

}
