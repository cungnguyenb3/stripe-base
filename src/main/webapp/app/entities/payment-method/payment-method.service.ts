import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { Pagination } from 'app/core/request/request.model';
import { IPaymentMethod } from './payment-method.model';

@Injectable({ providedIn: 'root' })
export class PaymentMethodService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/admin/payment-methods');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  query(req?: any): Observable<HttpResponse<IPaymentMethod[]>> {
    const options = createRequestOption(req);
    return this.http.get<IPaymentMethod[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
}
