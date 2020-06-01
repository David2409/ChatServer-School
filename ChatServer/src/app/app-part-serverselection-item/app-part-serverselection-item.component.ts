import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Server } from '../server';
import { MatDialog } from '@angular/material/dialog';
import { AppPartDialogModifyServerComponent, DialogModifyServerResult, DialogModifyServerResultType } from '../app-part-dialog-modify-server/app-part-dialog-modify-server.component';
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
    let input: DialogModifyServerResult = { event: DialogModifyServerResultType.NONE, server: this.server}
    this.dialog.open(AppPartDialogModifyServerComponent, { data: input }).afterClosed().subscribe((data: DialogModifyServerResult) => {
      if(data.event == DialogModifyServerResultType.NONE){
        return;
      }
      let newEvent: Event;
      if(data.event == DialogModifyServerResultType.REMOVE){
        newEvent = { type: EventType.DELETE_SERVER, obj: {serverId: this.server.id} }
      }
      this.eventChannel.emit(newEvent);
    });
  }

  EventChannel(event){
    this.eventChannel.emit(event);
  }
}