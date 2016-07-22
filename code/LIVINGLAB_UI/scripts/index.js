//Requisitos :   dataModel.js está declarado

var index = (function () {
	var varListId="#list_selectedVar";
	var varTreeLinkId="#link_varTree";

	showLoader = function () {
		setTimeout(function () {
			$.mobile.loading('show',{text: "Loading model. Wait...",
					textVisible: true});
		});
	};

	hideLoader = function () {
		setTimeout(function () {
			$.mobile.loading('hide');
		});
	};
	
	loadSelectVarList = function () {
	
	 $(varListId).empty();
	 var selectVars=dataModel.getChecked();
	 
	 if(selectVars.length>0)
	 { 
		//$(varTreeLinkId).
		$(varListId).append('<li data-role="list-divider">Selected Variables'+'</li>');
		for(var i=0;i<selectVars.length;i++){
			$(varListId).append('<li>'+selectVars[i].varDesc+'</li>');
		}
	 }
	
	$(varListId+":visible").listview("refresh");
	hideLoader();		
	};

	return {
		init : function () {
			showLoader();
			dataModel.init(loadSelectVarList);
		}
	}

})();
