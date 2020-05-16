import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-part-message',
  templateUrl: './app-part-message.component.html',
  styleUrls: ['./app-part-message.component.css']
})
export class AppPartMessageComponent implements OnInit {

  @Input() message;

  constructor() { }

  ngOnInit() {
    
  }
}