import { PaymentMethodService } from '../../entities/payment-method/payment-method.service';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ASC, DESC, SORT } from 'app/config/navigation.constants';
import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { IPaymentMethod } from 'app/entities/payment-method/payment-method.model';
import { combineLatest } from 'rxjs';
import { IUser } from '../user-management/user-management.model';
import { CustomerService } from 'app/entities/customer/customer.service';
import { ICustomer } from 'app/entities/customer/customer.model';
import { isPresent } from 'app/core/util/operators';

@Component({
  selector: 'jhi-customer-update',
  templateUrl: './customer-update.component.html',
})
export class CustomerUpdateComponent implements OnInit {
  customerId: string | null = null;
  paymentMethods: IPaymentMethod[] | null = null;
  totalItems = 0;
  isLoading = false;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  customer: ICustomer | null = null;

  constructor(
    // public activeModal: NgbActiveModal,
    private customerService: CustomerService,
    private paymentMethodService: PaymentMethodService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.customerId = this.activatedRoute.snapshot.paramMap.get('customerId');

    this.handleNavigation();
  }

  loadAll(): void {
    this.isLoading = true;
    this.paymentMethodService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
        customerId: this.customerId,
      })
      .subscribe({
        next: (res: HttpResponse<IPaymentMethod[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers);
        },
        error: () => (this.isLoading = false),
      });

    if (isPresent(this.customerId)) {
      this.customerService.findById(this.customerId).subscribe({
        next: (res: HttpResponse<ICustomer>) => {
          this.isLoading = false;
          this.customer = res.body;
        },
        error: () => (this.isLoading = false),
      });
    }
  }

  createPaymentMethod(): void {
    //
  }

  // openUpdateModal(user: IPaymentMethod | null): void {
  //   const modal = this.modalService.open(UserManagementUpdateDialogComponent, { size: 'lg', backdrop: 'static' });
  //   modal.componentInstance.subscription = user;
  //   modal.result.then(result => {
  //     if (result === true) {
  //       this.loadAll();
  //     }
  //   });
  // }

  private sort(): string[] {
    const result = [`${this.predicate},${this.ascending ? ASC : DESC}`];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
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

  private onSuccess(paymentMethods: IPaymentMethod[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.paymentMethods = paymentMethods;
  }
}
