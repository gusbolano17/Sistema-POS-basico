import {Component, inject, Input, OnInit, Optional} from '@angular/core';
import {PersonasService} from '../../../servicios/personas.service';
import {TabsModule} from 'primeng/tabs';
import {InputTextModule} from 'primeng/inputtext';
import {FloatLabelModule} from 'primeng/floatlabel';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {DividerModule} from 'primeng/divider';
import {ButtonModule} from 'primeng/button';
import {ToastModule} from 'primeng/toast';
import {MessageService} from 'primeng/api';
import {ToastService} from '../../../servicios/toast.service';
import {DialogService, DynamicDialogRef} from 'primeng/dynamicdialog';
import {ModalService} from '../../../servicios/modal.service';
import {Personas} from '../../../modelos/personas';
import {UsuarioService} from '../../../servicios/usuario.service';
import {CrearPersonaComponent} from '../../personas/crear-persona/crear-persona.component';
import {Usuarios} from '../../../modelos/usuarios';
import {FormUtilService} from '../../../servicios/form-util.service';
import {confirmPassValidator} from '../../../servicios/confirm-pass.validator';
import {UsuarioDto} from '../../../modelos/dtos/usario-dto';

@Component({
  selector: 'app-crear-usuario',
  imports:
    [
      TabsModule,
      InputTextModule,
      FloatLabelModule,
      ReactiveFormsModule,
      DividerModule,
      ButtonModule,
      ToastModule
    ],
  templateUrl: './crear-usuario.component.html',
  styleUrl: './crear-usuario.component.css',
  standalone: true,
  providers: [MessageService, ToastService, DialogService, ModalService]
})
export class CrearUsuarioComponent implements OnInit{

  @Input() titulo: string = 'Crear Usuario';
  @Input() editando : boolean = false;
  @Input() usuario : Usuarios | undefined;

  public persona : Personas | undefined;
  usuarioForm : FormGroup;

  private personaService = inject(PersonasService);
  private usuarioService = inject(UsuarioService);
  private modalService = inject(ModalService);
  private toastService = inject(ToastService);
  private formUtilService = inject(FormUtilService<Usuarios>);

  constructor(private fb : FormBuilder,  @Optional() private dialogRef : DynamicDialogRef) {
    this.usuarioForm = this.fb.group({
      personaId : fb.group({
        tipoDocumento: [ '', [Validators.required]],
        documento: ['', [Validators.required]],
        nombre: [{value: '', disabled: true}, [Validators.required]],
        apellido: [{value: '', disabled: true}, [Validators.required]],
      }),
      username : ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required]
    }, { validators: confirmPassValidator });
  }


  ngOnInit() {
    this.formUtilService.setearFormulario(this.usuario,this.usuarioForm);
    if(this.editando){
      this.persona = this.usuario?.personaId;
      console.log(this.usuario);
    }
  }


  obtenerPersonaDoc() {

    let tipoDocumento = this.usuarioForm.get('personaId.tipoDocumento')?.value;
    let documento = this.usuarioForm.get('personaId.documento')?.value;

    this.personaService.buscarPersonaPorDoc(tipoDocumento,documento).subscribe({
      next: resp => {
        this.persona = resp.body;
        this.usuarioForm.patchValue({
          personaId : {
            tipoDocumento,
            documento,
            nombre: this.persona.nombre,
            apellido: this.persona.apellido,
          }
        })
      },
      error: ({error, status}) => {
        this.toastService.mostrarToast(error.msg, "error");
        if(status == 404){
          this.modalService.open(CrearPersonaComponent, {
            persona : {
              tipoDocumento,
              documento
            }
          }).subscribe(res => {
            this.toastService.mostrarToast(res.msg, "success");
          })
        }
      }
    })
  }

  submitUsuario() {
    if(this.usuarioForm.valid) {
      const usu : UsuarioDto = this.usuarioForm.value;
      if(this.editando){
        this.usuarioService.editarUsuario(this.usuario?.id, usu).subscribe(resp => {
          this.toastService.mostrarToast(resp.msg,"success");
          this.dialogRef.close(resp);
        })
      }else{
        this.usuarioService.agregarUsuario(this.usuarioForm.value).subscribe(resp => {
          this.usuarioForm.reset();
          this.toastService.mostrarToast(resp.msg,"success");
        }, (error) => {
          this.toastService.mostrarToast(error.msg,"error");
        })
      }
    }
  }

}
