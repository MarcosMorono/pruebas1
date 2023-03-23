/**
 * @type Object MAQINT customize file.
 * 
 * Establishes basic configuration parameters. You can use tokens in parameter's
 * value.
 * @see www/js/core/config.js (tokenizer config)
 * 
 * @see Páxina 4.2 do manual de uso da maqueta corporativa
 */
var customize = {
    // Config de idioma
    lang : {
        // Indica se está habilitado ou non o selector de idioma
        enableLangSelector : true,
        // Indica o idioma por defecto (esto se indica na configuración de Spring)
        /*default: "gl"*/
    },
    // Config de contorno. Este bloque vai por configuración da aplicación no servidor.
    // env: {
        // Indica se se debe amosar a info de contorno (vai por configuración)
        /*showEnvDisplay: true,*/
        // Indica o nome do contorno (vai por configuración)
        /*envName: "CONTORNA DE PROBAS"*/
    // },
    // Config de interface de usuario
    ui : {
        // Indica o estado do menú lateral
        leftMenu: {
            // Estado por defecto en cada recarga de páxina. Valores: 0 [pechado], 1 [aberto]
            defaultState: 1,
            // Tenta auto-detectar a páxina na que estamos a navegar e indicar no menú lateral a opción que corresponde coa páxina actual (NON FUNCIONA BEN)
            //autoActive: true
        },
        // Estrutura da páxina
        structure : {
            // Composición por defecto. Valores: main [estrutura normal], nom [oculta os menús], nlm [oculta menú lateral], ntm [oculta menú superior], nph [oculta cabeceira]
            defaultState : "main"
        },
        // Alto contraste
        highContrast : {
            // Se está activado por defecto
            defaultState : false
        }
    }
};