import { Message } from './message';

export interface Room {
    serverId: String,
    id : String,
    name : String,
    messages: Message[],
    newMessages: Message[]
}

export const NullRoom : Room = {serverId: "NULL", id: "NULL", name: "", messages:[], newMessages:[]};