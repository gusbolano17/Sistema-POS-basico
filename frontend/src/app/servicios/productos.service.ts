import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {HttpUtil} from './http-util';
import {Inventario} from '../modelos/inventario';
import {InventarioDto} from '../modelos/dtos/inventario-dto';
import {ResponseDto} from '../modelos/dtos/response-dto';

@Injectable({
  providedIn: 'root'
})
export class ProductosService {

  private httpUtil : HttpUtil = new HttpUtil();
  private http = inject(HttpClient);

  listarInventario(){
    return this.http.get<ResponseDto<Inventario[]>>(`${this.httpUtil.url}/inventario/listar`);
  }

  agregarInventario(inventario : InventarioDto){
    return this.http.post<ResponseDto<Inventario>>(`${this.httpUtil.url}/inventario/crear`, inventario);
  }
}
