import { Component, OnInit, EventEmitter, Output, Input } from '@angular/core';
import { RoleRoom } from '../role-room';

@Component({
  selector: 'app-part-room-role-item',
  templateUrl: './app-part-room-role-item.component.html',
  styleUrls: ['./app-part-room-role-item.component.css']
})
export class AppPartRoomRoleItemComponent implements OnInit {

  @Input() role: RoleRoom;

  @Output()
  roleChange = new EventEmitter<RoleRoom>();

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

  clickRead(){
    this.role.canread = !this.role.canread;
    this.Role = this.role;
  }

  clickWrite(){
    this.role.canwrite = !this.role.canwrite;
    this.Role = this.role;
  }

  clickSee(){
    this.role.cansee = !this.role.cansee;
    this.Role = this.role;
  }
}
