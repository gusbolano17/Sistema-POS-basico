import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {HttpUtil} from './http-util';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private httpUtil : HttpUtil = new HttpUtil();
  private http = inject(HttpClient);

  buscarUsuarioNombre(nombre: string | undefined | null){
    return this.http.get(`${this.httpUtil.url}/usuarios/obtener/nombre/${nombre}`);
  }
}
