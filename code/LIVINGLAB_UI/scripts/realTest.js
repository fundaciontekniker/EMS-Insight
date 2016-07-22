var realTime = (function () {

	//--Identificadores de realTime.html--
	var id_popMaxRes = "#popMaxRes"
	var id_maxList="#maxList"
	var id_sList='#sList';
	var id_divPane="#divPane"
	var id_graphList="#graphList";

	var no_value_text="No value";
	var no_date_text="No date";
	
	var dataCol,datePicker;

	//Colores en formato rgb
	var colorsNum = [
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
			readyGraphs(selectedVars);} 
		else
			alert("No variables selected");
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

	//-----CARGA INICIAL Y PREPARACION DE LOS GRAFICOS
	function readyGraphs(selVars) {
		$(id_graphList).empty()
		$(id_sList).empty()
		graphCol = new HashMap();

		for (var i = 0; i < selVars.length; i++) {
			var v = selVars[i];
			var vg = new dataVar();
			vg.name = v.varName;
			vg.color = "rgb(" + colorsNum[i % colorsNum.length] + ")";
			vg.alarms=vg.alarms;
			if (!graphCol.get(v.varMeasUnit)) {
				$(id_graphList).append('<li id="d_' + i + '"/>')
				var dS = new Graph("#d_" + i, v.varMeasUnit);
				graphCol.put(v.varMeasUnit, dS)
			}
			graphCol.get(v.varMeasUnit).addVar(vg);
		}
		
		if (selVars.length > 3)
			addGridButton(id_sList, 3, selVars);
		else
			addGridButton(id_sList, selVars.length, selVars);
		updateData();
		hideLoader();
	};

	/*function updateData(selVars) {
		for (var i = 0; i < selVars.length; i++) {
			getVarValues(selVars[i].varName, graphCol.get(selVars[i].varMeasUnit)); 		
		}
	};*/
	
	//--Obtienes el ultimo valor conocido de las variables. Al finalizar, dibujará y actualizará el gráfico
	function updateData() {
		var d = new Date();
		var gCol = graphCol.values();
		var gUnit = graphCol.keys();
		for (var k = 0; k < gCol.length; k++) {
			var gVars = gCol[k].group;
			var s2 = new sync(gVars.length);
			maxValues_List=[];
			noValues_List=[];
			for (var g = 0; g < gVars.length; g++) {
				if(!gVars[g].visible){
					if (s2.dec()) {
						showIfMax();
						showIfNoValues();
						hideLoader();
					};
				}
				else
					getVarValues(gVars[g].name, gCol[k],s2,gUnit[k]); 		
			}
		}
	};

	function updateDate() {
		var d = new Date();
		var toShow = $("#select-choice-1").val() * 1000;
		var gCol = graphCol.values();
		for (var k = 0; k < gCol.length; k++) {
			gCol[k].updateGraph(d.getTime() - toShow, d.getTime());
		}
	};


	var mR;
	var maxValues_List=[];
	var noValues_List=[];
	
	
	function showIfMax(){
	if(maxValues_List.length>0){
		//console.log("showIfMax");
		$(id_maxList).empty();
		$(id_maxList).append("<li data-role='list-divider'>Too many values. Graphs will NOT be fully displayed for:</li>");
		for (var i = 0; i < maxValues_List.length; i++) {
			$(id_maxList).append('<li>'+maxValues_List[i]+'</li>');
		}		
		$(id_maxList).listview("refresh");
		$(id_popMaxRes).popup("open")
		}
	};

	function showIfNoValues(){
		if(noValues_List.length>0){
			//console.log("showIfNoValues");
			$(id_maxList).empty();
			$(id_maxList).append("<li data-role='list-divider'>The next variables don't have values for the last week.</li>");
			for (var i = 0; i < noValues_List.length; i++) {
				$(id_maxList).append('<li>'+noValues_List[i]+'</li>');
			}		
			$(id_maxList).listview("refresh");
			$(id_popMaxRes).popup("open");
		}
	};

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
					'<div class="name">' + vars[i].varDesc + '</div>' +
					'<div id="btn_' + vars[i].varName + '" class="val">wait...</div>' +
					'<div id="dbtn_' + vars[i].varName + '"  class="date">wait...</div>' +
					'</div>');
			} else {
				c++;
				$("#" + gridID).append('<div class="ui-block-b" id="d_' + vars[i].varName + '">' +
					'<div data-role="button">' +
					'<div>' + vars[i].varDesc + '</div>' +
					'<div id="btn_' + vars[i].varName + '"  class="val">wait...</div>' +
					'<div id="dbtn_' + vars[i].varName + '"  class="date">wait...</div>' +
					'</div>');
			}

		}
		$(container).trigger("create");

		for (var i = 0; i < vars.length; i++) {
			$('[id="d_' + vars[i].varName + '"] .ui-btn-inner').attr("style", "background-color:" +"rgba(" + colorsNum[i % colorsNum.length] + ",0.5);");
		}
	};

	//callback a realizar cuando se tenga el modelo cargado
	function firstLoad() {
		var selectedVars = dataModel.getChecked();
		if (selectedVars.length > 0) {
			showLoader();
			getMaxResults_call(readyGraphs(selectedVars));
			updateInterval = setInterval(function(){updateData(selectedVars);}, realTime_interval);
		} else {
			goFetchBoy();
		}
	};
	
	
	//--WS CALLS
	
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
			url :  homeWS_URL,
			data : soapRequest,
			contentType : 'text/xml;charset=UTF-8',
			processData : false,
			success : getMaxResults_success,
			complete : getMaxResults_complete,
			error : alert_error
		});
		
		function getMaxResults_success(xml, status, xhr) {
			mR=$(xml).find('maxResult').text();
			//console.log($(xml).find('maxResult'))
		};

		function getMaxResults_complete() {
				f;
		};
	};

	// Carga los valores del ultimo més desde la fecha actual
	function getVarValues(varName, graph,s,unit) {
		//console.log("getVarValues "+varName);
		var d = new Date();
		getVarValues_call(varName, (d.getTime() - (1000*60*60*24*7)), d.getTime()); 

		function getVarValues_call(varName, date1, date2, absolut) {
			var soapRequest = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' " +
				"xmlns:home='http://home.eefrmwrk.tekniker.es/'>" +
				"<soapenv:Header/>" +
				"<soapenv:Body>" +
				"<home:getVariableValues>" +
				"<varName>" + varName + "</varName>";
			if (date1)
				soapRequest = soapRequest + "<initialDate>" + date1 + "</initialDate>";
			if (date2)
				soapRequest = soapRequest + "<finalDate>" + date2 + "</finalDate>";
			if (absolut)
				soapRequest = soapRequest + "<absolut>" + absolut + "</absolut>";

			soapRequest = soapRequest + "</home:getVariableValues>" +
				"</soapenv:Body>" +
				"</soapenv:Envelope>";

			var vList = [];

			$.ajax({
				type : 'POST',
				url :  homeWS_URL,
				data : soapRequest,
				contentType : 'text/xml;charset=UTF-8',
				processData : false,
				success : getVarValues_success,
				complete : getVarValues_complete,
				error : alert_error
			});

			function getVarValues_success(xml, status, xhr) {
				$(xml).find('variable').each(function (index, element) {
									var v = new Value();
					v.value = $(element).find('value').text();
					v.timestamp = $(element).find('timestamp').text();
					v.unit = unit;
					vList.push(v);
				});
				
				if(vList.length==0){
					graph.disableVar(varName);
					noValues_List.push(varName);
				}
				if(mR==vList.length)
					maxValues_List.push(varName);
			};

			function getVarValues_complete(xhr, status) {
				//console.log("getVarValues_complete"+ varName)
				if (status == "success") {
					var d = new Date();
					var toShow = $("#select-choice-1").val() * 1000;
					for (var i = 0; i < graph.group.length; i++) {
						if (graph.group[i].name == varName) {	
							
							graph.group[i].data = vList;
							if(vList.length>0){
								$("[id='btn_" + varName + "']").text(vList[vList.length-1].value + " " + vList[vList.length-1].unit);
								$("[id='dbtn_" + varName + "']").text(new Date(Number(vList[vList.length-1].timestamp)).toLocaleString());
								graph.updateGraph(d.getTime() - toShow, d.getTime());}
							else{
								$("[id='btn_" + varName + "']").text(no_value_text);
								$("[id='dbtn_" + varName + "']").text(no_date_text);
							}
						}
					}
				}
				if (s.dec()) {
					showIfMax();
					showIfNoValues();
					hideLoader();
				};
			};
		}
	}
	
	return {
		init : function () {
			this.togglePane(true);
			dataModel.init(firstLoad);
		},
		setDate : function () {updateDate();},
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
