import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {LoginDTO} from '../modelos/dtos/login-dto';
import {HttpUtil} from './http-util';
import {Observable} from 'rxjs';
import {ResponseDto} from '../modelos/dtos/response-dto';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private httpUtil : HttpUtil = new HttpUtil();
  private http : HttpClient = inject(HttpClient);
  private router : Router = inject(Router);

  login(body : LoginDTO){
    return this.http.post<ResponseDto<string>>(`${this.httpUtil.url}/auth/login`, body).subscribe({
      next : r => {
        sessionStorage.setItem("token", r.body);
        this.router.navigateByUrl("/main");
      },
      error : err =>  {
        console.error(err)
      }
    })
  }

  isLoggedIn(): boolean{
    return sessionStorage.getItem("token") != null;
  }

}
