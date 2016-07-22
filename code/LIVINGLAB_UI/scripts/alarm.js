
var alarm = (function () {

	//Identificadores de elementos en alarm.html
	var id_newList = "#newList",
	id_alarmList = "#alarmList",
	id_ruleList = "#ruleList",

	//etiquetas pop-up ALARM
	id_alarm_popup = "#popupalarm",
	id_alarm_code = "#aCode",
	id_alarm_desc = "#aDesc",
	id_alarm_rule = "#aRule",
	id_alarm_message = "#aMess",
	id_alarm_severity = "#aSeverity",
	id_alarm_type = "#aType",
	id_alarm_state = "#aState";
	id_alarm_action = "#select-choice-a";

	//etiquetas pop-up RULE
	id_rule_popup = "#ruleBuilder",
	id_rule_header = "#rHeader",
	id_rule_name_li = "#rName_li",
	id_rule_name_text = "#rName_text",
	id_rule_EPL = "#rEPL_text",
	id_rule_Listener = "#rListener_text",
	id_rule_Severity = "#rSeverity_text",
	id_rule_Type = "#rType_text",
	id_rule_Status = "#rStatus_text",
	id_rule_var = "#rVariable",
	id_rule_value = "#rValue",
	id_rule_op = "#rOperation",
	id_rule_alarm = "#rAlarm",
	id_rule_button = "#rButton";

	var alarmList = [];
	var ruleList = [];

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

	function getAlarms(status) {
		var soapRequest =
			'<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:cep="http://cepmngr.eefrmwrk.tekniker.es/">' +
			'<soapenv:Header/>' +
			'<soapenv:Body>' +
			'<cep:getAlarms>';
		if (status)
			soapRequest = soapRequest + '<status>' + status + '</status>';

		soapRequest = soapRequest + '</cep:getAlarms>' +
			'</soapenv:Body>' +
			'</soapenv:Envelope>';

		$.ajax({
			type : 'POST',
			url : cepWS_URL,
			data : soapRequest,
			contentType : 'text/xml;charset=UTF-8',
			processData : false,
			success : getAlarms_success,
			error : getAlarms_error,
			complete : getAlarms_complete
		});

		function getAlarms_success(xml, status, xhr) {
			alarmList = [];
			$(xml).find('alarm').each(function (i, e) {
				var a = new Alarm();
				a.alarmCode = $(e).find('alarmCode').text();
				a.alarmSeverity = $(e).find('alarmSeverity').text();
				a.alarmDesc = $(e).find('alarmDesc').text();
				//a.alarmRule = $(e).find('alarmRule').text();
				a.alarmState = $(e).find('alarmState').text();
				a.alarmType = $(e).find('alarmType').text();
				a.alarmDuedate = $(e).find('alarmDuedate').text();
				a.alarmMessage = $(e).find('alarmMessage').text();
				a.alarmTimespan = new Date(Number($(e).find('alarmTimespan').text()));
				alarmList.push(a);
				//console.log(a);
			});
		}

		function getAlarms_complete() {

			$(id_newList).empty().append('<li data-role="list-divider">Pending</li>');
			$(id_alarmList).empty().append('<li data-role="list-divider">Old Alarms</li>');
			$("#sel-al").empty();

			var nC = 0,
			oC = 0;
			for (var i = 0; i < alarmList.length; i++) {

				$("#sel-al").append("<option value=" + alarmList[i].alarmCode + ">" + alarmList[i].alarmCode + "</option>");

				var listToAppend;
				if (alarmList[i].alarmState == "PENDING") {
					listToAppend = id_newList;
					nC++;
				} else {
					listToAppend = id_alarmList;
					oC++;
				}
				$(listToAppend).append('<li>' +
					'<a href="#" onClick=alarm.showAlarmInfo("' + alarmList[i].alarmCode + '") data-position-to="window" data-rel="popup">' + alarmList[i].alarmMessage +
					'<img src="img/sev_' + alarmList[i].alarmSeverity + '.jpg" class="ui-li-icon ui-li-thumb">' +
					getStateImg(alarmList[i].alarmState) +
					'<span class="ui-li-count">' + getDateFormat(alarmList[i].alarmTimespan) + '</span></a></li>');
			}
			if (nC == 0) {
				$(id_newList).append('<li>No new alarms</li>')
			}
			if (oC == 0) {
				$(id_alarmList).append('<li>No old alarms</li>')
			}
			$(id_newList + ":visible").listview("refresh");
			$(id_alarmList + ":visible").listview("refresh");
			hideLoader();
		};

		function getAlarms_error(xhr, status, error) {
			if (xhr.status == 500) {
				var xmlError = $.parseXML(xhr.responseText);
				console.log(xmlError);
				alert($(xmlError).find('faultstring').text());
			} else {
				alert(xhr.status + ":" + xhr.statusText);
			}
		};

	};

	function updateAlarm() {

		var alarmCode = $(id_alarm_code).text();

		var alarmState = "PENDING";
		var action = $(id_alarm_action).val();
		if (action == "accept") {
			alarmState = "CHECKED";
		}
		if (action == "cancel") {
			alarmState = "CANCELLED";
		}

		for (var i = 0; i < alarmList.length; i++) {
			if (alarmCode == alarmList[i].alarmCode) {
				updateAlarm_call(alarmList[i], alarmState)
				$(id_alarm_popup).popup("close");
				return;
			}
		}

		
	};

	function checkPendingAlarms(){
		console.log(alarmList.length);
		for (var i = 0; i < alarmList.length; i++) {
			if (alarmList[i].alarmState == "PENDING") {
				updateAlarm_call(alarmList[i], "CHECKED");
				}
		}
	};
	
	function getEPL(ruleName){
		for(var i=0;i<ruleList.length;i++){
			if(ruleList[i].ruleName ==ruleName){
				return ruleList[i].ruleEPL;
			}
		}
	};
	
	function showAlarmInfo(alarmCode) {

		for (var i = 0; i < alarmList.length; i++) {
			if (alarmList[i].alarmCode == alarmCode) {
				$(id_alarm_code).text(alarmCode);
				$(id_alarm_desc).text(alarmList[i].alarmDesc);
				//$(id_alarm_rule).text(getEPL(alarmList[i].alarmRule));
				$(id_alarm_message).text(alarmList[i].alarmMessage);
				$(id_alarm_severity).empty().append(alarmList[i].alarmSeverity + '<img src="img/sev_' + alarmList[i].alarmSeverity + '.jpg" class="left-icon">');
				$(id_alarm_type).text(alarmList[i].alarmType);
				$(id_alarm_state).empty().append(alarmList[i].alarmState + getStateImg(alarmList[i].alarmState));
				$(id_alarm_popup).popup("open");
				return;
			}
		}
		alarm("Alarm " + alarmCode + " not found");

	};
	
	
	function updateAlarm_call(alarm, alarmState) {

		var d = new Date();

		var soapRequest =
			'<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:cep="http://cepmngr.eefrmwrk.tekniker.es/">' +
			'<soapenv:Header/>' +
			'<soapenv:Body>' +
			'<cep:updateAlarm>' +
			'<alarmCode>' + alarm.alarmCode + '</alarmCode>' +
			'<alarmState>' + alarmState + '</alarmState>' +
			'<alarmTimespan>' + d.getTime() + '</alarmTimespan>' +
			'<alarmDesc>' + alarm.alarmDesc + '</alarmDesc>' +
			'<alarmSeverity>' + alarm.alarmSeverity + '</alarmSeverity>' +
			'<alarmType>' + alarm.alarmType + '</alarmType>' +
			'<alarmMessage>' + alarm.alarmMessage + '</alarmMessage>' +
			'</cep:updateAlarm>' +
			'</soapenv:Body>' +
			'</soapenv:Envelope>';

		$.ajax({
			type : 'POST',
			url : cepWS_URL,
			data : soapRequest,
			contentType : 'text/xml;charset=UTF-8',
			processData : false,
			success : updateAlarm_success,
			error : updateAlarm_error,
			complete : updateAlarm_complete
		});

		function updateAlarm_success(xml, status, xhr) {}

		function updateAlarm_complete() {
			getAlarms();
		}

		function updateAlarm_error(xhr, status, error) {
			if (xhr.status == 500) {
				var xmlError = $.parseXML(xhr.responseText);
				console.log(xmlError);
				alert($(xmlError).find('faultstring').text());
			} else {
				alert(xhr.status + ":" + xhr.statusText);
			}
		};
	};

	function getRules() {
		var soapRequest =
			'<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:cep="http://cepmngr.eefrmwrk.tekniker.es/">' +
			'<soapenv:Header/>' +
			'<soapenv:Body>' +
			'<cep:getRules/>' +
			'</soapenv:Body>' +
			'</soapenv:Envelope>';

		$.ajax({
			type : 'POST',
			url : cepWS_URL,
			data : soapRequest,
			contentType : 'text/xml;charset=UTF-8',
			processData : false,
			success : getRules_success,
			error : getRules_error,
			complete : getRules_complete
		});

		function getRules_success(xml, status, xhr) {
			ruleList = [];
			$(xml).find('rule').each(function (i, e) {
				var r = new Rule();
				r.ruleIndex=i;
				r.ruleEPL = $(e).find('ruleEPL').text();
				r.ruleName = $(e).find('ruleName').text();
				r.ruleStatus = $(e).find('ruleStatus').text();
				r.ruleInfo = $(e).find('ruleInfo').text();
				r.ruleListener = $(e).find('ruleListener').text();
				r.ruleSeverity = $(e).find('ruleSeverity').text();
				r.ruleMessage = $(e).find('ruleMessage').text();
				ruleList.push(r);
			});
		};

		function getRules_error(xhr, status, error) {
			if (xhr.status == 500) {
				var xmlError = $.parseXML(xhr.responseText);
				console.log(xmlError);
				alert($(xmlError).find('faultstring').text());
			} else {
				alert(xhr.status + ":" + xhr.statusText);
			}
		};

		function getRules_complete() {
			//$(id_ruleList).listview();
			$(id_ruleList).empty().append('<li data-role="list-divider">Rules</li>')
			for (var i = 0; i < ruleList.length; i++) {

				extractEPL(ruleList[i].ruleEPL);

				//var ind=ruleList[i].ruleListener.indexOf('ALARM:');
				//if(ind !== -1){
				//	console.log(ruleList[i].ruleListener.substring(6));
				addRuleToList(ruleList[i]);
				//}
			}
			$(id_ruleList + ":visible").listview("refresh");
			getAlarms();
		};
	};

	function extractEPL(EPL) {
		//console.log(EPL);
		/*var ind_v= EPL.indexOF('numValue');
		var ind_o= EPL.indexOF(">");
		if(ind_o==-1){
		ind_o= EPL.indexOF("<");}
		if(ind_o==-1)
		ind_o= EPL.indexOF("=");
		var sub1= EPL.substring(ind_v+8,ind-o);
		console.log("-->"+sub1);*/
	};

	 function addRuleToList (rule) {
		
		$(id_ruleList).append(
			'<li>' +
			'<a href="#" id="rule_' + rule.ruleIndex + '" onClick=alarm.loadRule("' + rule.ruleIndex + '")>' + rule.ruleName + '</a>' +
			'</li>');
	};

	//Abre el panel para editar regla nueva.
	function loadEditRule (rIndex) {
		if (rIndex>-1) {
			for (var i = 0; i < ruleList.length; i++) {
				if (ruleList[i].ruleIndex == rIndex) {
					$(id_rule_header).text(ruleList[i].ruleName);
					$(id_rule_EPL).text(ruleList[i].ruleEPL);
					$(id_rule_Listener).text(ruleList[i].ruleListener);
					$(id_rule_Severity).text(ruleList[i].ruleSeverity);
					$(id_rule_Type).text(ruleList[i].ruleMessage);
					$(id_rule_Status).text(ruleList[i].ruleStatus);
					id_rule_Listener = "#rListener_text",
					$("#ruleBuilder").trigger("create");
					$("#ruleBuilder").popup("open");
					return;
				}
			}
			alarm("Rule " + rIndex + " not found");

		} else {
			$("#header-name").text("New Rule");
			$("#rName_li").show();
			$("#rName").val("");
			$("#rValue").val("Nueva regla");
			$("#rButton").text("ADD").button('refresh'); ;
			$("#ruleBuilder ul:visible").listview("refresh");
			$("#ruleBuilder").popup("open");
		}
	};

	function getStateImg(type) {

		if (type == "PENDING") {
			return '<img src="img/glyphish/59-flag.png" alt="New" class="left-icon">';
		} else if (type == "CHECKED") {
			return '<img src="img/glyphish/117-todo.png" alt="Checked" class="left-icon">';
		} else if (type == "CANCELLED") {
			return '<img src="img/glyphish/21-skull.png" alt="Cancelled" class="left-icon">';
		} else {
			return '';
		}

	};

	function loadVariableList() {
		$("#sel-var").empty();
		var hL = dataModel.getHomeList();
		for (var i = 0; i < hL.length; i++) {
			var dL = hL[i].devList;
			for (var j = 0; j < dL.length; j++) {
				var vL = dL[j].varList;
				for (var k = 0; k < vL.length; k++) {
					var n = vL[k].varMetadata.varName;
					$("#sel-var").append("<option value=" + n + ">" + n + "</option>");
				}
			}
		}
	};

	return {
		init : function () {
			showLoader();

			dataModel.init(loadVariableList);

			getRules();			

			//Ver si hay cmabios cada 5 segundos
			setInterval(function () {
				getAlarms();
			}, 5000);

		},
		updateAlarm : function () {
			updateAlarm();
		},
		updateRule : function (rN) {
			loadEditRule(rN);
		},
		
		showAlarmInfo:function(alarmCode){
			showAlarmInfo(alarmCode);
		},
		
		loadRule:function(ruleCode){
			loadEditRule(ruleCode);
		},
		checkPendingAlarms:function(){
			checkPendingAlarms();
		}
	}

})();


//Usado para el notificador de alarmas. Simbolo con redondo
var alarmNotifier = (function () {
	var alarmList = [];
	
	function getNewAlarms() {
		var soapRequest =
			'<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:cep="http://cepmngr.eefrmwrk.tekniker.es/">' +
			'<soapenv:Header/>' +
			'<soapenv:Body>' +
			'<cep:getAlarms>'+
			'<status>PENDING</status>'+
			'</cep:getAlarms>' +
			'</soapenv:Body>' +
			'</soapenv:Envelope>';

		$.ajax({
			type : 'POST',
			url : cepWS_URL,
			data : soapRequest,
			contentType : 'text/xml;charset=UTF-8',
			processData : false,
			success : getAlarms_success,
			error : getAlarms_error,
			complete : getAlarms_complete
		});

		function getAlarms_success(xml, status, xhr) {
			alarmList = [];
			$(xml).find('alarm').each(function (i, e) {
				var a = new Alarm();
				a.alarmCode = $(e).find('alarmCode').text();
				a.alarmSeverity = $(e).find('alarmSeverity').text();
				a.alarmDesc = $(e).find('alarmDesc').text();
				a.alarmRule = $(e).find('alarmRule').text();
				a.alarmState = $(e).find('alarmState').text();
				a.alarmType = $(e).find('alarmType').text();
				a.alarmDuedate = $(e).find('alarmDuedate').text();
				a.alarmMessage = $(e).find('alarmMessage').text();
				a.alarmTimespan = new Date(Number($(e).find('alarmTimespan').text()));
				alarmList.push(a);
			});
		};

		function getAlarms_complete() {
			//console.log("getAlarms_complete");	
			var newC = 0;
			for (var i = 0; i < alarmList.length; i++) {
				newC++;
			}
			if(newC>0){
				//console.log("actualizar burbuja");	
			}
		};

		function getAlarms_error(xhr, status, error) {
			if (xhr.status == 500) {
				var xmlError = $.parseXML(xhr.responseText);
				alert($(xmlError).find('faultstring').text());
			} else if (xhr.status == 0) {
				
			} {
				alert(xhr.status + ":" + xhr.statusText);
			}
		};

	};
	
	return {
		init : function () {
			setInterval(function () {getNewAlarms();}, 5000);
	}
	}
})();

function getDateFormat(date) {
	var y = date.getFullYear();
	var m = date.getMonth()+1; //getMonth is from 0 to 11;
	if(m<10) m="0"+m;
	var d = date.getDate();
	if(d<10) d="0"+d;
	var h = date.getHours();
	if(h<10) h="0"+h;
	var min = date.getMinutes();
	if(min<10) min="0"+min;
	var s = date.getSeconds();
	if(s<10) s="0"+s;
	return y + "/" + m + "/" + d + " " + h + ":" + min + ":" + s;
}

function Alarm() {
	this.alarmCode = null;
	this.alarmSeverity = null;
	this.alarmDesc = null;
	this.alarmRule = null;
	this.alarmState = null;
	this.alarmType = null;
	this.alarmDuedate = null;
	this.alarmTimespan = null;
	this.alarmMessage = null;
}

function Rule() {
	this.ruleIndex=null;
	this.ruleEPL = null;
	this.ruleName = null;
	this.ruleStatus = null;
	this.ruleListener = null;
	this.ruleMessage=null;
	this.ruleSeverity=null;
}
