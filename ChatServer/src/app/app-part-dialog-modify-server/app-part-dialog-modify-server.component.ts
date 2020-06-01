import { Component, OnInit, Inject } from '@angular/core';
import { Server } from '../server';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-part-dialog-modify-server',
  templateUrl: './app-part-dialog-modify-server.component.html',
  styleUrls: ['./app-part-dialog-modify-server.component.css']
})
export class AppPartDialogModifyServerComponent implements OnInit {

  constructor(private dialogRef: MatDialogRef<AppPartDialogModifyServerComponent>, @Inject(MAT_DIALOG_DATA) public data: DialogModifyServerResult) { }

  ngOnInit() {

  }

  Remove(){
    this.data.event = DialogModifyServerResultType.REMOVE;
    this.dialogRef.close(this.data);
  }

  Save(){
    this.dialogRef.close();
  }

  Cancel(){
    this.dialogRef.close();
  }

}

export interface DialogModifyServerResult {
  event: DialogModifyServerResultType,
  server: Server
};

export enum DialogModifyServerResultType {
  CHANGED,
  REMOVE,
  NONE
};