import { Component, OnInit } from '@angular/core';
import { NoteService } from 'src/app/core/service/note.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { UpdateNoteComponent } from '../update-note/update-note.component';
import { UpdateService } from 'src/app/core/service/dataService/update.service';

@Component({
  selector: 'app-get-all-notes',
  templateUrl: './get-all-notes.component.html',
  styleUrls: ['./get-all-notes.component.scss']
})
export class GetAllNotesComponent implements OnInit {
  constructor(private noteservice: NoteService,private updateService:UpdateService, private snackbar: MatSnackBar,
    private dialog: MatDialog) { }

  noteList: any;
  pinNoteList:any

  ngOnInit() {
    this.updateService.currentMessage.subscribe(
      message=>{
        this.getNotes();
        this.getPinNotes();
      }
    )    
  }

  pinNote(item){
    this.noteservice.putRequest("Note/PinUnPin?noteID="+item.noteId,"").subscribe(
      (response:any):any => {
        if (response.statusCode == 301) {
          this.updateService.changeMessage("pin");
          this.snackbar.open("note pin", "close", { duration: 2500 })
        }
        else {
          this.updateService.changeMessage("unpin");
          this.snackbar.open("note not pin ", "close", { duration: 2500 })
        }
      }
    )

  }

  getNotes() {
    this.noteservice.getAllNotes("Note/GetAllNotes").subscribe(
      data => {
        this.noteList = data;
        console.log("list"+this.noteList)
      }
    )
  }
  getPinNotes() {
    this.noteservice.getAllNotes("Note/GetAllPinNotes").subscribe(
      data => {
        this.pinNoteList = data;
        
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
