import { Injectable } from '@angular/core';
import {FormGroup} from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class FormUtilService<T> {

  setearFormulario(model : T, formulario : FormGroup){
    if(model != undefined){
      formulario.patchValue(model)
    }
  }

}
