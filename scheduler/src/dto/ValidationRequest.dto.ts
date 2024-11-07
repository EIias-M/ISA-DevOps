import { IsNotEmpty, IsPositive, IsString } from 'class-validator';

export class ValidationRequestDto {
  @IsNotEmpty()
  @IsString()
  date: string;
}
