import { Component, OnInit, Inject } from '@angular/core';
import { Room } from '../room';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { EventType } from '../event-type.enum';
import { RoomDialog } from '../room-dialog';

@Component({
  selector: 'app-part-dialog-modify-room',
  templateUrl: './app-part-dialog-modify-room.component.html',
  styleUrls: ['./app-part-dialog-modify-room.component.css']
})
export class AppPartDialogModifyRoomComponent implements OnInit {

  constructor(private dialogRef: MatDialogRef<AppPartDialogModifyRoomComponent>, @Inject(MAT_DIALOG_DATA) public data: RoomDialog) { }

  ngOnInit() {

  } 

  Remove(){
    this.dialogRef.close({ type: EventType.DELETE_ROOM, obj: { serverId: this.data.serverId, roomId: this.data.id }});
  }

  Save(){
    this.dialogRef.close({ type: EventType.CHANGE_ROOM, obj: this.data});
  }

  Cancel(){
    this.dialogRef.close();
  }
}