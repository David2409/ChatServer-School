import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { User } from '../user';

@Component({
  selector: 'app-app-part-dialog-modify-user',
  templateUrl: './app-part-dialog-modify-user.component.html',
  styleUrls: ['./app-part-dialog-modify-user.component.css']
})
export class AppPartDialogModifyUserComponent implements OnInit {

  constructor(private dialogRef: MatDialogRef<AppPartDialogModifyUserComponent>, @Inject(MAT_DIALOG_DATA) public data: DialogModifyUserResult) { }

  ngOnInit(): void {

  }

  RemoveUser(){
    this.data.event = DialogModifyUserResultType.REMOVE;
    this.dialogRef.close(this.data);
  }

  Cancel(){
    this.dialogRef.close();
  }

  Save(){
    this.dialogRef.close(this.data);
  }
}

export interface DialogModifyUserResult {
  event: DialogModifyUserResultType,
  user: User
};

export enum DialogModifyUserResultType {
  CHANGE_PRIVIELEGES,
  REMOVE,
  NONE
};