import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { UserListComponent } from './user-list/user-list.component';
import { UserService } from './service/user.service';
import { HomepageComponent } from './homepage/homepage.component';
import { ChatItemComponent } from './chat-item/chat-item.component';
import { MessageComponent } from './message/message.component';
import { SignInComponent } from './sign-in/sign-in.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { AccountInfoComponent } from './account-info/account-info.component';
import { AccountService } from './account-service/account.service';

@NgModule({
  declarations: [
    AppComponent,
    UserListComponent,
    HomepageComponent,
    ChatItemComponent,
    MessageComponent,
    SignInComponent,
    SignUpComponent,
    AccountInfoComponent
  ],
  imports: [
    FormsModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [UserService, AccountService],
  bootstrap: [AppComponent]
})
export class AppModule { }
