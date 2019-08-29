import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './component/login/login.component';
import { LogoutComponent } from '../../src/app/component/logout/logout.component';
import { RegisterComponent } from './component/register/register.component';
import { ResetPasswordComponent } from './component/reset-password/reset-password.component';
import { ForgotpasswordComponent } from './component/forgotpassword/forgotpassword.component';
import { DashboardComponent } from './component/dashboard/dashboard.component';
import { CreatenoteComponent } from './component/createnote/createnote.component';
import { IconsComponent } from './component/icons/icons.component';
import { TrashComponent } from './component/trash/trash.component';
import { ArchiveComponent } from './component/archive/archive.component';
import { LabelNotesComponent } from './component/label-notes/label-notes.component';
import { ReminderComponent } from './component/reminder/reminder.component';
import { GrideViewComponent } from './component/gride-view/gride-view.component';
import { SearchComponent } from './component/search/search.component';
const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', component: LoginComponent },
  { path: 'logout', component: LogoutComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'resetpassword', component: ForgotpasswordComponent },
  { path: 'forgotpassword', component: ResetPasswordComponent },
  { path: 'dashboard', component: DashboardComponent,
  children:[
     
    {
      path:"createnote",
      component:CreatenoteComponent,
    },
    {
      path:"grid",
      component:GrideViewComponent
    },
    
    {
      path:"",
      component:CreatenoteComponent
    },
    {
      path:"reminder",
      component:ReminderComponent
    },
    {
      path:"getTrash",
      component:TrashComponent
    },
    {
      path:"search/:searchKey",
      component:SearchComponent
    },
    {
      path:"getLabelNotes/:labelId",
      component:LabelNotesComponent
    },
    {
      path:"getArchive",
      component:ArchiveComponent
    }
  
  ] },
  { path:'icons', component: IconsComponent }
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
