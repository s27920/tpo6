

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
        return true;
    }
    return false;
}
document.getElementById("searchButton").addEventListener("click", ()=>{
    search();
});

function search(){
    let searchPhrase = document.getElementById("searchBox").value;
    let featureCheckBoxes = Array.from(document.getElementsByClassName("featureCheckBox"));
    let cuisineCheckBoxes = Array.from(document.getElementsByClassName("cuisineCheckBox"));
    let priceCheckBoxes = Array.from(document.getElementsByClassName("priceCheckBox"));
    let featuresChecked = [];
    let cusinesChecked = [];
    let pricesChecked = [];

    featureCheckBoxes.forEach(checkBox=>{
        if(checkBox.checked){
            featuresChecked.push(checkBox.name);
        }
    });
    cuisineCheckBoxes.forEach(cuisineBox=>{
        if(cuisineBox.checked){
            cusinesChecked.push(cuisineBox.name)
        }
    });
    priceCheckBoxes.forEach(priceBox=>{
        if(priceBox.checked){
            pricesChecked.push(priceBox.name);
        }
    });

    const requestJson ={
        search: searchPhrase,
        features: featuresChecked,
        cuisines: cusinesChecked,
        prices: pricesChecked
    };
    fetch('helloServlet', {
        method: 'POST', headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(requests)
    }).then(result => {
        console.log('Success:', result);
    }).catch(error => {
        console.error('Error:', error);
    });
}

function turnButtonsOffOn(toggle){
    //quick note. I spent about an hour trying to overengineer a fancy solution instead of just passing a string... fml
    const buttons = document.querySelectorAll(".scrollBtn");
    buttons.forEach(button =>{
        button.style.visibility = toggle;
    })
}

document.getElementById("searchIcon").addEventListener("click", event =>{
    let target = document.getElementById("searchOptions");
    closeOutSearchBar(target);
});

document.getElementById("exitButton").addEventListener("click", event=>{
    let target = document.getElementById("searchOptions");
    closeOutSearchBar(target);
});


document.getElementById("filterButton").addEventListener("click", ()=>{
    let target = document.getElementById("searchOptions");
    extend(target, "300px", "25px");
});

function closeOutSearchBar(target){
    if (target.style.height === "300px" && target.style.top === "0px") {
        extend(target, "300px", "25px");
        setTimeout(()=>{
            slideTmp(target, "-25px");
        },100);
    }else{
        slideTmp(target, "-25px")
    }
}

function extend(target, extended, shrunk){
    if (target.style.height === shrunk || target.style.height==="") {
        target.style.height = extended;
    } else {
        target.style.height = shrunk;
    }
}
function slideTmp(target, ammount){
    if(target.style.top === ammount || target.style.top===""){
        target.style.top = "0px"
    }else{
        target.style.top = ammount
    }
}

document.getElementById("filterSpanButtonInstance1").addEventListener("click", event=>{
    let target = event.target.parentNode;
    extend(target, "200px", "20px")
});
document.getElementById("filterSpanButtonInstance2").addEventListener("click", event=>{
    let target = event.target.parentNode;
    extend(target, "200px", "20px")
});
document.getElementById("filterSpanButtonInstance3").addEventListener("click", event=>{
    let target = event.target.parentNode;
    extend(target, "200px", "20px")
});