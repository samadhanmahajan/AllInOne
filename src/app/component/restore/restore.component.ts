import { Component, OnInit, Input } from '@angular/core';
import { NoteService } from 'src/app/core/service/note.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-restore',
  templateUrl: './restore.component.html',
  styleUrls: ['./restore.component.scss']
})
export class RestoreComponent implements OnInit {

  constructor(private noteservice:NoteService,private snackbar:MatSnackBar) { }

  @Input() noteInfo:any
  ngOnInit() {
  }

  onRestore()
  {
    this.noteservice.trashUnTrashNote("Note/TrashUntrsh?noteID="+this.noteInfo.noteId).subscribe(
      (response:any):any=>
      {console.log(response)
        if(response.statusCode==200)
        {
          
          this.snackbar.open("note restored","close",{duration:2500})
        }
        else
        {
          this.snackbar.open("note not present in trash","close",{duration:2500});
        }
      }
    )
  }

  onDelete()
  {
    this.noteservice.deleteNote("Note/DeletePermanently?noteId="+this.noteInfo.noteId).subscribe(
      (response:any):any=>
      {console.log(response)
        if(response.statusCode==200)
        {
         
          this.snackbar.open("note deleted permanently..","close",{duration:2500});
        }
        else
        {
          this.snackbar.open("note not in trash","close",{duration:2500});
        }
      }
    )
  }
}