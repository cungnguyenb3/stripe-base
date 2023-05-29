import { Component, OnDestroy } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmailTemplate } from 'app/shared/model/email-template.model';
import { EmailTemplateService } from '../service/email-template.service';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'jhi-email-template-delete',
  templateUrl: './email-template-delete.component.html',
})
export class EmailTemplateDeleteComponent implements OnDestroy {
  emailTemplate?: IEmailTemplate;
  isDeleted?: boolean;
  private ngUnsubscribe = new Subject<void>();

  constructor(protected emailTemplateService: EmailTemplateService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.close();
  }

  doDelete(templateEmail: IEmailTemplate): void {
    if (templateEmail.id !== undefined) {
      this.emailTemplateService
        .delete(templateEmail.id)
        .pipe(takeUntil(this.ngUnsubscribe))
        .subscribe({
          next: () => this.activeModal.close('deleted'),
        });
    }
  }

  ngOnDestroy(): void {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }
}
