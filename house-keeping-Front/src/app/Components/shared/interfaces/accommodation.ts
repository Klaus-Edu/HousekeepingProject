import { ExtraServices } from "./extra-services";
import { Reservation } from "./reservation";

export interface Accommodation {
    id: number;
    name: string;
    observation: string;
    cleaningStatus: string;
    occupationStatus: string;
    reservation: Reservation[];
    extraServices: ExtraServices;
    activeReservation: Reservation | null;
}
