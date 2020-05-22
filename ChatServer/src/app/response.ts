import { ResponseType } from './response-type.enum';

export interface Response {
    type: ResponseType,
    obj
}
