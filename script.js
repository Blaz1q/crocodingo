var rand_counter=0;
let deg=0;
let prev_los=-1;
function krokodyl_mrugaj(){
    let croco = document.getElementById("croco");
    croco.src = "./imgs/logomrug.png"
    setTimeout( function(){
        croco.src = "./imgs/logo.png"
    },300);
    rand_counter= Math.floor(Math.random() * 3)+1;
    //console.log("mrug");
    //console.log(rand_counter);
}
function krokodyl_skacz(){
    let croco = document.getElementById("croco");
    let czapkiimg = document.getElementById("czapka");
    croco.style.transform = "translate(-50%,-55%)";
    czapkiimg.style.transform = "translate(-50%,-55%)";
    setTimeout( function(){
        croco.style.transform = "translate(-50%,-50%)";
        czapkiimg.style.transform = "translate(-50%,-50%)";
    },300);
}
function random_hat(){
    let czapkiimg = document.getElementById("czapka");
    let czapki =[
        "",
        "czapa_1",
        "czapa_2",
        "czapa_3",
        "czapa_4",
        "czapa_5",
        "czapa_6",
        "czapa_7",
        "czapa_8",
        "czapkabudowa",
        "czapkastudencka",
    ]
    let r = Math.floor(Math.random()*czapki.length);
    while(prev_los==r){
        r = Math.floor(Math.random()*czapki.length);
    }
    prev_los=r;
    if(r!=0){
        czapkiimg.src = "./imgs/"+czapki[r]+"_idle.png";
    }else{
        czapkiimg.src = "";
    }
    krokodyl_skacz();
}
function rotate(btn){
    deg+=832%360;
    btn.children[0].style.transform= "rotate("+deg*6+"deg)";
    console.log(deg);
    console.log(btn);
}
function init(){
    setInterval( function(){
        setTimeout(function(){
            krokodyl_mrugaj()
        },rand_counter*1000);
    },5000);
    setInterval( function(){
        krokodyl_skacz();
    },20*1000);
}