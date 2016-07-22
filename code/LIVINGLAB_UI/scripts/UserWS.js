

function alert_error(xhr, status, error){
		var xmlError = $.parseXML(xhr.responseText);
		console.log(xmlError);
		alert($(xmlError).find('faultstring').text());
};

//ELEMENTOS RELACIONADOS EN HTML
function checkLogin()
{
var sr_checkLogin ="<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' "+
					"xmlns:user='http://usermngr.eefrmwrk.tekniker.es/'>"+
					"<soapenv:Header/>"+
						"<soapenv:Body>"+
							"<user:checkLogin>"+
								"<login>"+$("#txtLogin").val()+"</login>"+
								"<password>"+$("#txtPass").val()+"</password>"+
							"</user:checkLogin>"+
						"</soapenv:Body>"+
					"</soapenv:Envelope>";
					
$.ajax( {
    type:'POST',  
    url:userWS_URL,  
    data:sr_checkLogin,
    contentType:'text/xml;charset=UTF-8',
	processData: false,
	success:checkLogin_success,
    error: alert_error
    });


function getUser(){
	
	var sr_getUser ="<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' "+
					"xmlns:user='http://usermngr.eefrmwrk.tekniker.es/'>"+
					"<soapenv:Header/>"+
						"<soapenv:Body>"+
							"<user:getUserByLogin>"+
								"<login>"+$("#txtLogin").val()+"</login>"+
							"</user:getUserByLogin>"+
						"</soapenv:Body>"+
					"</soapenv:Envelope>";
	$.ajax( {
    type:'POST',  
    url:userWS_URL,  
    data:sr_getUser,
    contentType:'text/xml;charset=UTF-8',
	processData: false,
	success:function (xml,status,xhr){
	var name=$(xml).find('name').text();
	alert('Hello '+name);
	$.session.set('user',name);},
    error:alert_error
    });
}
	
function checkLogin_success(xml, status,xhr){

	if($(xml).find('result').text()=='1'){
	console.log("OK");
	getUser();
	}else{
	alert("WHO ARE YOU?");
	}};
};

function isLoged(){

	
if(!$.session.get('user')){
	alert("Hace falta logearse");
}else{
	alert("Hola "+$.session.get('user'));}
}