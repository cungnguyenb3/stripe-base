import { Component, ViewChild, OnInit, AfterViewInit, ElementRef } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';

import { LoginService } from 'app/login/login.service';
import { AccountService } from 'app/core/auth/account.service';
import { Login } from './login.model';
import { checkFormInvalidForm } from '../shared/component/control-messages/validation.service';

@Component({
  selector: 'jhi-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit, AfterViewInit {
  @ViewChild('username', { static: false })
  username!: ElementRef;

  authenticationError = false;

  loginForm = this.fb.group({
    username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(254), Validators.email]],
    password: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]],
    rememberMe: [false, [Validators.required]],
  });

  constructor(
    private accountService: AccountService,
    private loginService: LoginService,
    private router: Router,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    // if already authenticated then navigate to home page
    this.accountService.identity().subscribe(() => {
      if (this.accountService.isAuthenticated()) {
        this.router.navigate(['']);
      }
    });
  }

  get formControl(): any {
    return this.loginForm.controls;
  }

  getValueFormControl(formName: string): any {
    return this.loginForm.get([formName])?.value;
  }

  ngAfterViewInit(): void {
    this.username.nativeElement.focus();
  }

  login(): void {
    this.loginService.login(this.createFromForm()).subscribe({
      next: account => {
        this.authenticationError = false;
        this.loginService.redirectToLandingPage(account!);
      },
      error: () => (this.authenticationError = true),
    });
  }

  checkFormInvalidForm(): boolean {
    return !checkFormInvalidForm(this.loginForm, document);
  }

  private createFromForm(): Login {
    return {
      username: this.getValueFormControl('username'),
      password: this.getValueFormControl('password'),
      rememberMe: this.getValueFormControl('rememberMe'),
    };
  }
}
