import { Component, OnInit, NgZone, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-part-dialog-create-server',
  templateUrl: './app-part-dialog-create-server.component.html',
  styleUrls: ['./app-part-dialog-create-server.component.css']
})
export class AppPartDialogCreateServerComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<AppPartDialogCreateServerComponent>, private ngZone: NgZone, @Inject(MAT_DIALOG_DATA) public data: CreateServerDialogResult) {}

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

export interface CreateServerDialogResult {
  create: boolean,
  name: String
};