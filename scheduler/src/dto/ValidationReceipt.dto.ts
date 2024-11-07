import { IsNotEmpty, IsPositive, IsString } from 'class-validator';

export class ValidationReceiptDto {
  constructor(validation: boolean) {
    this.validation = validation;
  }
  @IsNotEmpty()
  @IsPositive()
  validation: boolean;
}
