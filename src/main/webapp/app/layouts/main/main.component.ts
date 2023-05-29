import { Component, OnInit, RendererFactory2, Renderer2 } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRouteSnapshot, Data, NavigationEnd, Router } from '@angular/router';
import { TranslateService, LangChangeEvent } from '@ngx-translate/core';
import dayjs from 'dayjs/esm';

import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss'],
})
export class MainComponent implements OnInit {
  private renderer: Renderer2;
  showNavigationBar = true;
  showFooter = true;
  constructor(
    private accountService: AccountService,
    private titleService: Title,
    private router: Router,
    private translateService: TranslateService,
    rootRenderer: RendererFactory2
  ) {
    this.renderer = rootRenderer.createRenderer(document.querySelector('html'), null);
  }

  ngOnInit(): void {
    // try to log in automatically
    this.accountService.identity().subscribe();

    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.updateTitle();
        this.updateShowNavigationBar();
        this.updateShowFooter();
      }
    });

    this.translateService.onLangChange.subscribe((langChangeEvent: LangChangeEvent) => {
      this.updateTitle();
      dayjs.locale(langChangeEvent.lang);
      this.renderer.setAttribute(document.querySelector('html'), 'lang', langChangeEvent.lang);
    });
  }

  private getPageTitle(routeSnapshot: ActivatedRouteSnapshot): string {
    const title: string = routeSnapshot.data['pageTitle'] ?? '';
    if (routeSnapshot.firstChild) {
      return this.getPageTitle(routeSnapshot.firstChild) || title;
    }
    return title;
  }

  private updateTitle(): void {
    let pageTitle = this.getPageTitle(this.router.routerState.snapshot.root);
    if (!pageTitle) {
      pageTitle = 'global.title';
    }
    this.translateService.get(pageTitle).subscribe(title => this.titleService.setTitle(title));
  }
  // NOTE: This is a bit hacky.
  private updateShowNavigationBar(): void {
    this.showNavigationBar = this.getShowNavigationBar(this.router.routerState.snapshot.root) ?? true;
  }

  private getShowNavigationBar(snapshot: ActivatedRouteSnapshot): boolean | null {
    if (snapshot.firstChild) {
      return this.getShowNavigationBar(snapshot.firstChild) ?? dataShowNavigationBar(snapshot.data);
    } else {
      return dataShowNavigationBar(snapshot.data);
    }
  }

  private updateShowFooter(): void {
    this.showFooter = this.getShowFooter(this.router.routerState.snapshot.root) ?? true;
  }

  private getShowFooter(snapshot: ActivatedRouteSnapshot): boolean | null {
    if (snapshot.firstChild) {
      return this.getShowFooter(snapshot.firstChild) ?? dataShowFooter(snapshot.data);
    } else {
      return dataShowFooter(snapshot.data);
    }
  }
}

const dataShowNavigationBar = (data: Data): boolean | null => data['showNavigationBar'] as boolean | null;

const dataShowFooter = (data: Data): boolean | null => data['showFooter'] as boolean | null;
