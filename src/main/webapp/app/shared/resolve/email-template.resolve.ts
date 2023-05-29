import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { EmailTemplateService } from 'app/admin/email-template/service/email-template.service';
import { EmailTemplate, IEmailTemplate } from '../model/email-template.model';

import { Observable, of } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class EmailTemplateResolve implements Resolve<IEmailTemplate> {
  constructor(private service: EmailTemplateService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEmailTemplate> {
    return of(new EmailTemplate());
  }
}
