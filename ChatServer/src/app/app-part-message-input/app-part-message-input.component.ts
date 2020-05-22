import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Event } from '../event';
import { EventType } from '../event-type.enum';

@Component({
  selector: 'app-part-message-input',
  templateUrl: './app-part-message-input.component.html',
  styleUrls: ['./app-part-message-input.component.css']
})
export class AppPartMessageInputComponent implements OnInit {

  @Output() eventChannel : EventEmitter<Event> = new EventEmitter<Event>();

  constructor() { }

  ngOnInit() {
  }

  Send(){
    let message = (<HTMLInputElement>document.getElementById("messageInput")).value;
    let generatedEvent: Event = {type: EventType.SEND_MESSAGE, obj: { msg: message}};
    this.eventChannel.emit(generatedEvent);
  }
}
