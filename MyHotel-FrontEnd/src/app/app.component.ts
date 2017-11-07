import { Component } from '@angular/core';

@Component({ 
  moduleId: module.id,////////////
  selector: 'app-root',
  //templateUrl: './app.component.html',

  template: `
    <h1>{{title}}</h1>
    <nav>
      <a routerLink="/dashboard" routerLinkActive="active">Accueil</a>
      <a routerLink="/login" routerLinkActive="active">LogIn</a>
      <a routerLink="/signin" routerLinkActive="active">SignIn</a>
    </nav>
    <router-outlet></router-outlet>
  `,

  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'My Hotel';
}
