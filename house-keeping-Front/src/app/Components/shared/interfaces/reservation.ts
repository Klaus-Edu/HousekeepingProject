export interface Reservation {
    id: number;
    holderName: string;
    adults: number;
    child: number;
    babies: number;
    hasPet: boolean;
    checkin: string;
    checkout: string;
    isActive: boolean;
    accommodationIdReference: number
  }