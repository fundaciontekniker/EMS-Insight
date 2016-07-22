
var varTree = (function () {

	//-------VARIABLES PRIVADAS-----
	var electricOnly,
	goTo;

	homeList_Id = "#homeList";
	varList_Id = "#vList";

	//-------FUNCIONES PRIVADAS ------

	function sId(id){
		return "'"+id+"'";
	}

	function addCollapsibleHome(container, home) {

		//Añade un desplegable de Hogar a container
		$(container).append(
			'<div data-role="collapsible" id="home_' + home.homeName + '">' +
			'<h2>' + home.homeName +
			'<span id="hc_' + home.homeName + '" class="devCountBubble ui-btn-up-c ui-btn-corner-all" style="display:none; float:right;"></span>' +
			'</h2>' +
			'<div id="' + home.homeName + '_devs" data-role="collapsible-set" data-content-theme="c"/>' +
			'</div>');

		//Inicializa el desplegable de dispositivos de este hogar
		$("[id='" + home.homeName + "_devs']").collapsibleset();

		$("[id='home_" + home.homeName + "']").bind('expand', function () {
			if (!dataModel.getHome(home.homeName).hasLoadedDevices) {
				dataModel.loadHomeDevices(home.homeName, function () {
					for (var d = 0; d < dataModel.getHome(home.homeName).devList.length; d++) {
						addCollapsibleDevice(dataModel.getHome(home.homeName).devList[d], home);
					}
					$("[id='" + home.homeName + "_devs']").collapsibleset("refresh");
				});
			}
		});

		updateHomeCountBubbles(home.homeName);
	}

	function addCollapsibleDevice(dev, home) {
		$("[id='" + home.homeName + "_devs']").append(
			'<div data-role="collapsible" id="dev_' + dev.devName + '">' +
			'<h2>' + dev.devName +' - '+dev.desc +
			'<span id="vc_' + dev.devName + '" class="devCountBubble ui-btn-up-c ui-btn-corner-all" style="display:none; float:right;"></span>' +
			'</h2>' +
			'<ul id="' + dev.devName + '_vars" data-role="listview" data-split-icon="info" data-inset=true data-split-theme="b"/>' +
			'</div>');
		$("[id='" + dev.devName + "_vars']:visible").listview();

		$("[id='dev_" + dev.devName + "']").bind('expand', function () {
			if (!dataModel.getHome(home.homeName).getDev(dev.devName).hasLoadedVariables) {

				dataModel.loadDeviceVariables(dev.devName, home.homeName, function () {
					addCollapsibleHeader(dev, home);
					for (var v = 0; v < dataModel.getHome(home.homeName).getDev(dev.devName).varList.length; v++) {
						addCollapsibleCheckVar(dataModel.getHome(home.homeName).getDev(dev.devName).varList[v], dev, home);
					}
					$("[id='" + dev.devName + "_vars']").listview("refresh");
				});
			}
		});

		updateDevCountBubbles(dev.devName, home.homeName);
	};

	function addCollapsibleHeader(dev, home) {
		if (!electricOnly || (electricOnly && dev.hasElectricVars())) {
			$("[id='" + dev.devName + "_vars']").append(
				'<li data-theme="a">' +
				'<a href="#" onClick="varTree.toggleDev(' + sId(dev.devName) + ',' + sId(home.homeName) + ');">' +
				'<img src="img/uncheck.jpg" class="ui-li-icon"><img src="img/check.jpg" id="cb_' + dev.devName + '" class="ui-li-icon">Select All</a>' +
				'</li>');
			if (!dev.hasChecked(electricOnly))
				$("[id='cb_" + dev.devName + "']").hide();
		}
	};

	function addCollapsibleCheckVar(variable, dev, home) {
		if (!electricOnly || (electricOnly && variable.isElectricVar())) {
			$("[id='" + dev.devName + "_vars']").append(
				'<li>' +
				'<a href="#" id="v_' + variable.varMetadata.varName + '" name="' + variable.varMetadata.varName + '" class="checkVar">' +
				'<img src="img/uncheck.jpg" class="ui-li-icon"><img src="img/check.jpg" id="cb_' + variable.varMetadata.varName + '" class="cbox ui-li-icon">' + variable.varMetadata.varDesc + '</a>' +
				'<a href="#popVar" onCLick="varTree.loadVarInfo('+ sId(variable.varMetadata.varName) + ',' + sId(dev.devName) + ',' + sId(home.homeName) + ')"; data-rel="popup" data-position-to="window" data-transition="pop">Variable info</a>' +
				'</li>');
			$("[id='v_" + variable.varMetadata.varName + "']").bind("click", function () {
				setCheck(variable.varMetadata.varName, dev.devName, home.homeName, !variable.selected);
			});
			if (!variable.selected)
				$("[id='cb_" + variable.varMetadata.varName + "']").hide();
		}
	};

	function toggleDev(devName, homeName) {
		if (dataModel.getHome(homeName).getDev(devName).hasChecked(electricOnly)) {
			$("[id='" + devName + "_vars'] .cbox").hide();
			dataModel.getHome(homeName).getDev(devName).setAllChecked(false, electricOnly);
		} else {
			$("[id='" + devName + "_vars'] .cbox").show();
			dataModel.getHome(homeName).getDev(devName).setAllChecked(true, electricOnly);
		}
		updateDevCountBubbles(devName, homeName);
		updateHomeCountBubbles(homeName);
	};

	function updateHomeCountBubbles(homeName) {
		var c = dataModel.getHome(homeName).getChecked(electricOnly);
		if (c.length > 0) {
			$("[id='hc_" + homeName + "']").show();
			$("[id='hc_" + homeName + "']").text(c.length);
		} else {
			$("[id='hc_" + homeName + "']").hide();
		}
	}

	function updateDevCountBubbles(devName, homeName) {
		var c = dataModel.getHome(homeName).getDev(devName).getChecked(electricOnly);
		if (c.length > 0) {
			$("[id='cb_" + devName + "']").show();
			$("[id='vc_" + devName + "']").show();
			$("[id='vc_" + devName + "']").text(c.length);
		} else {
			$("[id='cb_" + devName + "']").hide();
			$("[id='vc_" + devName + "']").hide();
		}
	}

	function setCheck(varName, devName, homeName, state) {
		dataModel.getHome(homeName).getDev(devName).getVar(varName).selected = state;
		if (state) {
			$("[id='cb_" + varName + "']").show();
		} else {
			$("[id='cb_" + varName + "']").hide();
		}

		updateDevCountBubbles(devName, homeName);
		updateHomeCountBubbles(homeName);

	};

	 function loadVarInfo(varName, devName, homeName) {
		var v = dataModel.getHome(homeName).getDev(devName).getVar(varName).varMetadata;
		$(varList_Id).empty();
		$(varList_Id).append("<li data-role='list-divider'>" + v.varName + "</li>");
		if (v.varMeasUnit)
			$(varList_Id).append("<li>Measure Unit: " + v.varMeasUnit + "</li>");
		if (v.varDigType)
			$(varList_Id).append("<li>Digital Type: " + v.varDigType + "</li>");
		if (v.varPhyType)
			$(varList_Id).append("<li>Physical Type: " + v.varPhyType + "</li>");
		if (v.varURI)
			$(varList_Id).append("<li>Uri: " + v.varUri + "</li>");
		if (v.varAccess)
			$(varList_Id).append("<li>Access: " + v.varAccess + "</li>");
		if (v.varLoc)
			$(varList_Id).append("<li>Localization: " + v.varLoc + "</li>");
		$(varList_Id).listview('refresh');
	};

	function loadCollapsible() {
		for (var h = 0; h < dataModel.getHomeList().length; h++) {
			var home = dataModel.getHomeList()[h];
			addCollapsibleHome(homeList_Id, home);
			if (home.hasLoadedDevices) {
				for (var d = 0; d < home.devList.length; d++) {
					var dev = home.devList[d];
					addCollapsibleDevice(dev, home);
					if (dev.hasLoadedVariables) {
						addCollapsibleHeader(dev, home);
						for (var v = 0; v < dev.varList.length; v++) {
							var variable = dev.varList[v];
							addCollapsibleCheckVar(variable, dev, home);
						}
					}
				}
			}
		}
		//$(homeList_Id).trigger('create');
	};
	
	
	function clearSelection() {
		for ( var h = 0; h < dataModel.getHomeList().length; h++) {
			var home = dataModel.getHomeList()[h];
			for ( var d = 0; d < home.devList.length; d++) {
				var dev = home.devList[d];
				for ( var v = 0; v < dev.varList.length; v++) {
					var vr = dev.varList[v];
					setCheck(vr.varMetadata.varName, dev.devName, home.homeName, false);
				}
			}
		}
	};
		//$(homeList_Id).trigger('create');
		
	

	//-------FUNCIONES PUBLICAS--------
	return {
		init : function () {
			dataModel.init(loadCollapsible);
		},
		
		clearSelection:function(){ 
			clearSelection();
		},
		
		loadVarInfo:function(varName, devName, homeName){
			loadVarInfo(varName, devName, homeName);
		},
		
		toggleDev:function(devName, homeName){
			toggleDev(devName, homeName);
		},
		store : function () {

			//if(dataModel.getChecked().length>0){
			sessionStorage.setItem('dataCol', JSON.stringify(dataModel.getHomeList()));
			
			if(document.referrer.includes(index_URL))
				window.location.href = document.referrer;
			else
				 window.location.href = index_URL;
			//}
			//else{
			//	alert("No variables selected");
			//}
		}
	};
})();
