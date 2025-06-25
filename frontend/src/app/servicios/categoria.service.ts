import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {HttpUtil} from './http-util';
import {ResponseDto} from '../modelos/dtos/response-dto';
import {Categoria} from '../modelos/Categorias';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {

  private http = inject(HttpClient);
  private httpUtil = new HttpUtil();

  listaCategorias() {
    return this.http.get<ResponseDto<Categoria[]>>(`${this.httpUtil.url}/categoria/listar`);
  }

  listarCategoriaNombre(nombre: string){
    return this.http.get<ResponseDto<Categoria[]>>(`${this.httpUtil.url}/categoria/listar/nombre/${nombre}`);
  }

}
