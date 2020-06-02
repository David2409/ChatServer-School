import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Role } from '../role';
import { RoleOperation } from '../role-operation';

@Component({
  selector: 'app-part-server-role-item',
  templateUrl: './app-part-server-role-item.component.html',
  styleUrls: ['./app-part-server-role-item.component.css']
})
export class AppPartServerRoleItemComponent implements OnInit {

  @Input() role: Role;

  new: boolean;

  @Output()
  roleChange = new EventEmitter<Role>();

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
    this.new = this.role.operation == RoleOperation.NEW;
  }

  clickInvite(){
    this.role.caninvite = !this.role.caninvite;
    this.SetOperation();
    this.Role = this.role;
  }

  clickChange(){
    this.role.canchange = !this.role.canchange;
    this.SetOperation();
    this.Role = this.role;
  }

  changeName(){
    this.SetOperation();
    this.Role = this.role;
  }

  SetOperation(){
    if(this.new != true && this.role.operation != RoleOperation.DELETE){
      this.role.operation = RoleOperation.UPDATE;
    }
  }

  clickDelete(){
      if(this.role.operation == RoleOperation.DELETE || (this.role.operation == RoleOperation.NONE && this.new == true)){
        if(this.new == true){
          this.role.operation = RoleOperation.NEW;
        }
        else{
          this.role.operation = RoleOperation.UPDATE;
        }
      } else {
        if(this.new == true){
          this.role.operation = RoleOperation.NONE;
        }
        else{
          this.role.operation = RoleOperation.DELETE;
        }
      }
      this.Role = this.role;
  }

  GetDel(){
    return this.role.operation == RoleOperation.DELETE || (this.role.operation == RoleOperation.NONE && this.new == true);
  }
}
