import {Component, OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
//for msg
import { AlertService, AuthenticationService } from './_services/index';
import {
  //REACTIVE_FORM_DIRECTIVES,
  Validators,
  FormGroup,
  FormBuilder,
  FormControl
} from '@angular/forms';

@Component({
  //selector: 'USERlogin',
  moduleId:module.id,
  templateUrl: './login.component.html'//,  // path:./
  //directives: [REACTIVE_FORM_DIRECTIVES]
})
export class LoginComponent implements OnInit{
  model: any = {};
    loading = false;
    returnUrl: string;

  username = new FormControl('', [
    Validators.required,
    Validators.minLength(5)
  ]);

  password = new FormControl('', [Validators.required]);
  builder: FormBuilder;
  loginForm: FormGroup = this.builder.group({
    username: this.username,
    password: this.password
  });
//private builder: FormBuildeR
  constructor(private route: ActivatedRoute,
        private router: Router,
        private authenticationService: AuthenticationService,
        private alertService: AlertService) { }

  //login () {
    
  //  console.log(this.loginForm.value);
    // Attempt Logging in...
  //}
  ngOnInit() {
        // reset login status
        this.authenticationService.logout();
 
        // get return url from route parameters or default to '/'
        this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    }
 
    login() {
        this.loading = true;
        this.authenticationService.login(this.model.username, this.model.password)
            .subscribe(
                data => {
                    this.router.navigate([this.returnUrl]);
                },
                error => {
                    this.alertService.error(error);
                    this.loading = false;
                });
    }
}
