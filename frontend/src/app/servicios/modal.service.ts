import {inject, Injectable, Type} from '@angular/core';
import {DialogService, DynamicDialogRef} from 'primeng/dynamicdialog';

@Injectable({
  providedIn: 'root'
})
export class ModalService {

  private dialogS = inject(DialogService);
  private ref : DynamicDialogRef = new DynamicDialogRef();

  open(compontent : Type<any>, data : { }){
    this.ref = this.dialogS.open(compontent, {
      inputValues: data,
      modal: true,
    })
  }

}
