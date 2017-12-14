import { Hotel } from "./hotel";
export class Offer {
    id_offre: number;
    name: string;
    dateIn: Date;
    dateOut: Date;
    nbPersonne: number;
    hotel: Hotel;
    prix: number;
    description: string;
}