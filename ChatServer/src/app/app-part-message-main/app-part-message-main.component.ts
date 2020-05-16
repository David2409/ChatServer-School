import { Component, OnInit, Input, Output, EventEmitter, } from '@angular/core';
import { Room } from '../room';

@Component({
  selector: 'app-part-message-main',
  templateUrl: './app-part-message-main.component.html',
  styleUrls: ['./app-part-message-main.component.css']
})
export class AppPartMessageMainComponent implements OnInit {

  constructor() { }

  @Input() room: Room;
  @Output() eventChannel : EventEmitter<Event> = new EventEmitter<Event>();

  ngOnInit() {
  }

  EventChannel(event){
    this.eventChannel.emit(event);
  }
}
