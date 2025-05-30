import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {HttpUtil} from './http-util';
import {UsuarioDto} from '../modelos/dtos/usario-dto';
import {ResponseDto} from '../modelos/dtos/response-dto';
import {Usuarios} from '../modelos/usuarios';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private httpUtil : HttpUtil = new HttpUtil();
  private http = inject(HttpClient);

  buscarUsuarioNombre(nombre: string | undefined | null){
    return this.http.get(`${this.httpUtil.url}/usuarios/obtener/nombre/${nombre}`);
  }

  agregarUsuario(usuario : UsuarioDto){
    return this.http.post<ResponseDto<Usuarios>>(`${this.httpUtil.url}/usuarios/crear`, usuario);
  }
}
