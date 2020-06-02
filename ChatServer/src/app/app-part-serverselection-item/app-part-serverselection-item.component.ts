import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Server } from '../server';
import { MatDialog } from '@angular/material/dialog';
import { Event } from '../event';
import { EventType } from '../event-type.enum';

@Component({
  selector: 'app-part-serverselection-item',
  templateUrl: './app-part-serverselection-item.component.html',
  styleUrls: ['./app-part-serverselection-item.component.css']
})
export class AppPartServerselectionItemComponent implements OnInit {

  constructor(private dialog: MatDialog) { }

  @Input() server : Server;
  @Input() selected : Server;
  @Output() outServer: EventEmitter<Server> = new EventEmitter<Server>();
  @Output() eventChannel: EventEmitter<Event> = new EventEmitter<Event>();

  Emit(){
    this.outServer.emit(this.server);
  }

  ngOnInit() {

  }

  Change(){
    this.EventChannel({type: EventType.DATA_SERVER, obj: {serverId: this.server.id }});
  }

  EventChannel(event){
    this.eventChannel.emit(event);
  }
}