import { Injectable } from "@angular/core";
import * as Rx from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {

  constructor() { }

  public subject: Rx.Subject<MessageEvent>;
  private socket : WebSocket;

  public connect(url): Rx.Subject<MessageEvent>{
    if(!this.subject){
      this.subject = this.create(url);
    }
    return this.subject;
  }

  private create(url) : Rx.Subject<MessageEvent> {
    this.socket = new WebSocket(url);

    let observable = Rx.Observable.create((obs: Rx.Observer<MessageEvent>) =>{
      this.socket.onmessage = obs.next.bind(obs);
      this.socket.onerror = obs.error.bind(obs);
      this.socket.close = obs.complete.bind(obs);
      return this.socket.close.bind(this.socket);
    });

    let observer = {
      next: (data: any) => {
        if(this.socket.readyState === WebSocket.OPEN){
          console.log("send");
          console.log(data);
          this.socket.send(JSON.stringify(data));
        }
      }
    }

    return Rx.Subject.create(observer, observable);
  }
}