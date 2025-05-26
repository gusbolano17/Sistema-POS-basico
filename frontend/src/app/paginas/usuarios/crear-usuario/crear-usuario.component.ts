import {Component, inject} from '@angular/core';
import {PersonasService} from '../../../servicios/personas.service';
import {TabsModule} from 'primeng/tabs';
import {InputTextModule} from 'primeng/inputtext';
import {FloatLabelModule} from 'primeng/floatlabel';
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {DividerModule} from 'primeng/divider';
import {SelectModule} from 'primeng/select';
import {ButtonModule} from 'primeng/button';
import {DatePicker} from 'primeng/datepicker';
import {ToastModule} from 'primeng/toast';
import {MessageService} from 'primeng/api';
import {ToastService} from '../../../servicios/toast.service';
import {DialogService} from 'primeng/dynamicdialog';
import {ModalService} from '../../../servicios/modal.service';
import {Personas} from '../../../modelos/personas';

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
export class CrearUsuarioComponent {

  public persona : Personas | undefined;
  usuarioForm : FormGroup;

  private personaService = inject(PersonasService);

  constructor(private fb : FormBuilder) {
    this.usuarioForm = fb.group({
      persona : new FormGroup({
        tipoDocumento: new FormControl( '', [Validators.required]),
        documento: new FormControl('', [Validators.required]),
        nombre: new FormControl({value: '', disabled: true}, [Validators.required]),
        apellido: new FormControl({value: '', disabled: true}, [Validators.required]),
      })
    });
  }


  obtenerPersonaDoc() {

    let tipoDocumento = this.usuarioForm.get('persona.tipoDocumento')?.value;
    let documento = this.usuarioForm.get('persona.documento')?.value;
    this.personaService.buscarPersonaPorDoc(tipoDocumento, documento).subscribe(resp => {
      this.persona = resp.body;
      this.usuarioForm.patchValue({
        persona : {
          tipoDocumento: this.persona?.tipoDocumento,
          documento: this.persona?.documento,
          nombre: this.persona?.nombre,
          apellido: this.persona?.apellido,
        }
      });
    });
  }

}
