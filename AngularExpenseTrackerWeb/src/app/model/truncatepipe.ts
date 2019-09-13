import { Pipe, PipeTransform } from '@angular/core';
@Pipe({
   name: 'truncate'
})
export class TruncatePipe implements PipeTransform {
   
    transform(value: string, len?: number):any {
       
       //si no hay argumento salir y regresar el valor original
       if (!len || value == null || value == undefined ) 
          return value;
       
       // if both are provided, then return string between those
       return value.substring(0, len);
   }
    
}