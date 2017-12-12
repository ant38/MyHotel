import { Hotel } from "./hotel";
import { User } from "./user";
export class UserComment {
    Image: SVGImageElement;
    Rating: number;
    Comment: string;
    Client: User;
    Hotel: Hotel;
}