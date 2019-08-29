import { Component, OnInit } from '@angular/core';
import { UpdateNoteComponent } from '../update-note/update-note.component';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NoteService } from 'src/app/core/service/note.service';

@Component({
  selector: 'app-reminder',
  templateUrl: './reminder.component.html',
  styleUrls: ['./reminder.component.scss']
})
export class ReminderComponent implements OnInit {

  constructor(private noteservice: NoteService, private snackbar: MatSnackBar,
    private dialog: MatDialog) { }

  noteList: any;

  ngOnInit() {
    this.getNote();
  }

  getNote() {
    this.noteservice.getAllNotes("Note/GetAllReminderNotes").subscribe(
      data => {
        this.noteList = data;
      }
    )
  }

  openDialog(note: any) {
    const ref = this.dialog.open(UpdateNoteComponent, {

      width: "458px",
      height: "259px",

      data: {
        noteId: note.noteId,
        title: note.title,
        description: note.description
      }
    })
  }
}
