"use strict";(self.webpackChunktelegraph=self.webpackChunktelegraph||[]).push([[355],{3355:(M,a,o)=>{o.r(a),o.d(a,{HomeModule:()=>v});var g=o(3155),t=o(2029),m=o(4310),d=o(4406),h=o(6031),r=o(6814),c=o(8209);function p(n,l){if(1&n&&(t.ynx(0),t.TgZ(1,"a",3),t._UZ(2,"img",4),t.TgZ(3,"p",5),t._uU(4),t.qZA()(),t.BQk()),2&n){const e=t.oxw().$implicit;t.xp6(1),t.s9C("routerLink",e.routerLink),t.xp6(1),t.s9C("alt",e.name),t.s9C("src",e.src,t.LSH),t.xp6(2),t.Oqu(e.name)}}function u(n,l){if(1&n&&(t.ynx(0),t.YNc(1,p,5,4,"ng-container",2),t.BQk()),2&n){const e=l.$implicit;t.xp6(1),t.Q6J("ngIf",e.permision)}}let x=(()=>{class n{constructor(e,i,s){this.LanguageService=e,this.authService=i,this.modulesService=s,this.modules=[],this.userName="",this.apps=[],this.lang="ar",this.lang=this.LanguageService.get_app_lang(),this.getModules()}getModules(){this.modules=this.modulesService.getModules(!0)}ngOnInit(){"local"!==g.D.type&&(this.authService.isLoggedIn()||this.authService.handleSingleSignOn("Home").subscribe(e=>{e||this.authService.logoutLocal()})),this.modulesService.change_modules_headers.subscribe(()=>{this.lang=this.LanguageService.get_app_lang(),this.getModules()})}static#t=this.\u0275fac=function(i){return new(i||n)(t.Y36(m.T),t.Y36(d.e),t.Y36(h.s))};static#n=this.\u0275cmp=t.Xpm({type:n,selectors:[["app-home"]],decls:2,vars:1,consts:[["id","main",1,"card-list"],[4,"ngFor","ngForOf"],[4,"ngIf"],[1,"card",3,"routerLink"],[1,"logos",3,"alt","src"],[1,"names"]],template:function(i,s){1&i&&(t.TgZ(0,"main",0),t.YNc(1,u,2,1,"ng-container",1),t.qZA()),2&i&&(t.xp6(1),t.Q6J("ngForOf",s.modules))},dependencies:[r.sg,r.O5,c.rH],styles:[".header[_ngcontent-%COMP%]{width:100%;height:70px;background-color:var(--main-color)}.rtl[_ngcontent-%COMP%]{direction:rtl;margin-right:10px}.ltr[_ngcontent-%COMP%]{direction:ltr;margin-left:10px}.card-list[_ngcontent-%COMP%]{display:flex;flex-wrap:wrap;align-content:center;margin:8rem 0;gap:3rem}.card-list[_ngcontent-%COMP%]   a[_ngcontent-%COMP%]{text-decoration:none}@media all and (max-width: 500px){.card-list[_ngcontent-%COMP%]{flex-direction:column;align-items:center}}main[_ngcontent-%COMP%]{width:100%;height:calc(100% - 50px);display:flex;justify-content:center;align-items:center}.card[_ngcontent-%COMP%]{display:flex;text-align:center;justify-content:center;align-items:center;width:250px;height:200px;border-radius:15px;box-shadow:1px 1px 1px 4px #0000000f;cursor:pointer;transition:.4s}.card[_ngcontent-%COMP%]   img[_ngcontent-%COMP%]{border-radius:10px;margin:20px 0}.card[_ngcontent-%COMP%]:hover{transform:scale(.95);box-shadow:1px 1px 1px 5px #00000040}.card[_ngcontent-%COMP%]:hover   .logos[_ngcontent-%COMP%]{opacity:1;transition:.4s}.names[_ngcontent-%COMP%]{font-family:Lato,sans-serif;font-size:17px!important;color:#000}.logos[_ngcontent-%COMP%]{width:90px;filter:hue-rotate(298deg) brightness(115%);opacity:.85}"]})}return n})();var f=o(4973);const C=[{path:"",component:x}];let v=(()=>{class n{static#t=this.\u0275fac=function(i){return new(i||n)};static#n=this.\u0275mod=t.oAB({type:n});static#e=this.\u0275inj=t.cJS({imports:[f.m,c.Bz.forChild(C)]})}return n})()}}]);