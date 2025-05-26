import {Routes} from '@angular/router';

export const usuariosRoutes : Routes = [
  {
    path: 'crear',
    loadComponent: ()=> import('./crear-usuario/crear-usuario.component').then(m => m.CrearUsuarioComponent)
  },
  {
    path: 'consultar',
    loadComponent: ()=> import('./consultar-usuario/consultar-usuario.component').then(m => m.ConsultarUsuarioComponent)
  }
];
