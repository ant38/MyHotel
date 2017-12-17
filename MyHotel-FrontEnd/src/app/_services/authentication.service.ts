import { Injectable } from '@angular/core';
import { Http, 
  Response,
  Headers } from '@angular/http';//RequestOptionsArgs,RequestOptions,
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map'

/*import { AUTH_CONFIG } from '../_guards/auth0-variables';
import * as auth0 from 'auth0-js';

import * as jwt_decode from 'jwt-decode';

export const TOKEN_NAME: string = 'jwt_token';//
*/

@Injectable()
export class AuthenticationService {
/*
    private headers = new Headers({ 'Content-Type': 'application/json' });

    auth0 = new auth0.WebAuth({
    clientID: AUTH_CONFIG.clientID,
    domain: AUTH_CONFIG.domain,
    responseType: 'token id_token',
    audience: `https://${AUTH_CONFIG.domain}/userinfo`,
    redirectUri: AUTH_CONFIG.callbackURL,
    scope: 'openid'
});*/

    constructor(private http: Http) { }
/*
  setToken(token: string): void {
    localStorage.setItem(TOKEN_NAME, token);
  }
    getToken(): string {
    return localStorage.getItem(TOKEN_NAME);
  }

  getTokenExpirationDate(token: string): Date {
    const decoded = jwt_decode(token);

    if (decoded.exp === undefined) return null;

    const date = new Date(0); 
    date.setUTCSeconds(decoded.exp);
    return date;
  }

  isTokenExpired(token?: string): boolean {
    if(!token) token = this.getToken();
    if(!token) return true;

    const date = this.getTokenExpirationDate(token);
    if(date === undefined) return false;
    return !(date.valueOf() > new Date().valueOf());
}*/

    login(username: string, password: string) {
        
	return this.http.post('http://18.216.255.59/MyHotel/rest/clients/isClient', null, { params: { username: username, password: password }}).map((response: Response) =>  {
                // login successful if there's a jwt token in the response
                let user = response.json();
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
