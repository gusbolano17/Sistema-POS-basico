import {Routes} from '@angular/router';

export const productosRoutes : Routes = [
  {
    path: 'crear',
    loadComponent :() => import('./crear-productos/crear-productos.component').then(m => m.CrearProductosComponent),
  },
  {
    path: 'consultar',
    loadComponent: () => import('./consultar-productos/consultar-productos.component').then(m => m.ConsultarProductosComponent),
  }
]
