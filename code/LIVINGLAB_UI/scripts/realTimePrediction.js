var realTime = (function () {
	
	//-Identificadores--
	var id_popMaxRes = "#popMaxRes"
	var id_maxList="#maxList"
	var id_sList='#sList';
	var id_divPane="#divPane"
	var id_graphList="#graphList";

	var dataCol,
	datePicker;

	//Colores en formato rgb
	var colorsNum = [
		//'0,0,0',
		//'0,0,128',
		'0,0,255',
		'0,128,0',
		'0,128,128',
		'0,128,255',
		'0,255,0',
		'0,255,128',
		'0,255,255',
		'128,0,0',
		'128,0,128',
		'128,0,255',
		'128,128,0',
		'128,128,128',
		'128,128,255',
		'128,255,0',
		'128,255,128',
		'128,255,255',
		'255,0,0',
		'255,0,128',
		'255,0,255',
		'255,128,0',
		'255,128,128',
		'255,128,255',
		'255,255,0',
		'255,255,128'];

	var graphCol;

	function check_go(page) {
		$(id_sList).hide();
		$(id_divPane).show();
		var selectedVars = dataModel.getChecked();

		if (selectedVars.length > 0) {
			$.mobile.changePage(page, "none");
			readyGraphs(selectedVars);

		} else
			alert("No variables selected");
	};

	function readyGraphs(selVars) {
		$(id_graphList).empty()
		$(id_sList).empty()
		graphCol = new HashMap();

		var s = new sync(selVars.length);
		for (var i = 0; i < selVars.length; i++) {
			var v = selVars[i];
			var vg = new dataVar();
			vg.name = v.varName;
			vg.color = "rgb(" + colorsNum[i % colorsNum.length] + ")";
			vg.pred_color = "rgba(" + colorsNum[i % colorsNum.length] + ",0.3)";
			if (!graphCol.get(v.varMeasUnit)) {
				$(id_graphList).append('<li id="d_' + i + '"/>')
				var dS = new Graph("#d_" + i, v.varMeasUnit);
				graphCol.put(v.varMeasUnit, dS)
			}
			graphCol.get(v.varMeasUnit).addVar(vg);
			getVarValues(v, graphCol, s); //graphCol.updateData();
			
		}

		if (selVars.length > 3)
			addGridButton(id_sList, 3, selVars);
		else
			addGridButton(id_sList, selVars.length, selVars);
	};

	function updateData() {
		var d = new Date();
		// push a new data point onto the back
		var gCol = graphCol.values();
		var s2 = new sync(gCol.length);
		for (var k = 0; k < gCol.length; k++) {
			var gVars = gCol[k].group;
			for (var g = 0; g < gVars.length; g++) {
				getLastValue(gVars[g].name, gVars[g].data, s2);
			}
		}
	};

	/*function updatePrediction() {
		var d = new Date();
		// push a new data point onto the back
		var gCol = graphCol.values();
		var s2 = new sync(gCol.length);
		for (var k = 0; k < gCol.length; k++) {
			var gVars = gCol[k].group;
			for (var g = 0; g < gVars.length; g++) {
				getPrediction(gVars[g].name, gVars[g].pred_data, s2);
			}
		}
	};*/
	
	
	function updateDate() {
		var d = new Date();
		var toShow = $("#select-choice-1").val() * 1000;
		// push a new data point onto the back
		var gCol = graphCol.values();
		for (var k = 0; k < gCol.length; k++) {
			gCol[k].updateGraph(d.getTime() - toShow, d.getTime());
		}
	};


	var mR;
	var maxL=[];
	function getMaxResults_call(f) {

		var soapRequest = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' " +
			"xmlns:home='http://home.eefrmwrk.tekniker.es/'>" +
			"<soapenv:Header/>" +
			"<soapenv:Body>" +
			"<home:getMaxResults></home:getMaxResults>" +
			"</soapenv:Body>" +
			"</soapenv:Envelope>";
		
		var vList = [];

		$.ajax({
			type : 'POST',
			url : homeWS_URL,
			data : soapRequest,
			contentType : 'text/xml;charset=UTF-8',
			processData : false,
			success : getVarValues_success,
			complete : getVarValues_complete,
			error : alert_error
		});
		
		function getVarValues_success(xml, status, xhr) {
			mR=$(xml).find('maxResult').text();
			console.log($(xml).find('maxResult'))
		};

		function getVarValues_complete() {
				f;
		}
		function alert_error(xhr, status, error) {
			var xmlError = $.parseXML(xhr.responseText);
			console.log(xmlError);
			alert($(xmlError).find('faultstring').text());
		}
	};

	function getVarValues(varMetadata, setCol, s) {

		var d = new Date();
		getVarValues_call(varMetadata, (d.getTime() - (1000*60*60*24*7)), d.getTime());

		function getVarValues_call(varMetadata, date1, date2, absolut) {
			var soapRequest = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' " +
				"xmlns:home='http://home.eefrmwrk.tekniker.es/'>" +
				"<soapenv:Header/>" +
				"<soapenv:Body>" +
				"<home:getVariableValues>" +
				"<varName>" + varMetadata.varName + "</varName>";
			if (date1)
				soapRequest = soapRequest + "<initialDate>" + date1 + "</initialDate>";
			if (date2)
				soapRequest = soapRequest + "<finalDate>" + date2 + "</finalDate>";
			if (absolut)
				soapRequest = soapRequest + "<absolut>" + absolut + "</absolut>";

			soapRequest = soapRequest + "</home:getVariableValues>" +"</soapenv:Body>" +"</soapenv:Envelope>";

			var vList = [];
			var predList = [];
			
			$.ajax({
				type : 'POST',
				url :  homeWS_URL,
				data : soapRequest,
				contentType : 'text/xml;charset=UTF-8',
				processData : false,
				success : getVarValues_success,
				complete : getVarValues_complete,
				error : getVarValues_error
			});

			function getVarValues_success(xml, status, xhr) {
				$(xml).find('variable').each(function (index, element) {
					var v = new Value();
					v.value = $(element).find('value').text();
					v.timestamp = $(element).find('timestamp').text();
					vList.push(v);
				});
				if(mR==vList.length)
					maxL.push(varMetadata.varName);
			};

			function getVarValues_error(xhr, status, error) {
				if (xhr.status == 500) {
					var xmlError = $.parseXML(xhr.responseText);
					console.log(xmlError);
					alert($(xmlError).find('faultstring').text());
				} else if (xhr.status == 0) {
					//Llamada abortada (Si el usuario cambia de página antes de finalizar, por ejemplo
					//NO HACER NADA
				} else {
					console.log(error);
					alert(xhr.status + ":" + xhr.statusText);
				}
			};

			function getVarValues_complete(xhr, status) {
				if (status == "success") {
					var g = setCol.get(varMetadata.varMeasUnit)
						for (var i = 0; i < g.group.length; i++) {
							if (g.group[i].name == varMetadata.varName) {
								g.group[i].data = vList;
								g.group[i].pred_data = predList;
								g.updateGraph();
								if (s.dec()) {
									showIfMax();
									updateData();
								}
							}
						}
				}
			};
		}
	}

	function showIfMax(){
	if(maxL.length>0){
		$(id_maxList).empty();
		$(id_maxList).append("<li data-role='list-divider'>Too many values. Graphs will NOT be fully displayed for:</li>");
		for (var i = 0; i < maxL.length; i++) {
			$(id_maxList).append('<li>'+maxL[i]+'</li>');
		}		
		$(id_maxList).listview("refresh");

		$(id_popMaxRes).popup("open");}
	}

	function showLoader() {
		setTimeout(function () {
			$.mobile.loading('show');
		});
	};
	function hideLoader() {
		setTimeout(function () {
			$.mobile.loading('hide');
		});
	};

	function goFetchBoy() {
		alert("Variables must be selected first");
		window.location.href = "varTree.html";

	};
//-------------------------------
	function getLastValue(varName, varData, s) {
		var soapRequest = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' " +
			"xmlns:home='http://home.eefrmwrk.tekniker.es/'>" +
			"<soapenv:Header/>" +
			"<soapenv:Body>" +
			"<home:getVariableValue>" +
			"<varName>" + varName + "</varName>" +
			"</home:getVariableValue>" +
			"</soapenv:Body>" +
			"</soapenv:Envelope>";
		$.ajax({
			type : 'POST',
			url :  homeWS_URL,
			data : soapRequest,
			contentType : 'text/xml;charset=UTF-8',
			processData : false,
			success : getLastValue_success,
			complete : getLastValue_complete,
			error : getLastValue_error
		});
		var v;
		function getLastValue_success(xml, status, xhr) {
			$(xml).find('variable').each(function (index, element) {
				v = new Value();
				v.value = $(element).find('value').text();
				v.unit = $(element).find('measureUnit').text();
				var now = new Date();
				v.timestamp = now.getTime();
			});
		};

		function getLastValue_error(xhr, status, error) {
			if (xhr.status == 500) {
				var xmlError = $.parseXML(xhr.responseText);
				console.log(xmlError);
				alert($(xmlError).find('faultstring').text());
			} else if (xhr.status == 0) {
				//Llamada abortada (Si el usuario cambia de página antes de finalizar, por ejemplo
				//NO HACER NADA
			} else {
				alert(xhr.status + ":" + xhr.statusText);
			}
		};

		function getLastValue_complete(xhr, status) {
			if (status == "success") {
				varData.push(v);
				$("[id='btn_" + varName + "']").text(v.value + " " + v.unit);
				if (s.dec()) {
					updateDate();
					hideLoader();
				}
			}
		};

	};
//---------------
	
	function updatePrediction() {

		var selVars = dataModel.getChecked();
		var d = new Date();
		var date1=d.getTime() - (1000*60*60*24*31);//obtiene datos del ultimo mes (31 dias)
		var date2=d.getTime();

		var steps=$("#txtSteps").val();
		var pred=$("#txtPred").val();
		interval=(12*300000);// intervalos de 5 minutos
		
		var soapRequest = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' "
				+ "xmlns:pred='http://prediction.eefrmwrk.tekniker.es/'>"
				+ "<soapenv:Header/>" + "<soapenv:Body>" + "<pred:createModel>";

		soapRequest = soapRequest + "<instanceTarget>cosa</instanceTarget>";
		for ( var i = 0; i < selVars.length; i++) {
			soapRequest = soapRequest + "<varNames>" + selVars[i].varName+ "</varNames>";
		}
		
		for ( var i = 0; i < selVars.length; i++) {
			soapRequest = soapRequest + "<funcNames>median</funcNames>";
		}
		for ( var i = 0; i < selVars.length; i++) {
			soapRequest = soapRequest + "<normalize>false</normalize>";
		}
		if (date1)
			soapRequest = soapRequest + "<initialDate>" + date1	+ "</initialDate>";

		if (date2)
			soapRequest = soapRequest + "<finalDate>" + date2 + "</finalDate>";

		soapRequest = soapRequest + "<interval>" + interval + "</interval>"
				+ "</pred:createModel>" + "</soapenv:Body>"
				+ "</soapenv:Envelope>";

		var vList = [];

		$.ajax({
			type : 'POST',
			url : predictionWS_URL,
			data : soapRequest,
			contentType : 'text/xml;charset=UTF-8',
			processData : false,
			success : createModel_success,
			complete : createModel_complete,
			error : alert_error
		});
		var scs=false;
		function createModel_success(xml, status, xhr) {
			console.log("createModel_success");
			scs=true;
		};

		function createModel_complete() {
			console.log("createModel_complete");
			if(scs)
			predict(updateDate);
		};

		function alert_error(xhr, status, error) {
			if (xhr.status == 500) {
				var xmlError = $.parseXML(xhr.responseText);
				console.log(xmlError);
				alert($(xmlError).find('faultstring').text());
			} else if (xhr.status == 0) {
				//Llamada abortada (Si el usuario cambia de página antes de finalizar, por ejemplo
				//NO HACER NADA
			} else {
				console.log(error);
				alert(xhr.status + ":" + xhr.statusText);
			}
		};
	};
	
	
	function predict(callback) {
	var selVars = dataModel.getChecked();
	var steps=$("#txtPred").val();
	var soapRequest = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' "
				+ "xmlns:pred='http://prediction.eefrmwrk.tekniker.es/'>"
				+ "<soapenv:Header/>"
				+ "<soapenv:Body>"
				+ "<pred:predict>" 
				+ "<steps>10</steps>" 
				+ "</pred:predict>"
				+ "</soapenv:Body>" + "</soapenv:Envelope>";

		var vList = [];
		maxL = [];

		$.ajax({
			type : 'POST',
			url : predictionWS_URL,
			data : soapRequest,
			contentType : 'text/xml;charset=UTF-8',
			processData : false,
			success :predict_success,
			complete : predict_complete,
			error : alert_error
		});

		var predData;
		
		function predict_success(xml, status, xhr) {
			console.log('predict_success');
			predData =new HashMap();
			$(xml).find('result').each(function (index, element) {
				v = new Value();
				v.name = $(element).find('name').text();
				v.value = $(element).find('value').text();
				v.unit = $(element).find('measureUnit').text();
				v.timestamp = $(element).find('timestamp').text();	
				if (!predData.get(v.name)){
					predData.put(v.name,[])
				}
				predData.get(v.name).push(v);
			});
		};

		function predict_complete() {
			for(j=0;j<predData.values().length;j++){
				var g = graphCol.get(predData.values()[j][0].unit);
				for(i=0; i<g.group.length;i++){
				g.group[i].pred_data=predData.get(g.group[i].name);
			}
			}
			callback();
			
		
		};
		
		function alert_error(xhr, status, error) {
			if (xhr.status == 500) {
				var xmlError = $.parseXML(xhr.responseText);
				console.log(xmlError);
				alert($(xmlError).find('faultstring').text());
			} else if (xhr.status == 0) {
				//Llamada abortada (Si el usuario cambia de página antes de finalizar, por ejemplo
				//NO HACER NADA
			} else {
				console.log(error);
				alert(xhr.status + ":" + xhr.statusText);
			}
		};
	
};

	function addGridButton(container, cN, vars) {
		var colNum;
		if (cN == 2) {
			colNum = 2;
			gridType = "ui-grid-a";
		} else if (cN == 3) {
			colNum = 3;
			gridType = "ui-grid-b";
		} else if (cN == 4) {
			colNum = 4;
			gridType = "ui-grid-c";
		} else if (cN == 5) {
			colNum = 5;
			gridType = "ui-grid-d";
		} else {
			colNum = 1;
			gridType = "ui-grid-solo";
		}

		var c = colNum;
		for (var i = 0; i < vars.length; i++) {
			if (c == (colNum)) {
				c = 1;
				gridID = "gr_" + i;
				$(container).append('<div id="' + gridID + '" class=' + gridType + '>');
				$("#" + gridID).append('<div class="ui-block-b" id="d_' + vars[i].varName + '">' +
					'<div data-role="button">' +
					'<div id="btn_' + vars[i].varName + '" class="val" style="font-size: 200%">wait...</div>' +
					'<div class="name">' + vars[i].varName + '</div>' +
					'</div>');
			} else {
				c++;
				$("#" + gridID).append('<div class="ui-block-b" id="d_' + vars[i].varName + '">' +
					'<div data-role="button">' +
					'<div id="btn_' + vars[i].varName + '"  class="val" style="font-size: 200%">wait...</div>' +
					'<div>' + vars[i].varName + '</div>' +
					'</div>');
			}

		}
		$(container).trigger("create");

		for (var i = 0; i < vars.length; i++) {
			//console.log($('[id="d_' + vars[i].varName + '"] .ui-btn-inner'));
			$('[id="d_' + vars[i].varName + '"] .ui-btn-inner')
			//$("." + vars[i].varName + " .ui-btn-inner")
			.attr("style", "background-color:" +
				"rgba(" + colorsNum[i % colorsNum.length] + ",0.5);");
		}

	};

	//callback a realizar cuando se tenga el modelo cargado
	function firstLoad() {

		var selectedVars = dataModel.getChecked();
		if (selectedVars.length > 0) {
			showLoader();
			getMaxResults_call(readyGraphs(selectedVars));
			updateInterval = setInterval(function () {updateData();}, 3000);
			updatePrediction();
			predictInterval = setInterval(function () {updatePrediction();}, 30000);//cambiar a 300000 (5 min)
		} else {
			goFetchBoy();
		}

	};

	return {

		init : function () {
			this.togglePane(true);
			dataModel.init(firstLoad);
		},
		setDate : function () {
			updateDate();
		},
		togglePane : function (a) {
			if (a) {
				$(id_sList).hide();
				$(id_divPane).show();
			} else {
				$(id_divPane).hide();
				$(id_sList).show();
			}
		}
	}
})();
