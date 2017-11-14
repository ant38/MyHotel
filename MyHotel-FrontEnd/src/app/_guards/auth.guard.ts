import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
//Modified by Qianqian: it's here to show the 1st page.(Not logged User)
@Injectable()
export class AuthGuard implements CanActivate {

    constructor(private router: Router) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        if (localStorage.getItem('currentUser')) {
            // logged in so return true
            return true;
        }

        // not logged in so redirect to DashBoard page with the return url
        this.router.navigate(['/dashboard'], { queryParams: { returnUrl: state.url }});
        return false;
    }
}
