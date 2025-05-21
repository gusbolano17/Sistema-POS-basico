import {Component, inject} from '@angular/core';
import {TabsModule} from 'primeng/tabs';
import {InputTextModule} from 'primeng/inputtext';
import {FloatLabelModule} from 'primeng/floatlabel';
import {FormsModule} from '@angular/forms';
import {DividerModule} from 'primeng/divider';
import {SelectChangeEvent, SelectModule} from 'primeng/select';
import {ButtonModule} from 'primeng/button';
import {DatePicker} from 'primeng/datepicker';
import {ToastModule} from 'primeng/toast';
import {Personas} from '../../../modelos/personas';
import {PersonasService} from '../../../servicios/personas.service';
import {TableModule} from 'primeng/table';
import {OpcionesBusqueda} from '../../../modelos/enums/opciones-busqueda';
import {TiposDocumentos} from '../../../modelos/enums/tipos-documentos';
import {Departamento} from '../../../modelos/departamento';
import {Ciudad} from '../../../modelos/ciudad';
import {DepartamentoService} from '../../../servicios/departamento.service';
import {MunicipioService} from '../../../servicios/municipio.service';
import {ToastService} from '../../../servicios/toast.service';
import {MessageService} from 'primeng/api';
import {ModalService} from '../../../servicios/modal.service';
import {DialogService} from 'primeng/dynamicdialog';
import {CrearPersonaComponent} from '../crear-persona/crear-persona.component';

@Component({
  selector: 'app-consultar-persona',
  imports: [
    TabsModule,
    InputTextModule,
    FloatLabelModule,
    FormsModule,
    DividerModule,
    SelectModule,
    ButtonModule,
    ToastModule,
    TableModule,
    DatePicker
  ],
  templateUrl: './consultar-persona.component.html',
  styleUrl: './consultar-persona.component.css',
  standalone : true,
  providers: [MessageService, ToastService, DialogService, ModalService]
})
export class ConsultarPersonaComponent{

  public opcionesBusqueda = Object.values(OpcionesBusqueda);
  public tiposDocumentos = Object.values(TiposDocumentos);

  public nombre : string = '';
  public tipoDocumento : string = '';
  public documento : string = '';
  public opcionActual : string = '';
  public fechaInicial : Date | undefined;
  public fechaFinal : Date | undefined;

  public personas : Personas[] = [];
  public departamentos: Departamento[] = [];
  public ciudades: Ciudad[] = [];

  public departamento : Departamento | undefined;
  public ciudad : Ciudad | undefined;

  protected readonly OpcionesBusqueda = OpcionesBusqueda;

  private personaService = inject(PersonasService);
  private departamentoService = inject(DepartamentoService);
  private municipioService = inject(MunicipioService);
  private toastService = inject(ToastService);
  private modalService = inject(ModalService);

  switchearBusqueda(event : any) {
    this.opcionActual = event.value;

    if (this.opcionActual == OpcionesBusqueda.TODO) {
      this.personaService.listarPersonas().subscribe(resp => {
        this.personas = resp.body;
        this.toastService.mostrarToast(resp.msg, resp.code == 'ok' ? 'success' : 'error');
      }, error => {
        this.toastService.mostrarToast(error.error.msg, 'error');
      })
    }else if (this.opcionActual == OpcionesBusqueda.LOCACION) {
      this.departamentoService.listarDepartamentos().subscribe(resp => {
        this.departamentos = resp;
      }, error => {
        this.toastService.mostrarToast(error.error.msg, 'error');
      })
    }

  }

  listarCiudadesDep(event: SelectChangeEvent) {
    this.municipioService.listarMunicipiosDep(event.value.nombre).subscribe(resp => {
      this.ciudades = resp;
    });
  }

  submitBusqueda() {
    switch (this.opcionActual) {
      case OpcionesBusqueda.NOMBRE:
        this.personaService.buscarPersonaPorNombre(this.nombre).subscribe(resp => {
          this.personas = resp.body;
          this.toastService.mostrarToast(resp.msg, 'success')}, error => {
          this.toastService.mostrarToast(error.error.msg, 'error');
        })
        break;
      case OpcionesBusqueda.DOCUMENTO:
        this.personaService.buscarPersonaPorDoc(this.tipoDocumento, this.documento).subscribe(resp => {
          this.personas = [resp.body];
          this.toastService.mostrarToast(resp.msg, 'success');
        }, error => {
          this.toastService.mostrarToast(error.error.msg, 'error');
        })
        break;
      case OpcionesBusqueda.FECHA_CREACION:
        this.personaService.listarPersonasFechas(this.fechaInicial, this.fechaFinal).subscribe(resp => {
          this.personas = resp.body;
          this.toastService.mostrarToast(resp.msg, 'success');
        })
        break;
      case OpcionesBusqueda.LOCACION:
        this.personaService.buscarPersonaLocacion(this.departamento?.nombre, this.ciudad?.municipio).subscribe(resp => {
          this.personas = resp.body;
          this.toastService.mostrarToast(resp.msg, 'success');
        }, error => {
          this.toastService.mostrarToast(error.error.msg, 'error');
        })
        break;
    }
  }

  selectPersona(id: number) {
    this.personaService.buscarPersona(id).subscribe(resp => {
      this.modalService.open(CrearPersonaComponent, {titulo : "Editar Persona", editando : true, persona : resp})
        .subscribe((res) => {
          this.toastService.mostrarToast(res.msg,'success')})
    })
  }
}
