import { Component, OnInit } from '@angular/core';
import * as io from 'socket.io-client';

@Component({
  selector: 'app-part-login',
  templateUrl: './app-part-login.component.html',
  styleUrls: ['./app-part-login.component.css']
})
export class AppPartLoginComponent implements OnInit {

  //private socket;

  constructor() {/*
    //var io = require("socket.io-client");
    //var socket = io.connect("ws://localhost:4444", { path: "/"});
    var socket = new WebSocket("ws:localhost:4444");
    socket.onopen = function(event) {
      console.log("Connected to Server")
      console.log("\t" + event);
      socket.send("Hallo");
    };

    socket.onmessage = function(event) {
      console.log(event.data);
      socket.send(event.data);
    }
    
    socket.onerror = function(error){
      console.log(error);
    }*/
  }

  ngOnInit() {
  }
}