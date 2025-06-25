import {Categoria} from './Categorias';
import {Personas} from './personas';

export interface Inventario {
  id : number;
  nombre: string;
  descripcion: string;
  precio: number;
  stock: number;
  imagenRef : string;
  categoriaId: Categoria;
  proveedorId: Personas;
}
