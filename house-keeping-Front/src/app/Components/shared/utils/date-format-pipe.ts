import { formatDate } from '@angular/common';
import { Pipe, PipeTransform, LOCALE_ID, Inject } from '@angular/core';

@Pipe({
  name: 'dateFormat'
})
export class DateFormatPipe implements PipeTransform {

  constructor(@Inject(LOCALE_ID) private locale: string) {}

  transform(value: any, format: string = 'dd/MM/yyyy'): any {
    // Divide em (dia, mês e ano) pois a entrada é entrada é "dd-MM-yyyy"
    const parts = value.split('-');

    // formata para "yyyy-MM-dd"
    const formattedDate = `${parts[2]}-${parts[1]}-${parts[0]}`;

    return formatDate(formattedDate, format, this.locale);
  }
}
