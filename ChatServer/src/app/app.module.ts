import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { AppPartLoginComponent } from './app-part-login/app-part-login.component';
import { AppPartMainComponent } from './app-part-main/app-part-main.component';
import { AppPartServerselectionComponent } from './app-part-serverselection/app-part-serverselection.component';
import { AppPartRoomselectionComponent } from './app-part-roomselection/app-part-roomselection.component';
import { AppPartServerselectionItemComponent } from './app-part-serverselection-item/app-part-serverselection-item.component';
import { AppPartRoomselectionItemComponent } from './app-part-roomselection-item/app-part-roomselection-item.component';
import { AppPartMessageComponent } from './app-part-message/app-part-message.component';
import { AppPartMessageListComponent } from './app-part-message-list/app-part-message-list.component';
import { AppPartMessageInputComponent } from './app-part-message-input/app-part-message-input.component';
import { AppPartMessageMainComponent } from './app-part-message-main/app-part-message-main.component';
import { AppPartDialogCreateServerComponent } from './app-part-dialog-create-server/app-part-dialog-create-server.component';
import { AppPartDialogCreateRoomComponent } from './app-part-dialog-create-room/app-part-dialog-create-room.component';
import { AppPartDialogInviteComponent } from './app-part-dialog-invite/app-part-dialog-invite.component';
import { AppPartDialogModifyServerComponent } from './app-part-dialog-modify-server/app-part-dialog-modify-server.component';
import { AppPartDialogModifyRoomComponent } from './app-part-dialog-modify-room/app-part-dialog-modify-room.component';
import { MatDialogModule } from '@angular/material/dialog';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  declarations: [
    AppComponent,
    AppPartLoginComponent,
    AppPartMainComponent,
    AppPartServerselectionComponent,
    AppPartRoomselectionComponent,
    AppPartServerselectionItemComponent,
    AppPartRoomselectionItemComponent,
    AppPartMessageComponent,
    AppPartMessageListComponent,
    AppPartMessageInputComponent,
    AppPartMessageMainComponent,
    AppPartDialogCreateServerComponent,
    AppPartDialogCreateRoomComponent,
    AppPartDialogInviteComponent,
    AppPartDialogModifyServerComponent,
    AppPartDialogModifyRoomComponent
  ],
  imports: [
    BrowserModule,
    MatDialogModule,
    BrowserAnimationsModule
  ],
  entryComponents: [
    AppPartDialogCreateServerComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
