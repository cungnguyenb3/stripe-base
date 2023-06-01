import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { Pagination } from 'app/core/request/request.model';
import { ISubscription } from './subscription.model';

@Injectable({ providedIn: 'root' })
export class SubscriptionService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/admin/subscriptions');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  query(req?: Pagination): Observable<HttpResponse<ISubscription[]>> {
    const options = createRequestOption(req);
    return this.http.get<ISubscription[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  update(id: string, subs: ISubscription): Observable<{}> {
    return this.http.put<{}>(`${this.resourceUrl}/${id}`, subs);
  }

  cancelSubscription(id: string): Observable<any> {
    return this.http.patch(`${this.resourceUrl}/${id}/cancel`, { observe: 'response' });
  }

  downloadInvoice(id: string): Observable<any> {
    return this.http.get(`${this.resourceUrl}/${id}/invoice/download`, { observe: 'response' });
  }
}
