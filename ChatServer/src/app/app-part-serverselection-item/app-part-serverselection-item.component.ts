import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Server } from '../server';

@Component({
  selector: 'app-part-serverselection-item',
  templateUrl: './app-part-serverselection-item.component.html',
  styleUrls: ['./app-part-serverselection-item.component.css']
})
export class AppPartServerselectionItemComponent implements OnInit {

  constructor() { }

  @Input() server : Server;
  @Input() selected : Server;
  @Output() outServer: EventEmitter<Server> = new EventEmitter<Server>();

  Emit(){
    this.outServer.emit(this.server);
  }

  ngOnInit() {
  }
}