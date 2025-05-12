import {CanActivateFn, Router} from '@angular/router';
import {inject} from '@angular/core';
import {LoginService} from './login.service';

export const authGuard: CanActivateFn = () => {

  const router = inject(Router);
  const authS = inject(LoginService);

  if(authS.isLoggedIn()){
    return true;
  }

  router.navigateByUrl('/login');

  return false;
};
