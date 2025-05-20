import {inject, Injectable} from '@angular/core';
import {MessageService} from 'primeng/api';

@Injectable({
  providedIn: 'root'
})
export class ToastService {

  private msgService = inject(MessageService);

  mostrarToast(msg : string, severity : string){
    this.msgService.add({severity, summary: 'Mensaje', detail: msg});
  }
}
