import {Component, inject, Input, OnInit, Optional} from '@angular/core';
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
import {ModalService} from '../../../servicios/modal.service';
import {DialogService, DynamicDialogRef} from 'primeng/dynamicdialog';
import {FormUtilService} from '../../../servicios/form-util.service';

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
  providers: [ToastService,MessageService,DialogService, ModalService]
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
  private formUtilService = inject(FormUtilService<Personas>);

  constructor(private formBuilder: FormBuilder,  @Optional() private dialogRef?: DynamicDialogRef) {
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

    this.formUtilService.setearFormulario(this.persona, this.personaForm);
  }

  listarCiudadesDep(event : any) {
    this.municipioService.listarMunicipiosDep(event.value.nombre).subscribe(resp => {
      this.ciudades = resp;
    });
  }

  submitPersona() {
    if (this.personaForm.valid) {
      const persona : PersonaDTO = this.personaForm.value;
      if (this.editando) {
        this.personaService.editarPersona(this.persona?.id, persona).subscribe({
          next: (resp) => this.dialogRef?.close(resp),
          error: (error) => this.toastService.mostrarToast(error.error.msg, 'error')
        })
      }else{
        this.personaService.agregarPersona(persona).subscribe({
          next: (resp) => {
            this.dialogRef?.close(resp);
            this.personaForm.reset();
          },
          error: (error) => this.toastService.mostrarToast(error.error.msg, 'error')
        })
      }
    }
  }


}
