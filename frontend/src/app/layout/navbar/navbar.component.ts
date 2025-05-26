import {Component, EventEmitter, inject, OnInit, Output, ViewChild} from '@angular/core';
import {Toolbar} from 'primeng/toolbar';
import {Button} from 'primeng/button';
import {Avatar} from 'primeng/avatar';
import {Popover} from 'primeng/popover';
import {MenuItem} from 'primeng/api';
import {Menu} from 'primeng/menu';
import {LoginService} from '../../servicios/login.service';
import {Ripple} from 'primeng/ripple';
import {UsuarioService} from '../../servicios/usuario.service';
import {ModalService} from '../../servicios/modal.service';
import {DialogService} from 'primeng/dynamicdialog';
import {CrearUsuarioComponent} from '../../paginas/usuarios/crear-usuario/crear-usuario.component';


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
  standalone: true,
  providers: [ModalService, DialogService]
})
export class NavbarComponent implements OnInit{

  @Output() toggleSidebar = new EventEmitter<void>();
  @ViewChild('op') op!: Popover;

  public usuario : string | undefined | null = '';
  items: MenuItem[] | undefined;

  private modalS = inject(ModalService);
  private loginS = inject(LoginService);
  private usuarioS = inject(UsuarioService);

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
            icon: 'pi pi-user',
            command: () => {
              this.openUsuarioModal();
            }
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

    this.usuarioS.buscarUsuarioNombre(this.usuario).subscribe(
      resp => {
        console.log(resp)
      }
    )
  }

  toggle(event : any){
    this.op.toggle(event);
  }

  openUsuarioModal(){
    this.modalS.open(CrearUsuarioComponent, {titulo : "Usuario", editando : false})
  }

}
