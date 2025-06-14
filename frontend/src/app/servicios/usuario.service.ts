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

  listarUsuarios() {
    return this.http.get<ResponseDto<Usuarios[]>>(`${this.httpUtil.url}/usuarios/listar`);
  }

  buscarUsuarioNombre(nombre: string | undefined | null){
    return this.http.get<ResponseDto<Usuarios[]>>(`${this.httpUtil.url}/usuarios/listar/nombre/${nombre}`);
  }

  buscarUsuarioId(id: number) {
    return this.http.get(`${this.httpUtil.url}/usuarios/obtener/${id}`);
  }

  listarPersonasFechas(fechaInicial: Date | undefined, fechaFinal: Date | undefined) {
    return this.http.get<ResponseDto<Usuarios[]>>(`${this.httpUtil.url}/usuarios/listar/fechas/${fechaInicial}/${fechaFinal}`);
  }

  agregarUsuario(usuario : UsuarioDto){
    return this.http.post<ResponseDto<Usuarios>>(`${this.httpUtil.url}/usuarios/crear`, usuario);
  }

    editarUsuario(id: number | undefined, usuario: UsuarioDto){
    return this.http.put<ResponseDto<Usuarios>>(`${this.httpUtil.url}/usuarios/editar/${id}`, usuario);
  }

  eliminarUsuario(id: number){
    return this.http.delete<ResponseDto<any>>(`${this.httpUtil.url}/usuarios/eliminar/${id}`);
  }


}
