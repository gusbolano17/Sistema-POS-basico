import {Component, inject, OnInit} from '@angular/core';
import {Card} from 'primeng/card';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {FloatLabel} from 'primeng/floatlabel';
import {InputText} from 'primeng/inputtext';
import {Divider} from 'primeng/divider';
import {DatePicker} from 'primeng/datepicker';
import {Select, SelectChangeEvent} from 'primeng/select';
import {Ciudad} from '../../../modelos/ciudad';
import {Departamento} from '../../../modelos/departamento';
import {confirmPassValidator} from '../../../servicios/confirm-pass.validator';
import {DepartamentoService} from '../../../servicios/departamento.service';
import {MunicipioService} from '../../../servicios/municipio.service';
import {Button} from 'primeng/button';
import {LoginService} from '../../../servicios/login.service';
import {UsuarioDto} from '../../../modelos/dtos/usario-dto';
import {Router} from '@angular/router';

@Component({
  selector: 'app-registrar-usuario',
  imports: [
    Card,
    ReactiveFormsModule,
    FormsModule,
    FloatLabel,
    InputText,
    Divider,
    DatePicker,
    Select,
    Button
  ],
  templateUrl: './registrar-usuario.component.html',
  styleUrl: './registrar-usuario.component.css',
  standalone: true,
})
export class RegistrarUsuarioComponent implements OnInit{

  public departamentos: Departamento[] = [];
  public ciudades: Ciudad[] = [];

  private formBuilder = inject(FormBuilder);
  private departamentoService = inject(DepartamentoService);
  private municipioService = inject(MunicipioService);
  private loginService = inject(LoginService);
  private router = inject(Router);

  public usuarioForm : FormGroup = this.formBuilder.group({
    personaId: this.formBuilder.group({
      tipoDocumento: ['', [Validators.required]],
      documento: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(10)]],
      nombre: ['', [Validators.required]],
      apellido: ['', [Validators.required]],
      email: ['', [Validators.email]],
      telefono: [''],
      direccion: [''],
      fechaNacimiento: [Date, [Validators.required]],
      pais : [''],
      departamento : [''],
      ciudad : ['']
    }),
    username: ['', [Validators.required, Validators.minLength(3)]],
    password: ['', [Validators.required, Validators.minLength(6)]],
    confirmPassword: ['', Validators.required]
  }, { validators: confirmPassValidator });


  ngOnInit(): void {
    this.departamentoService.listarDepartamentos().subscribe(resp => {
      this.departamentos = resp;
    });
  }


  listarCiudadesDep(event : any) {
    this.municipioService.listarMunicipiosDep(event.value.nombre).subscribe(resp => {
      this.ciudades = resp;
    });
  }

  submitUsuario() {
    if (this.usuarioForm.valid) {

      this.loginService.registrarUsuario(this.usuarioForm.value).subscribe({
        next: (resp) => {
          this.usuarioForm.reset();
          this.router.navigate(['/login']);
        },
        error: (err) => {
          console.log(err);
        }
      })
    }
  }
}
