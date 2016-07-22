var predictors = (function () {

	//Id
	var id_predictors="#predictors",
		id_editor="#editor",
		id_graph="#graph",
		id_sel_pred = "#sel_pred",
		id_predList = "#predList",
		id_predVarList="#predVarList",
		id_predInfo = "#predInfo",
		id_graphCanvas="#graphCanvas",
		id_txtPredName="#txt_predName",
		id_txtPredInitialDate="#txt_predInitialDate",
		id_txtPredFinalDate="#txt_predFinalDate",
		id_txtPredPeriodicity="#txt_predPeriodicity",
		id_txtPredAlgorithm="#txt_predAlgorithm",
		id_txtPredInterval="#txt_predInterval",
		id_txtPredSteps="#txt_predSteps",
		id_txtPredStatus="#txt_predStatus",
		id_btnPredCycle="#btn_predCycle";
	var graph;	
	var zoom=2;	//3 niveles de zomm
	
	var dtPicker;
	var predMap= new HashMap();

	var selectedPred; //Selected predictor;
	
	var colorsNum = ['0,0,255',
	         		//'0,128,0',
	         		//'0,128,128',
	         		//'0,128,255',
	         		'0,255,0',
	         		//'0,255,128',
	         		//'0,255,255',
	         		'128,0,0',
	         		'128,0,128',
	         		//'128,0,255',
	         		'128,128,0',
	         		//'128,128,128',
	         		//'128,128,255',
	         		//'128,255,0',
	         		//'128,255,128',
	         		//'128,255,255',
	         		'255,0,0',
	         		//'255,0,128',
	         		'255,0,255',
	         		//'255,128,0',
	         		//'255,128,128',
	         		'0,255,255',
	         		'255,255,0',
	         		'255,255,128'];
	
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

	function alarm_error(xhr, status, error) {
		if (xhr.status == 500) {
			var xmlError = $.parseXML(xhr.responseText);
			console.log(xmlError);
			alert($(xmlError).find('faultstring').text());
		} if (xhr.status == 0) {
			console.log("Call canceled:");
			console.log(xmlError);
		}else {
			alert(xhr.status + ":" + xhr.statusText);
		}
		hideLoader();
	};


	
	function loadPredictors(callback) {
		var soapRequest =
			'<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:pred="http://prediction.eefrmwrk.tekniker.es/">'+
			'<soapenv:Header/><soapenv:Body><pred:getLoadedPredictors/></soapenv:Body></soapenv:Envelope>';
		
		$.ajax({
			type : 'POST',
			url : predictionWS_URL,
			data : soapRequest,
			contentType : 'text/xml;charset=UTF-8',
			processData : false,
			success : loadPredictors_success,
			complete : loadPredictors_complete,
			error : alarm_error
		});

		function loadPredictors_success(xml, status, xhr) {
			predMap= new HashMap();
			$(xml).find('result').each(function (i, e) {
				var p = new Predictor();
				p.name=$(e).find('predictorName').text();
				p.algorithm = $(e).find('algorithm').text();
				p.initialDate=$(e).find('initialDate').text();
				p.finalDate = $(e).find('finalDate').text();
				p.periodicity=$(e).find('periodicity').text();
				p.repeatInterval=$(e).find('repeatInterval').text();
				p.status=$(e).find('status').text();
				p.steps=$(e).find('steps').text();
				predMap.put(p.name,p);
			});
		};


		function loadPredictors_complete() {
			console.log("loadPredictors_complete");
			$(id_predList).empty().append('<li data-role="list-divider">Predictors</li>')
			sc= new sync(predMap.values().length-1);
			for (var i = 0; i < predMap.values().length; i++) {
				if(predMap.values()[i].name!="Actual_Variables"){
					addPredToList(predMap.values()[i]);
					loadPredictorVariables(predMap.values()[i].name,sc);
				}
			}			
		};
	};

	
	function loadPredictorVariables(predName,sc) {
		var soapRequest =
			'<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:pred="http://prediction.eefrmwrk.tekniker.es/">'+
			'<soapenv:Header/><soapenv:Body><pred:getPredictorVariables>'+
			'<predictorName>'+predName+'</predictorName>'+
			'</pred:getPredictorVariables></soapenv:Body></soapenv:Envelope>';
		
		$.ajax({
			type : 'POST',
			url : predictionWS_URL,
			data : soapRequest,
			contentType : 'text/xml;charset=UTF-8',
			processData : false,
			success :loadPredictorVariables_success,
			complete : loadPredictorVariables_complete,
			error : alarm_error
		});
		var varList = [];
		function loadPredictorVariables_success(xml, status, xhr) {
			 varList = [];
			$(xml).find('result').each(function (i, e) {
				var p = new PredictorVariable();
				p.varName=$(e).find('varName').text();
				p.interpolation_function = $(e).find('itpl_f').text();
				p.normalized=$(e).find('norm').text();
				varList.push(p);
			});
		};

		function loadPredictorVariables_complete() {
			predMap.get(predName).variables=varList;
			if(sc.dec()){
				console.log("loadPredictorVariables_complete");
				var selPredName = localStorage.getItem("selectedPred"); //Busca en cache
				if (selPredName)
					loadInfo(selPredName);
				else
					if(predMap.values().length>2)
						loadInfo(predMap.values()[1].name);
				
				loadEditorForm("Actual_Variables");
				$(id_predList+":visible").listview("refresh");
			}
		};
	};
	
	

	function loadSampledData(predName) {
		var soapRequest =
			'<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:pred="http://prediction.eefrmwrk.tekniker.es/">'+
			'<soapenv:Header/><soapenv:Body><pred:getSampledData>'+
			'<predictorName>'+predName+'</predictorName>'+
			'</pred:getSampledData></soapenv:Body></soapenv:Envelope>';
		
		$.ajax({
			type : 'POST',
			url : predictionWS_URL,
			data : soapRequest,
			contentType : 'text/xml;charset=UTF-8',
			processData : false,
			success :loadSampledData_success,
			complete : loadSampledData_complete,
			error : alarm_error
		});
		var sampledVars;
		function loadSampledData_success(xml, status, xhr) {
			sampledVars= new HashMap();
			$(xml).find('result').each(function (i, e) {
				var name=$(e).find('name').text();
				var v = new Value();
				v.value = $(e).find('value').text();
				v.timestamp = $(e).find('timestamp').text();
				if(sampledVars.get(name)==null){
					var c=[];
					sampledVars.put(name,c);
				}
				sampledVars.get(name).push(v);
			});
		};

		function loadSampledData_complete() {		
			console.log("loadSampledData_complete");
			for (var i = 0; i < graph.group.length; i++) {
				graph.group[i].data=sampledVars.get(graph.group[i].name);
			}
			if(predMap.get(predName).repeatInterval>0)
				getLastPrediction(predName);
			else
				getPrediction(predName);
		};
	};
	
	function loadRealData() {
		sc= new sync(selectedPred.variables.length);
		for(var i=0;i<selectedPred.variables.length;i++)
			getVariableData(selectedPred.variables[i].name,sc);
	}
	
	
	function getVariableData(varName,sc){
	var soapRequest =
	'<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:home="http://home.eefrmwrk.tekniker.es/">'+
	"<soapenv:Header/>"+
	   "<soapenv:Body>"+
	     "<home:getVariableValues>"+
	         "<varName>"+varName+"</varName>"+
	         "<initialDate>"+selectedPred.initialDate+"</initialDate>"+
	         "<finalDate>"+selectedPred.finalDate+"</finalDate>"+
	      "</home:getVariableValues>"+
	   "</soapenv:Body>"+
	"</soapenv:Envelope>";
		
		$.ajax({
			type : 'POST',
			url : homeWS_URL,
			data : soapRequest,
			contentType : 'text/xml;charset=UTF-8',
			processData : false,
			success :getVariableData_success,
			complete : getVariableData_complete,
			error : alarm_error
		});
		var sampledVars;
		function getVariableData_success(xml, status, xhr) {
			sampledVars= new HashMap();
			$(xml).find('result').each(function (i, e) {
				var name=$(e).find('name').text();
				var v = new Value();
				v.value = $(e).find('value').text();
				v.timestamp = $(e).find('timestamp').text();
				if(sampledVars.get(name)==null){
					var c=[];
					sampledVars.put(name,c);
				}
				sampledVars.get(name).push(v);
			});
		};

		function getVariableData_complete() {		
			for (var i = 0; i < graph.group.length; i++) {
				graph.group[i].data=sampledVars.get(graph.group[i].name);
			}
			if(selectedPred.repeatInterval>0)
				getLastPrediction(predName);
			else
				getPrediction(predName);
		};
	};
	
	
	
	function getLastPrediction(predName) {
	var soapRequest =
		'<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:pred="http://prediction.eefrmwrk.tekniker.es/">'+
		'<soapenv:Header/><soapenv:Body><pred:getLastPrediction>'+
		'<predictorName>'+predName+'</predictorName>'+
		'</pred:getLastPrediction></soapenv:Body></soapenv:Envelope>';
	
	$.ajax({
		type : 'POST',
		url : predictionWS_URL,
		data : soapRequest,
		contentType : 'text/xml;charset=UTF-8',
		processData : false,
		success :getLastPrediction_success,
		complete : getLastPrediction_complete,
		error : alarm_error
	});
	var predVars;
	function getLastPrediction_success(xml, status, xhr) {
		console.log("getLastPrediction_success");
		predVars= new HashMap();
		$(xml).find('result').each(function (i, e) {
			var name=$(e).find('name').text();
			var v = new Value();
			v.value = $(e).find('value').text();
			v.timestamp = $(e).find('timestamp').text();
			if(predVars.get(name)==null){
				var c=[];
				predVars.put(name,c);
			}
			predVars.get(name).push(v);
		});
	};

	function getLastPrediction_complete() {
		console.log("getLastPrediction_success");
		for (var i = 0; i < graph.group.length; i++) {
			graph.group[i].pred_data=predVars.get(graph.group[i].name);
		}

		updateGraph();
		hideLoader();
	};
};
function getPrediction(predName) {
	var soapRequest =
		'<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:pred="http://prediction.eefrmwrk.tekniker.es/">'+
		'<soapenv:Header/><soapenv:Body><pred:predict>'+
		'<predictorName>'+predName+'</predictorName>'+
		'<steps>'+predMap.get(predName).steps+'</steps>'+
		'</pred:predict></soapenv:Body></soapenv:Envelope>';
	
	$.ajax({
		type : 'POST',
		url : predictionWS_URL,
		data : soapRequest,
		contentType : 'text/xml;charset=UTF-8',
		processData : false,
		success :getPrediction_success,
		complete : getPrediction_complete,
		error : alarm_error
	});
	var predVars;
	function getPrediction_success(xml, status, xhr) {
		console.log("getPrediction_success");
		predVars= new HashMap();
		$(xml).find('result').each(function (i, e) {
			var name=$(e).find('name').text();
			var v = new Value();
			v.value = $(e).find('value').text();
			v.timestamp = $(e).find('timestamp').text();
			if(predVars.get(name)==null){
				var c=[];
				predVars.put(name,c);
			}
			predVars.get(name).push(v);
		});
	};

	function getPrediction_complete() {
		console.log("getPrediction_complete");
		
		for (var i = 0; i < graph.group.length; i++) {
			graph.group[i].pred_data=predVars.get(graph.group[i].name);
		}

		updateGraph();
		hideLoader();
	};
};
	

	function sync(num) {
		var n = num;
		this.dec = function () {
			n = n - 1;
			return (n == 0)
		};
	};
	
	function addPredToList(pred) {
		
		$(id_predList).append(
			'<li>' +
			"<a href='#' id='v_" + pred.name + "' onClick=loadInfo('" + pred.name + "');>" + pred.name + '</a>' +
			'<a href="#graph" id="v_'+pred.name+'_cycle" onCLick=predictors.loadGraph("'+pred.name+'"); >SHOW GRAPH</a>'+
			'</li>');
	};
	
	
	this.loadInfo=function(predName){
		selectedPred=predMap.get(predName);
		localStorage.setItem("selectedPred",selectedPred.name); 
		$(id_txtPredName).val(selectedPred.name);
		$(id_txtPredAlgorithm).val(selectedPred.algorithm);
		$(id_txtPredInitialDate).val(new Date(Number(selectedPred.initialDate)).toLocaleString());
		$(id_txtPredFinalDate).val(new Date(Number(selectedPred.finalDate)).toLocaleString());
			
		/*dtPicker.d1=new Date(Number(selectedPred.initialDate));
		dtPicker.d2=new Date(Number(selectedPred.finalDate))
		dtPicker.reloadDateBoxes();*/
			
		$(id_txtPredPeriodicity).val(selectedPred.periodicity);
		$(id_txtPredInterval).val(selectedPred.repeatInterval);
		$(id_txtPredSteps).val(selectedPred.steps);
			
		if(selectedPred.repeatInterval>0){
			$(id_txtPredStatus).val("In prediction cycle("+ selectedPred.repeatInterval+" ms) - "+selectedPred.status);
			$(id_btnPredCycle+":visible").val("Stop Cycle").button('refresh');}
		else{
			$(id_txtPredStatus).val("No prediction cycle - "+selectedPred.status);
		    $(id_btnPredCycle+":visible").val("Start Cycle").button('refresh');}
	}
	
	function loadEditorForm(predName){
		console.log("loadEditorForm")
		$(id_txtPredAlgorithm+"_e").val(predMap.get(predName).algorithm);
		dtPicker.d1=new Date(Number(predMap.get(predName).initialDate));
		dtPicker.d2=new Date(Number(predMap.get(predName).finalDate))
		
		$(id_txtPredPeriodicity+"_e").val(predMap.get(predName).periodicity);
		$(id_txtPredSteps+"_e").val(predMap.get(predName).steps);
		
		$(id_predVarList).empty();
		var selectedVars = dataModel.getChecked();
		if(selectedVars.length==0)
			$(id_predVarList).append('<div>No variables are selected</div>');
		else
		for(var i=0;i<selectedVars.length;i++){
			$(id_predVarList).append(
					'<div>'+ selectedVars[i].varName + '</div>');
		};
		dtPicker.reloadDateBoxes();
	}
	function updateGraph(){	
		graph.updateGraph(zoom);
	}
	
	function addPredictor(){
		var soapRequest =
			'<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:pred="http://prediction.eefrmwrk.tekniker.es/">'+
			'<soapenv:Header/><soapenv:Body><pred:addPredictor>'+
			'<predictorName>'+$(id_txtPredName+"_e").val()+'</predictorName>';
			var selectedVars = dataModel.getChecked();
			for(var i=0;i<selectedVars.length;i++){
				soapRequest=soapRequest+'<varNames>'+selectedVars[i].varName+'</varNames>';
			}
			
			soapRequest=soapRequest+ '<initialDate>'+dtPicker.d1.getTime()+'</initialDate>'+
	         '<finalDate>'+dtPicker.d2.getTime()+'</finalDate>'+
	         '<interval>'+$(id_txtPredPeriodicity+"_e").val()+'</interval>'+
	         '<algorithm>'+$(id_txtPredAlgorithm+"_e").val()+'</algorithm>'+
	         '<steps>'+$(id_txtPredSteps+"_e").val()+'</steps>'+
			'</pred:addPredictor></soapenv:Body></soapenv:Envelope>';
		
		$.ajax({
			type : 'POST',
			url : predictionWS_URL,
			data : soapRequest,
			contentType : 'text/xml;charset=UTF-8',
			processData : false,
			success :addPredictor_success,
			error : alarm_error,
			complete : addPredictor_complete
		});
		var varList = [];
		function addPredictor_success(xml, status, xhr) {
			alert("Predictor correctly saved")
			hideLoader();
		};

		function addPredictor_complete() {
			loadPredictors();
		};
	};
	
	function startCycle(){
		var soapRequest =
			'<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:pred="http://prediction.eefrmwrk.tekniker.es/">'+
			'<soapenv:Header/><soapenv:Body><pred:startPredictorCycle>'+
	        '<predictorName>'+selectedPred.name+'</predictorName>'+
	        '<repeatInterval>'+$(id_txtPredInterval).val()+'</repeatInterval>'+
	        '</pred:startPredictorCycle></soapenv:Body></soapenv:Envelope>';
		
		$.ajax({
			type : 'POST',
			url : predictionWS_URL,
			data : soapRequest,
			contentType : 'text/xml;charset=UTF-8',
			processData : false,
			success :startCycle_success,
			error : alarm_error,
			complete : startCycle_complete
		});
		var varList = [];
		function startCycle_success(xml, status, xhr) {
			alert("Predictor correctly started")
			hideLoader();
		};

		function startCycle_complete() {
			loadPredictors();
		};
	};
	
	function stopCycle(){
		var soapRequest =
			'<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:pred="http://prediction.eefrmwrk.tekniker.es/">'+
			'<soapenv:Header/><soapenv:Body><pred:stopPredictorCycle>'+
	        '<predictorName>'+selectedPred.name+'</predictorName>'+
	        '</pred:stopPredictorCycle></soapenv:Body></soapenv:Envelope>';
		
		$.ajax({
			type : 'POST',
			url : predictionWS_URL,
			data : soapRequest,
			contentType : 'text/xml;charset=UTF-8',
			processData : false,
			success :stopCycle_success,
			error : alarm_error,
			complete : stopCycle_complete
		});
		var varList = [];
		function stopCycle_success(xml, status, xhr) {
			alert("Predictor correctly stopped");
			hideLoader();
		};

		function stopCycle_complete() {
			loadPredictors();
		};
	};
	
	function updateActualPredictor(){
		
		predMap.get("Actual_Variables").variables = dataModel.getChecked();
		predMap.get("Actual_Variables").initialDate=dtPicker.d1.getTime();
		predMap.get("Actual_Variables").finalDate=dtPicker.d2.getTime();
		predMap.get("Actual_Variables").periodicity=$(id_txtPredPeriodicity+"_e").val();
		predMap.get("Actual_Variables").algorithm=$(id_txtPredAlgorithm+"_e").val();
		predMap.get("Actual_Variables").steps=$(id_txtPredSteps+"_e").val();
		
		readyGraph("Actual_Variables");
		updatePredictor("Actual_Variables");
	}
	
	function updatePredictor(predName){
		
		var soapRequest =
			'<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:pred="http://prediction.eefrmwrk.tekniker.es/">'+
			'<soapenv:Header/><soapenv:Body><pred:editPredictor>'+
			'<predictorName>'+predName+'</predictorName>';
			for(var i=0;i<predMap.get(predName).variables.length;i++){
				soapRequest=soapRequest+'<varNames>'+predMap.get(predName).variables[i].varName+'</varNames>';
			}
			soapRequest=soapRequest+ '<initialDate>'+predMap.get(predName).initialDate+'</initialDate>'+
	         '<finalDate>'+predMap.get(predName).finalDate+'</finalDate>'+
	         '<interval>'+predMap.get(predName).periodicity+'</interval>'+
	         '<algorithm>'+predMap.get(predName).algorithm+'</algorithm>'+
	         '<steps>'+predMap.get(predName).steps+'</steps>'+
			'</pred:editPredictor></soapenv:Body></soapenv:Envelope>';
		
		$.ajax({
			type : 'POST',
			url : predictionWS_URL,
			data : soapRequest,
			contentType : 'text/xml;charset=UTF-8',
			processData : false,
			success :updatePredictor_success,
			error : alarm_error,
			complete : updatePredictor_complete
		});
		function updatePredictor_success(xml, status, xhr) {	
			console.log("updatePredictor_success");
		};

		function updatePredictor_complete() {
			loadSampledData(predName);
			hideLoader();
		};
	};
	var graphUpdater;
	function readyGraph(predName){
		$(graphCanvas).empty();
		graph=new Graph(graphCanvas,"");
		for (var i = 0; i < predMap.get(predName).variables.length; i++) {
			var vg = new dataVar();
			vg.name = predMap.get(predName).variables[i].varName;
			vg.color = "rgb(" + colorsNum[i % colorsNum.length] + ")";
			vg.pred_color = "rgba(" + colorsNum[i % colorsNum.length] + ",0.3)";
			graph.addVar(vg);
		}
	}

	
	return {
		init : function () {	
			console.log("init");
			dtPicker = new DatePicker();
			dataModel.init(loadPredictors);
		},
		loadGraph:function(predName) {
			showLoader();
			clearInterval(graphUpdater);
			readyGraph(predName);
			loadSampledData(predName);	
			if(predMap.get(predName).repeatInterval>0)
				graphUpdater = setInterval(function(){loadSampledData(predName)},10000);
		},
		previewGraph:function(predName) { //Update "Actual_Variable" Graph, then predicts and shows graph
			showLoader();
			updateActualPredictor();
		},
		addNewPredictor:function() {
			showLoader();
			addPredictor()
		},
		toogleZoom:function(){
			zoom++;
			if(zoom==3)
				zoom=0;
			updateGraph();
		},
		toogleCycle:function(){
			if(selectedPred.repeatInterval>0)
				stopCycle();
			else
				startCycle();				
		}
	}
})();


function Predictor() {
	this.name = null;
	this.algorithm=null;
	this.initialDate=null;
	this.finalDate=null;
	this.periodicity=null;
	this.repeatInterval=null;
	this.status=null;
	this.steps=null;
}

function PredictorVariable() {
	this.varName=null;
	this.normalized=null;
	this.interpolation_function=null;
}
