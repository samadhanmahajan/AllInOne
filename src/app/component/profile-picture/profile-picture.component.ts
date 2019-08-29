import { Component, OnInit } from '@angular/core';
import { NoteService } from 'src/app/core/service/note.service';
import { UpdateService } from 'src/app/core/service/dataService/update.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialogRef, MatDialog } from '@angular/material/dialog';
import { ImageCroppedEvent } from 'ngx-image-cropper';

@Component({
  selector: 'app-profile-picture',
  templateUrl: './profile-picture.component.html',
  styleUrls: ['./profile-picture.component.scss']
})
export class ProfilePictureComponent implements OnInit {

 constructor(private noteService: NoteService,private updateService:UpdateService,
    private dialog:MatDialog,private snackBar:MatSnackBar){}
  imageChangedEvent: any = '';
  croppedImage: any = '';
  ngOnInit(){

  }


  fileChangeEvent(event: any): void {
      this.imageChangedEvent = event;
  }
  imageCropped(event: ImageCroppedEvent) {
      this.croppedImage = event.base64;
  }
  imageLoaded() {
      // show cropper
  }
  cropperReady() {
      // cropper ready
  }
  loadImageFailed() {
      // show message
  }
  upload(){

    this.noteService.putRequest("User/uploadprofilepic?image="+this.croppedImage,"").subscribe(
      (response:any):any=>
     {
       if(response.statuscode==200)
       {
        this.updateService.changeMessage("uploaded successfully");
         this.snackBar.open("uploaded successfully","close",{duration:2500});
       }
     }
    )
     this.dialog.closeAll();
  }
}