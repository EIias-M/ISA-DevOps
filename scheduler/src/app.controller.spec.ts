import { Test, TestingModule } from '@nestjs/testing';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { ValidationRequestDto } from './dto/ValidationRequest.dto';
import { HttpException } from '@nestjs/common';

describe('AppController', () => {
  let appController: AppController;

  const goodPaymentDto: ValidationRequestDto = {
    date: '2024-03-20',
  };

  const badPaymentDto: ValidationRequestDto = {
    date: '2024-03-16',
  };

  beforeEach(async () => {
    const app: TestingModule = await Test.createTestingModule({
      controllers: [AppController],
      providers: [AppService],
    }).compile();

    appController = app.get<AppController>(AppController);
  });

  describe('root', () => {
    it('should return no transactions at startup', () => {
      expect(appController.getAllTransactions().length).toBe(0);
    });
  });

  describe('payByCredit()', () => {
    it('should return a PaymentReceiptDto (generated UUID and input amount) with transaction success', () => {
      const paymentReceiptDto = appController.validate(goodPaymentDto);
      expect(paymentReceiptDto.validation).toBe(true);
      expect(appController.getAllTransactions().length).toBe(1);
    });
  });

  describe('payByCredit()', () => {
    it('should throw exception transaction failure', () => {
      expect(() => appController.validate(badPaymentDto)).toThrow(
        HttpException,
      );
      expect(appController.getAllTransactions().length).toBe(0);
    });
  });
});
