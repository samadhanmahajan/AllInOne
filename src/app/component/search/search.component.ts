import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NoteService } from 'src/app/core/service/note.service';
import { UpdateService } from 'src/app/core/service/dataService/update.service';
import { observable, BehaviorSubject } from 'rxjs';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {

  constructor(private route: ActivatedRoute, private noteservice: NoteService, private updateService: UpdateService) { }
  searchKey: any
  noteList: any
  searchNotes: any
  private update = new BehaviorSubject([]);
  currentMessage = this.update.asObservable();
  ngOnInit() {
    this.updateService.currentMessage.subscribe(
      message => {
        this.searchKey = this.route.snapshot.paramMap.get('searchKey');
        //this.getNotes() 
        this.search()
      }
    )
  }

  search() {
    console.log(this.searchKey + "skey")

    this.noteservice.getRequest("Note/searchNote?key=" + this.searchKey).subscribe(
      (data: any) => {
        this.update.next(data)
        this.searchNotes = data;
      }
    )
  }
}
