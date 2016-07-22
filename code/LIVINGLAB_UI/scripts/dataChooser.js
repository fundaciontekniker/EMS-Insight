var dataChooser = (function () {

var day1,time1,d1, //day,time and complete date 1
	day2,time2,d2; //day,time and complete date 1

var wait=false,
		waitCallback;
		
	/*this.changeDates = function () {
		var dt1 = $('#ctrldate_1').val() + " " + $('#ctrltime_1').val();
		var dt2 = $('#ctrldate_2').val() + " " + $('#ctrltime_2').val();
		this.setDates(new Date(dt1), new Date(dt2));
	}*/

	function u1() {
		var dt1 = $('#ctrldate_1').val() + " " + $('#ctrltime_1').val();
		d1 = new Date(dt1);
		localStorage.putItem('date1',d1);
	};

	function u2 () {
		var dt2 = $('#ctrldate_2').val() + " " + $('#ctrltime_2').val();
		d2 = new Date(dt2);
		localStorage.putItem('date2',d2);
	};

	 function setDates(nd1, nd2) {
		$('#ctrldate_1').datebox('setTheDate', nd1).trigger('datebox', {
			'method' : 'doset'
		});
		$('#ctrltime_1').datebox('setTheDate', nd1).trigger('datebox', {
			'method' : 'doset'
		});
		$('#ctrldate_2').datebox('setTheDate', nd2).trigger('datebox', {
			'method' : 'doset'
		});
		$('#ctrltime_2').datebox('setTheDate', nd2).trigger('datebox', {
			'method' : 'doset'
		});
		d1 = nd1;
		d2 = nd2;
	};

	
	function openAndChooseDate1(callback){
		console.log("openAndChooseDate1");
		date1 = localStorage.getItem('date1');
		if(date1){
			callback();
		}
		else{
			alert("Selecciona fecha1");
			$('#ctrldate_1').datebox('open');
			wait=true;
			waitCallback=callback;
		}
	};
	
	function openAndChooseDate2(callback){
		console.log("openAndChooseDate2");
		date2 = localStorage.getItem('date2');
		if(date2){
			callback();
		}
		else{
			alert("Selecciona fecha2");
			$('#ctrldate_2').datebox('open');
			wait=true;
			waitCallback=callback;
		}
	};
	
	//Actualizar fechas al cambiar
	$('#ctrldate_1').bind('change', function (e, p) {
		$('.ctrldate_1_button').find('.ui-btn-text').text($(this).val());
		console.log("BANANAS!!");
		day1=$(this).val();
		u1();
		console(wait);
		if(wait){
			wait=false;
			waitCallback();
		}
	});
	$('#ctrltime_1').bind('change', function (e, p) {
		$('.ctrltime_1_button').find('.ui-btn-text').text($(this).val());
		time1=$(this).val();
		u1();
	});
	$('#ctrldate_2').bind('change', function (e, p) {
		$('.ctrldate_2_button').find('.ui-btn-text').text($(this).val());
		day2=$(this).val();
		u2();
		if(wait){
			wait=false;
			waitCallback();
		}
	});
	$('#ctrltime_2').bind('change', function (e, p) {
		$('.ctrltime_2_button').find('.ui-btn-text').text($(this).val());
		time2=$(this).val();
		u2();
		
	});
	
	//---Abrir ventanas al pulsar botones
	$('.ctrldate_1_button').on('vclick', function () {
		$('#ctrldate_1').datebox('open');
	});

	$('.ctrltime_1_button').on('vclick', function () {
		$('#ctrltime_1').datebox('open');
	});	
	
	$('.ctrldate_2_button').on('vclick', function () {
		$('#ctrldate_2').datebox('open');	
	});
	
	$('.ctrltime_2_button').on('vclick', function () {
		$('#ctrltime_2').datebox('open');
	});
	
	return{
		init:function(callback){
			console.log("INIT");
			openAndChooseDate1(callback);
		}
	}
	
	
})();


