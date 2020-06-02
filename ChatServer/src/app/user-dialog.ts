import { RoleUser } from './role-user';

export interface UserDialog {
    serverId: String,
    userId: String,
    name: String,
    roles: RoleUser[]
}
