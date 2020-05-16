import { Component, OnInit } from '@angular/core';
import { Server } from './server';
import { Event } from './event';
import { EventType } from './event-type.enum';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'ChatServer';

  servers: Server[];
  socket: WebSocket = new WebSocket("ws:localhost:4444");


  ExecuteEvent(event: Event){
    switch(event.type){
      case EventType.SEND_MESSAGE:
        
        break;
    }
  }

  constructor() {
    this.socket = new WebSocket("ws:localhost:4444");
    this.socket.onopen = function(event) {
      console.log("Connected to Server");
    };

    this.socket.onmessage = function(event) {
      console.log(event.data);
      this.send(event.data);
    }
    
    this.socket.onerror = function(error){
      console.log(error);
    }


















































































































    this.servers = 
    [
      {
        id : "101",
        name: "Server1",
        rooms: [
          {
            id: "100",
            name: "Room1",
            messages: [{
              id: "100",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message",
              sendedAt: "10.12.2002 16:37:06"
            }],
            newMessages: [{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            },{
              id: "101",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 2",
              sendedAt: "10.12.2002 16:37:07"
            }],
          },
          {
            id: "101",
            name: "Room2",
            messages: [{
              id: "150",
              serverId: "100",
              roomId: "100",
              from: "from",
              msg: "This is a message 3",
              sendedAt: "10.12.2002 16:37:06"
            }],
            newMessages: [],
          }
        ],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "102",
        name: "Server2",
        rooms: [
          {
            id: "102",
            name: "Room1",
            messages: [],
            newMessages: [],
          }
        ],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "103",
        name: "Server3",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "104",
        name: "Server4",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      },
      {
        id : "105",
        name: "Server5",
        rooms: [],
        onlineUser: [],
        offlineUser: []
      }
    ];
  }
}
