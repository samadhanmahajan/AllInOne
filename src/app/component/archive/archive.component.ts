import { Component, OnInit, Input } from '@angular/core';
import { NoteService } from 'src/app/core/service/note.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UpdateService } from 'src/app/core/service/dataService/update.service';
import { Message } from '@angular/compiler/src/i18n/i18n_ast';


@Component({
  selector: 'app-archive',
  templateUrl: './archive.component.html',
  styleUrls: ['./archive.component.scss']
})
export class ArchiveComponent implements OnInit {

  constructor(private noteservice:NoteService,private snackBar:MatSnackBar,private updateServ:UpdateService) { }
  notesList:any
 
  ngOnInit() {
    this.updateServ.currentMessage.subscribe(
     message=>{
   this.getArchive();
  }
  )
  }
  getArchive()
  {
    this.noteservice.archiveNotes("Note/GetAllArchievedNotes").subscribe(
      data =>{
        this.notesList=data; 
      }
    )
  }

}