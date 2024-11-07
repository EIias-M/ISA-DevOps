import {
  Body,
  Controller,
  Get,
  HttpException,
  HttpStatus,
  Post,
} from '@nestjs/common';

import { AppService } from './app.service';
import { ValidationRequestDto } from './dto/ValidationRequest.dto';
import { ValidationReceiptDto } from './dto/ValidationReceipt.dto';

@Controller('apischeduling')
export class AppController {
  constructor(private readonly appService: AppService) {}

  @Get()
  getAllTransactions(): ValidationReceiptDto[] {
    return this.appService.findAll();
  }

  @Post()
  validate(
    @Body() validationRequestDto: ValidationRequestDto,
  ): ValidationReceiptDto {
    try {
      return this.appService.validate(validationRequestDto);
    } catch (e) {
      throw new HttpException(
        'business error: ' + e.message,
        HttpStatus.BAD_REQUEST,
      );
    }
  }
}
