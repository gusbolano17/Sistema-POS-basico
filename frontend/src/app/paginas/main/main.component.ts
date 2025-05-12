import {Component, OnInit, signal, WritableSignal} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {NavbarComponent} from '../../layout/navbar/navbar.component';
import {SidebarComponent} from '../../layout/sidebar/sidebar.component';

@Component({
  selector: 'app-main',
  imports: [
    RouterOutlet,
    NavbarComponent,
    SidebarComponent
  ],
  templateUrl: './main.component.html',
  styleUrl: './main.component.css',
  standalone: true
})
export class MainComponent implements OnInit{

  public sidebarOpened : WritableSignal<boolean> = signal(false);

  ngOnInit(): void {
  }

  toggleSidebar() {
    this.sidebarOpened.update(v => !v);
  }
}
