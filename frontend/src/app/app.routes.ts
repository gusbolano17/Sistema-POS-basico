import { Routes } from '@angular/router';
import {LoginComponent} from './paginas/login/login.component';
import {MainComponent} from './paginas/main/main.component';
import {authGuard} from './servicios/auth.guard';

export const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'login'},
  {path: 'login', component: LoginComponent},
  {
    path: 'main',
    component: MainComponent,
    canActivate: [authGuard],
    children: [{
      path: 'dashboard',
      loadComponent: ()=> import('./paginas/main/dashboard/dashboard.component').then(m => m.DashboardComponent),
    },{
      path: 'personas',
      loadComponent: () => import('./paginas/personas/personas.component').then(m => m.PersonasComponent),
    },{
      path: '',
      pathMatch: 'full',
      redirectTo: 'dashboard',
    }]
  }
];
