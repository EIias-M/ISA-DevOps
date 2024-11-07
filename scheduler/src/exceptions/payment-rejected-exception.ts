export class ValidationRejectedException extends Error {
  constructor(date: string) {
    super(`Validation rejected as "${date}" cannot be scheduled`);
  }
}
