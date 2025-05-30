import {Personas} from './personas';

export interface Usuarios extends Personas{

  username : string;
  password : string;
  fechaCreacion : Date;

}
