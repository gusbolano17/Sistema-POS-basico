import {Departamento} from './departamento';
import {Ciudad} from './ciudad';

export interface Personas {
    id : number;
    nombre : string;
    apellido : string;
    email : string;
    tipoDocumento : string;
    documento : string;
    telefono : string;
    direccion : string;
    fechaNacimiento : Date;
    fechaCreacion : Date;
    pais : string;
    departamentoId : Departamento;
    ciudadId : Ciudad;
}
