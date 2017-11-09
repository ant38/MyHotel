import {Component} from '@angular/core';
import {
  //REACTIVE_FORM_DIRECTIVES,
  Validators,
  FormGroup,
  FormBuilder,
  FormControl
} from '@angular/forms';

@Component({
  //selector: 'login-form',
    moduleId:module.id,
  templateUrl: './app.login-form.component.html'//,  // path:./
  //directives: [REACTIVE_FORM_DIRECTIVES]
})
export class LoginForm {
  username = new FormControl('', [
    Validators.required,
    Validators.minLength(5)
  ]);

  password = new FormControl('', [Validators.required]);

  loginForm: FormGroup = this.builder.group({
    username: this.username,
    password: this.password
  });

  constructor(private builder: FormBuilder) { }

  login () {
    
    console.log(this.loginForm.value);
    // Attempt Logging in...
  }
}
