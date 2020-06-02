import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UserDialog } from '../user-dialog';
import { EventType } from '../event-type.enum';

@Component({
  selector: 'app-app-part-dialog-modify-user',
  templateUrl: './app-part-dialog-modify-user.component.html',
  styleUrls: ['./app-part-dialog-modify-user.component.css']
})
export class AppPartDialogModifyUserComponent implements OnInit {

  constructor(private dialogRef: MatDialogRef<AppPartDialogModifyUserComponent>, @Inject(MAT_DIALOG_DATA) public data: UserDialog) { }

  ngOnInit(): void {

  }

  RemoveUser(){
    this.dialogRef.close({ type: EventType.REMOVE_USER, obj: this.data });
  }

  Cancel(){
    this.dialogRef.close();
  }

  Save(){
    this.dialogRef.close({ type: EventType.CHANGE_USER, obj: this.data});
  }
}