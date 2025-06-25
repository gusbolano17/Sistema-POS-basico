export interface InventarioDto {
  nombre : string;
  descripcion : string;
  stock : number;
  categoria : number;
  precio : number;
  proveedor : Proveedor;
}

interface Proveedor{
  tipoDocumento : string;
  documento : string;
}
