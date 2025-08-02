import {Component, inject, OnInit} from '@angular/core';
import {TabsModule} from 'primeng/tabs';
import {InputTextModule} from 'primeng/inputtext';
import {FloatLabelModule} from 'primeng/floatlabel';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {ToastModule} from 'primeng/toast';
import {Categoria} from '../../../modelos/Categorias';
import {Button} from 'primeng/button';
import {CategoriaService} from '../../../servicios/categoria.service';
import {Select} from 'primeng/select';
import {Divider} from 'primeng/divider';
import {InputNumber} from 'primeng/inputnumber';
import {FileUpload, FileUploadEvent} from 'primeng/fileupload';
import {Personas} from '../../../modelos/personas';
import {PersonasService} from '../../../servicios/personas.service';
import {CrearPersonaComponent} from '../../personas/crear-persona/crear-persona.component';
import {InventarioDto} from '../../../modelos/dtos/inventario-dto';
import {ProductosService} from '../../../servicios/productos.service';
import {ModalService} from '../../../servicios/modal.service';
import {ToastService} from '../../../servicios/toast.service';
import {MessageService} from 'primeng/api';
import {DialogService} from 'primeng/dynamicdialog';

@Component({
  selector: 'app-crear-productos',
  imports: [
    TabsModule,
    InputTextModule,
    FloatLabelModule,
    ReactiveFormsModule,
    ToastModule,
    Button,
    Select,
    Divider,
    InputNumber,
    // FileUpload
  ],
  templateUrl: './crear-productos.component.html',
  styleUrl: './crear-productos.component.css',
  standalone: true,
  providers: [ToastService,MessageService,DialogService, ModalService]
})
export class CrearProductosComponent implements OnInit {

  private formBuilder = inject(FormBuilder);
  private inventarioService = inject(ProductosService);
  private categoriaService = inject(CategoriaService);
  private personaService = inject(PersonasService);
  private modalService = inject(ModalService);
  private toastService = inject(ToastService);

  public categorias : Categoria[] = [];
  // public imagenes: any[] = [];

  public productoForm: FormGroup = this.formBuilder.group({
    nombre: ['', Validators.required],
    descripcion: ['', Validators.required],
    categoria: ['', Validators.required],
    precio: [0, Validators.required],
    stock: [0, Validators.required],
    proveedor: this.formBuilder.group({
      tipoDocumento: ['', Validators.required],
      documento: ['', Validators.required],
      nombre: ['', Validators.required],
      apellido: [''],
    })
  });
  public persona: Personas | null = null;

  ngOnInit() {
    this.categoriaService.listaCategorias().subscribe(resp => {
      this.categorias = resp.body;
    })
  }

  // subir(event: any) {
  //   const archivo : File = event.files[0];
  //   this.imagenes.push(archivo);
  // }

  obtenerPersonaDoc() {

    let tipoDocumento = this.productoForm.get('proveedor.tipoDocumento')?.value;
    let documento = this.productoForm.get('proveedor.documento')?.value;

    this.personaService.buscarPersonaPorDoc(tipoDocumento,documento).subscribe({
      next: resp => {
        this.persona = resp.body;
        this.productoForm.patchValue({
          proveedor : {
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

  agregarInventario(){
    if(this.productoForm.valid) {
      const inv : InventarioDto = this.productoForm.value;
      this.inventarioService.agregarInventario(inv).subscribe({
        next: resp => {
          this.toastService.mostrarToast(resp.msg, "success");
          this.productoForm.reset();
        },
        error: ({error}) => this.toastService.mostrarToast(error.msg, 'error')
      })
    }
  }
}
