import { Room } from './room';
import { User } from './user';

export interface Server {
    id : String,
    name : String,
    rooms : Room[],
    onlineUser : User[],
    offlineUser : User[]
}

export const NullServer : Server = {id: "", name:"", rooms:[], onlineUser:[], offlineUser:[]};