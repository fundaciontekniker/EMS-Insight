
var prediction = (function () {

	//Identificadores de prediction.html;
	var id_graph="#g",
		id_select="#select-choice-1",
		id_num="#num";
	
	var g;

	reloadGraph = function () {
		showLoader();
		var field = $(id_select).val();
		g.clear();
		//loadPredictionModel("model.arff", field, "Fecha", null);
		console.log(dataModel.getChecked());
	};

	isInt = function (n) {
		return n % 1 == 0;
	};

	loadPredictionModel = function (model, predField, dateField, algorithm) {

		console.log("loadPredictionModel");
		var soapRequest =
			"<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' " +
			"xmlns:pred='http://prediction.eefrmwrk.tekniker.es/'>" +
			"<soapenv:Header/>" +
			"<soapenv:Body>" +
			"<pred:loadModel>" +
			"<model>" + model + "</model>" +
			"<predictionField>" + predField + "</predictionField>" +
			"<dateField>" + dateField + "</dateField>";
		if (algorithm)
			soapRequest = soapRequest + "<algorithm>" + algorithm + "</algorithm>";

		soapRequest = soapRequest + "</pred:loadModel>" +
			"</soapenv:Body>" +
			"</soapenv:Envelope>";

		$.ajax({
			type : 'POST',
			url : predictionWS_URL,
			data : soapRequest,
			contentType : 'text/xml;charset=UTF-8',
			processData : false,
			success : loadPredictionModel_success,
			complete : loadPredictionModel_complete,
			error : alert_error
		});

		function loadPredictionModel_success(xml, status, xhr) {};

		function loadPredictionModel_complete() {
			var num = $(id_num).val();
			console.log(num)
			if (isInt(num)) {
				getModelData(num);
			} else {
				getModelData();
			}
		};

	};

	getModelData = function (showLast) {

		console.log("getModelData");
		var soapRequest =
			"<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' " +
			"xmlns:pred='http://prediction.eefrmwrk.tekniker.es/'>" +
			"<soapenv:Header/>" +
			"<soapenv:Body>" +
			"<pred:getModelData></pred:getModelData>" +
			"</soapenv:Body>" +
			"</soapenv:Envelope>";

		var vList = [];

		$.ajax({
			type : 'POST',
			url : predictionWS_URL,
			data : soapRequest,
			contentType : 'text/xml;charset=UTF-8',
			processData : false,
			success : getModelData_success,
			complete : getModelData_complete,
			error : alert_error
		});

		function getModelData_success(xml, status, xhr) {

			console.log(xml);
			$(xml).find('data').each(function (index, element) {
				//if(Number($(element).find('value').text())){
				var v = new Variable();
				v.value = $(element).find('value').text();
				v.timestamp = $(element).find('timestamp').text();
				vList.push(v);
				//}
			});
		};

		function getModelData_complete() {
			if (showLast)
				vList = vList.slice(0 - showLast);
			else
				$(id_num).val(vList.length);

			g.changeData(vList, "data", "area");
			getPrediction(10, null, vList[vList.length - 1])
		};

	};

	getPrediction = function (numPred, overlays, last) {

		console.log("getPrediction");

		console.log("Last:" + last);
		var soapRequest =
			"<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' " +
			"xmlns:pred='http://prediction.eefrmwrk.tekniker.es/'>" +
			"<soapenv:Header/>" +
			"<soapenv:Body>" +
			"<pred:getPrediction>" +
			"<numPred>" + numPred + "</numPred>";
		if (overlays) {
			for (var i = 0; i < overlays.length; i++) {
				soapRequest = soapRequest + "<overlay>" + overlays[i] + "</overlay>";
			}
		}

		soapRequest = soapRequest + "</pred:getPrediction>" +
			"</soapenv:Body>" +
			"</soapenv:Envelope>";

		var vList = [];
		vList.push(last);

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

			console.log(xml);
			$(xml).find('predicted').each(function (index, element) {
				var v = new Variable();
				v.value = $(element).find('value').text();
				v.timestamp = $(element).find('timestamp').text();
				vList.push(v);
			});
		};

		function getPrediction_complete() {
			//console.log(vList);
			g.changeData(vList, "predict", "area");
			hideLoader();
		};

	};

	Variable = function () {
		this.value = null;
		this.timestamp = null;
	};

	alert_error = function (xhr, status, error) {
		var xmlError = $.parseXML(xhr.responseText);
		console.log(xmlError);
		alert($(xmlError).find('faultstring').text());
	};

	showLoader = function () {
		setTimeout(function () {
			$.mobile.loading('show');
		});
	};

	hideLoader = function () {
		setTimeout(function () {
			$.mobile.loading('hide');
		});
	};

	return {
		init : function () {
			g = new Graph(id_graph);
			$(id_num).change(function () {reloadGraph();})			
			$(id_select).change(function () {reloadGraph();}).trigger("change");
		}
	}
})();
