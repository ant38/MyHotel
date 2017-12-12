import { Room } from "./room";
import { User } from "./user";
export class Booking {
    id_booking: number;
    version: number;
    dateIn: Date;
    dateOut: Date;
    paid: boolean;
    roomlist: Room[];
    clientList: User[];
}