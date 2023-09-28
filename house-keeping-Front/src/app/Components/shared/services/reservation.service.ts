import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Reservation } from '../interfaces/reservation';
import { environment } from 'src/environments/environment';
import { Observable, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private apiUrl = environment.baseApiUrl;
  private unsubscribe$ = new Subject<void>();

  constructor(private http: HttpClient) { }

  public getReservations(): Observable<Reservation[]>{
    return this.http.get<Reservation[]>(`${this.apiUrl}/reservation/get/all`).pipe(
      takeUntil(this.unsubscribe$)
    );
  }

  ngOnDestroy() {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }
}
