import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { RoleUser } from '../role-user';

@Component({
  selector: 'app-part-user-role-item',
  templateUrl: './app-part-user-role-item.component.html',
  styleUrls: ['./app-part-user-role-item.component.css']
})
export class AppPartUserRoleItemComponent implements OnInit {

  @Input() role: RoleUser;

  @Output()
  roleChange = new EventEmitter<RoleUser>();

  @Input()
  get Role(){
    return this.role;
  }

  set Role(val) {
    this.role = val;
    this.roleChange.emit(this.role);
  }

  constructor() { }

  ngOnInit(): void {

  }

  clickHas(){
    this.role.has = !this.role.has;
    this.Role = this.role;
  }
}