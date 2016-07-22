

var energy = (function () {


var eGraph;
var selectedDevices=[];

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

	
//-------------------------------------------------------------
 getElectricEnergy=function() {
	showLoader();
	//Ver dispositivos seleccionados y limpiar medidas
	selectedDevices=dataModel.getCheckedDevs(true);
	for(i=0;i<selectedDevices.length;i++){
		dataModel.getDev(selectedDevices[i]).measureList=[];
	}
	
	/*//Obtener datos del formulario html
	var dt1=datePicker.d1;
	var dt2=datePicker.d2;
	if(!dt1){dt1=null;}else{dt1=dt1.getTime()};
	if(!dt2){dt2=null;}else{dt2=dt2.getTime()};*/
	
	var absolut = $('input:radio[name=mSel]:checked').val();
	var action = $('input:radio[name=mType]:checked').val();
	
	if(action=="consumption"){
	return getGroupConsumptionCall(selectedDevices, null, null, null, absolut);	
	}
	else{
	return getGroupGeneratedEnergyCall(selectedDevices, null, null, null, absolut);	
	}
	

};
function getGroupConsumptionCall(devNames, initialDate, finalDate, duration, absolut) {
	console.log("getGroupConsumptionCall("+devNames+","+ initialDate+","+ finalDate+","+duration+","+absolut+")");

	if (devNames.length == 0) {
		alert("No hay dispostitvos selecionados");
		return false;
	}
	var soapRequest =
		"<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' " +
		"xmlns:ener='http://energy.eefrmwrk.tekniker.es/'>" +
		"<soapenv:Header/>" +
		"<soapenv:Body>" +
		"<ener:getGroupConsumption>";

	for (i = 0; i < devNames.length; i++)
		soapRequest = soapRequest + "<devName>" + devNames[i] + "</devName>";
	if (initialDate)
		soapRequest = soapRequest + "<initialDate>" + initialDate + "</initialDate>";
	if (finalDate)
		soapRequest = soapRequest + "<finalDate>" + finalDate + "</finalDate>";
	if (duration)
		soapRequest = soapRequest + "<duration>" + duration + "</duration>";
	if (absolut)
		soapRequest = soapRequest + "<absolut>" + absolut + "</absolut>";
	soapRequest = soapRequest + "</ener:getGroupConsumption>" +
		"</soapenv:Body>" +
		"</soapenv:Envelope>";

	$.ajax({
		type : 'POST',
		url : energyWS_URL,
		data : soapRequest,
		contentType : 'text/xml;charset=UTF-8',
		processData : false,
		success : loadMeasureList,
		error : alert_error,
		complete : loadGraphData
	});
	return true;
};

function getGroupGeneratedEnergyCall(devNames, initialDate, finalDate, duration, absolut) {
	console.log("getGroupGeneratedCall("+devNames+","+ initialDate+","+ finalDate+","+duration+","+absolut+")");
	if (devNames.length == 0) {
		alert("No hay dispostitvos selecionados");
		return false;
	}
	var soapRequest =
		"<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' " +
		"xmlns:ener='http://energy.eefrmwrk.tekniker.es/'>" +
		"<soapenv:Header/>" +
		"<soapenv:Body>" +
		"<ener:getGroupGeneratedEnergy>";

	for (i = 0; i < devNames.length; i++)
		soapRequest = soapRequest + "<devName>" + devNames[i] + "</devName>";
	if (initialDate)
		soapRequest = soapRequest + "<initialDate>" + initialDate + "</initialDate>";
	if (finalDate)
		soapRequest = soapRequest + "<finalDate>" + finalDate + "</finalDate>";
	if (duration)
		soapRequest = soapRequest + "<duration>" + duration + "</duration>";
	if (absolut)
		soapRequest = soapRequest + "<absolut>" + absolut + "</absolut>";
	soapRequest = soapRequest + "</ener:getGroupGeneratedEnergy>" +
		"</soapenv:Body>" +
		"</soapenv:Envelope>";
		
	$.ajax({
		type : 'POST',
		url : energyWS_URL,
		data : soapRequest,
		contentType : 'text/xml;charset=UTF-8',
		processData : false,
		success : loadMeasureList,
		error : alert_error,
		complete : loadGraphData
	});
	return true;
};

function getLocalizatedConsumption() {
	//Limpiar array
	measureList = [];

	//Obtener datos del formulario html
	var localization = $("#loc").val();
	var initialDate = null;
	var finalDate = null;
	var duration = null;
	var absolut = $('input:radio[name=selMode]:checked').val();

	//Llamada al servicio.
	getLocalizatedConsumptionCall(localization, initialDate, finalDate, duration, absolut);
};

function getLocalizatedConsumptionCall(localization, initialDate, finalDate, duration, absolut) {

	var soapRequest =
		"<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' " +
		"xmlns:ener='http://energy.eefrmwrk.tekniker.es/'>" +
		"<soapenv:Header/>" +
		"<soapenv:Body>" +
		"<ener:getLocalizatedConsumption>";
	if (localization)
		soapRequest = soapRequest + "<localization>" + localization + "</localization>";
	if (initialDate)
		soapRequest = soapRequest + "<initialDate>" + initialDate + "</initialDate>";
	if (finalDate)
		soapRequest = soapRequest + "<finalDate>" + finalDate + "</finalDate>";
	if (duration)
		soapRequest = soapRequest + "<duration>" + duration + "</duration>";
	if (absolut)
		soapRequest = soapRequest + "<absolut>" + absolut + "</absolut>";
	soapRequest = soapRequest + "</ener:getLocalizatedConsumption>" +
		"</soapenv:Body>" +
		"</soapenv:Envelope>";

	//Importane! AJAX realiza llamada  de forma ASINCRONA. No bloquea proceso.
	$.ajax({
		type : 'POST',
		url : energyWS_URL,
		data : soapRequest,
		contentType : 'text/xml;charset=UTF-8',
		processData : false,
		success : loadMeasureList,
		error : alert_error,
		complete : loadGraphData
	});
	//console.log("Esto se escribirá antes de que acabe la llmada AJAX!")
};

function getLocalizatedGeneratedEnergy() {
	//Limpiar array
	measureList = [];

	//Obtener datos del formulario html
	var localization = $("#loc").val();
	var initialDate = null;
	var finalDate = null;
	var duration = null;
	var absolut = $('input:radio[name=selMode]:checked').val();

	//Llamada al servicio.
	getLocalizatedGeneratedEnergyCall(localization, initialDate, finalDate, duration, absolut);
};

function getLocalizatedGeneratedEnergyCall(localization, initialDate, finalDate, duration, absolut) {

	var soapRequest =
		"<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' " +
		"xmlns:ener='http://energy.eefrmwrk.tekniker.es/'>" +
		"<soapenv:Header/>" +
		"<soapenv:Body>" +
		"<ener:getLocalizatedGeneratedEnergy>";
	if (localization)
		soapRequest = soapRequest + "<localization>" + localization + "</localization>";
	if (initialDate)
		soapRequest = soapRequest + "<initialDate>" + initialDate + "</initialDate>";
	if (finalDate)
		soapRequest = soapRequest + "<finalDate>" + finalDate + "</finalDate>";
	if (duration)
		soapRequest = soapRequest + "<duration>" + duration + "</duration>";
	if (absolut)
		soapRequest = soapRequest + "<absolut>" + absolut + "</absolut>";
	soapRequest = soapRequest + "</ener:getLocalizatedGeneratedEnergy>" +
		"</soapenv:Body>" +
		"</soapenv:Envelope>";

	//Importane! AJAX realiza llamada  de forma ASINCRONA. No bloquea proceso.
	//
	$.ajax({
		type : 'POST',
		url : energyWS_URL,
		data : soapRequest,
		contentType : 'text/xml;charset=UTF-8',
		processData : false,
		success : loadMeasureList,
		error : alert_error,
		complete : loadGraphData
	});
	//console.log("Esto se escribirá antes de que acabe la llmada AJAX!")
};


//------------------------------------------------------------------
function loadMeasureList(xml, status, xhr) {

	$(xml).find('values').each(function (index, element) {
		var measure = new GeneralElectricMeasure();
		measure.devName = $(element).find('deviceId').text();
		measure.activeIn = $(element).find('activeIn').text();
		measure.activeOut = $(element).find('activeOut').text();
		measure.qActiveIn = $(element).find('qActiveIn').text();
		measure.qActiveOut = $(element).find('qActiveOut').text();
		measure.reactive1 = $(element).find('reactive1').text();
		measure.qReactive1 = $(element).find('qReactive1').text();
		measure.reactive2 = $(element).find('reactive2').text();
		measure.qReactive2 = $(element).find('qReactive2').text();
		measure.reactive3 = $(element).find('reactive3').text();
		measure.qReactive3 = $(element).find('qReactive3').text();
		measure.reactive4 = $(element).find('reactive4').text();
		measure.qReactive4 = $(element).find('qReactive4').text();
		measure.timestamp = $(element).find('timestamp').text();
		dataModel.getDev(measure.devName).measureList.push(measure);
	});
};

function alert_error(xhr, status, error) {
	var xmlError = $.parseXML(xhr.responseText);
	console.log(xmlError);
	alert($(xmlError).find('faultstring').text());
};

//Utiliza los elementos de measureList para cargar la gráfica
function loadGraphData() {
	
	console.log("loadGraphData");
	eGraph.clear();		
	for(i=0;i<selectedDevices.length;i++){
		var device=dataModel.getDev(selectedDevices[i]);
		var vList= device.getChecked(true);
		var measureList = device.getOrderMeasures();
		eGraph.changeData(measureList, vList);
	}
	hideLoader();	
};


	goFetchBoy = function () {
		alert("Variables must be selected first");
		window.location.href = "varTree.html";
	};

	firstLoad=function(){
		var selectedDevs = dataModel.getCheckedDevs(true);
				if (selectedDevs.length > 0) {
					getElectricEnergy();
				} else {
					goFetchBoy();
				}
	}
	
	return {
		init : function () {
			
			datePicker = new DatePicker();
			eGraph= new ElectricGraph("#grMob",datePicker);
			
			dataModel.init(firstLoad);

		},
		getElectricEnergy:function(){getElectricEnergy();}
		
	}

})();




