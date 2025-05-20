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
  standalone : true
})
export class ConsultarPersonaComponent{

  public opcionesBusqueda = Object.values(OpcionesBusqueda);
  public tiposDocumentos = Object.values(TiposDocumentos);

  public tipoDocumento : string = '';
  public documento : string = '';
  public opcionActual : string = '';

  public personas : Personas[] = [];
  public departamentos: Departamento[] = [];
  public ciudades: Ciudad[] = [];

  protected readonly OpcionesBusqueda = OpcionesBusqueda;

  private personaService = inject(PersonasService);
  private departamentoService = inject(DepartamentoService);
  private municipioService = inject(MunicipioService);

  switchearBusqueda(event : any) {
    this.opcionActual = event.value;

    if (this.opcionActual == OpcionesBusqueda.TODO) {
      this.personaService.listarPersonas().subscribe(resp => {
        this.personas = resp.body;
      })
    }else if (this.opcionActual == OpcionesBusqueda.LOCACION) {
      this.departamentoService.listarDepartamentos().subscribe(resp => {
        this.departamentos = resp;
      })
    }

  }

  listarCiudadesDep(event: SelectChangeEvent) {
    this.municipioService.listarMunicipiosDep(event.value.nombre).subscribe(resp => {
      this.ciudades = resp;
    });
  }
}
