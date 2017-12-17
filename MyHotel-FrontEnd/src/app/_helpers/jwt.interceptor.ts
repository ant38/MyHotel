import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
//import {  AuthenticationService } from '../_services/index';
 
@Injectable()
export class JwtInterceptor implements HttpInterceptor {

    //constructor(private authenticationService: AuthenticationService) {}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // add authorization header with jwt token if available
        let currentUser = JSON.parse(localStorage.getItem('currentUser'));
        if (currentUser) {// && currentUser.token
            request = request.clone({
                setHeaders: {
                    Authorization: `Bearer ${currentUser.getToken}` //this.authenticationService
                }
            });
        }
 
        return next.handle(request);
    }
}
