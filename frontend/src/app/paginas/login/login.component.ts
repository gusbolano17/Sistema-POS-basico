import {Component, inject} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {LoginService} from '../../servicios/login.service';
import {ButtonModule} from 'primeng/button';
import {ToastService} from '../../servicios/toast.service';
import {MessageService} from 'primeng/api';
import {Toast} from 'primeng/toast';
import {InputTextModule} from 'primeng/inputtext';
import {FloatLabelModule} from 'primeng/floatlabel';
import {Card} from 'primeng/card';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [
    ReactiveFormsModule,
    ButtonModule,
    Toast,
    InputTextModule,
    FloatLabelModule,
    Card,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  standalone: true,
  providers: [LoginService,MessageService, ToastService]
})
export class LoginComponent {

  private formBuilder = inject(FormBuilder);
  private loginS = inject(LoginService);
  private router = inject(Router);

  public loginForm: FormGroup = this.formBuilder.group({
    username: ['', Validators.required],
    password: ['', Validators.required]
  });

  submitLogin() {
    this.loginS.login(this.loginForm.value);
  }

  redirectRegister(){
    this.router.navigate(['/registrar-usuario']);
  }
}
