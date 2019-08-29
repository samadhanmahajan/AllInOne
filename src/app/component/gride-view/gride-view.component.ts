import { Component, OnInit } from '@angular/core';
import { NoteService } from 'src/app/core/service/note.service';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UpdateNoteComponent } from '../update-note/update-note.component';

@Component({
  selector: 'app-gride-view',
  templateUrl: './gride-view.component.html',
  styleUrls: ['./gride-view.component.scss']
})
export class GrideViewComponent implements OnInit {
  pinNoteList: Object;

  constructor(private noteservice: NoteService, private snackbar: MatSnackBar,
    private dialog: MatDialog) { }

  noteList: any;

  ngOnInit() {
    this.getNote();
    this.getPinNotes();
  }

  getPinNotes() {
    this.noteservice.getAllNotes("Note/GetAllPinNotes").subscribe(
      data => {
        this.pinNoteList = data;
        
      }
    )
  }
  pinNote(item){
    this.noteservice.putRequest("Note/PinUnPin?noteID="+item.noteId,"").subscribe(
      (response:any):any => {
        if (response.statusCode == 301) {
            
          this.snackbar.open("note pin", "close", { duration: 2500 })
        }
        else {
         
          this.snackbar.open("note not pin ", "close", { duration: 2500 })
        }
      }
    )

  }
  getNote() {
    this.noteservice.getAllNotes("Note/GetAllNotes").subscribe(
      data => {
        this.noteList = data;
        console.log('get all note ==>', data);
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
