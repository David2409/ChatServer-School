import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-part-dialog-create-room',
  templateUrl: './app-part-dialog-create-room.component.html',
  styleUrls: ['./app-part-dialog-create-room.component.css']
})
export class AppPartDialogCreateRoomComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<AppPartDialogCreateRoomComponent>, @Inject(MAT_DIALOG_DATA) public data: CreateRoomDialogResult) {}

  ngOnInit() {

  }

  onClickCancel(): void{
    this.dialogRef.close(this.data);
  }

  onClickCreate(): void{
    this.data.create = true;
    this.dialogRef.close(this.data);
  }
}

export interface CreateRoomDialogResult {
  create: boolean,
  name: String
};