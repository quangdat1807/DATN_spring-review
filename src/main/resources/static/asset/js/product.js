
const url = window.location.pathname;
console.log('name' + url);




const urlParams = new URLSearchParams(window.location.search);
const param = urlParams.get('sort');
const sortBy = urlParams.get('sortBy');



const urlPrice = new URLSearchParams(window.location.search);
const price_min = urlPrice.get('price_min');
const price_max = urlPrice.get('price_max');



if (price_max == 100000) {
    document.getElementById("filter_price_mobile").selectedIndex = "1";
    document.getElementById("filter_price_desktop").selectedIndex = "1";
}else if (price_min == 100000 && price_max==300000) {
    document.getElementById("filter_price_mobile").selectedIndex = "2";
    document.getElementById("filter_price_desktop").selectedIndex = "2";
}else if (price_min == 300000 && price_max==500000) {
    document.getElementById("filter_price_mobile").selectedIndex = "3";
    document.getElementById("filter_price_desktop").selectedIndex = "3";
}else if (price_min == 500000 && price_max==700000) {
    document.getElementById("filter_price_mobile").selectedIndex = "4";
    document.getElementById("filter_price_desktop").selectedIndex = "4";
}else if (price_min == 700000) {
    document.getElementById("filter_price_mobile").selectedIndex = "5";
    document.getElementById("filter_price_desktop").selectedIndex = "5";
}






if (param == 'tensanpham') {
    document.getElementById('nameASC').checked = true;
    document.getElementById("filter_products").selectedIndex = "1";
    document.getElementById('page_default').remove();
    $("#page_sort").css("display", 'block');

}else if(param == 'gia'){
    document.getElementById('priceASC').checked = true;
    document.getElementById("filter_products").selectedIndex = "3";
    document.getElementById('page_default').remove();
    $("#page_sort").css("display", 'block');
} 



if(param == 'tensanpham' && sortBy == 'DESC'){
   
    document.getElementById('nameDESC').checked = true;
    document.getElementById("filter_products").selectedIndex = "2";
    $("#page_sort").css("display", 'block');
}else if(param == 'gia' && sortBy == 'DESC'){
    document.getElementById('priceDESC').checked = true;
    document.getElementById("filter_products").selectedIndex = "4";
    
    $("#page_sort").css("display", 'block');
}


var x = location.search;
console.log(x);

var a = x.indexOf('&'); 

let sub =x.substr(0, a).toString();



var test = `${url}${sub}`;

console.log(test);





document.body.addEventListener('change', function (e) {
    let target = e.target;

    if (price_min === null && price_max === null) {
        switch (target.id) {
            case 'nameASC':
                window.location.assign('?sort=tensanpham')
                
                break;
            case 'nameDESC':
                window.location.assign('?sort=tensanpham&sortBy=DESC')
                break;
            case 'priceASC':
                window.location.assign('?sort=gia')
                break;
            case 'priceDESC':
                window.location.assign('?sort=gia&sortBy=DESC')
                break;
        }
    }else{
        if (price_max == 100000) {
            switch (target.id) {
                case 'nameASC':
                    window.location.assign(`?price_max=${price_max}&sort=tensanpham`)
                    
                    break;
                case 'nameDESC':
                    window.location.assign(`?price_max=${price_max}&sort=tensanpham&sortBy=DESC`)
                    break;
                case 'priceASC':
                    window.location.assign(`?price_max=${price_max}&sort=gia`)
                    break;
                case 'priceDESC':
                    window.location.assign(`?price_max=${price_max}&sort=gia&sortBy=DESC`)
                    break;
            }
        }else if (price_min == 100000 && price_max==300000 || price_min == 300000 && price_max==500000 || price_min == 500000 && price_max==700000) {
            switch (target.id) {
                case 'nameASC':
                    window.location.assign(`?price_min=${price_min}&price_max=${price_max}&sort=tensanpham`)
                    
                    break;
                case 'nameDESC':
                    window.location.assign(`?price_min=${price_min}&price_max=${price_max}&sort=tensanpham&sortBy=DESC`)
                    break;
                case 'priceASC':
                    window.location.assign(`?price_min=${price_min}&price_max=${price_max}&sort=gia`)
                    break;
                case 'priceDESC':
                    window.location.assign(`?price_min=${price_min}&price_max=${price_max}&sort=gia&sortBy=DESC`)
                    break;
            }
        }else if (price_min == 700000) {
            switch (target.id) {
                case 'nameASC':
                    window.location.assign(`?price_min=${price_min}&sort=tensanpham`)
                    
                    break;
                case 'nameDESC':
                    window.location.assign(`?price_min=${price_min}&sort=tensanpham&sortBy=DESC`)
                    break;
                case 'priceASC':
                    window.location.assign(`?price_min=${price_min}&sort=gia`)
                    break;
                case 'priceDESC':
                    window.location.assign(`?price_min=${price_min}&sort=gia&sortBy=DESC`)
                    break;
            }
        }
    }
    
        
});

function sortChanged(obj)
{
    
    var value = obj.value;
    if (value === ''){
        window.location.assign(url)
    }
    else if (value === '1'){
      window.location.assign('?sort=tensanpham')  
    }
    else if (value === '2'){
        window.location.assign('?sort=tensanpham&sortBy=DESC') 
    }
    else if (value === '3'){
        window.location.assign('?sort=gia') 
    }
    else if (value === '4'){
        window.location.assign('?sort=gia&sortBy=DESC') 
    }
    
}

var x = location.search;

console.log("pram: " + param);

function priceChanged(obj)
{

    var value = obj.value;

 if(param === null && sortBy === null ) {
    if (value === ''){
        window.location.assign(url)
    }
    else if (value === '1'){
    window.location.assign(`?price_max=100000`)  
    }
    else if (value === '2'){
        window.location.assign(`?price_min=100000&price_max=300000`) 
    }
    else if (value === '3'){
        window.location.assign(`?price_min=300000&price_max=500000`) 
    }
    else if (value === '4'){
        window.location.assign(`?price_min=500000&price_max=700000`) 
    }
    else if (value === '5'){
        window.location.assign(url+`?price_min=700000`) 
    }
}else if(param !='' || sortBy!='') {
   
        if (param != '') {
            if (sortBy == 'DESC') {
                if (value === ''){
                    window.location.assign(url+`?sort=${param}&sortBy=${sortBy}`)
                }
                else if (value === '1'){
                window.location.assign(`?price_max=100000&sort=${param}&sortBy=${sortBy}`)  
                }
                else if (value === '2'){
                    window.location.assign(`?price_min=100000&price_max=300000&sort=${param}&sortBy=${sortBy}`) 
                }
                else if (value === '3'){
                    window.location.assign(`?price_min=300000&price_max=500000&sort=${param}&sortBy=${sortBy}`) 
                }
                else if (value === '4'){
                    window.location.assign(`?price_min=500000&price_max=700000&sort=${param}&sortBy=${sortBy}`) 
                }
                else if (value === '5'){
                    window.location.assign(url+`?price_min=700000&sort=${param}&sortBy=${sortBy}`) 
                }
            }else if(param != '' && price_min != '' && price_max != '') {
                if (value === ''){
                    window.location.assign(url+`?sort=${param}`)
                }
                else if (value === '1'){
                window.location.assign(`?price_max=100000&sort=${param}`)  
                }
                else if (value === '2'){
                    window.location.assign(`?price_min=100000&price_max=300000&sort=${param}`) 
                }
                else if (value === '3'){
                    window.location.assign(`?price_min=300000&price_max=500000&sort=${param}`) 
                }
                else if (value === '4'){
                    window.location.assign(`?price_min=500000&price_max=700000&sort=${param}`) 
                }
                else if (value === '5'){
                    window.location.assign(url+`?price_min=700000&sort=${param}`) 
                }
            }
            
        
        }
}

}



