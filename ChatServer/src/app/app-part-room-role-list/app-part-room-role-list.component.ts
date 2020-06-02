import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Role } from '../role';
import { RoleRoom } from '../role-room';

@Component({
  selector: 'app-part-room-role-list',
  templateUrl: './app-part-room-role-list.component.html',
  styleUrls: ['./app-part-room-role-list.component.css']
})
export class AppPartRoomRoleListComponent implements OnInit {

  constructor() { }

  @Input() roles: RoleRoom[];
  @Output() rolesOut: EventEmitter<RoleRoom[]> = new EventEmitter<RoleRoom[]>(); 

  ngOnInit(): void {

  }

}
