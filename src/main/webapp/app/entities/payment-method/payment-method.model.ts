export interface IPaymentMethod {
  id: string;
  type?: string;
  card?: string;
}

export class PaymentMethod implements IPaymentMethod {
  constructor(public id: string, public type: string, public card: string) {}
}
