import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Server } from '../server';
import { Room, NullRoom } from '../room';

@Component({
  selector: 'app-part-roomselection',
  templateUrl: './app-part-roomselection.component.html',
  styleUrls: ['./app-part-roomselection.component.css']
})
export class AppPartRoomselectionComponent implements OnInit {

  constructor() { }

  @Input() server : Server;
  @Output() selectedRoomOut : EventEmitter<Room> = new EventEmitter<Room>();
  @Input() selectedRoom : Room;

  Emit(out: Room){
    this.selectedRoomOut.emit(out);
    this.selectedRoom = out;
  }

  ngOnInit() {
    this.selectedRoom = NullRoom; 
  }
}