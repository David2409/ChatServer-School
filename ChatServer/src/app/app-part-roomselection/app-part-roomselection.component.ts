import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Server } from '../server';
import { Room, NullRoom } from '../room';
import { AppPartDialogCreateRoomComponent, CreateRoomDialogResult } from '../app-part-dialog-create-room/app-part-dialog-create-room.component';
import { EventType } from '../event-type.enum';
import { Event } from '../event';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-part-roomselection',
  templateUrl: './app-part-roomselection.component.html',
  styleUrls: ['./app-part-roomselection.component.css']
})
export class AppPartRoomselectionComponent implements OnInit {

  constructor(private dialog: MatDialog) { }

  @Input() server : Server;
  @Output() selectedRoomOut : EventEmitter<Room> = new EventEmitter<Room>();
  @Input() selectedRoom : Room;

  @Output() eventChannel : EventEmitter<Event> = new EventEmitter<Event>();

  Emit(out: Room){
    this.selectedRoomOut.emit(out);
    this.selectedRoom = out;
  }

  ngOnInit() {
    this.selectedRoom = NullRoom; 
  }

  CreateServer(){
    this.dialog.open(AppPartDialogCreateRoomComponent, { data: { create: false, name: "NEW ROOM"}}).afterClosed().subscribe((data: CreateRoomDialogResult) => {
      if(data.create == true){
        let obj: Room;
        obj = { serverId: this.server.id, id: '', name: data.name, messages: null, newMessages: null};
        let cR: Event = { type: EventType.CREATE_ROOM, obj: obj};
        this.EventChannel(cR);
      }
    });
  }

  EventChannel(event){
    this.eventChannel.emit(event);
  }
}