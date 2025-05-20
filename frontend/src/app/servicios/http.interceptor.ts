import {HttpInterceptorFn} from '@angular/common/http';

export const httpInterceptor: HttpInterceptorFn = (req, next) => {
  const token = sessionStorage.getItem("token");
  if (token) {
    const newRequest = req.clone({
      setHeaders:{
        Authorization: `Bearer ${token}`
      }
    });
    console.log(newRequest)
    return next(newRequest);
  }
  return next(req);

};
