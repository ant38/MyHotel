import { Hotel } from "./hotel";
export class Room {
    id_room: number;
    version: number;
    places: number;
    bathroom: boolean;
    kitchen: boolean;
    doubleBed: number;
    simpleBed: number;
    sofaBed: number;
    tv: boolean;
    size: number;
    type: string;
    roomNumber: number;
    hotel: Hotel;
}