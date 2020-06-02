import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { Event } from '../event';
import { User } from '../user';
import { MatDialog } from '@angular/material/dialog';
import { AppPartDialogModifyUserComponent } from '../app-part-dialog-modify-user/app-part-dialog-modify-user.component';
import { EventType } from '../event-type.enum';

@Component({
  selector: 'app-part-user',
  templateUrl: './app-part-user.component.html',
  styleUrls: ['./app-part-user.component.css']
})
export class AppPartUserComponent implements OnInit {

  @Input() user: User;
  @Output() eventChannel: EventEmitter<Event> = new EventEmitter<Event>();

  constructor(private dialog: MatDialog) { }

  ngOnInit(): void {
  }

  EventChannel(event){
    this.eventChannel.emit(event);
    console.log(event);
  }

  changeUser(){
    this.EventChannel({type: EventType.DATA_USER, obj: {userId: this.user.id } });
  }
}