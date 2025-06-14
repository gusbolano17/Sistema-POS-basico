import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {LoginService} from '../../servicios/login.service';
import {ButtonModule} from 'primeng/button';
import {ToastService} from '../../servicios/toast.service';
import {MessageService} from 'primeng/api';
import {Toast} from 'primeng/toast';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, ButtonModule, Toast],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  standalone: true,
  providers: [LoginService,MessageService, ToastService]
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
