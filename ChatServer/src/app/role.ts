import { RoleOperation } from './role-operation';

export interface Role {
    id: String,
    name: String,
    caninvite: boolean,
    canchange: boolean
    operation: RoleOperation
}
