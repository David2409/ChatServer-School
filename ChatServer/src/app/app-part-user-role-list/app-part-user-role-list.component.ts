import { Component, OnInit,Input, EventEmitter, Output } from '@angular/core';
import { RoleUser } from '../role-user';

@Component({
  selector: 'app-part-user-role-list',
  templateUrl: './app-part-user-role-list.component.html',
  styleUrls: ['./app-part-user-role-list.component.css']
})
export class AppPartUserRoleListComponent implements OnInit {

  constructor() { }

  @Input() roles: RoleUser[];
  @Output() rolesOut: EventEmitter<RoleUser[]> = new EventEmitter<RoleUser[]>();

  ngOnInit(): void {
    
  }
}