$(document).ready(function(){

   elementoNombre();
});



function elementoNombre(){

    document.getElementById("correoTxt").outerHTML = localStorage.email;
}