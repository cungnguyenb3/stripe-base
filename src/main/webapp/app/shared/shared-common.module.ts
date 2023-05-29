import { NgModule } from '@angular/core';
import { DecimalPipe, TitleCasePipe } from '@angular/common';
import { TrimTextDirective } from './directive/trim-text-directive';

@NgModule({
  imports: [],
  declarations: [TrimTextDirective],
  entryComponents: [],
  exports: [TrimTextDirective],
  providers: [DecimalPipe, TitleCasePipe],
})
export class SharedCommonModule {}
