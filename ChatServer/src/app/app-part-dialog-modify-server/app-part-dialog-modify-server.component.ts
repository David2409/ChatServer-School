import { Component, OnInit, Inject } from '@angular/core';
import { Server } from '../server';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ServerDialog } from '../server-dialog';
import { RoleOperation } from '../role-operation';
import { EventType } from '../event-type.enum';

@Component({
  selector: 'app-part-dialog-modify-server',
  templateUrl: './app-part-dialog-modify-server.component.html',
  styleUrls: ['./app-part-dialog-modify-server.component.css']
})
export class AppPartDialogModifyServerComponent implements OnInit {

  constructor(private dialogRef: MatDialogRef<AppPartDialogModifyServerComponent>, @Inject(MAT_DIALOG_DATA) public data: ServerDialog) { }

  ngOnInit() {
    for(let i = 0; i < this.data.roles.length; i++){
      this.data.roles[i].operation = RoleOperation.NONE;
    }
  }

  Remove(){
    this.dialogRef.close({ type: EventType.DELETE_SERVER, obj: { serverId: this.data.serverId }});
  }

  Save(){
    for(let i = 0; i < this.data.roles.length; i++){
      if(this.data.roles[i].operation == RoleOperation.NONE){
        this.data.roles.splice(i, 1);
        i--;
      }
    }
    this.dialogRef.close( {type: EventType.CHANGE_SERVER, obj: this.data } );
  }

  Cancel(){
    this.dialogRef.close();
  }

}