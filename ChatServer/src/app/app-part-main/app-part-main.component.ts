import { Component, OnInit, EventEmitter, Output, Input } from '@angular/core';
import { Server } from '../server';
import { Room, NullRoom } from '../room';
import { Event } from '../event';
import { EventType } from '../event-type.enum';

@Component({
  selector: 'app-part-main',
  templateUrl: './app-part-main.component.html',
  styleUrls: ['./app-part-main.component.css']
})
export class AppPartMainComponent implements OnInit {

  constructor() { }

  @Output() eventChannel : EventEmitter<Event> = new EventEmitter<Event>();
  @Input() servers: Server[];
  selectedServer: Server;
  selectedRoom: Room;
  

  ngOnInit() {
    
  }

  SetServer(s: Server){
    this.selectedServer = s;
    this.selectedRoom = NullRoom;
  }

  SetRoom(r: Room){
    if(this.selectedRoom.id != NullRoom.id){
      for(let i = 0; i < this.selectedRoom.newMessages.length; i++){
        this.selectedRoom.messages.push(this.selectedRoom.newMessages[this.selectedRoom.newMessages.length-i-1]);
      }
    }
    this.selectedRoom.newMessages.length = 0;
    this.selectedRoom = r;
  }

  EventChannel(event: Event){
    switch(event.type){
      case EventType.SEND_MESSAGE:
        event.obj["roomId"] = this.selectedRoom.id;
        event.obj["serverId"] = this.selectedServer.id;
    }
    this.eventChannel.emit(event);
    console.log("beep")
  }
}
