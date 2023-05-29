import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
  selector: '[jhiTrimText]',
})
export class TrimTextDirective {
  constructor(private element: ElementRef) {}

  @HostListener('blur')
  onblur(): any {
    if (this.element.nativeElement.value) {
      this.element.nativeElement.value = this.element.nativeElement.value.trim();
      this.element.nativeElement.dispatchEvent(new Event('input'));
    }
  }
}
