import {Routes} from '@angular/router';

export const personasRoutes : Routes = [
  {
    path: 'crear',
    loadComponent: ()=> import('./crear-persona/crear-persona.component').then(m => m.CrearPersonaComponent)
  },
  {
    path: 'consultar',
    loadComponent: ()=> import('./consultar-persona/consultar-persona.component').then(m => m.ConsultarPersonaComponent)
  }
];
