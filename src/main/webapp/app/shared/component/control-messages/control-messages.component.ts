import { Component, Input } from '@angular/core';
import { FormControl } from '@angular/forms';
import { ValidationService } from './validation.service';

@Component({
  selector: 'control-messages',
  template: `
    <div *ngIf="errorMessage !== null">
      <small class="form-text text-danger text-danger-override">
        {{ errorMessage }}
      </small>
    </div>
  `,
})
export class ControlMessagesComponent {
  @Input() control: FormControl | undefined;

  get errorMessage(): null | any {
    if (this.control) {
      for (const propertyName in this.control.errors) {
        if (Object.prototype.hasOwnProperty.call(this.control.errors, propertyName) && this.control.touched) {
          return ValidationService.getValidatorErrorMessage(propertyName, this.control.errors[propertyName]);
        }
      }
    }

    return null;
  }
}
