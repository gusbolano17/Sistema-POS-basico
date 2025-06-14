import {AbstractControl, ValidationErrors, ValidatorFn} from '@angular/forms';

export const confirmPassValidator : ValidatorFn = (control : AbstractControl) : ValidationErrors | null => {
  const passwordControl = control.get('password');
  const confirmPasswordControl = control.get('confirmPassword');

  // Si los controles no existen, salimos
  if (!passwordControl || !confirmPasswordControl) return null;

  // No validamos coincidencia si alguno de los campos tiene errores propios
  if (passwordControl.invalid || confirmPasswordControl.invalid) return null;

  const password = passwordControl.value;
  const confirmPassword = confirmPasswordControl.value;

  // Si no coinciden, devolvemos el error al grupo
  if (password !== confirmPassword) {
    return { PasswordNotMatch: true };
  }

  return null;
}
