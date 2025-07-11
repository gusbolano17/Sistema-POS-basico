import { Routes } from '@angular/router';
import {LoginComponent} from './paginas/login/login.component';
import {MainComponent} from './paginas/main/main.component';
import {authGuard} from './servicios/auth.guard';
import {personasRoutes} from './paginas/personas/personas.routes';
import {usuariosRoutes} from './paginas/usuarios/usuarios.routes';
import {productosRoutes} from './paginas/productos/productos.routes';
import {RegistrarUsuarioComponent} from './paginas/login/registrar-usuario/registrar-usuario.component';

export const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'login'},
  {path: 'login', component: LoginComponent},
  {path: 'registrar-usuario', component: RegistrarUsuarioComponent},
  {
    path: 'main',
    component: MainComponent,
    canActivate: [authGuard],
    children: [{
      path: 'dashboard',
      loadComponent: ()=> import('./paginas/main/dashboard/dashboard.component').then(m => m.DashboardComponent),
    },{
      path: 'personas',
      children: personasRoutes
    },{
      path: 'usuarios',
      children: usuariosRoutes
    },{
      path: 'productos',
      children: productosRoutes
      },{
      path: '',
      pathMatch: 'full',
      redirectTo: 'dashboard',
    }]
  }
];
