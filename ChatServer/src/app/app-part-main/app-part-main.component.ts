import { Component, OnInit, EventEmitter, Output, Input } from '@angular/core';
import { Server } from '../server';
import { Room, NullRoom } from '../room';
import { Event } from '../event';

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
    console.log(this.selectedRoom);
    this.selectedRoom = r;
  }

  EventChannel(event){
    this.eventChannel.emit(event);
  }
}
