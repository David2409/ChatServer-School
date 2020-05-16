import { EventType } from './event-type.enum';

export interface Event {
    type: EventType,
    obj
}