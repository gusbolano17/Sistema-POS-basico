import {inject, Injectable, Type} from '@angular/core';
import {DialogService, DynamicDialogRef} from 'primeng/dynamicdialog';

@Injectable({
  providedIn: 'root'
})
export class ModalService {

  private dialogS = inject(DialogService);
  private ref : DynamicDialogRef | null = null;

  open(compontent : Type<any>, data : { }){
    this.ref = this.dialogS.open(compontent, {
      inputValues: data,
      modal: true,
    })

    return this.ref.onClose;
  }

  close(){
    if(this.ref){
      this.ref.close();
      this.ref = null;
    }
  }

}
