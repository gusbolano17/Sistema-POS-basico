import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Departamento} from '../modelos/departamento';
import {HttpUtil} from './http-util';

@Injectable({
  providedIn: 'root'
})
export class DepartamentoService {

  private httpUtil : HttpUtil = new HttpUtil();
  private http = inject(HttpClient);

  listarDepartamentos() : Observable<Departamento[]> {
    return this.http.get<Departamento[]>(`${this.httpUtil.url}/departamentos/listare`);
  }
}
