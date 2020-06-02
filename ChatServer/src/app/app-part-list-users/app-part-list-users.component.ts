import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Server } from '../server';
import { Event } from '../event';
import { MatDialog } from '@angular/material/dialog';
import { AppPartDialogInviteComponent, DialogInviteResult } from '../app-part-dialog-invite/app-part-dialog-invite.component';
import { EventType } from '../event-type.enum';

@Component({
  selector: 'app-part-list-users',
  templateUrl: './app-part-list-users.component.html',
  styleUrls: ['./app-part-list-users.component.css']
})
export class AppPartListUsersComponent implements OnInit {

  @Input() server: Server;
  @Output() eventChannel: EventEmitter<Event> = new EventEmitter<Event>();

  constructor(private dialog: MatDialog) { }

  ngOnInit(): void {
  }

  EventChannel(event: Event){
    event.obj["serverId"] = this.server.id;
    this.eventChannel.emit(event);
  }

  InviteUser(){
    this.dialog.open(AppPartDialogInviteComponent).afterClosed().subscribe((data: DialogInviteResult) => {
      if(data.invite == true){
        let event: Event = {type: EventType.INVITE_USER, obj: { username: data.name, serverId: this.server.id}}
        this.EventChannel(event);
      }
    });
  }
}
