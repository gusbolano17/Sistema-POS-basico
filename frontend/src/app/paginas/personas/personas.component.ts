import { Component } from '@angular/core';
import {TabPanels} from 'primeng/tabs';
import {TabPanel} from 'primeng/tabview';
@Component({
  selector: 'app-personas',
  imports: [
    TabMenu,
    TabPanels,
    TabPanel
  ],
  templateUrl: './personas.component.html',
  styleUrl: './personas.component.css'
})
export class PersonasComponent {

}
