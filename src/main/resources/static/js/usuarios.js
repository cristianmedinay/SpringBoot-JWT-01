// Call the dataTables jQuery plugin
$(document).ready(function() {
    cargarUsuario();
  $('#usuarios').DataTable();
  elementoNombre();
});

function elementoNombre(){

    document.getElementById("correoTxt").outerHTML = localStorage.email;
}
async function cargarUsuario(){

    const request = await fetch('api/usuarios',{
        method:'GET',
        headers:{
            'Accept':'application/json',
            'Content-Type':'application/json',
            'Authorization':localStorage.token
        }

    });
    const users = await request.json();
    console.log(users);
    let listHTML='';
   for(let item of users){
      let btnEliminar = '<a onclick="eliminarUsuario('+item.id+')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>'
      let telefono = item.telefono == null ? '-' : item.telefono;
      let datos = '<tr><td>'+item.id+'</td><td>'+item.nombre+"  "+item.apellido+'</td><td>'+item.email+'</td><td>'+telefono+'</td><td>'+btnEliminar+'</td></tr>';
      listHTML += datos;
        console.log(item.nombre);
    }
    //const datos = '<tr><td>Tiger Nixon</td><td>prueba</td><td>Edinburgh</td><td>61</td><td>2011/04/25</td></tr>';
    document.querySelector('#usuarios tbody').outerHTML =listHTML;

}

async function eliminarUsuario(id){

        //false negativo confirmacion
        if(!confirm('desea eliminar ?')){
            return;
        }
        const request = await fetch('api/usuarios/'+id,{
             method:'DELETE',
             headers:{
                    'Accept':'application/json',
                    'Content-Type':'application/json',
                    'Authorization':localStorage.token
             }
        });
        $(document).ready(function() {
        cargarUsuario();
        $('#usuarios').DataTable();
        });
         //location.reaload();
        //window.location.reload(true);
}