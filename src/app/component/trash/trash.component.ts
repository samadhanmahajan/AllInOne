import { Component, OnInit } from '@angular/core';
import { NoteService } from 'src/app/core/service/note.service';


@Component({
  selector: 'app-trash',
  templateUrl: './trash.component.html',
  styleUrls: ['./trash.component.scss']
})
export class TrashComponent implements OnInit {

 
  notesList:any
  constructor(private noteservice:NoteService) { }

  ngOnInit() {
    this.getTrash();
  }

  getTrash() {
    this.noteservice.getTrashNotes("Note/GetAllTrashedNotes").subscribe(
      data => {
        this.notesList= data;
        console.log('get trash note ==>', data);
      }
    )
  }
  
}