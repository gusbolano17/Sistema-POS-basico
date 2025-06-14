import {Component, inject, OnInit} from '@angular/core';
import Swal from 'sweetalert2';

import {Button} from 'primeng/button';
import {DatePicker} from 'primeng/datepicker';
import {FloatLabel} from 'primeng/floatlabel';
import {FormsModule} from '@angular/forms';
import {InputText} from 'primeng/inputtext';
import {Select} from 'primeng/select';
import {Tab, TabList, TabPanel, TabPanels, Tabs} from 'primeng/tabs';
import {TableModule} from 'primeng/table';
import {Toast} from 'primeng/toast';
import {ModalService} from '../../../servicios/modal.service';
import {DialogService} from 'primeng/dynamicdialog';
import {ToastService} from '../../../servicios/toast.service';
import {MessageService} from 'primeng/api';
import {OpcionesBusquedaUsuario} from '../../../modelos/enums/opciones-busqueda';
import {Usuarios} from '../../../modelos/usuarios';
import {UsuarioService} from '../../../servicios/usuario.service';
import {DatePipe} from '@angular/common';
import {CrearUsuarioComponent} from '../crear-usuario/crear-usuario.component';
import {LoginService} from '../../../servicios/login.service';

@Component({
  selector: 'app-consultar-usuario',
  imports: [
    Button,
    DatePicker,
    FloatLabel,
    FormsModule,
    InputText,
    Select,
    Tab,
    TabList,
    TabPanel,
    TabPanels,
    TableModule,
    Tabs,
    Toast,
    DatePipe
  ],
  templateUrl: './consultar-usuario.component.html',
  styleUrl: './consultar-usuario.component.css',
  standalone : true,
  providers: [MessageService, ToastService, DialogService, ModalService]
})
export class ConsultarUsuarioComponent implements OnInit {

  public opcionesBusqueda = Object.values(OpcionesBusquedaUsuario);

  public opcionActual : string | null = null;
  public nombre: string = '';
  public fechaInicial : Date | undefined;
  public fechaFinal : Date | undefined;

  public usuarios: Usuarios[] = [];

  protected readonly OpcionesBusquedaUsuario = OpcionesBusquedaUsuario;

  public usuarioActual : string | undefined | null = '';

  private usuariosService = inject(UsuarioService);
  private modalService = inject(ModalService);
  private toastService = inject(ToastService);
  private loginService = inject(LoginService);

  ngOnInit() {
    this.usuarioActual = this.loginService.getLoggedUser();
  }

  switchearBusqueda(event : any) {
    this.opcionActual = event.value;

    if (this.opcionActual == OpcionesBusquedaUsuario.TODO) {
      this.usuariosService.listarUsuarios().subscribe({
        next: res => {
          this.usuarios = res.body;
          this.toastService.mostrarToast(res.msg, res.code == 'ok' ? 'success' : 'error');
        },
        error: err => {
          this.toastService.mostrarToast(err.error.msg, 'error');
        }
      })
    }

  }

  submitBusqueda() {
    switch (this.opcionActual) {
      case OpcionesBusquedaUsuario.NOMBRE:
        this.usuariosService.buscarUsuarioNombre(this.nombre).subscribe(resp => {
          this.usuarios = resp.body;
          this.toastService.mostrarToast(resp.msg, 'success')}, error => {
          this.toastService.mostrarToast(error.error.msg, 'error');
        })
        break;
      case OpcionesBusquedaUsuario.FECHA_CREACION:
        this.usuariosService.listarPersonasFechas(this.fechaInicial, this.fechaFinal).subscribe(resp => {
          this.usuarios = resp.body;
          this.toastService.mostrarToast(resp.msg, 'success');
        })
        break;
    }
  }

  selectUsuario(id: number) {
    this.usuariosService.buscarUsuarioId(id).subscribe(resp => {
      this.modalService.open(CrearUsuarioComponent, {titulo : "Editar Usuario", editando : true, usuario : resp})
        .subscribe((res) => {
          this.toastService.mostrarToast(res.msg,'success')})
    })
  }

  eliminarUsuario(id: number) {

    Swal.fire({
      title: 'Advertencia',
      text: 'Estas seguro de eliminar este usuario?',
      icon: 'warning',
      denyButtonText: 'Cancelar',
      showDenyButton: true,
      confirmButtonColor: 'green',
      theme: 'dark',
    }).then((result) => {
        if(result.isConfirmed) {
          this.usuariosService.eliminarUsuario(id).subscribe({
            next: res => {
              this.toastService.mostrarToast(res.msg, 'success' );
              this.usuarios.splice(this.usuarios.findIndex(u => u.id === id), 1);
            },
            error: err => {
              this.toastService.mostrarToast(err.error.msg, 'error' );
            }
          })
        }
    })

  }

}
