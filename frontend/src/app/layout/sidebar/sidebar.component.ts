import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Sidebar} from 'primeng/sidebar';
import {Menu} from 'primeng/menu';
import {MenuItem} from 'primeng/api';
import {Ripple} from 'primeng/ripple';
import {Button} from 'primeng/button';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'sidebar',
  imports: [
    Sidebar,
    Menu,
    Ripple,
    Button,
    RouterLink
  ],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css',
  standalone: true
})
export class SidebarComponent implements OnInit{

  @Input() visible: boolean = false;
  @Output() onHide = new EventEmitter<void>();

  items: MenuItem[] | undefined;

  ngOnInit(): void {
    this.items = [
      {
        separator: true
      },
      {
        label: 'Personas',
        icon: 'pi pi-id-card',
        items: [
          {
            label: 'Crear',
            icon: 'pi pi-plus',
            route : 'personas/crear'
          },
          {
            label: 'Consultar',
            icon: 'pi pi-search',
            route : 'personas/consultar'
          }
        ]
      },
      {
        label: 'Usuarios',
        icon: 'pi pi-user',
        items: [
          {
            label: 'Crear',
            icon: 'pi pi-plus',
            route : 'usuarios/crear'
          },
          {
            label: 'Consultar',
            icon: 'pi pi-search',
            route : 'usuarios/consultar'
          }
        ]
      },
      {
        label : 'Inventario',
        icon: 'pi pi-box',
        items: [
          {
            label: 'Crear',
            icon: 'pi pi-plus',
            route : 'productos/crear'
          },
          {
            label: 'Consultar',
            icon: 'pi pi-search',
            route : 'productos/consultar'
          }
        ]
      }
    ];
  }

}
