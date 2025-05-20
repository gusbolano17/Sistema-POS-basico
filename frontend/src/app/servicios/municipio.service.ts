import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {HttpUtil} from './http-util';
import {Observable} from 'rxjs';
import {Ciudad} from '../modelos/ciudad';

@Injectable({
  providedIn: 'root'
})
export class MunicipioService {

  private httpUtil : HttpUtil = new HttpUtil();
  private http = inject(HttpClient);

  listarMunicipiosDep(dep : string) : Observable<Ciudad[]>{
    return this.http.get<Ciudad[]>(`${this.httpUtil.url}/municipios/listar-departamento/${dep}`);
  }
}
