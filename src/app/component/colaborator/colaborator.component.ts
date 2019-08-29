import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatSnackBar, MatDialog } from '@angular/material';
import { NoteService } from 'src/app/core/service/note.service';
import { Router } from '@angular/router';
import { FormControl, Validators } from '@angular/forms';
import { Collaborator } from 'src/app/core/model/collaborator';

@Component({
  selector: 'app-colaborator',
  templateUrl: './colaborator.component.html',
  styleUrls: ['./colaborator.component.scss']
})
export class ColaboratorComponent implements OnInit {

  collaborator: Collaborator = new Collaborator();
  collaboratedUsers:any[];
  emailId:any
  open: boolean = false;
  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private noteService: NoteService,
    private snackBar: MatSnackBar,
    private router: Router, private dialog: MatDialog) { 
      
    }
  ngOnInit() {
    this.emailId = localStorage.getItem("emailId");
    this.noteService.getRequest("Note/GetAllCollaboratedUsers?noteID="+this.data.noteId).subscribe((response: any) => {
      this.collaboratedUsers = response;
      console.log(this.collaboratedUsers)
    })
  }

  collaborateNote() {
    console.log(this.data.noteId)
    console.log(this.emailId)
    this.noteService.collaborateNote("Note/CollaborateNote?collaboratorEmail="+this.collaborator.emailId+"&noteID=" + this.data.noteId).subscribe(
      (response: any): any => {
        if (response.statusCode = 202) {

          this.snackBar.open("note updated", "close", { duration: 2500 });
        }
        else {
          this.snackBar.open("note note updated", "close", { duration: 2500 });
        }
      }

    )
    this.dialog.closeAll();
  }

  removeCollaborator(email) {
    
    this.noteService.collaborateNote("Note/CollaboratorRemoveFromNote?collaboratorEmail="+email.emailId+"&noteID=" + this.data.noteId).subscribe(
      (response: any): any => {
        if (response.statusCode = 202) {

          this.snackBar.open("note updated", "close", { duration: 2500 });
        }
        else {
          this.snackBar.open("note note updated", "close", { duration: 2500 });
        }
      }

    )
    this.dialog.closeAll();
  }
}
