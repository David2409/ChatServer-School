import { Component } from '@angular/core';
import { Server } from './server';
import { ResponseType } from './response-type.enum';
import { User } from './user';
import { Response } from './response';
import { WebsocketService } from './websocket-service.service';
import { throwError } from 'rxjs';
import * as mes from './message';
import { map, catchError } from 'rxjs/operators';
import { MatDialog } from '@angular/material/dialog';
import { AppPartDialogCreateServerComponent } from './app-part-dialog-create-server/app-part-dialog-create-server.component';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'ChatServer';

  servers: Server[];
  user: User;
  loggedIn: boolean = true;

  webSocket: WebsocketService;

  GetServer(serverId){
    for(let i = 0; i < this.servers.length; i++){
      if(this.servers[i].id == serverId){
        return this.servers[i];
      }
    }
    return null;
  }

  GetRoom(server: Server, roomId){
    for(let i = 0; i < server.rooms.length; i++){
      if(server.rooms[i].id == roomId){
        return server.rooms[i];
      }
    }
    return null;
  }

  ExecuteEvent(event){
    console.log(event);
    this.webSocket.subject.next(event);
  }


  AddMessage(message: mes.Message){
    console.log("Add Message");
    let server = this.GetServer(message.serverId);
    console.log(server)
    if(server != null){
      console.log("Got Server")
      let room = this.GetRoom(server, message.roomId);
      if(room != null){
        console.log("Got Room");
        room.newMessages.push(message);
        console.log("Added Messages");
      }
    }
  }

  LogIn(user){
    this.user = user;
    this.loggedIn = true;
  }

  LogOut(){
    this.user = null;
    this.loggedIn = false;
  }

  ExecuteResponse(data){
    console.log("Execute");
    console.log(data);
    let temp = JSON.parse(data);
    temp.type = ResponseType[temp.type];
    let r: Response = temp;
    switch(r.type){
      case ResponseType.LOGGED_IN:
        this.LogIn(r.obj);
        break;
      case ResponseType.NEW_MESSAGE:
        this.AddMessage(r.obj as mes.Message);
        break;
      case ResponseType.SERVER_MAP:
        this.servers = r.obj;
        break;
    }
  }

  constructor(dialog: MatDialog) {
    this.user = null;

    this.webSocket = new WebsocketService();
    this.webSocket.connect("ws:localhost:4444");

    //this.socket.onmessage = this.ExecuteResponse;
    
    this.webSocket.subject.pipe(
      map( (data: MessageEvent) => {
          return data.data;
        }, catchError(error => {
          return throwError('Something went wrong!');
        }))).subscribe((data: any) => {
          this.ExecuteResponse(data);
        });
    
    dialog.open(AppPartDialogCreateServerComponent);





































































































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
