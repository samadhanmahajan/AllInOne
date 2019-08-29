import { Component, OnInit, Input } from '@angular/core';
import { NoteService } from 'src/app/core/service/note.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-label-notes',
  templateUrl: './label-notes.component.html',
  styleUrls: ['./label-notes.component.scss']
})
export class LabelNotesComponent implements OnInit {

  constructor(private noteservice:NoteService,private snackBar:MatSnackBar,private route:ActivatedRoute) { }
  notesList:any
  labelId
  //  @Input() noteInfo:any
  ngOnInit() {
    this.labelId=this.route.snapshot.paramMap.get('labelId');
   this.getLabelNotes();
  }
  getLabelNotes()
  {console.log(this.labelId+"buy")
    this.noteservice.archiveNotes("Label/GetNotesOfLabel?labelID="+this.labelId).subscribe(
      data =>{
        this.notesList=data;
        console.log(data);
      }
    )
  }

}
