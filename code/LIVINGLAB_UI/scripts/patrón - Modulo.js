/**
PATRÓN DE MODULO

Un clase auto-contenida, con metodos públicos y privados.
Suele tener un método init para inicializar;
  
Ejemplo de uso, en HTML:

  <script>
	module.init();
    $("#IdButton").on("click",function({ module.publicFunction("maa")});
  </script>
 **/

var module = (function () {

	//funciones y variables privadas
	var privateVar;
	privateFunction = function (par) {
		//console.log(par);
		//doStuff;
	};

	
	//funciones públicas
	return {
		init : function () {
			privateVar = "foo";
            console.log(privateVar);
			//Más cosas
		},
		publicFunction : function (a) {
			privateFunction(a);
			//Más cosas
		}
	}

})();

