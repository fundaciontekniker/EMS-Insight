# EMS-Insight
Resultados del proyecto EMS-Insight

#CONFIGURACIÓN

Fichero eefrmwrk.alarm.feed\src\main\resources\feedserver.properties          
Introducir la URL en la que se publica el feed  
```sh
FeedManager.defaultAlarmUrl=http\://
```
Fichero eefrmwrk.monitoring.tac.mng.ws\src\main\resources\tac.config.properties     
URL de conexión al servicio CEP     
```sh
cep.client.url=http://  
```
URL de conexión al servicio Home    
```sh
home.client.url=http:// 
```
Fichero eefrmwrk.prediction.weather.parser.ws\src\main\resources\weather.prediction.config.properties   
URL de conexión al servicio CEP     
```sh
cep.client.url=http://  
```
Fichero eefrmwrk.cepmngr.listener.gcm\src\main\resources\gcm.config.properties      
URL de conexión al servicio de envío de mensajes a traves de GCM        
```sh
gcm.client.url=http://          
```
Fichero eefrmwrk.database.SQL.dbmodel\src\main\resources\hibernate.cfg.xml		
Rellenar los parámetros de conexión a la base de datos de reglas		
```sh
       <property name="hibernate.connection.url"></property>
       <property name="hibernate.connection.username"></property>
       <property name="hibernate.connection.password"></property>
       <property name="hibernate.default_schema"></property>
```
	   
Fichero eefrmwrk.gcm.mng.dao.domain\src\main\resources\ormmapping\GCM.cfg.xml		
Rellenar los parámetros de conexión a la base de datos de la tabla de configuración de los mensajes GCM				
```sh	   
	   	<property name="connection.url"></property>
		<property name="connection.username"></property>
		<property name="connection.password"></property>
```
Fichero eefrmwrk.monitoring.tac.mng.ws\src\main\resources\ormmapping\EMSTAC.cfg.xml			
Rellenar los parámetros de conexión a la base de datos del TAC Vista			
```sh
		<property name="connection.url"></property>
		<property name="connection.username"></property>
		<property name="connection.password"></property>
```
Fichero \code\eefrmwrk.gcm.mng.ws\src\main\resources\gcm.config.properties		
Añadir KEY de autenticación en el servicio GCM		
```sh
gcm.authtoken=
```
Fichero mobile\ems\AndroidManifest.xml		
KEY de autenticación en el servicio GCM		
```sh
		<meta-data
	            android:name="com.google.android.maps.v2.API_KEY"
	            android:value="" />
```	        
URL de conexión al servicio web GCM		
```sh
	    <meta-data android:name="gcm.api.url" 
	               android:value=""/>
```	    
URL de la web de consulta de datos 		
```sh
	    <meta-data android:name="web.url" 
	               android:value=""/>
```				   
#BASE DE DATOS
Crear el schema de la base de datos con los scripts SQL situados en el directorio db.

#WEB
Fichero code\LIVINGLAB_UI\scripts\chp.js		
Introducir URL del servicio REST CEP,	Linea 13:	
```sh
			url : "http://localhost/livinglab.public.cep.rest/cep/"+user+"/"+value,
```	
Fichero code\LIVINGLAB_UI\scripts\conf.js			
Introducir URLs del servicio web CEP y HomeWS 	
```sh	
var cepWS_URL= "http://localhost/livinglab.public.cep.ws/CepManagerWS"; 
var homeWS_URL="http://localhost/livinglab.public.home.ws/HomeWS";	
```	
