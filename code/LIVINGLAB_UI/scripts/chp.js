
var chp = (function () {

	varList_Id = "#userList";
	value_Id = "#value";
	
	//-------FUNCIONES PRIVADAS ------
	function sendValue(){
		var user=$(varList_Id).val();
		var value=$(value_Id).val();
		$.ajax({
			type : 'GET',
			url : "http://localhost/livinglab.public.cep.rest/cep/"+user+"/"+value,
			contentType : 'text/xml;charset=UTF-8',
			processData : false,
			success : function(a,b,c){console.log("SUCCESS");},
			complete : function(a,b,c){console.log("COMPLETE");},
			error : function(a,b,c){console.log("ERROR");}
		});
	};
	
	function loadSelect() {
		dataModel.loadHomeDevices("CHP", function () {
			dataModel.loadDeviceVariables("CHP_P","CHP",function(){
				vList=dataModel.getDev("CHP_P").varList;
				
				for (var v = 0; v < vList.length; v++) {
					vmd=vList[v].varMetadata;
					$(varList_Id).append("<option value='"+vmd.varName+"'>"+vmd.varDesc+"</option>");
				}
				$(varList_Id).trigger("change");
			});
		});
	
	};
	
	
	
	//-------FUNCIONES PUBLICAS--------
	return {
		init : function () {
			dataModel.init(loadSelect);
		},
		sendValue: function(){
			sendValue();
		}
	};
})();