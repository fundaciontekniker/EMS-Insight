function DatePicker() {
	var d = this;	
	this.d1=null;
	this.d2=null;
	
	function getCacheDate(dt) {
		var v = sessionStorage.getItem(dt);
		if (v)
			return new Date(Number(v));
		else
			return null;
	};

	this.loadFromCache=function(){
		d.d1 = getCacheDate('date1');
		console.log(d.d1);
		d.d2 = getCacheDate('date2');
		console.log(d.d2);
		d.reloadDateBoxes();
	};
	
	this.reloadDateBoxes=function(){
		if (d.d1) {
			var t1=getSimpleTime(d.d1);
			$('#ctrldate_1').datebox('setTheDate', d.d1).trigger('datebox', {'method' : 'doset'});
			$('#ctrltime_1').trigger('datebox', {'method':'set','value':t1});
			
			$('.ctrldate_1_button').find('.ui-btn-text').text($("#ctrldate_1").val());
			$('.ctrltime_1_button').find('.ui-btn-text').text($("#ctrltime_1").val());
		}
		if (d.d2) {
			var t2=getSimpleTime(d.d2);
			$('#ctrldate_2').datebox('setTheDate', d.d2).trigger('datebox', {'method' : 'doset'});
			$('#ctrltime_2').trigger('datebox', {'method':'set','value':t2});
			
			$('.ctrldate_2_button').find('.ui-btn-text').text($("#ctrldate_2").val());
			$('.ctrltime_2_button').find('.ui-btn-text').text($("#ctrltime_2").val());
		}
	}


	
	function u1() {
		var dt1 = $('#ctrldate_1').val().split("-").join(" ") + " " + $('#ctrltime_1').val();
		d.d1 = new Date(dt1);
		sessionStorage.setItem('date1', d.d1.getTime());
	}

	function u2() {
		var dt2 = $('#ctrldate_2').val().split("-").join(" ")  + " " + $('#ctrltime_2').val();
		d.d2 = new Date(dt2);
		sessionStorage.setItem('date2', d.d2.getTime());
	}

	//------BINDINGS-------------
	$('#ctrldate_1').bind('change', function (e, p) {
		$('.ctrldate_1_button').find('.ui-btn-text').text($(this).val());
		u1();
	});
	
	$('#ctrltime_1').bind('change', function (e, p) {
		$('.ctrltime_1_button').find('.ui-btn-text').text($(this).val());
		u1();
	});
	
	$('#ctrldate_2').bind('change', function (e, p) {
		$('.ctrldate_2_button').find('.ui-btn-text').text($(this).val());
		u2();
	});
	
	$('#ctrltime_2').bind('change', function (e, p) {
		$('.ctrltime_2_button').find('.ui-btn-text').text($(this).val());
		u2();
	});

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
	
	function getSimpleTime(date){
		var h=date.getHours();
		if(h<10)h="0"+h;
		var m=date.getMinutes();
		if(m<10)m="0"+m;
		return h+":"+m;
		
	}
}
