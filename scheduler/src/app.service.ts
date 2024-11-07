import { Injectable } from '@nestjs/common';

import { ValidationRequestDto } from './dto/ValidationRequest.dto';
import { ValidationReceiptDto } from './dto/ValidationReceipt.dto';
import { ValidationRejectedException } from './exceptions/payment-rejected-exception';

@Injectable()
export class AppService {
  private static readonly magicDate: Date = new Date('2024-03-18'); // Date limite

  private transactions: Array<ValidationReceiptDto>;

  constructor() {
    this.transactions = [];
  }

  findAll(): ValidationReceiptDto[] {
    return this.transactions;
  }

  validate(validationRequestDto: ValidationRequestDto): ValidationReceiptDto {
    let validationReceiptDto: ValidationReceiptDto;
    const requestDate: Date = new Date(validationRequestDto.date);

    if (requestDate.getTime() > AppService.magicDate.getTime()) {
      validationReceiptDto = new ValidationReceiptDto(true);

      this.transactions.push(validationReceiptDto);
      console.log(
        'Date accepté (Votre reservation a été crée aprés le 2024-03-18)',
      );
      return validationReceiptDto;
    } else {
      console.log(
        'Date rejetée (Votre reservation a été crée avant le 2024-03-18)',
      );
      throw new ValidationRejectedException(validationRequestDto.date);
    }
  }
}
