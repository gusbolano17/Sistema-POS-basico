import {Personas} from './personas';

export interface Usuarios{

  id: number;
  username : string;
  password : string;
  fechaCreacion : Date;
  personaId : Personas;
}
