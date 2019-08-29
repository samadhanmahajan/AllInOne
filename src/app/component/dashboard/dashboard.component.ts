import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { MatDialog } from '@angular/material';
import { Note } from 'src/app/core/model/note';
import { LabelComponent } from '../label/label.component';
import { LabelService } from 'src/app/core/service/label.service';
import { UpdateService } from 'src/app/core/service/dataService/update.service';
import { ProfilePictureComponent } from '../profile-picture/profile-picture.component';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  grid: boolean = false;
  fundooTitle = 'FundooNotes';
  noteList: any
  labelList: any
  email = localStorage.getItem("emailID");
  constructor(private router: Router, private updateService: UpdateService, private dialog: MatDialog, private labelservice: LabelService) { }

  ngOnInit() {

    this.updateService.currentMessage.subscribe(
      message => {
        this.getLabel()
      }
    )
  }

  onSearch(event: any) {
    let searchKey: any = event.target.value
    this.updateService.changeMessage("searching");
    this.router.navigate(['/dashboard/search/' + searchKey])
  }


  onLogout() {
    localStorage.clear();
    this.router.navigate(['/login']);
  }

  gridList() {
    this.grid = !this.grid;
    if (this.grid) {
      this.router.navigate(['/dashboard/grid/']);
    } else {
      this.router.navigate(['/dashboard/createnote/']);
    }
  }

  getNotes() {
    if (this.grid) {
      this.router.navigate(['/dashboard/grid/']);
    } else {
      this.router.navigate(['/dashboard/createnote/']);
    }
  }

  showLabelNotes(item) {
    this.updateService.changeMessage("update label");
    this.router.navigate(['/dashboard/getLabelNotes/' + item.labelId])
  }

  onArchive() {
    this.router.navigate(['/dashboard/getArchive'])
  }

  onDelete() {
    this.router.navigate(['/dashboard/getTrash']);
  }

  editLabel() {
    const ref = this.dialog.open(LabelComponent, {

      width: "280px",
    })
  }
  getLabel() {
    this.labelservice.getRequest("Label/GetLabels").subscribe(
      (data: any): any => {
        this.labelList = data
      }
    )
  }
  onReminder() {
    this.router.navigate(['/dashboard/reminder']);
  }

  profileDialog(): void {
    const dialogRef = this.dialog.open(ProfilePictureComponent, {
      width: '70%', height: '85%'
    });
    dialogRef.afterClosed().subscribe(result => {

    });

  }
  onRefresh() { }
}