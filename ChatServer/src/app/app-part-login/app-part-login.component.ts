import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { Event } from '../event';
import { EventType } from '../event-type.enum';
import { User } from '../user';

@Component({
  selector: 'app-part-login',
  templateUrl: './app-part-login.component.html',
  styleUrls: ['./app-part-login.component.css']
})
export class AppPartLoginComponent implements OnInit {

  @Output() eventChannel: EventEmitter<Event> = new EventEmitter<Event>();

  EmitEvent(event: Event){
    this.eventChannel.emit(event);
  }

  Login(){
    let user: User = {  username: (<HTMLInputElement> document.getElementById("inputUsername")).value, 
                        password: (<HTMLInputElement> document.getElementById("inputPassword")).value };
    let event: Event = { type: EventType.LOGIN, obj: user };
    this.eventChannel.emit(event);
  }

  SignUp(){
    let user: User = {  username: (<HTMLInputElement> document.getElementById("inputUsername")).value, 
                        password: (<HTMLInputElement> document.getElementById("inputPassword")).value };
    let event: Event = { type: EventType.CREATE_USER, obj: user };
    this.eventChannel.emit(event);
  }

  constructor() {

  }

  ngOnInit() {
  }
}