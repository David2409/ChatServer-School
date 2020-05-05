import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { AppPartLoginComponent } from './app-part-login/app-part-login.component';

@NgModule({
  declarations: [
    AppComponent,
    AppPartLoginComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
