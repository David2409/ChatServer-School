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
import { Room } from './room';
import { General } from './general';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'ChatServer';

  servers: Server[];
  user: User;
  loggedIn: boolean = false;

  webSocket: WebsocketService;

  GetServer(serverId){
    for(let i = 0; i < this.servers.length; i++){
      if(this.servers[i].id == serverId){
        return this.servers[i];
      }
    }
    return null;
  }

  GetServerPos(serverId){
    for(let i = 0; i < this.servers.length; i++){
      if(this.servers[i].id == serverId){
        return i;
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

  GetRoomPos(server: Server, roomId){
    for(let i = 0; i < server.rooms.length; i++){
      if(server.rooms[i].id == roomId){
        return i;
      }
    }
    return null;
  }

  GetUserPos(users: User[], userId){
    for(let i = 0; i < users.length; i++){
      if(users[i].id == userId){
        return i;
      }
    }
    return null;
  }

  GetUser(users: User[], userid){
    for(let i = 0; i < users.length; i++){
      if(users[i].id == userid){
        return users[i];
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
      case ResponseType.NEW_SERVER:
        this.servers.push(r.obj as Server);
        break;
      case ResponseType.NEW_ROOM:
        this.GetServer((r.obj as Room).serverId).rooms.push((r.obj as Room));
        break;
      case ResponseType.DELETED_SERVER:
        this.servers.splice(this.GetServerPos((r.obj as General).serverId),1);
        break;
      case ResponseType.DELETED_ROOM:
        let s: Server = this.GetServer((r.obj as Room).serverId);
        s.rooms.splice(this.GetRoomPos(s, (r.obj as Room).id), 1);
        break;
      case ResponseType.ONLINE_USER:
        let data: General = (r.obj as General);
        let s2: number = this.GetServerPos(data.serverId);
        this.servers[s2].onlineUser.push(this.servers[s2].offlineUser.splice(this.GetUserPos(this.servers[s2].offlineUser, data.userId), 1)[0]);
        console.log(this.servers[s2]);
        break;
      case ResponseType.OFFLINE_USER:
        let data2: General = (r.obj as General);
        let s3: number = this.GetServerPos(data2.serverId);
        this.servers[s3].offlineUser.push(this.servers[s3].onlineUser.splice(this.GetUserPos(this.servers[s3].onlineUser, data2.userId), 1)[0]);
        break;
      case ResponseType.REMOVED_USER:
        let data3: General = (r.obj as General);
        let s4: number = this.GetServerPos(data3.serverId);
        this.servers[s4].offlineUser.splice(this.GetUserPos(this.servers[s4].onlineUser, data3.userId), 1);
        break;
    }
  }

  constructor(dialog: MatDialog) {
    this.user = null;
    this.servers = [];
    this.webSocket = new WebsocketService();
    this.webSocket.connect("ws:localhost:4444");

    //this.socket.onmessage = this.ExecuteResponse;
    
    //dialog.open(AppPartDialogCreateServerComponent);

    this.webSocket.subject.pipe(
      map( (data: MessageEvent) => {
          return data.data;
        }, catchError(error => {
          return throwError('Something went wrong!');
        }))).subscribe((data: any) => {
          this.ExecuteResponse(data);
        });

    //this.ExecuteEvent({type:EventType.LOGIN, obj: { username: "Chrascher", password: "test"}});
  }
}