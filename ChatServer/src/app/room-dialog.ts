import { RoleRoom } from './role-room';

export interface RoomDialog {
    serverId: String,
    id: String,
    name: String,
    roles: RoleRoom[]
}
