
turnButtonsOffOn("hidden");

function insertElement(){
    const newLi = document.createElement("li");
    const newDiv = document.createElement("div");
    document.getElementById("enumerate-list").append(newLi);
    newDiv.className = "enumerate";
    newLi.append(newDiv);
    if(checkListScrollability()){
        turnButtonsOffOn("visible");
    }
}

function removeAllElements(){
    const ul = document.getElementById("enumerate-list");
    const children = ul.children;
    if(checkListScrollability()){
        turnButtonsOffOn("hidden");
    }
    Array.from(children).forEach(child =>{
        ul.removeChild(child);
    })
}

function scrollRight() {
    const list = document.getElementById("enumerate-list");
    const listWidth = list.scrollWidth;
    const newX = list.scrollLeft + list.clientWidth / 2;
    if (newX <= listWidth) {
        list.scrollTo({
            left: newX,
            behavior: 'smooth'
        });
    } else {
        list.scrollTo({
            left: listWidth,
            behavior: 'smooth'
        });
    }
}
function scrollBack(){
    const list = document.getElementById("enumerate-list");
    const newX = list.scrollLeft - list.clientWidth / 2;

    if(newX >= 0){
        list.scrollTo({
            left: newX,
            behavior: "smooth"
        })
    }else{
        list.scrollTo({
            left: 0,
            behavior: "smooth"
        })
    }
}

function checkListScrollability(){
    const list = document.getElementById("enumerate-list");
    if (list.scrollWidth > list.clientWidth){
        console.log("true");
        return true;
    }
    console.log("false");
    return false;
}

function turnButtonsOffOn(toggle){
    //quick note. I spent about an hour trying to over-engineer a fancy solution instead of just passing a string... fml
    const buttons = document.querySelectorAll(".scrollBtn");
    buttons.forEach(button =>{
        button.style.visibility = toggle;
    })
}
let droppedDown = false;
let extended = false;
document.getElementById("searchIcon").addEventListener("click", event =>{
    if(!droppedDown){
        slideDown();
    }else{
        slideUp();
    }
});

document.getElementById("exitButton").addEventListener("click", event=>{
    if(droppedDown && extended){
        setTimeout(slideUp,490);
    }else{
        slideUp();
    }
    if(extended){
        hide();
    }
});


document.getElementById("filterButton").addEventListener("click", ()=>{
    if(extended){
        hide();
    }else{
        extend();
    }
});

function slideUp(){
    let target = document.getElementById("searchOptions");
    target.classList.add("slidUp");
    setTimeout(()=>{ 
        target.style.top = "-25px";
        droppedDown = false;
        target.classList.remove("slidUp");
    },490);
}
function slideDown(){
    let target = document.getElementById("searchOptions");
        target.classList.add("slidDown");
        droppedDown = true;
        setTimeout(()=>{ 
            target.style.top = 0;
            target.classList.remove("slidDown");
        },490);
}

function extend(){
    let target = document.getElementById("searchOptions");
    target.classList.add("extended");
    setTimeout(()=>{
        target.style.height = "300px";
        extended = true;
        target.classList.remove("extended");
    },490);
}
function hide(){
    let target = document.getElementById("searchOptions");
    target.classList.add("shrunk");
    setTimeout(()=>{
        target.style.height = "25px";
        extended = false;
        target.classList.remove("shrunk");
    },490);
}




