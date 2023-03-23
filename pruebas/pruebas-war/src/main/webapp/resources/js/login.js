////Arquetipo: Modificaciones en la maqueta  
////No toqueis!, puede sufrir modificaciones
////Si es necesario cread uno propio

$(document).ready(function(){
    //Prevenir intentos acceso automatizados sucesivos
    $('#checkhumano').click(function(){
        if($(this).prop('checked') === false){
             $('#botonentrarlogin').attr("disabled","disabled");   
        } else {
            $('#botonentrarlogin').removeAttr('disabled');
        }
    });
});
