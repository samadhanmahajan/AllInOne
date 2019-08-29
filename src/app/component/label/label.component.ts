import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators, FormGroup } from '@angular/forms';
import { LabelService } from 'src/app/core/service/label.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { Label } from 'src/app/core/model/label';
import { UpdateService } from 'src/app/core/service/dataService/update.service';

@Component({
  selector: 'app-label',
  templateUrl: './label.component.html',
  styleUrls: ['./label.component.scss']
})
export class LabelComponent implements OnInit {
  constructor(private labelservice:LabelService,private snackBar:MatSnackBar,private updateService:UpdateService,
   private dialog:MatDialog) { }

  label:Label=new Label();
  labelForm:FormGroup;
  
  labelsList:any
  ngOnInit() {
    this.updateService.currentMessage.subscribe(
      message=>{
        this.getLabel();
      }
    )
      
    this.labelForm=new FormBuilder().group(
      {
        labelName:new FormControl(this.label.labelName,Validators.required)
      }
    )
  }
  getLabel()
  {
    this.labelservice.getRequest("Label/GetLabels").subscribe(
      (data:any):any=>
      {
        this.labelsList=data
        console.log("get All label"+data);
        
      }
    )
  }
  editLabel()
  {
    console.log(this.labelForm.value);
    this.updateService.changeMessage("editLabel");
    this.labelservice.postRequest("Label/Create",this.labelForm.value).subscribe(
      (response:any):any=>
      {
        
        if(response.statuscode==200)
        {
          this.updateService.changeMessage("label");
          this.snackBar.open("label created ","close",{duration:2500});
        }
      }
    )
    this.dialog.closeAll();
  }
  updateLabel(item:any)
  {
    this.updateService.changeMessage("updateLabel");
    this.labelservice.putRequest("Label/Update?labelID="+item.labelId,this.labelForm.value).subscribe
    (
      (response:any):any=>
      {
        if(response.statusCode==200)
        {
          this.updateService.changeMessage("updatelabel");
          this.snackBar.open("label updated","close",{duration:2500})
        }
      }
    )
    this.dialog.closeAll();
  }
  deleteLabel(item:any)
  {
    console.log(item.labelId);
    this.updateService.changeMessage("deleteLabel");
    this.labelservice.deleteRequest("Label/Delete?labelID="+item.labelId).subscribe
    (
     (response:any):any=>
     {
       if(response.statuscode==200)
       {
        this.updateService.changeMessage("deletelabel");
         this.snackBar.open("label deleted","close",{duration:2500});
       }
     }
    )
     this.dialog.closeAll();
  }
}