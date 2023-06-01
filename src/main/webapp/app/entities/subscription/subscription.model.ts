export interface ISubscription {
  id: string;
  defaultPaymentMethod?: string;
  description?: string;
  status?: string;
  paymentMethodId?: string;
}

export class Subscription implements ISubscription {
  constructor(
    public id: string,
    public defaultPaymentMethod: string,
    public description: string,
    public status: string,
    public paymentMethodId: string
  ) {}
}
