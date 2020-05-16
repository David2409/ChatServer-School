import { Component, OnInit, Input, HostListener, EventEmitter, Output  } from '@angular/core';
import { Server } from '../server';


@Component({
  selector: 'app-part-serverselection',
  templateUrl: './app-part-serverselection.component.html',
  styleUrls: ['./app-part-serverselection.component.css']
})
export class AppPartServerselectionComponent implements OnInit {

  constructor() { }

  @Input() servers : Server[];
  @Output() selectedServerOut : EventEmitter<Server> = new EventEmitter<Server>();
  selectedServer : Server;

  Emit(out: Server){
    this.selectedServerOut.emit(out);
    this.selectedServer = out;
  }

  ngOnInit() {
    this.selectedServer = {id : "null", name: "null", rooms: [], offlineUser: [], onlineUser: []};
  }
}