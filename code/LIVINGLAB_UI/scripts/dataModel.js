//MODULO dataModel
/**
Este modulo representa la estructura de datos hogar/dispositivo/variable
El metodo INIT tiene como parámetro una función de callback. El método cargará de caché la estructura (y de BBDD si no la encuentra).
La función de callback es diferente por cada .js que necesite este modulo, realizando una acción al finalizar la carga (cargar desplegable, dibujar gráficos...)

 **/

var dataModel = (function () {


	//funciones/variables privadas
	var homeList = [];

	showLoader = function () {
		setTimeout(function () {
			$.mobile.loading('show', {
				text : "Loading model. Wait...",
				textVisible : true
			});
		});
	};

	hideLoader = function () {
		setTimeout(function () {
			$.mobile.loading('hide');
		});
	};

	addHome = function (hName) {
		var h = new Home();
		h.homeName = hName;
		homeList.push(h);
	};
	getHome = function (hName) {
		for (var i = 0; i < homeList.length; i++) {
			if (homeList[i].homeName == hName) {
				return homeList[i];
			}
		}
		console.log("Not found")
	};

	parseJSON = function (dataJSON) {

		var loc = JSON.parse(dataJSON);

		for (i = 0; i < loc.length; i++) {
			for (j = 0; j < loc[i].devList.length; j++) {
				for (k = 0; k < loc[i].devList[j].varList.length; k++) {
					var v = new Variable;
					v.varMetadata = loc[i].devList[j].varList[k].varMetadata;
					v.selected = loc[i].devList[j].varList[k].selected;
					v.lastValue = loc[i].devList[j].varList[k].lastValue;
					loc[i].devList[j].varList[k] = v;
				}
				var d = new Device();
				d.hasLoadedVariables = loc[i].devList[j].hasLoadedVariables;
				d.devName = loc[i].devList[j].devName;
				d.desc = loc[i].devList[j].desc;
				d.varList = loc[i].devList[j].varList;
				loc[i].devList[j] = d;
			}
			var h = new Home();
			h.hasLoadedDevices = loc[i].hasLoadedDevices;
			h.homeName = loc[i].homeName;
			h.devList = loc[i].devList;
			loc[i] = h;

		}
		homeList = loc;
	};

	// FUNCIONES DE CARGA MEDIANTE AJAX
	sync = function (num) {
		var n = num;
		this.dec = function () {
			n = n - 1;
			return (n == 0)
		};
		this.branch = function (p) {
			n = n - 1 + p;
		};
	};

	function alert_error(xhr, status, error) {
		hideLoader();
		if (xhr.status == 500) {
			var xmlError = $.parseXML(xhr.responseText);
			alert($(xmlError).find('faultstring').text());
		} if (xhr.status == 0) {
		
		}else {
			alert(xhr.status + ":" + xhr.statusText);
		}
	};

	function getHomeDevice_call(homeName, callback) {
		showLoader();
		var soapRequest = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' " +
			"xmlns:home='http://home.eefrmwrk.tekniker.es/'>" +
			"<soapenv:Header/>" +
			"<soapenv:Body>" +
			"<home:getHomeDevices>" +
			"<homeName>" + homeName + "</homeName>" +
			"</home:getHomeDevices>" +
			"</soapenv:Body>" +
			"</soapenv:Envelope>";
		$.ajax({
			type : 'POST',
			url : homeWS_URL,
			data : soapRequest,
			contentType : 'text/xml;charset=UTF-8',
			processData : false,
			success : getHomeDevices_success,
			complete : getHomeDevices_complete,
			error : alert_error
		});

		function getHomeDevices_success(xml, status, xhr) {
			$(xml).find('device').each(function (index, element) {
				var dev = new Device();
				dev.devName = $(element).find('name').text();
				dev.desc = $(element).find('desc').text();
				dev.URI=$(element).find('URI').text();
				getHome(homeName).addDev(dev);
			});
		};

		function getHomeDevices_complete() {
			getHome(homeName).hasLoadedDevices = true;
			callback();
			hideLoader();
		};
	};

	//----------------------------------------------------------------------------
	// getDeviceVariables
	function getDeviceVariables_call(devName, homeName, callback) {
		showLoader();
		var soapRequest = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' " +
			"xmlns:home='http://home.eefrmwrk.tekniker.es/'>" +
			"<soapenv:Header/>" +
			"<soapenv:Body>" +
			"<home:getDeviceVariables>" +
			"<devName>" + devName + "</devName>" +
			"</home:getDeviceVariables>" +
			"</soapenv:Body>" +
			"</soapenv:Envelope>";
		$.ajax({
			type : 'POST',
			url : homeWS_URL,
			data : soapRequest,
			contentType : 'text/xml;charset=UTF-8',
			processData : false,
			success : getDeviceVariables_success,
			complete : getDeviceVariables_complete,
			error : alert_error
		});

		function getDeviceVariables_success(xml, status, xhr) {

			$(xml).find('variable').each(function (index, element) {

				var variable = new Variable();
				variable.varMetadata = new VarMetadata();
				variable.varMetadata.varName = $(element).find('name').text();
				variable.varMetadata.varPhyType = $(element).find('physicalType').text();
				variable.varMetadata.varDigType = $(element).find('digitalType').text();
				variable.varMetadata.varMeasUnit = $(element).find('measureUnit').text();
				variable.varMetadata.varAccess = $(element).find('access').text();
				variable.varMetadata.varStoreDB = $(element).find('storeDB').text();
				variable.varMetadata.varLoc = $(element).find('localization').text();
				variable.varMetadata.varDesc = $(element).find('description').text();
				variable.selected = false;

				if (Number($(element).find('value').text())) {
					variable.lastValue = new Value();
					variable.lastValue.value = $(element).find('value').text();
					variable.lastValue.timestamp = $(element).find('timestamp').text();
				}
				getHome(homeName).getDev(devName).addVar(variable);
				console.log(variable.varMetadata.varName);
			});
		};
		function getDeviceVariables_complete() {
			getHome(homeName).getDev(devName).hasLoadedVariables = true;
			callback();
			hideLoader();
		};
	};

	function getAlarms(callback) {	
		var soapRequest = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' " +
			"xmlns:cep='http://cepmngr.eefrmwrk.tekniker.es/'>" +
			"<soapenv:Header/>" +
			"<soapenv:Body>" +
		    "<cep:getAlarms></cep:getAlarms>"+
			"</soapenv:Body>" +
			"</soapenv:Envelope>";

		var vList = [];

		$.ajax({
			type : 'POST',
			url : cepWS_URL,
			data : soapRequest,
			contentType : 'text/xml;charset=UTF-8',
			processData : false,
			success : getAlarms_success,
			complete : getAlarms_complete,
			error : getAlarms_error
		});

		function getAlarms_success(xml, status, xhr) {
			$(xml).find('alarm').each(function (index, element) {
				var t= $(element).find('alarmType').text();
				var c =$(element).find('alarmCode').text();
				var sev =$(element).find('alarmSeverity').text();
				var arr_t = t.split("_");
				if(arr_t[0]=="OverType")
				{	
					for(var i =0;i<selVars.length;i++){
						var rxp = new RegExp(arr_t[2]);
						if(rxp.exec(selVars[i].varName)){
							if(!selVars[i].alarms)
								selVars[i].alarms=[];
							var al= new alarmThreshold(c,arr_t[1],sev);
							selVars[i].alarms.push(al);
					}
				}}
			});
		};

		function getAlarms_error(xhr, status, error) {
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

		function getAlarms_complete(xhr, status) {
			if (status == "success") {
				//console.log("ok");
				callback;
			}
		};	
	}
	
	
	//funciones públicas
	return {
		init : function (callback) {
			
			//Busca en cache si se tiene cargada el modelo
			//SI-> Parsear y realizar el callback
			//NO-> Llamar a base de datos, guardar en cache y realizar callback

			var dataJSON = sessionStorage.getItem('dataCol');

			
			if (dataJSON) {
				console.log("Cargando JSON desde cache")
				parseJSON(dataJSON);
				callback();

			} else {
				for (var i = 0; i < home_array.length; i++) {
					addHome(home_array[i]);
				}
				callback();
			}
		},

		loadHomeDevices : function (homeName, callback) {
			if (getHome(homeName).hasLoadedDevices)
				callback();
			else
				getHomeDevice_call(homeName, callback);
		},

		loadDeviceVariables : function (devName, homeName, callback) {
			console.log(devName)
			if (getHome(homeName).getDev(devName).hasLoadedVariables)
				callback();
			else
				getDeviceVariables_call(devName, homeName, callback);
		},
		getHomeList : function () {
			return homeList;
		},
		addHome : function (hName) {
			return addHome(hName);
		},
		getHome : function (hName) {
			return getHome(hName);
		},
		getDev : function (dName) {
			var keys = [];
			for (var i = 0; i < homeList.length; i++) {
				var dCol = homeList[i].devList;
				for (var j = 0; j < dCol.length; j++) {
					if (dCol[j].devName == dName) {
						return dCol[j];
					}
				}
			}
			return null;
		},
		getChecked : function (electricOnly) {
			var keys = [];
			for (var i = 0; i < homeList.length; i++) {
				var vCol = homeList[i].getChecked(electricOnly);
				keys = keys.concat(vCol);

			}
			return keys;
		},
		getCheckedDevs : function (electricOnly) {
			var keys = [];
			for (var i = 0; i < homeList.length; i++) {
				var dCol = homeList[i].devList;
				for (var j = 0; j < dCol.length; j++) {
					if (dCol[j].hasChecked(electricOnly)) {
						keys.push(dCol[j].devName);
					}
				}
			}
			return keys;
		}
	}

})();

function GeneralElectricMeasure() {
	this.devName = null;
	this.activeIn = null;
	this.activeOut = null;
	this.qActiveIn = null;
	this.qActiveOut = null;
	this.reactive1 = null;
	this.reactive2 = null;
	this.reactive3 = null;
	this.reactive4 = null;
	this.qReactive1 = null;
	this.qReactive2 = null;
	this.qReactive3 = null;
	this.qReactive4 = null;
	this.timestamp = null;
}

function Home() {
	this.homeName = null;
	this.devList = [];
	this.hasLoadedDevices = false;

	this.addDev = function (device) {
		this.devList.push(device);
	}

	this.getDev = function (dName) {
		for (i = 0; i < this.devList.length; i++) {
			if (this.devList[i].devName == dName) {
				return this.devList[i];
			}
		}
	}

	this.getChecked = function (electricOnly) {
		var cVars = []
		for (var i = 0; i < this.devList.length; i++) {
			var a = this.devList[i].getChecked(electricOnly)
				cVars = cVars.concat(a);
		}
		return cVars;
	}

	this.hasElectricDevices = function () {
		for (i = 0; i < this.devList.length; i++) {
			if (this.devList[i].hasElectricVars()) {
				return true;
			}
		}
		return false;
	}

}

function Device() {
	this.devName = null;
	this.desc = null;
	this.URI=null;
	this.varList = [];
	this.hasLoadedVariables = false;

	this.addVar = function (variable) {
		this.varList.push(variable);
	}

	this.getVar = function (vName) {
		for (var i = 0; i < this.varList.length; i++) {
			if (this.varList[i].varMetadata.varName == vName) {
				return this.varList[i];
			}
		}
	}

	this.hasElectricVars = function () {
		for (var i = 0; i < this.varList.length; i++) {
			if (this.varList[i].isElectricVar()) {
				return true;
			}
		}
		return false;
	}

	this.getChecked = function (electricOnly) {
		var cVars = []
		for (var i = 0; i < this.varList.length; i++) {
			if (electricOnly) {
				if (this.varList[i].isElectricVar() && this.varList[i].selected) {
					cVars.push(this.varList[i].varMetadata);
				}
			} else {
				if (this.varList[i].selected) {
					cVars.push(this.varList[i].varMetadata);
				}
			}
		}
		return cVars;
	}

	this.setAllChecked = function (sel, electricOnly) {
		for (var i = 0; i < this.varList.length; i++) {
			if (electricOnly) {
				if (this.varList[i].isElectricVar()) {
					this.varList[i].selected = sel;
				}
			} else {
				this.varList[i].selected = sel;
			}
		}
	}

	this.hasChecked = function (electricOnly) {
		for (var i = 0; i < this.varList.length; i++) {
			if (electricOnly) {
				if (this.varList[i].isElectricVar() && this.varList[i].selected) {
					return true;
				}
			} else {
				if (this.varList[i].selected) {
					return true;
				}
			}
		}
		return false;
	}

	this.getOrderMeasures = function () {
		return this.measureList.sort(function (a, b) {
			return a.timestamp - b.timestamp;
		});
	}
}

function Variable() {
	this.varMetadata = null;
	this.selected = null;
	this.lastValue = null;
	this.isElectricVar = function () {

		d = this.varMetadata.varPhyType;
		if (d == "activeIn" || d == "activeOut" || d == "qActiveIn" || d == "qActiveOut" ||
			d == "reactive1" || d == "reactive2" || d == "reactive3" || d == "reactive4" ||
			d == "qReactive1" || d == "qReactive2" || d == "qReactive3" || d == "qReactive4")
			return true;
		else
			return false;
	}
}

function VarMetadata() {
	this.varName = null;
	this.varMeasUnit = null;
	this.varDigType = null;
	this.varPhyType = null;
	this.varAccess = null;
	this.varUri = null;
	this.varStoreDB = null;
	this.varDesc = null;
	this.varLoc = null;
}

function Value() {
	this.value = null;
	this.timestamp = null;
	this.quality = null;
}

function Alarm(){
	this.name=null;
	this.value=null;
	this.severity=null;
}
