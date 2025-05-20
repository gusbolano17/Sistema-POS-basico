import {inject, Injectable} from '@angular/core';
import {HttpUtil} from './http-util';
import {HttpClient} from '@angular/common/http';
import {Personas} from '../modelos/personas';
import {ResponseDto} from '../modelos/dtos/response-dto';
import {PersonaDTO} from '../modelos/dtos/persona-dto';

@Injectable({
  providedIn: 'root'
})
export class PersonasService {

  private httpUtil : HttpUtil = new HttpUtil();
  private http : HttpClient = inject(HttpClient);

  listarPersonas(){
    return this.http.get<ResponseDto<Personas[]>>(`${this.httpUtil.url}/personas/listar`);
  }

  buscarPersona(id : number) {
    return this.http.get<ResponseDto<Personas>>(`${this.httpUtil.url}/personas/obtener/${id}`);
  }

  buscarPersonaPorDoc(tipoDoc : string, doc : string){
    return this.http.get<ResponseDto<Personas[]>>(`${this.httpUtil.url}/personas/obtener/doc/${tipoDoc}/${doc}`);
  }

  agregarPersona(persona : PersonaDTO){
    return this.http.post<ResponseDto<Personas>>(`${this.httpUtil.url}/personas/crear`, persona);
  }

  editarPersona(persona : PersonaDTO){
    return this.http.put<ResponseDto<Personas>>(`${this.httpUtil.url}/personas/actualizar`, persona);
  }

}
