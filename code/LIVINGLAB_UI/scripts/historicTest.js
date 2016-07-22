
var historic = (function() {

	var id_graphList = "#graphList";
	var id_popMaxRes = "#popMaxRes"
	var id_maxList = "#maxList"
	//
	var msg_alert_SelectVariables = "Variables must be selected first", 
	    msg_alert_ChooseDate1 = "Choose initial and final date",
	    msg_alert_ChooseDate2 = "Choose initial date", 
	    msg_alert_ChooseDate3 = "Choose final date";

	var fechasOK;

	var dateP, // Selector de fechas
	dataCol,
	graphCol;

	var colors = [
		    '0,0,255', '0,128,0', '0,128,128', '0,128,255', '0,255,0', '0,255,128',
			'0,255,255', '128,0,0', '128,0,128', '128,0,255', '128,128,0',
			'128,128,128', '128,128,255', '128,255,0', '128,255,128',
			'128,255,255', '255,0,0', '255,0,128', '255,0,255', '255,128,0',
			'255,128,128', '255,128,255', '255,255,0', '255,255,128' ];
	
	showLoader = function() {
		setTimeout(function() {
			$.mobile.loading('show');
		});
	};

	hideLoader = function() {
		setTimeout(function() {
			$.mobile.loading('hide');
		});
	};
	
	//------------------------------------------
	
	
	function loadData(callback){
	
		var selVars=dataModel.getChecked();
		showLoader();
		$(id_graphList).empty()
		dataCol = new HashMap();
		areaCol = [];
		
		var sc = new sync(selVars.length);
		for (var i = 0; i < selVars.length; i++) {
			getVarValues(selVars[i],sc,callback);
		}
	};
	
	function getVarValues(varMetadata, sc,callback) {

		var absolut = $("input[name='radio-choice-2']:checked").val();

		getVarValues_call(varMetadata, dtP.d1.getTime(), dtP.d2.getTime(),absolut);
				
		function getVarValues_call(varMetadata, date1, date2, absolut) {

			var soapRequest = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' "
					+ "xmlns:home='http://home.eefrmwrk.tekniker.es/'>"
					+ "<soapenv:Header/>"
					+ "<soapenv:Body>"
					+ "<home:getVariableValues>"
					+ "<varName>"
					+ varMetadata.varName + "</varName>";
			if (date1)
				soapRequest = soapRequest + "<initialDate>" + date1+ "</initialDate>";
						
			if (date2)
				soapRequest = soapRequest + "<finalDate>" + date2+ "</finalDate>";
						
			if (absolut)
				soapRequest = soapRequest + "<absolut>" + absolut+ "</absolut>";
						

			soapRequest = soapRequest + "</home:getVariableValues>"	+ "</soapenv:Body>" + "</soapenv:Envelope>";
				
			var vList = [];
			maxL = [];

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

				$(xml).find('variable').each(function(index, element) {
					v = new Value();
					v.value = $(element).find('value').text();
					v.timestamp = $(element).find('timestamp').text();
					vList.push(v);
				});
				if (mR == vList.length)
					maxL.push(varMetadata.varName);
				/*
				 else{ 
				 var old_value=v.value; 
				  v= new Value(); v.value =
				  old_value; v.timestamp=date2; vList.push(v); 
				  }
				 */
			};
			

			function getVarValues_complete() {
				
				dataCol.put(varMetadata.varName,vList);
				if (sc.dec()) {
						callback();
						////hideLoader();
						//updateGraphs();
						}
			}

			function alert_error(xhr, status, error) {
				var xmlError = $.parseXML(xhr.responseText);
				console.log(xmlError);
				alert($(xmlError).find('faultstring').text());
			}
		}
	};

	
	//--------------------
	
	function readyGraphs() {
		var selVars =dataModel.getChecked();
		$(id_graphList).empty()
		graphCol = new HashMap();
		areaCol=[];
		
		for ( var i = 0; i < selVars.length; i++) {
			var v = selVars[i];
			var vg = new dataVar();
			vg.name = v.varName;
			vg.color = "rgb(" + colors[i % colors.length] + ")";
			vg.type = v.varPhyType;	
			vg.data=dataCol.get(vg.name);
			if (vg.type== "evento") {
				areaCol.push(vg);	
			}
		}
		
		var sc = new sync(selVars.length);
		for ( var i = 0; i < selVars.length; i++) {
			var v = selVars[i];
			var vg = new dataVar();
			vg.name = v.varName;
			vg.color = "rgb(" + colors[i % colors.length] + ")";
			vg.type = v.varPhyType;
			vg.data=dataCol.get(vg.name);
			if (vg.type != "evento") {
				if (!graphCol.get(v.varMeasUnit)) {
					$(id_graphList).append('<div id="d_' + i + '"/>')
					var gr = new Graph("#d_" + i, v.varMeasUnit);
					for(var a=0;a<areaCol.length;a++){
						gr.addArea(areaCol[a]);
					}
					graphCol.put(v.varMeasUnit, gr)
				}
				graphCol.get(v.varMeasUnit).addVar(vg);	
			}
		}
		
		
		showIfMax();
		hideLoader();
		updateGraphs();
	};
	

	var mR;			//Campo para guardar kis resultados maximos
	var maxL = [];  //Array con las variables que han superado los resultados máximos
	function getMaxResults_call(callback) {

		var soapRequest = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' "
				+ "xmlns:home='http://home.eefrmwrk.tekniker.es/'>"
				+ "<soapenv:Header/>"
				+ "<soapenv:Body>"
				+ "<home:getMaxResults></home:getMaxResults>"
				+ "</soapenv:Body>" + "</soapenv:Envelope>";

		var vList = [];

		$.ajax({
			type : 'POST',
			url : homeWS_URL,
			data : soapRequest,
			contentType : 'text/xml;charset=UTF-8',
			processData : false,
			success : getMaxResults_success,
			complete : getMaxResults_complete,
			error : alert_error
		});

		function getMaxResults_success(xml, status, xhr) {
			mR = $(xml).find('maxResult').text();
		};

		function getMaxResults_complete() {
			console.log("getMaxResultsComplete")
			callback;
		}
		function alert_error(xhr, status, error) {
			var xmlError = $.parseXML(xhr.responseText);
			console.log(xmlError);
			alert($(xmlError).find('faultstring').text());
		}
	};

	
	function showIfMax() {
		if (maxL.length > 0) {
			$(id_maxList).empty();
			$(id_maxList)
					.append(
							"<li data-role='list-divider'>Too many values. Graphs will NOT be fully displayed for:</li>");
			for ( var i = 0; i < maxL.length; i++) {
				$(id_maxList).append('<li>' + maxL[i] + '</li>');
			}
			$(id_maxList).listview("refresh");

			$(id_popMaxRes).popup("open");
		}
	}

	function updateGraphs() {
		var gList = graphCol.values();
		for ( var i = 0; i < gList.length; i++) {
			gList[i].updateGraph(dtP.d1, dtP.d2);
		}
	};

	//--Clase para sincronizar----
	function sync(num) {
		var n = num;
		this.dec = function() {
			n = n - 1;
			return (n == 0)
		};
	};

	function goFetchBoy() {
		alert(msg_alert_SelectVariables);
		window.location.href = "varTree.html";
	};

	loadSelectedVars = function() {
		var selectedVars = dataModel.getChecked();
		if (selectedVars.length > 0) {
			// readyGraphs(dataModel.getChecked());
			//getMaxResults_call(readyGraphs(dataModel.getChecked()));
			getMaxResults_call(loadData(readyGraphs));
		} else {
			goFetchBoy();
		}
	};

	loadSelectedDates = function() {
		if (!dtP.d1 && !dtP.d2) {
			alert(msg_alert_ChooseDate1);
		} else if (!dtP.d1) {
			alert(msg_alert_ChooseDate2);
		} else if (!dtP.d2) {
			alert(msg_alert_ChooseDate3);
		} else {
			fechasOK = true;
			dataModel.init(loadSelectedVars);
		}
	};

	return {
		init : function() {
			fechasOK = false;
			dtP = new DatePicker();
			loadSelectedDates();
		},
		buttonClick : function() {
			if (fechasOK) {
				loadSelectedVars();
			} else {
				loadSelectedDates();
			}
		}, 
		dataCol:function(){
			return dataCol;
		}
	}

})();

