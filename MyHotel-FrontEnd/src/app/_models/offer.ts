import { Room } from "./room";
export class Offer {
    id_offre: number;
    version: number;
    dateStart: Date;
    dateEnd: Date;
    price: number;
    rooms: Room[];
}
