/**
 * JAVASCRIPTS USANDOS EN AMBITO GLOBAL DE XILAX
 */
 
// GLOBALES
var origenDatos = null; 
var valorDefecto= null; 

/*
Funcion para componer correctamente el nombre de un elemento html usado por js o jquery
*/
function defineElemento(elemento){
	return elemento.includes('#') ? elemento : '#' + elemento;
}

/*
 Función que inicializa un combo (lo pone vacio y/o vacío con opción "Elija una opcion"
 para ello se pasa el nombre del combo y un booleam 
 true si queremos que se añada la opcion por defecto (elija Opcion) 
 o false que no cargue nada 
*/
function inicializaCombo(combo, inicializaTodo){
	combo = defineElemento(combo);
	
	$(combo).empty();
	
	if(inicializaTodo){
		$(combo).append($('<option>', {value : '',text : ''}));
		}
	
	$(combo).trigger("chosen:updated"); 
}

/*
Función que realiza la configuración para que actualice correctamente los combos durante el evento change
Se le pasa el nombre del combo
*/
function configuraCombo(combo){
	combo = defineElemento(combo);
	
	$(combo).change();
	$(combo).trigger("chosen:updated"); 
}
				
/*
Funcion que establece el origen de datos de los combos.
Es importante que estos venga con el formato CLAVE - VALOR sino no funciona
Es necesario pasar el nombre del combo y previamente establecer a la variable global
origenDatos los datos que se le van establecer, normalmente este origen suele ser la respuesta de un ajax
*/			
function setOrigenDatos(combo){
	var nombreCombo = defineElemento(combo);

	$.each(origenDatos, function(i, fila) {
			$(nombreCombo).append($('<option>', {value : fila.clave,text : fila.valor}));
		});
	
	}

/*
Funcion para mostrar o ocultar un elemento html
Parametros elemento nombre del elemento que queremos mostrar o ocultar
Parametros esVisible true si lo queremeos mostrar y fase si lo queremos ocultar
*/
function mostrarElemento(elemento, esVisible){
    
     elemento = defineElemento(elemento);  

    if(esVisible){
    	  $(elemento).removeClass('hidden');
    }else{
    	$(elemento).addClass('hidden');
	}
}

/*
Funcion que determina si el valor del select esta vacio o no
*/
function valorSelectVacio(select){
		select = defineElemento(select);
		return $(!$ (select).val ()) ? false : true;
	}