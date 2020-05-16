import { Message } from './message';

export interface Room {
    id : String,
    name : String,
    messages: Message[],
    newMessages: Message[]
}

export const NullRoom : Room = {id: "null", name: "", messages:[], newMessages:[]};