import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Message } from '../message';
import { Room } from '../room';

@Component({
  selector: 'app-part-message-list',
  templateUrl: './app-part-message-list.component.html',
  styleUrls: ['./app-part-message-list.component.css']
})
export class AppPartMessageListComponent implements OnInit {

  @Input() room: Room;
  @Output() eventChannel : EventEmitter<Event> = new EventEmitter<Event>();

  constructor() { }

  ngOnInit() {
  }
}