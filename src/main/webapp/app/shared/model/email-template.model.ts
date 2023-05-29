export interface IEmailTemplate {
  id?: number;
  templateName: string;
  emailTemplateEvent: string;
  recipientType: string;
  subject: string;
  body: string;
  isDefault: boolean;
  activated: boolean;
  isDeleted: boolean;
}
export class EmailTemplate implements IEmailTemplate {
  public isDefault: boolean;
  public activated: boolean;
  public isDeleted: boolean;
  public templateName: string;
  public emailTemplateEvent: string;
  public recipientType: string;
  public subject: string;
  public body: string;

  constructor() {
    this.recipientType = 'CANDIDATE';
    this.templateName = '';
    this.emailTemplateEvent = '';
    this.subject = '';
    this.body = '';
    this.isDefault = true;
    this.activated = true;
    this.isDeleted = false;
  }
}
export interface IEmailTemplateSearch {
  searchTerm?: string;
  emailRecipient?: string;
}

export class EmailTemplateSearch implements IEmailTemplateSearch {
  public searchTerm?: string;
  public emailRecipient?: string;

  constructor() {
    this.searchTerm = '';
    this.emailRecipient = '';
  }
}
