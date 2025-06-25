import {Personas} from '../personas';

export interface UsuarioDto {

  persona : Persona,
  username : string
  password: string

}

interface Persona {
  tipoDocumento : string;
  documento : string;
}
