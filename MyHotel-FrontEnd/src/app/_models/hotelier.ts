import { Hotel } from "./hotel";
export class Hotelier {
    id: number;
    username: string;
    password: string;
    prenom: string;
    nom: string;
    dateNaissance: Date;
    status: string;
    email: string;
    phone: number;
    hotels: Hotel[];
}
