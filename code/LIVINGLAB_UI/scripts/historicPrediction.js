var historicPrediction = (function() {

	var id_graphList = "#graphList";
	var id_popMaxRes = "#popMaxRes"
	var id_maxList = "#maxList"

	var msg_alert_SelectVariables = "Variables must be selected first", 
	msg_alert_ChooseDate1 = "Choose initial and final date", 
	msg_alert_ChooseDate2 = "Choose initial date", 
	msg_alert_ChooseDate3 = "Choose final date";

	var fechasOK;

	var dateP, // Selector de fechas
	setCol;

	var colors =     [ '0,0,255', '0,255,0', '255,0,0','255,255,0' ];
	var colors_p =   [ '0,0,155', '0,155,0', '155,0,0', '155,155,0'];

	
	
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

	function readyGraphs(selVars) {
		showLoader();
		$(id_graphList).empty()
		setCol = new HashMap();

		var sc = new sync(selVars.length*3);
		for ( var i = 0; i < selVars.length; i++) {
			var v = selVars[i];
			var vg = new dataVar();
			vg.name = v.varName;
			vg.color = "rgba(" + colors[i % colors.length] + ",0.2)";
			if (!setCol.get(v.varMeasUnit)) {
				$(id_graphList).append('<li id="d_' + i + '"/>')
				var dS = new Graph("#d_" + i, v.varMeasUnit);
				setCol.put(v.varMeasUnit, dS)
			}
			setCol.get(v.varMeasUnit).addVar(vg);

			var vg_s = new dataVar();
			vg_s.name = "SAMP_" + v.varName;
			vg_s.color = "rgb(" + colors[i % colors.length] + ")";
			setCol.get(v.varMeasUnit).addVar(vg_s);
			
			var vg_p = new dataVar();
			vg_p.name = "PRED_" + v.varName;
			vg_p.color = "rgb(" + colors_p[i % colors.length] + ")";
			setCol.get(v.varMeasUnit).addVar(vg_p);

			getVarValues(v, sc);
			getSampled(v,sc);
			getPrediction(v, sc)
		}
	};


	
	var mR;
	var maxL = [];
	function getMaxResults_call(f) {

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
			// console.log($(xml).find('maxResult'))
		}
		;

		function getMaxResults_complete() {
			f;
		}
		function alert_error(xhr, status, error) {
			var xmlError = $.parseXML(xhr.responseText);
			console.log(xmlError);
			alert($(xmlError).find('faultstring').text());
		}
	}
	;

	function getVarValues(varMetadata, sc) {

		var absolut = $("input[name='radio-choice-2']:checked").val();
		var date1 = dtP.d1.getTime();
		var date2 = dtP.d2.getTime();

		var soapRequest = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' "
				+ "xmlns:home='http://home.eefrmwrk.tekniker.es/'>"
				+ "<soapenv:Header/>"
				+ "<soapenv:Body>"
				+ "<home:getVariableValues>"
				+ "<varName>"
				+ varMetadata.varName + "</varName>";
		if (date1)
			soapRequest = soapRequest + "<initialDate>" + date1
					+ "</initialDate>";
		if (date2)
			soapRequest = soapRequest + "<finalDate>" + date2 + "</finalDate>";

		soapRequest = soapRequest
				+ "<absolut>absolut</absolut></home:getVariableValues>"
				+ "</soapenv:Body>" + "</soapenv:Envelope>";

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
			else {
				var old_value = v.value;
				v = new Value();
				v.value = old_value;
				v.timestamp = date2;
			}
		}
		;

		function getVarValues_complete() {
			var g = setCol.get(varMetadata.varMeasUnit)
			for ( var i = 0; i < g.group.length; i++) {
				if (g.group[i].name == varMetadata.varName) {
					g.group[i].data = vList;
					if (sc.dec()) {
						showIfMax();
						hideLoader();
						updateGraphs();
					}
				}
			}
		}

		function alert_error(xhr, status, error) {
			var xmlError = $.parseXML(xhr.responseText);
			console.log(xmlError);
			alert($(xmlError).find('faultstring').text());
		}

	}	;

	

	function createModel() {

		var selVars = dataModel.getChecked();
		var date1 = dtP.d1.getTime();
		var date2 = dtP.d2.getTime();
		var steps=$("#txtSteps").val();
		var pred=$("#txtPred").val();
		interval=Math.round((date2-date1)/steps);
		
		var soapRequest = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' "
				+ "xmlns:pred='http://prediction.eefrmwrk.tekniker.es/'>"
				+ "<soapenv:Header/>" + "<soapenv:Body>" + "<pred:createModel>";

		soapRequest = soapRequest + "<instanceTarget>cosa</instanceTarget>";
		for ( var i = 0; i < selVars.length; i++) {
			soapRequest = soapRequest + "<varNames>" + selVars[i].varName+ "</varNames>";
		}
		
		
		//funcNames y normalizar  //10/07/2014
		for ( var i = 0; i < selVars.length; i++) {
			soapRequest = soapRequest + "<funcNames>median</funcNames>";
		}
		for ( var i = 0; i < selVars.length; i++) {
			soapRequest = soapRequest + "<normalize>false</normalize>";
		}
		if (date1)
			soapRequest = soapRequest + "<initialDate>" + date1	+ "</initialDate>";

		if (date2)
			soapRequest = soapRequest + "<finalDate>" + (date2-(interval*pred)) + "</finalDate>";

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

		function createModel_success(xml, status, xhr) {
			console.log("createModel_success");
		}
		;

		function createModel_complete() {
			console.log("createModel_complete");
			predict(loadSelectedVars);
		}
		;

		function alert_error(xhr, status, error) {
			var xmlError = $.parseXML(xhr.responseText);
			console.log(xmlError);
			alert($(xmlError).find('faultstring').text());
		};
	};

	function getSampled(varMetadata, sc) {

		var soapRequest = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' "
					+ "xmlns:pred='http://prediction.eefrmwrk.tekniker.es/'>"
					+ "<soapenv:Header/>"
					+ "<soapenv:Body>"
						+ "<pred:getSampledData>"
							+ "<varNames>"+ varMetadata.varName + "</varNames>"
						+ "</pred:getSampledData>"
					+ "</soapenv:Body>" 
					+ "</soapenv:Envelope>";

			var vList = [];
			maxL = [];

			$.ajax({
				type : 'POST',
				url : predictionWS_URL,
				data : soapRequest,
				contentType : 'text/xml;charset=UTF-8',
				processData : false,
				success : getSampledData_success,
				complete : getSampledData_complete,
				error : alert_error
			});

			function getSampledData_success(xml, status, xhr) {

				$(xml).find('result').each(function(index, element) {
					console.log($(element).find('name').text())
					//if (==varMetadata){
					v = new Value();
					v.value = $(element).find('value').text();
					v.timestamp = $(element).find('timestamp').text();
					vList.push(v);//}
				});
				console.log(vList);
			};

			function getSampledData_complete() {
				var g = setCol.get(varMetadata.varMeasUnit)
				for ( var i = 0; i < g.group.length; i++) {
					if (g.group[i].name == "SAMP_"+varMetadata.varName) {
						g.group[i].data = vList;
						if (sc.dec()) {
							showIfMax();
							hideLoader();
							updateGraphs();
						}
					}
				}
			};

			function alert_error(xhr, status, error) {
				var xmlError = $.parseXML(xhr.responseText);
				console.log(xmlError);
				alert($(xmlError).find('faultstring').text());
			};
	};
	
	
	function getPrediction(varMetadata, sc) {

		var selVars = dataModel.getChecked();
		var algorithm = $("input[name='radio-choice-2']:checked").val();
		var date1 = dtP.d1.getTime();
		var date2 = dtP.d2.getTime();
		var steps=$("#txtSteps").val();
		var pred=$("#txtPred").val();
		interval=Math.round((date2-date1)/steps);
		
		interval=Math.round((date2-date1)/steps);
	
		var soapRequest = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' "
					+ "xmlns:pred='http://prediction.eefrmwrk.tekniker.es/'>"
					+ "<soapenv:Header/>"
					+ "<soapenv:Body>"
					+ "<pred:getPrediction>" 
					+ "<varNames>" +varMetadata.varName+ "</varNames>"
					+ "</pred:getPrediction>"
					+ "</soapenv:Body>" + "</soapenv:Envelope>";

			var vList = [];
			maxL = [];

			$.ajax({
				type : 'POST',
				url : predictionWS_URL,
				data : soapRequest,
				contentType : 'text/xml;charset=UTF-8',
				processData : false,
				success : getPrediction_success,
				complete : getPrediction_complete,
				error : alert_error
			});

			function getPrediction_success(xml, status, xhr) {

				$(xml).find('result').each(function(index, element) {
					if($(element).find('name').text()==varMetadata.varName){
					v = new Value();
					v.value = $(element).find('value').text();
					v.timestamp = $(element).find('timestamp').text();
					vList.push(v);}
				});
				if (mR == vList.length)
					maxL.push(varMetadata.varName);
				else {
					var old_value = v.value;
					v = new Value();
					v.value = old_value;
					v.timestamp = date2;
				}
			};

			function getPrediction_complete() {
				var g = setCol.get(varMetadata.varMeasUnit)
				for ( var i = 0; i < g.group.length; i++) {
					if (g.group[i].name == "PRED_" + varMetadata.varName) {
						g.group[i].data = vList;
						if (sc.dec()) {
							showIfMax();
							hideLoader();
							updateGraphs();
						}
					}
				}
			};
			
			function alert_error(xhr, status, error) {
				var xmlError = $.parseXML(xhr.responseText);
				console.log(xmlError);
				alert($(xmlError).find('faultstring').text());
			};
		
	};

	function predict(callback) {
		var selVars = dataModel.getChecked();
		var steps=$("#txtPred").val();
		var soapRequest = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' "
					+ "xmlns:pred='http://prediction.eefrmwrk.tekniker.es/'>"
					+ "<soapenv:Header/>"
					+ "<soapenv:Body>"
					+ "<pred:predict>" + 
					"<steps>"
					+ steps + "</steps>" + "</pred:predict>"
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

			function predict_success(xml, status, xhr) {
				console.log('predict_success');
			};

			function predict_complete() {
				callback();
			};
			
			function alert_error(xhr, status, error) {
				var xmlError = $.parseXML(xhr.responseText);
				console.log(xmlError);
				alert($(xmlError).find('faultstring').text());
			};
		
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
		var gList = setCol.values();
		for ( var i = 0; i < gList.length; i++) {
			gList[i].updateGraph(dtP.d1, dtP.d2);
		}
	}
	;

	function sync(num) {
		var n = num;
		this.dec = function() {
			n = n - 1;
			return (n == 0)
		};
	}
	;

	function goFetchBoy() {
		alert(msg_alert_SelectVariables);
		window.location.href = "varTree.html";

	}
	;

	loadSelectedVars = function() {
		console.log("Loading vars");
		var selectedVars = dataModel.getChecked();
		if (selectedVars.length > 0) {
			getMaxResults_call(readyGraphs(selectedVars));
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
			dataModel.init(createModel);
		}
	};

	return {
		init : function() {
			fechasOK = false;
			dtP = new DatePicker();
			loadSelectedDates();
			showLoader();
		},
		buttonClick : function() {
			if (fechasOK) {
				createModel();
			} else {
				loadSelectedDates();
			}
		}
	}

})();
