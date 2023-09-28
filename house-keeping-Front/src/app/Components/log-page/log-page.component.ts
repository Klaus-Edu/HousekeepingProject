import { Component, OnInit } from '@angular/core';
import { ReservationService } from '../shared/services/reservation.service';
import { Reservation } from '../shared/interfaces/reservation';
import { AccommodationService } from '../shared/services/accommodation.service';
import { Accommodation } from '../shared/interfaces/accommodation';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-log-page',
  templateUrl: './log-page.component.html',
  styleUrls: ['./log-page.component.css']
})
export class LogPageComponent implements OnInit {

  inactiveReservations: Reservation[] = [];
  accommodationNames = new Map<number, string>();
  filteredReservations: Reservation[] = [];

  constructor(private reservationService: ReservationService,
    private accommodationService: AccommodationService) { }

  ngOnInit(): void {
    this.loadInactiveReservations();
  }

  private loadInactiveReservations(): void {
    forkJoin([
      this.reservationService.getReservations(),
      this.accommodationService.getAccommodations()
    ]).subscribe(([reservations, accommodations]) => {
      // filtra reservas inativas
      this.inactiveReservations = reservations.filter((reservation) => !reservation.isActive);

      // mapeia os nomes das acomodações
      accommodations.forEach((accommodation) => {
        this.accommodationNames.set(accommodation.id, accommodation.name);
      });

      // Inicializa o filteredReservations para mostrar dados ao entrar na pagina
      this.filteredReservations = this.inactiveReservations;
    });
  }

  onSearchChange(searchEvent: any) {
    const searchTerm = searchEvent.target.value.toLowerCase();

    // Filtra baseado no input de pesquisa
    this.filteredReservations = this.inactiveReservations.filter((reservation) => {
      return (
        reservation.holderName.toLowerCase().includes(searchTerm) ||
        reservation.checkin.toLowerCase().includes(searchTerm) ||
        reservation.checkout.toLowerCase().includes(searchTerm)
      );
    });
  }

  public hasPetParse(hasPet: boolean) {
    return hasPet ? 'Sim' : 'Não';
  }
}
