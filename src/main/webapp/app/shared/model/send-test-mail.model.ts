import { IUser } from 'app/admin/user-management/user-management.model';
import { IEmailTemplate } from 'app/shared/model/email-template.model';

export interface ISendTestMail {
  emailTemplateEventName: string;
  recipientType: string;
  emailTemplate: IEmailTemplate;
  recipientUser: IUser;
  timeZone?: string;
}

export class SendTestMail implements ISendTestMail {
  emailTemplateEventName: string;
  recipientType: string;
  emailTemplate: IEmailTemplate;
  recipientUser: IUser;

  constructor(user: IUser, emailTemplate: IEmailTemplate) {
    this.recipientUser = user;
    this.recipientType = emailTemplate.recipientType;
    this.emailTemplate = emailTemplate;
    this.emailTemplateEventName = emailTemplate.emailTemplateEvent;
  }
}
