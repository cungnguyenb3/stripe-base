import { Directive, Output, Input, EventEmitter, HostBinding, HostListener, Renderer2, ElementRef } from '@angular/core';

@Directive({
  selector: '[jhiDragDrop]',
})
export class DragDropDirective {
  @Output() onFileDropped = new EventEmitter<any>();
  renderer: Renderer2;
  elementRef: ElementRef;

  constructor(renderer: Renderer2, el: ElementRef) {
    this.renderer = renderer;
    this.elementRef = el;
  }

  @HostListener('dragover', ['$event']) onDragOver(evt: any) {
    evt.preventDefault();
    evt.stopPropagation();
    this.renderer.addClass(this.elementRef.nativeElement, 'dragover');
  }

  @HostListener('dragleave', ['$event']) public onDragLeave(evt: any) {
    evt.preventDefault();
    evt.stopPropagation();
    this.renderer.removeClass(this.elementRef.nativeElement, 'dragover');
  }

  @HostListener('drop', ['$event']) public ondrop(evt: any) {
    evt.preventDefault();
    evt.stopPropagation();
    this.renderer.removeClass(this.elementRef.nativeElement, 'dragover');
    const files = evt.dataTransfer.files;
    if (files.length > 0) {
      this.onFileDropped.emit(files);
    }
  }
}
