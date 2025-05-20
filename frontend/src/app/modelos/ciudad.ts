import {Departamento} from './departamento';

export interface Ciudad {
  id : number;
  municipio : string;
  codigo : string;
  departamentoId : Departamento;
}
