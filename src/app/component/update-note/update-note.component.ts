import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatSnackBar, MatDialog } from '@angular/material';
import { NoteService } from 'src/app/core/service/note.service';
import { Router } from '@angular/router';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { Note } from 'src/app/core/model/note';
import { UpdateService } from 'src/app/core/service/dataService/update.service';

@Component({
  selector: 'app-update-note',
  templateUrl: './update-note.component.html',
  styleUrls: ['./update-note.component.scss']
})
export class UpdateNoteComponent implements OnInit {

  open: boolean = true;
  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private noteService: NoteService,
    private snackBar: MatSnackBar,private updateService:UpdateService,
    private router: Router, private dialog: MatDialog) { }

  updateForm: FormGroup;

  note: Note = new Note();

  ngOnInit() {
    this.updateForm = new FormBuilder().group({

      "title": new FormControl(this.data.title),
      "description": new FormControl(this.data.description)
    });
  }

  updateNote() {
    console.log( "update note"+this.data.noteId)
    this.noteService.updateNote("Note/Update?noteID=" + this.data.noteId, this.updateForm.value).subscribe(
      (response: any): any => {
        if (response.statusCode = 202) {
          this.updateService.changeMessage("updated");
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