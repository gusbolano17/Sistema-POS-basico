import {Component, EventEmitter, Output} from '@angular/core';
import {Toolbar} from 'primeng/toolbar';
import {Button} from 'primeng/button';
import {Avatar} from 'primeng/avatar';


@Component({
  selector: 'navbar',
  imports: [
    Toolbar,
    Button,
    Avatar
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
  standalone: true
})
export class NavbarComponent {
  @Output() toggleSidebar = new EventEmitter<void>();

}
