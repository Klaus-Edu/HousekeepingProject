import { Component, OnInit, ViewChild } from '@angular/core';
import { AccommodationService } from '../shared/services/accommodation.service';
import { Accommodation } from '../shared/interfaces/accommodation';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ExtraServices } from '../shared/interfaces/extra-services';
import { PartialAccommodation } from '../shared/interfaces/partial-accommodation';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  accommodations: Accommodation[] = [];
  @ViewChild('infoAccommodation') infoAccModal: any;
  atualizacaoBemSucedida: boolean = false;

  selectedAcc: Accommodation = {
    id: 0,
    name: '',
    observation: '',
    cleaningStatus: '',
    occupationStatus: '',
    reservation: [],
    extraServices: {
      id: 0,
      extraCleaning: false,
      extraBed: false,
      extraCradle: false
    },
    activeReservation: null
  };

  observationData: PartialAccommodation = {
    observation: '',
    cleaningStatus: '',
    occupationStatus: ''
  };

  extraServicesData: ExtraServices = {
    id: null,
    extraCleaning: false,
    extraBed: false,
    extraCradle: false
  };

  constructor(
    private accommodationService: AccommodationService,
    private modalService: NgbModal
  ) { }

  ngOnInit(): void {
    this.loadAccommodations();
  }

  private loadAccommodations(): void {
    this.accommodationService.getAccommodations().subscribe((response) => {
      this.accommodations = response;
      this.accommodations.sort((a, b) => a.id - b.id);
      this.accommodations.forEach((accommodation) => {
        accommodation.reservation.forEach(reservation => {
          if (reservation.isActive == true) {
            accommodation.activeReservation = reservation;
            return;
          }
        });
      })
    })
  }

  public onEditExtraServices(accommodation: Accommodation, id: number) {
    this.observationData.observation = accommodation.observation;
    this.observationData.cleaningStatus = accommodation.cleaningStatus;
    this.observationData.occupationStatus = accommodation.occupationStatus;

    this.extraServicesData.id = accommodation.extraServices.id;
    this.extraServicesData.extraCleaning = accommodation.extraServices.extraCleaning;
    this.extraServicesData.extraBed = accommodation.extraServices.extraBed;
    this.extraServicesData.extraCradle = accommodation.extraServices.extraCradle

    this.updateAccommodation(id);
    this.modalService.dismissAll();
  }

  private updateAccommodation(id: number): void {
    this.accommodationService.patchObservation(this.observationData, id).subscribe(
      response => this.handleHttpSuccess(response),
      error => this.handleHttpError('patchObservation', error)
    );

    this.accommodationService.patchExtraServices(this.extraServicesData, id).subscribe(
      response => this.handleHttpSuccess(response),
      error => this.handleHttpError('patchExtraServices', error)
    );
  }

  openInfoModal(accommodation: Accommodation) {
    this.selectedAcc = accommodation;
    this.modalService.open(this.infoAccModal, { size: 'lg' });
  }

  getAccommodationStatus(occupationStatus: string) {
    const statusMap: { [key: string]: string } = {
      DESOCUPADO: 'vacatedClass',
      OCUPADO: 'occupiedClass',
      MANUTENCAO: 'maintenanceClass',
    };

    const statusClass = statusMap[occupationStatus];

    if (!statusClass) {
      throw new Error('Status de ocupação indefinido: ' + occupationStatus);
    }

    return statusClass;
  }

  getOccupationStatusStyle(): any {
    let color = '';

    switch (this.selectedAcc!.occupationStatus) {
      case 'DESOCUPADO':
        color = '#35AE56';
        break;
      case 'OCUPADO':
        color = '#EB3D45';
        break;
      case 'MANUTENCAO':
        color = '#EBC859';
        break;
    }

    return { 'border': "2px solid " + color };
  }

  getCleaningStatus(cleaningStatus: string) {
    const statusMap: { [key: string]: string } = {
      LIMPO: 'cleanClass',
      SUJO: 'dirtyClass',
    };

    const statusClass = statusMap[cleaningStatus];

    if (!statusClass) {
      throw new Error('Status de limpeza indefinido: ' + cleaningStatus);
    }

    return statusClass;
  }

  getHasPetStatus(hasPet?: boolean) {
    return hasPet === true ? 'truePetClass' : 'falsePetClass';
  }

  private handleHttpSuccess(response: any): void {
    this.atualizacaoBemSucedida = true;
    setTimeout(() => {
      this.atualizacaoBemSucedida = false;
    }, 5000);
  }

  private handleHttpError(apiMethod: string, error: any): void {
    console.error(`Erro ao fazer a solicitação HTTP para ${apiMethod}:`, error);
  }
}

