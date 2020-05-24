import { Component, OnInit, Input, HostListener, EventEmitter, Output, NgModule  } from '@angular/core';
import { Server, NullServer } from '../server';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AppPartDialogCreateServerComponent } from '../app-part-dialog-create-server/app-part-dialog-create-server.component';

@Component({
  selector: 'app-part-serverselection',
  templateUrl: './app-part-serverselection.component.html',
  styleUrls: ['./app-part-serverselection.component.css']
})
export class AppPartServerselectionComponent implements OnInit {

  constructor(public dialog: MatDialog) { }

  @Input() servers : Server[];
  @Output() selectedServerOut : EventEmitter<Server> = new EventEmitter<Server>();
  selectedServer : Server;

  @Output() eventChannel : EventEmitter<Event> = new EventEmitter<Event>();

  Emit(out: Server){
    this.selectedServerOut.emit(out);
    this.selectedServer = out;
  }

  CreateServer(){
    
  }

  ngOnInit() {
    this.selectedServer = NullServer;
  }
}