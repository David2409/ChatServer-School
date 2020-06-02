import { Role } from './role';

export interface ServerDialog {
    serverId: String,
    name: String,
    roles: Role[]
}
