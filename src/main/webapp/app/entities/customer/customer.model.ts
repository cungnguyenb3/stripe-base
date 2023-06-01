export interface ICustomer {
  id: string;
  name?: string;
  description?: string;
  email?: string;
  phone?: string;
}

export class Customer implements ICustomer {
  constructor(public id: string, public name: string, public description: string, public email: string, public phone: string) {}
}
