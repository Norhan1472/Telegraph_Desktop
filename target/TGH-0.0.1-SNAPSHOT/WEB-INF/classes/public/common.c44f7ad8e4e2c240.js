(self.webpackChunktelegraph=self.webpackChunktelegraph||[]).push([[592],{7650:(v,h,a)=>{"use strict";a.d(h,{T:()=>E});var p=a(6814),d=a(217),c=a(2029);let E=(()=>{class r{export(u,l){a.e(486).then(a.bind(a,1486)).then(e=>{const o={Sheets:{data:e.utils.table_to_sheet(u)},SheetNames:["data"]},n=e.write(o,{bookType:"xlsx",type:"array"});this.saveAsExcelFile(n,l)})}saveAsExcelFile(u,l){const o=new Blob([u],{type:"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8"});d.saveAs(o,l+"_"+(0,p.p6)(new Date,"dd-MMM-YYYY hh:mm","en-US")+".xlsx")}static#e=this.\u0275fac=function(l){return new(l||r)};static#t=this.\u0275prov=c.Yz7({token:r,factory:r.\u0275fac,providedIn:"root"})}return r})()},217:function(v,h){var a,d;void 0!==(d="function"==typeof(a=function(){"use strict";function c(e,t,o){var n=new XMLHttpRequest;n.open("GET",e),n.responseType="blob",n.onload=function(){l(n.response,t,o)},n.onerror=function(){console.error("could not download file")},n.send()}function E(e){var t=new XMLHttpRequest;t.open("HEAD",e,!1);try{t.send()}catch{}return 200<=t.status&&299>=t.status}function r(e){try{e.dispatchEvent(new MouseEvent("click"))}catch{var t=document.createEvent("MouseEvents");t.initMouseEvent("click",!0,!0,window,0,0,0,80,20,!1,!1,!1,!1,0,null),e.dispatchEvent(t)}}var i="object"==typeof window&&window.window===window?window:"object"==typeof self&&self.self===self?self:"object"==typeof global&&global.global===global?global:void 0,u=i.navigator&&/Macintosh/.test(navigator.userAgent)&&/AppleWebKit/.test(navigator.userAgent)&&!/Safari/.test(navigator.userAgent),l=i.saveAs||("object"!=typeof window||window!==i?function(){}:"download"in HTMLAnchorElement.prototype&&!u?function(e,t,o){var n=i.URL||i.webkitURL,s=document.createElement("a");s.download=t=t||e.name||"download",s.rel="noopener","string"==typeof e?(s.href=e,s.origin===location.origin?r(s):E(s.href)?c(e,t,o):r(s,s.target="_blank")):(s.href=n.createObjectURL(e),setTimeout(function(){n.revokeObjectURL(s.href)},4e4),setTimeout(function(){r(s)},0))}:"msSaveOrOpenBlob"in navigator?function(e,t,o){if(t=t||e.name||"download","string"!=typeof e)navigator.msSaveOrOpenBlob(function w(e,t){return typeof t>"u"?t={autoBom:!1}:"object"!=typeof t&&(console.warn("Deprecated: Expected third argument to be a object"),t={autoBom:!t}),t.autoBom&&/^\s*(?:text\/\S*|application\/xml|\S*\/\S*\+xml)\s*;.*charset\s*=\s*utf-8/i.test(e.type)?new Blob(["\ufeff",e],{type:e.type}):e}(e,o),t);else if(E(e))c(e,t,o);else{var n=document.createElement("a");n.href=e,n.target="_blank",setTimeout(function(){r(n)})}}:function(e,t,o,n){if((n=n||open("","_blank"))&&(n.document.title=n.document.body.innerText="downloading..."),"string"==typeof e)return c(e,t,o);var s="application/octet-stream"===e.type,M=/constructor/i.test(i.HTMLElement)||i.safari,y=/CriOS\/[\d]+/.test(navigator.userAgent);if((y||s&&M||u)&&typeof FileReader<"u"){var _=new FileReader;_.onloadend=function(){var f=_.result;f=y?f:f.replace(/^data:[^;]*;/,"data:attachment/file;"),n?n.location.href=f:location=f,n=null},_.readAsDataURL(e)}else{var A=i.URL||i.webkitURL,m=A.createObjectURL(e);n?n.location=m:location.href=m,n=null,setTimeout(function(){A.revokeObjectURL(m)},4e4)}});i.saveAs=l.saveAs=l,v.exports=l})?a.apply(h,[]):a)&&(v.exports=d)}}]);