import { Component, OnInit, Input } from '@angular/core';
import { Role } from '../role';

@Component({
  selector: 'app-app-part-role-server',
  templateUrl: './app-part-role-server.component.html',
  styleUrls: ['./app-part-role-server.component.css']
})
export class AppPartRoleServerComponent implements OnInit {

  @Input() role: Role;

  constructor() { }

  ngOnInit(): void {

  }

}
