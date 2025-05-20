import {Component, inject} from '@angular/core';
import {TabsModule} from 'primeng/tabs';
import {InputTextModule} from 'primeng/inputtext';
import {FloatLabelModule} from 'primeng/floatlabel';
import {FormsModule} from '@angular/forms';
import {DividerModule} from 'primeng/divider';
import {SelectModule} from 'primeng/select';
import {ButtonModule} from 'primeng/button';
import {DatePicker} from 'primeng/datepicker';
import {ToastModule} from 'primeng/toast';
import {Personas} from '../../../modelos/personas';
import {PersonasService} from '../../../servicios/personas.service';
import {TableModule} from 'primeng/table';

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
    TableModule
  ],
  templateUrl: './consultar-persona.component.html',
  styleUrl: './consultar-persona.component.css',
  standalone : true
})
export class ConsultarPersonaComponent{

  public opcionesBusqueda : string[] = ['Todo' ,'Documento', 'Nombre', 'Fecha creacion', 'Locacion'];

  public tipoDocumento : string = '';
  public documento : string = '';
  public personas : Personas[] = [];

  private personaService = inject(PersonasService);

  switchearBusqueda(event : any) {
    let op = event.value;

    switch (op) {
      case "Todo":
        this.personaService.listarPersonas().subscribe(resp => {
          console.log(resp)
        })
        break;
    }
  }


}
