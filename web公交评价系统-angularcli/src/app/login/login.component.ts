import {
  Component,
  Inject,
  trigger,
  state,
  style,
  transition,
  animate,
  OnDestroy
} from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import { AuthService } from '../core/auth.service'
import { MdlDialogService, MdlDialogReference } from 'angular2-mdl';
import { Auth, Image } from '../domain/entities';
import { RegisterDialogComponent } from './register-dialog.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  animations: [
    trigger('loginState', [
      state('inactive', style({
        transform: 'scale(1)'
      })),
      state('active',   style({
        transform: 'scale(1.1)'
      })),
      transition('inactive => active', animate('100ms ease-in')),
      transition('active => inactive', animate('100ms ease-out'))
    ])
  ]
})
export class LoginComponent implements OnDestroy {

  username = '';
  password = '';
  did = '';
  auth: Auth;
  slides: Image[] = [];
  photo = '';
  subscription: Subscription;
  loginBtnState: string = 'inactive';

  constructor(
    @Inject('auth') private authService,
    @Inject('bing') private bingService,
    private dialogService: MdlDialogService,
    private router: Router) {
    this.bingService.getImageUrl()
      .subscribe((images: Image[]) => {
        this.slides = [...images];
        this.rotateImages(this.slides);
      });
  }
  onSubmit(){
    this.subscription = this.authService
      .loginWithCredentials(this.username, this.password)
      .subscribe(auth => {
        this.auth = Object.assign({}, auth);
        if(!auth.hasError){
          if(this.judge(this.username))
          {
            this.router.navigate(['manage']);
          }
          else {
            this.router.navigate(['todo']);
          }
        }
      });
  }

  judge(username: string){
    if(username === "guo" || username === "wang" || username === "yang"){
      return true;
    }
    else {
      return false;
    }
  }

  ngOnDestroy(){
    if(this.subscription !== undefined)
      this.subscription.unsubscribe();
  }
  rotateImages(arr: Image[]){
    const length = arr.length
    let i = 0;
    setInterval(() => {
      if((i+1) % length == 0){
        i = (i + 2) % length;
      }
      else{
        i = (i + 1) % length;
      }     
      this.photo = this.slides[i].contentUrl;
    }, 10000);
  }
  toggleLoginState(state: boolean){
    this.loginBtnState = state ? 'active' : 'inactive';
  }
  register($event: MouseEvent){
    let pDialog = this.dialogService.showCustomDialog({
      component: RegisterDialogComponent,
      isModal: true,
      styles: {'width': '350px'},
      clickOutsideToClose: true,
      enterTransitionDuration: 400,
      leaveTransitionDuration: 400
    });
    pDialog.map( (dialogReference: MdlDialogReference) => {
      console.log('dialog visible', dialogReference);
    });

  }
}
