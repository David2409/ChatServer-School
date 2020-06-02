import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Role } from '../role';
import { RoleOperation } from '../role-operation';

@Component({
  selector: 'app-part-server-role-list',
  templateUrl: './app-part-server-role-list.component.html',
  styleUrls: ['./app-part-server-role-list.component.css']
})
export class AppPartServerRoleListComponent implements OnInit {

  constructor() { }

  @Input() roles: Role[];
  @Output() rolesOut: EventEmitter<Role[]> = new EventEmitter<Role[]>();

  newRole: Role;

  ngOnInit(): void {
    this.newRole = { id:"", name:"NEW ROLE", caninvite: false, canchange: false, operation: RoleOperation.NEW };
  }

  AddRole(){
    console.log(this.roles);
    this.roles.push(this.newRole);
    this.newRole = { id:"", name:"NEW ROLE", caninvite: false, canchange: false, operation: RoleOperation.NEW };
  }
}