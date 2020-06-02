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
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { AppPartListUsersComponent } from './app-part-list-users/app-part-list-users.component';
import { AppPartUserComponent } from './app-part-user/app-part-user.component';
import { AppPartDialogModifyUserComponent } from './app-part-dialog-modify-user/app-part-dialog-modify-user.component';
import { AppPartServerRoleListComponent } from './app-part-server-role-list/app-part-server-role-list.component';
import { AppPartServerRoleItemComponent } from './app-part-server-role-item/app-part-server-role-item.component';
import { AppPartRoomRoleListComponent } from './app-part-room-role-list/app-part-room-role-list.component';
import { AppPartRoomRoleItemComponent } from './app-part-room-role-item/app-part-room-role-item.component';
import { AppPartUserRoleItemComponent } from './app-part-user-role-item/app-part-user-role-item.component';
import { AppPartUserRoleListComponent } from './app-part-user-role-list/app-part-user-role-list.component';

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
    AppPartDialogModifyRoomComponent,
    AppPartListUsersComponent,
    AppPartUserComponent,
    AppPartDialogModifyUserComponent,
    AppPartServerRoleListComponent,
    AppPartServerRoleItemComponent,
    AppPartRoomRoleListComponent,
    AppPartRoomRoleItemComponent,
    AppPartUserRoleItemComponent,
    AppPartUserRoleListComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    FormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule
  ],
  entryComponents: [
    AppPartDialogCreateServerComponent,
    AppPartDialogCreateRoomComponent,
    AppPartDialogInviteComponent,
    AppPartDialogModifyRoomComponent,
    AppPartDialogModifyServerComponent
  ],
  providers: [
  ],
  bootstrap: [AppComponent],
})
export class AppModule { }
