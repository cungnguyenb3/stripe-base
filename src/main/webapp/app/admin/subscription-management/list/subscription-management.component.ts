import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { Authority } from '../../../config/authority.constants';
import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { ASC, DESC, SORT } from 'app/config/navigation.constants';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { ISubscription } from 'app/entities/subscription/subscription.model';
import { SubscriptionService } from 'app/entities/subscription/subscription.service';
import { UserManagementUpdateDialogComponent } from 'app/admin/user-management/update/user-management-update-dialog.component';
import { UserManagementDeleteDialogComponent } from 'app/admin/user-management/delete/user-management-delete-dialog.component';
import { UserManagementDetailDialogComponent } from 'app/admin/user-management/detail/user-management-detail-dialog.component';

@Component({
  selector: 'jhi-subscription-mgmt',
  templateUrl: './subscription-management.component.html',
})
export class SubscriptionManagementComponent implements OnInit {
  currentAccount: Account | null = null;
  subscriptions: ISubscription[] | null = null;
  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;

  constructor(
    private subscriptionService: SubscriptionService,
    private accountService: AccountService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.handleNavigation();
  }

  loadAll(): void {
    this.isLoading = true;
    this.subscriptionService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe({
        next: (res: HttpResponse<ISubscription[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers);
        },
        error: () => (this.isLoading = false),
      });
  }

  openUpdateModal(user: ISubscription | null): void {
    const modal = this.modalService.open(UserManagementUpdateDialogComponent, { size: 'lg', backdrop: 'static' });
    modal.componentInstance.subscription = user;
    modal.result.then(result => {
      if (result === true) {
        this.loadAll();
      }
    });
  }

  deleteUser(user: ISubscription): void {
    const modalRef = this.modalService.open(UserManagementDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.user = user;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }

  cancelSubscription(subscription: ISubscription): void {
    this.subscriptionService.cancelSubscription(subscription.id).subscribe(() => {
      this.loadAll();
    });
  }

  downloadInvoice(subscription: ISubscription): void {
    this.subscriptionService.downloadInvoice(subscription.id).subscribe(res => {
      const fileUrl = res.body.downloadUrl;
      window.location.href = fileUrl;
    });
  }

  openDetailDialog(user: ISubscription): void {
    const modal = this.modalService.open(UserManagementDetailDialogComponent, { size: 'lg', backdrop: 'static' });
    modal.componentInstance.userInput = user;
  }

  transition(): void {
    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute.parent,
      queryParams: {
        page: this.page,
        sort: `${this.predicate},${this.ascending ? ASC : DESC}`,
      },
    });
  }

  private handleNavigation(): void {
    combineLatest([this.activatedRoute.data, this.activatedRoute.queryParamMap]).subscribe(([data, params]) => {
      const page = params.get('page');
      this.page = +(page ?? 1);
      const sort = (params.get(SORT) ?? data['defaultSort']).split(',');
      this.predicate = sort[0];
      this.ascending = sort[1] === ASC;
      this.loadAll();
    });
  }

  private sort(): string[] {
    const result = [`${this.predicate},${this.ascending ? ASC : DESC}`];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  private onSuccess(users: ISubscription[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.subscriptions = users;
  }
}
