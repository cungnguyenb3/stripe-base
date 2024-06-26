import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { ASC, DESC, SORT } from 'app/config/navigation.constants';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { UserManagementService } from '../service/user-management.service';
import { User } from '../user-management.model';
import { UserManagementDeleteDialogComponent } from '../delete/user-management-delete-dialog.component';
import { UserManagementUpdateDialogComponent } from '../update/user-management-update-dialog.component';
import { UserManagementDetailDialogComponent } from '../detail/user-management-detail-dialog.component';
import { Authority } from '../../../config/authority.constants';
import { CustomerService } from 'app/entities/customer/customer.service';
import { Customer, ICustomer } from 'app/entities/customer/customer.model';

@Component({
  selector: 'jhi-user-mgmt',
  templateUrl: './user-management.component.html',
})
export class UserManagementComponent implements OnInit {
  currentAccount: Account | null = null;
  customers: ICustomer[] | null = null;
  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  protected readonly Authority = Authority;

  constructor(
    private userService: UserManagementService,
    private customerService: CustomerService,
    private accountService: AccountService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
    this.handleNavigation();
  }

  setActive(user: User, isActivated: boolean): void {
    this.userService.update({ ...user, activated: isActivated }).subscribe(() => this.loadAll());
  }

  trackIdentity(_index: number, item: User): number {
    return item.id!;
  }

  deleteUser(user: ICustomer): void {
    const modalRef = this.modalService.open(UserManagementDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.user = user;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }

  loadAll(): void {
    this.isLoading = true;
    this.customerService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe({
        next: (res: HttpResponse<ICustomer[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers);
        },
        error: () => (this.isLoading = false),
      });
  }

  directToDetail(customer: ICustomer): void {
    this.router.navigate(['/admin/customer-update', customer.id]);
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

  openUpdateModal(user: ICustomer | null): void {
    const modal = this.modalService.open(UserManagementUpdateDialogComponent, { size: 'lg', backdrop: 'static' });
    modal.componentInstance.userInput = user;
    modal.result.then(result => {
      if (result === true) {
        this.loadAll();
      }
    });
  }

  openDetailDialog(user: ICustomer): void {
    const modal = this.modalService.open(UserManagementDetailDialogComponent, { size: 'lg', backdrop: 'static' });
    modal.componentInstance.userInput = user;
  }

  isCurrentLoggedUser(user: ICustomer): boolean {
    // return !this.currentAccount || this.currentAccount.login === user.login;
    return true;
  }

  isModified(user: ICustomer): boolean {
    // if (user.authorities?.includes(Authority.ADMIN) && !this.currentAccount?.authorities.includes(Authority.ADMIN)) {
    //   return false;
    // }
    return true;
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

  private onSuccess(users: ICustomer[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.customers = users;
  }
}
