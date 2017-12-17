import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';

import { User } from '../_models/index';

@Injectable()
export class UserService {
    constructor(private http: Http) { }

    getAll() {
        return this.http.get('http://18.216.255.59/MyHotel/rest/clients', this.jwt()).map((response: Response) => response.json());
    }

    getById(id: number) {
        return this.http.get('http://18.216.255.59/MyHotel/rest/clients' + id, this.jwt()).map((response: Response) => response.json());
    }

    create(user: User) {
//let headers = new Headers({ 'Content-Type': 'application/json' });model.prenom
    //let options = new RequestOptions({ headers: headers });
    let body = JSON.stringify(user);
        return this.http.post('http://18.216.255.59/MyHotel/rest/clients/newClient', null, { params: { prenom: user.prenom,nom: user.nom,username: user.username, password: user.password, dateNaissance: user.dateNaissance,email: user.email }}).map((response: Response) => response.json());
    }

    update(user: User) {
        return this.http.put('http://18.216.255.59/MyHotel/rest/clients' + user.id, user, this.jwt()).map((response: Response) => response.json());
    }

    delete(id: number) {
        return this.http.delete('http://18.216.255.59/MyHotel/rest/clients' + id, this.jwt()).map((response: Response) => response.json());
    }

    

    // private helper methods

    private jwt() {
        // create authorization header with jwt token
        let currentUser = JSON.parse(localStorage.getItem('currentUser'));
        if (currentUser && currentUser.token) {
            let headers = new Headers({ 'Authorization': 'Bearer ' + currentUser.token });
            return new RequestOptions({ headers: headers });
        }
    }
}
