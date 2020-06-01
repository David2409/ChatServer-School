import { Component, OnInit, Input, EventEmitter, Output, NgZone  } from '@angular/core';
import { Server, NullServer } from '../server';
import { MatDialog } from '@angular/material/dialog';
import { AppPartDialogCreateServerComponent, CreateServerDialogResult } from '../app-part-dialog-create-server/app-part-dialog-create-server.component';
import { Event } from '../event';
import { EventType } from '../event-type.enum';

@Component({
  selector: 'app-part-serverselection',
  templateUrl: './app-part-serverselection.component.html',
  styleUrls: ['./app-part-serverselection.component.css']
})
export class AppPartServerselectionComponent implements OnInit {

  constructor(private dialog: MatDialog, private ngZone: NgZone) { }

  @Input() servers : Server[];
  @Output() selectedServerOut : EventEmitter<Server> = new EventEmitter<Server>();
  selectedServer : Server;

  @Output() eventChannel : EventEmitter<Event> = new EventEmitter<Event>();

  Emit(out: Server){
    this.selectedServerOut.emit(out);
    this.selectedServer = out;
  }

  CreateServer(){
    this.dialog.open(AppPartDialogCreateServerComponent, { data: { create: false, name: "NEW SERVER"}}).afterClosed().subscribe((data: CreateServerDialogResult) => {
      if(data.create == true){
        let cS: Event = { type: EventType.CREATE_SERVER, obj: { name: data.name}};
        this.EventChannel(cS);
      }
    });
  }

  ngOnInit() {
    this.selectedServer = NullServer;
  }

  EventChannel(event){
    this.eventChannel.emit(event);
  }
}