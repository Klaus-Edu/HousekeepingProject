import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Accommodation } from '../interfaces/accommodation';
import { Observable, Subject } from 'rxjs';
import { PartialAccommodation } from '../interfaces/partial-accommodation';
import { ExtraServices } from '../interfaces/extra-services';
import { takeUntil } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AccommodationService {
  private apiUrl = environment.baseApiUrl;
  private unsubscribe$ = new Subject<void>();

  constructor(private http: HttpClient) { }

  public getAccommodations(): Observable<Accommodation[]>{
    return this.http.get<Accommodation[]>(`${this.apiUrl}/accommodations/get/all`).pipe(
      takeUntil(this.unsubscribe$)
      );
  }

  public getAccommodationById(id:number): Observable<Accommodation>{
    return this.http.get<Accommodation>(`${this.apiUrl}/accommodations/get/${id}`).pipe(
      takeUntil(this.unsubscribe$)
      );
  }

  public patchObservation(obs: PartialAccommodation, id: number): Observable<Accommodation>{
    return this.http.patch<Accommodation>(`${this.apiUrl}/accommodations/edit/obs/${id}`, obs).pipe(
      takeUntil(this.unsubscribe$)
      );
  }

  public patchExtraServices(extra: ExtraServices, id: number): Observable<Accommodation>{
    return this.http.patch<Accommodation>(`${this.apiUrl}/accommodations/edit/extra/${id}`, extra).pipe(
      takeUntil(this.unsubscribe$)
      );
  }

  ngOnDestroy() {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }
}
