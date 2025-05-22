import {Component, EventEmitter, inject, OnInit, Output, ViewChild} from '@angular/core';
import {Toolbar} from 'primeng/toolbar';
import {Button} from 'primeng/button';
import {Avatar} from 'primeng/avatar';
import {Popover} from 'primeng/popover';
import {MenuItem} from 'primeng/api';
import {Menu} from 'primeng/menu';
import {LoginService} from '../../servicios/login.service';
import {Ripple} from 'primeng/ripple';


@Component({
  selector: 'navbar',
  imports: [
    Toolbar,
    Button,
    Avatar,
    Popover,
    Menu,
    Ripple
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
  standalone: true
})
export class NavbarComponent implements OnInit{

  @Output() toggleSidebar = new EventEmitter<void>();
  @ViewChild('op') op!: Popover;

  public usuario : string | undefined | null = '';

  private loginS = inject(LoginService);
  items: MenuItem[] | undefined;

  ngOnInit(): void {
    this.usuario = this.loginS.getLoggedUser();
    this.items = [
      {
        separator: true
      },
      {
        items: [
          {
            label: 'Info del usuario',
            icon: 'pi pi-user'
          },
          {
            label: 'Cerrar sesión',
            icon: 'pi pi-power-off',
            command: () => {
              this.loginS.logout();
            }
          }
        ]
      }]
  }

  toggle(event : any){
    this.op.toggle(event);
  }

}
