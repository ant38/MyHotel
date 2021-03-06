﻿import { Injectable } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map'

@Injectable()
export class AuthenticationService {
    constructor(private http: Http) { }

    login(username: string, password: string) {
        
	return this.http.post('http://18.216.255.59/MyHotel/rest/clients/isClient', null, { params: { username: username, password: password }}).map((response: Response) =>  {
                // login successful if there's a jwt token in the response
                let user = response.json();
	              console.log(user);
    //localStorage.setItem(TOKEN_NAME, response.headers.get("Authorization")); //'id_token'
                console.log(response.headers.get("Authorization"));
                if (user) {// && user.token
                    // store user details and jwt token in local storage to keep user logged in between page refreshes
                    localStorage.setItem('currentUser', JSON.stringify(user));
		    //this.loggedIn.next(true);
		    //localStorage.setItem('isLoggedin', 'true');
                }

                return user;
            });
    }

    logout() {
        // remove user from local storage to log user out
        localStorage.removeItem('currentUser');
        //this.loggedIn.next(false);
    }
}
