import { Injectable } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidationErrors } from '@angular/forms';

@Injectable({ providedIn: 'root' })
export class ValidationService {
  static getValidatorErrorMessage(validatorName: string, validatorValue?: any): any {
    const config = {
      required: 'This field is required.',
      invalidEmailAddress: 'Invalid email address.',
      invalidPassword: 'Invalid password. Password must be within 4-50 non white space characters and at least one number',
      minlength: `This field is required to be at least ${validatorValue.requiredLength} characters.`,
      maxlength: `This field cannot be longer than ${validatorValue.requiredLength} characters.`,
      min: `This field should be at least ${validatorValue.min}.`,
      max: `This field cannot be more than ${validatorValue.max}.`,
      email: `Please enter a valid email address. For example abc@domain.com.`,
      invalidZip: 'Invalid zip code. Input 5 or 9 digit number.',
      invalidColor: 'Invalid color.',
      pattern: 'Invalid character(s).',
    };
    return config[validatorName as keyof typeof config];
  }

  static emailValidator(control: FormControl): any {
    // RFC 2822 compliant regex
    if (
      control.value.match(
        /[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/
      )
    ) {
      return null;
    } else {
      return { email: true };
    }
  }

  static passwordValidator(control: FormControl): any {
    // {6,100}           - Assert password is between 6 and 100 characters
    // (?=.*[0-9])       - Assert a string has at least one number
    if (control.value && control.value.trim()) {
      if (control.value.match(/^(?=.*[0-9])[a-zA-Z0-9!@#$%^&*]{4,50}$/)) {
        return null;
      } else {
        return { invalidPassword: true };
      }
    }
  }
}

/*
  check formValidity before save data
*/
export function checkFormInvalidForm(formdata: FormGroup, document: Document): boolean {
  const listControl = Object.keys(formdata.controls);
  for (let i = 0; i < listControl.length; i++) {
    const control: AbstractControl = formdata.controls[listControl[i]];
    const controlErr: ValidationErrors | null = control.errors;
    if (controlErr != null) {
      const element = document.getElementById(listControl[i]);
      if (element) {
        return false;
      }
    }
  }
  return true;
}
