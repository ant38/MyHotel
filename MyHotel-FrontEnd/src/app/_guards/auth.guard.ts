import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
/*import { AuthenticationService } from '../_services/index';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/catch';*/

//Modified by Qianqian: it's here to show the 1st page.(Not logged User)
@Injectable()
export class AuthGuard implements CanActivate {

    constructor(private router: Router) { }  //private authService: AuthenticationService,

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        if (localStorage.getItem('currentUser')) {
            // logged in so return true
            return true;
        }

        // not logged in so redirect to DashBoard page with the return url
        this.router.navigate(['/dashboard'], { queryParams: { returnUrl: state.url }});
        return false;

    }
/*
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (!this.authService.isTokenExpired()) {
      return true;
    }

    this.router.navigate(['/dashboard'], { queryParams: { returnUrl: state.url }});
    return false;
}
        canActivateChild(){
		return this.authService.isAuthenticated();
        }*/
}
