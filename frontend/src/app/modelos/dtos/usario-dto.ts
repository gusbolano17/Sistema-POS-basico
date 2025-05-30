import {Personas} from '../personas';

export interface UsuarioDto {

  persona : {
    tipoDocumento : string;
    documento : string;
  },
  username : string
  password: string

}
