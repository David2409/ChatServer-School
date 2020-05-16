import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Room } from '../room';

@Component({
  selector: 'app-part-roomselection-item',
  templateUrl: './app-part-roomselection-item.component.html',
  styleUrls: ['./app-part-roomselection-item.component.css']
})
export class AppPartRoomselectionItemComponent implements OnInit {

  @Input() room : Room;
  @Input() selected : Room;
  @Output() outRoom: EventEmitter<Room> = new EventEmitter<Room>();

  Emit(){
    this.outRoom.emit(this.room);
  }

  constructor(){}

  ngOnInit() {
    
  }

}
