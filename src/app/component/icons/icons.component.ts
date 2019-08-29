import { Component, OnInit, Input } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NoteService } from 'src/app/core/service/note.service';
import { LabelService } from 'src/app/core/service/label.service';
import { MatDialog } from '@angular/material/dialog';
import { ColaboratorComponent } from '../colaborator/colaborator.component';
import { UpdateService } from 'src/app/core/service/dataService/update.service';
//import {MatDatepickerModule} from '@angular/material/datepicker';

@Component({
  selector: 'app-icons',
  templateUrl: './icons.component.html',
  styleUrls: ['./icons.component.scss']
})
export class IconsComponent implements OnInit {

  constructor(private dialog: MatDialog, private snackbar: MatSnackBar, private updateService: UpdateService, private noteservice: NoteService, private labelService: LabelService) { }
  labelsList: any;
  noteLabelsList: any;
  collaborators: any
  noteList: any
  date:any
  @Input() noteInfo: any

  ngOnInit() {
    this.updateService.currentMessage.subscribe(
      message => {
        this.getCollabrators();
        this.getLabels();
        this.getLabelsOfNote();
        this.getNote();
      }
    )

    console.log("note",this.noteInfo)

  }

  colors = [
    [
      { colorName: "white", colorCode: '#FFFFFF' },
      { colorName: 'green', colorCode: '#008000' },
      { colorName: 'grey', colorCode: '#808080' }
    ],
    [
      { colorName: 'indian red', colorCode: '#CD5C5C' },
      { colorName: 'crimson', colorCode: '#DC143C' },
      { colorName: 'yellow', colorCode: '#FFFF00' }
    ],
    [
      { colorName: 'Purple', colorCode: '#800080' },
      { colorName: 'Teal', colorCode: '#008080' },
      { colorName: 'light blue', colorCode: '#ADD8E6' }
    ]
  ]
 

  getNote() {
    this.noteservice.getAllNotes("Note/GetAllReminderNotes").subscribe(
      data => {
        this.noteList = data;

      }
    )
  }
  getCollabrators() {

    this.labelService.getRequest("Note/GetAllCollaboratedUsers?noteID=" + this.noteInfo.noteId).subscribe(
      (data: any): any => {
        this.collaborators = data;

      }
    )

  }
  onTrash() {

    this.noteservice.trashUnTrashNote("Note/TrashUntrsh?noteID=" + this.noteInfo.noteId).subscribe(
      (response: any): any => {
        if (response.statusCode == 200) {
          this.updateService.changeMessage("notetrash");
          this.snackbar.open("note is trashed", "close", { duration: 2500 })
        }
        else {
          this.snackbar.open("note is untrashed", "close", { duration: 2500 })
        }
      }
    )
  }

  onArchive() {
    this.noteservice.ArchievedUnarchived("Note/ArchievedUnarchived?noteID=" + this.noteInfo.noteId).subscribe(
      (response: any): any => {
        if (response.statusCode == 200) {
          this.updateService.changeMessage("notearchive");
          this.snackbar.open(response.statusMessage, "close", { duration: 2500 })
        }
      }
    )

  }

  getLabels() {
    this.labelService.getRequest("Label/GetLabels").subscribe(
      (data: any): any => {
        this.labelsList = data;
        
      }
    )
  }
  addLabeltoNote(label: any) {
    
    this.labelService.getRequest("Label/addLabelToNote?labelID=" + label.labelId + "&noteID=" + this.noteInfo.noteId).subscribe(
      (respose: any): any => {
        
        if (respose.statusCode == 200) {
          this.updateService.changeMessage("labelAdded");
          this.snackbar.open("label added to note ", "close", { duration: 2500 });
        }
        else {
          this.snackbar.open("please check fields...", "close", { duration: 2500 });
        }
      }
    )
  }

  onReminder(){
    console.log("date "+this.date)
    this.labelService.getRequest("Note/SetRemainderToNote?noteID=" + this.noteInfo.noteId + "&time=" + this.date).subscribe(
      (respose: any): any => {
        console.log(respose)
        if (respose.statusCode == 200) {
          this.updateService.changeMessage("ReminderAdded");
          this.snackbar.open("reminder added to note ", "close", { duration: 2500 });
        }
        else {
          this.snackbar.open("please check fields...", "close", { duration: 2500 });
        }
      }
    )
  }
  
  addReminderToNote(time) {
    console.log(time);
    console.log("reminder "+this.noteInfo.remainder);
    this.labelService.getRequest("Note/SetRemainderToNote?noteID=" + this.noteInfo.noteId + "&time=" + time).subscribe(
      (respose: any): any => {
        console.log(respose)
        if (respose.statusCode == 200) {
          this.updateService.changeMessage("ReminderAdded");
          this.snackbar.open("reminder added to note ", "close", { duration: 2500 });
        }
        else {
          this.snackbar.open("please check fields...", "close", { duration: 2500 });
        }
      }
    )
  }


  getLabelsOfNote() {
    this.labelService.getRequest("Label/GetLabelsOfNote?noteID=" + this.noteInfo.noteId).subscribe(
      (data: any): any => {
        console.log("noteLabels  ==>" + data);
        this.noteLabelsList = data;
        console.log("noteLabelsList  ==>" + this.noteLabelsList);

      }
    )
  }
  removeLabel(labels: any) {
    console.log(labels.labelId)
    this.labelService.getRequest("Label/RemoveLabelFromNote?labelID=" + labels.labelId + "&noteID=" + this.noteInfo.noteId).subscribe(
      (response: any): any => {
        if (response.statusCode == 200) {
          this.updateService.changeMessage("Remove Label");
          this.snackbar.open("label removed from note", "close", { duration: 2500 });
        }
        else {
          this.snackbar.open("Please check fields....", "close", { duration: 2500 });
        }
      }
    )
  }
  removeReminder() {
    this.labelService.getRequest("Note/RemoveReminderFromNote?noteID=" + this.noteInfo.noteId).subscribe(
      (response: any): any => {
        if (response.statusCode == 200) {
          
          this.updateService.changeMessage("reminder Remove");
          this.snackbar.open("reminder removed from note", "close", { duration: 2500 });
        }
        else {
          this.snackbar.open("Please check fields....", "close", { duration: 2500 });
        }
      }
    )
  }
  removeCollab(item: any) {
    console.log(item.emailId)
    this.labelService.getRequest("Note/CollaboratorRemoveFromNote?collaboratorEmail=" + item.emailId + "&noteID=" + this.noteInfo.noteId).subscribe(
      (response: any): any => {
        if (response.statusCode == 200) {
          this.updateService.changeMessage("reminder collaborator");
          this.snackbar.open("Collaborator removed from note", "close", { duration: 2500 });
        }
        else {
          this.snackbar.open("Please check fields....", "close", { duration: 2500 });
        }
      }
    )
  }
  openDialog() {
    const ref = this.dialog.open(ColaboratorComponent, {

      width: "312px",
      height: "100px",

      data: {
        noteId: this.noteInfo.noteId

      }
    })
  }

  addReminder() {
    const ref = this.dialog.open(ColaboratorComponent, {

      width: "458px",
      height: "259px",

      data: {
        noteId: this.noteInfo.noteId

      }
    })
  }

  onColor(color: any) {
    
    this.noteservice.setColor("Note/SetColour?noteID=" + this.noteInfo.noteId + "&color=" + color, '').subscribe(
      (response: any): any => {
        if (response.statusCode == 200) {
          // this.updateService.changeMessage("color");
          this.snackbar.open("color set to note", "close", { duration: 2500 });
        }
      }
    )
  }

}