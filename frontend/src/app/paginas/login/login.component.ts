import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {LoginService} from '../../servicios/login.service';
import {ButtonModule} from 'primeng/button';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, ButtonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  standalone: true
})
export class LoginComponent {

  public loginForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private loginS : LoginService) {
    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]],
    })
  }

  submitLogin() {
    this.loginS.login(this.loginForm.value);
  }
}
