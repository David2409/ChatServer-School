import { Component, OnInit, Inject } from '@angular/core';
import { Room } from '../room';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-part-dialog-modify-room',
  templateUrl: './app-part-dialog-modify-room.component.html',
  styleUrls: ['./app-part-dialog-modify-room.component.css']
})
export class AppPartDialogModifyRoomComponent implements OnInit {

  constructor(private dialogRef: MatDialogRef<AppPartDialogModifyRoomComponent>, @Inject(MAT_DIALOG_DATA) public data: DialogModifyRoomResult) { }

  ngOnInit() {

  } 

  Remove(){
    this.data.event = DialogModifyRoomResultType.REMOVE;
    this.dialogRef.close(this.data);
  }

  Save(){
    this.dialogRef.close();
  }

  Cancel(){
    this.dialogRef.close();
  }
}

export interface DialogModifyRoomResult {
  event: DialogModifyRoomResultType,
  room: Room
};

export enum DialogModifyRoomResultType {
  CHANGED,
  REMOVE,
  NONE
};