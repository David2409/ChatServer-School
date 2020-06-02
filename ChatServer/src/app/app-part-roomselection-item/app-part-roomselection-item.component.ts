import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Room } from '../room';
import { MatDialog } from '@angular/material/dialog';
import { EventType } from '../event-type.enum';
import { Event } from '../event';

@Component({
  selector: 'app-part-roomselection-item',
  templateUrl: './app-part-roomselection-item.component.html',
  styleUrls: ['./app-part-roomselection-item.component.css']
})
export class AppPartRoomselectionItemComponent implements OnInit {

  @Input() room : Room;
  @Input() selected : Room;
  @Output() outRoom: EventEmitter<Room> = new EventEmitter<Room>();
  @Output() eventChannel: EventEmitter<Event> = new EventEmitter<Event>();

  Emit(){
    this.outRoom.emit(this.room);
  }

  constructor(private dialog: MatDialog){}

  ngOnInit() {
    
  }

  Change(){
    let event: Event = {type: EventType.DATA_ROOM, obj: { serverId: this.room.serverId, roomId: this.room.id}};
    this.EventChannel(event);
  }

  EventChannel(event){
    this.eventChannel.emit(event);
  }
}