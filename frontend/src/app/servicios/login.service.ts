import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {LoginDTO} from '../modelos/dtos/login-dto';
import {HttpUtil} from './http-util';
import {Observable} from 'rxjs';
import {ResponseDto} from '../modelos/dtos/response-dto';
import {Router} from '@angular/router';
import {jwtDecode} from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private timerLogout : any;

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

  logout(){
    sessionStorage.clear();
    if (this.timerLogout) clearTimeout(this.timerLogout);
    this.router.navigateByUrl("/login");
  }

  autoLogout(token: string) {
    const decoded: any = jwtDecode(token);
    const expMs = decoded.exp * 1000 - Date.now();
    this.timerLogout = setTimeout(() => this.logout(), expMs);
  }

  initAutoLogout() {
    const token = sessionStorage.getItem('token');
    if (token) this.autoLogout(token);
  }

  getLoggedUser(){
    const token = sessionStorage.getItem('token');
    if (token != null) {
      const decoded = jwtDecode(token);
      return decoded.sub;
    }
    return null;
  }

}
