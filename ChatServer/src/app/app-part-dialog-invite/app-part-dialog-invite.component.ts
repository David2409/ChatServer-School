import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-part-dialog-invite',
  templateUrl: './app-part-dialog-invite.component.html',
  styleUrls: ['./app-part-dialog-invite.component.css']
})
export class AppPartDialogInviteComponent implements OnInit {

  data: DialogInviteResult = { name: "", invite: false}

  constructor(private dialogRef: MatDialogRef<AppPartDialogInviteComponent>) { }

  ngOnInit() {
    
  }

  onClickInvite(){
    this.data.invite = true
    this.dialogRef.close(this.data);
  }

  onClickCancel(){
    this.dialogRef.close();
  }
}

export interface DialogInviteResult {
  invite: boolean,
  name: string
}