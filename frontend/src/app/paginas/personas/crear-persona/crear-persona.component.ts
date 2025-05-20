import {Component, inject, Input, OnInit} from '@angular/core';
import {TabsModule} from 'primeng/tabs';
import {InputTextModule} from 'primeng/inputtext';
import {FloatLabelModule} from 'primeng/floatlabel';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {DividerModule} from 'primeng/divider';
import {PersonasService} from '../../../servicios/personas.service';
import {DepartamentoService} from '../../../servicios/departamento.service';
import {MunicipioService} from '../../../servicios/municipio.service';
import {Departamento} from '../../../modelos/departamento';
import {SelectModule} from 'primeng/select';
import {Ciudad} from '../../../modelos/ciudad';
import {ButtonModule} from 'primeng/button';
import {PersonaDTO} from '../../../modelos/dtos/persona-dto';
import {DatePicker} from 'primeng/datepicker';
import {ToastModule} from 'primeng/toast';
import {ToastService} from '../../../servicios/toast.service';
import {MessageService} from 'primeng/api';
import {Personas} from '../../../modelos/personas';

@Component({
  selector: 'app-crear-persona',
  imports:
    [
      TabsModule,
      InputTextModule,
      FloatLabelModule,
      ReactiveFormsModule,
      DividerModule,
      SelectModule,
      ButtonModule,
      DatePicker,
      ToastModule
    ],
  templateUrl: './crear-persona.component.html',
  styleUrl: './crear-persona.component.css',
  standalone : true,
  providers: [ToastService,MessageService]
})
export class CrearPersonaComponent implements OnInit{

  public departamentos : Departamento[] = [];
  public ciudades : Ciudad[] = [];
  public personaForm : FormGroup;

  @Input() titulo : string = "Crear persona";
  @Input() editando : boolean = false;
  @Input() persona : Personas | undefined;

  private personaService = inject(PersonasService);
  private departamentoService = inject(DepartamentoService);
  private municipioService = inject(MunicipioService);
  private toastService = inject(ToastService);

  constructor(private formBuilder: FormBuilder) {
    this.personaForm = this.formBuilder.group({
      nombre: ['', [Validators.required]],
      apellido: ['', [Validators.required]],
      tipoDocumento: ['', [Validators.required]],
      documento: ['', [Validators.required]],
      email: ['', [Validators.email]],
      telefono: [''],
      direccion: [''],
      fechaNacimiento: [Date, [Validators.required]],
      pais : [''],
      departamento : [''],
      ciudad : ['']
    })
  }

  ngOnInit(): void {
    this.departamentoService.listarDepartamentos().subscribe(resp => {
      this.departamentos = resp;
    });
    console.log(this.persona)
    this.personaForm.patchValue({
      nombre: this.persona?.nombre,
      apellido : this.persona?.apellido,
      tipoDocumento: this.persona?.tipoDocumento,
      documento: this.persona?.documento,
      fechaNacimiento: this.persona?.fechaNacimiento,
      direccion: this.persona?.direccion,
      telefono: this.persona?.telefono,
      email: this.persona?.email,
      pais: this.persona?.pais,
      departamento: this.persona?.departamentoId,
      ciudad: this.persona?.ciudadId
    });
  }

  listarCiudadesDep(event : any) {
    this.municipioService.listarMunicipiosDep(event.value.nombre).subscribe(resp => {
      this.ciudades = resp;
    });
  }

  submitPersona() {
    if (this.personaForm.valid) {

      const persona : PersonaDTO = this.personaForm.value;

      this.personaService.agregarPersona(persona).subscribe(resp =>{
        this.toastService.mostrarToast(resp.msg, resp.code == '201' ? 'success' : 'error');
        this.personaForm.reset();
      });
    }
  }


}
