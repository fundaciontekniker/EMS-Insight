function dataVar(name, color,desc) {
	this.data = [];
	this.alarms = [];
	this.name = name;
	if(desc)
		this.desc = desc;
	else
		this.desc = name;
	this.color = color;
	this.visible=true;
}

function alarmThreshold(name,value,severity) {
	this.name = name;
	this.value = value;
	this.severity=severity;
}


function Graph(container, measureUnit) {
	
	var thisGraph=this;
	var maxX = null;
	var minX = null;
	
	this.group = [];

	this.getDataVar =function(varName){
		for(i=0;i<this.group.length;i++)
			if(this.group[i].name==varName)
				return this.group[i];
	}
	
	this.disableVar =function(varName){
		for(i=0;i<this.group.length;i++)
			if(this.group[i].name==varName){
				this.group[i].visible=false;
				$("#l_"+this.group[i].name).fadeToggle( "slow", "linear" );
				$("#cbl_"+this.group[i].name).fadeToggle( "slow", "linear" );	
			}
	}
	
	var margin = {
		top : 10,
		right : 150,
		bottom : 10,
		left : 60
	},
	aspectRatio =  (3 / 1),
	conW = 800;//$(container).width(),
	width = conW - margin.left - margin.right,
	height = (conW / aspectRatio) - margin.top - margin.bottom;

	var gr = d3.select(container).append("svg")
	.attr("viewBox", "0 0 " + (width + margin.left + margin.right) + " " + (height + margin.top + margin.bottom))
	.attr("preserveAspectRatio", "xMinYMin")
	.attr("class", measureUnit)

	var svg = gr.append("g")
	.attr("transform", "translate(" + margin.left + "," + margin.top + ")");

	svg.append("defs").append("clipPath")
	.attr("id", "clip")
	.append("rect")
	.attr("width", width)
	.attr("height", height);

	var path = svg.append("g").attr("clip-path", "url(#clip)");

	var scaleX = d3.time.scale().range([0, width]);
	var scaleY = d3.scale.linear().range([0, height]);
	var yAxis = d3.svg.axis().scale(scaleY).ticks(10).orient("left");
	var xAxis = d3.svg.axis().scale(scaleX).ticks(10).orient("bottom");

	svg.append("g")
	.attr("class", "x axis")
	.attr("transform", "translate(0," + height + ")")
	.call(xAxis);

	svg.append("g")
	.attr("class", "y axis")
	.attr("title", "ok")
	.call(yAxis)
	.append("text")
	.attr("transform", "translate(5,10)")
	.attr("class", "y label")
	.text(measureUnit);

	// add legend
	var legend = svg.append("g").attr("class", "legend");
	 var focus = svg.append("g")
     .attr("class", "focus")
     .style("display", "none");

	 focus.append("circle")
     	.attr("r", 4.5);

	 focus.append("text")
     	.attr("x", 9)
     	.attr("dy", ".35em");

	
	this.line = d3.svg.line()
		.defined(function (d) {
			return !isNaN(d.value);
		})
		.x(function (d) {
			//console.log(scaleX(Number(d.timestamp)));
			return scaleX(Number(d.timestamp));
		})
		.y(function (d) {
			//console.log(scaleY(Number(d.value)));
			return scaleY(Number(d.value));
		});

	var formatValue = d3.format(",.2f");
	
	//Se añade variable, linea y leyenda
	this.addVar = function (v) {
		v.drawLine = path.append("path")
			.attr("class","line")
			.attr("id","l_" + v.name)
			.attr("style","stroke:" + v.color)
			.on("mouseover",function(){focus.style("display", null); })
		    .on("mouseout",function() {focus.style("display", "none"); })
		    .on("mousemove",function(){
		    	focus.attr("transform", "translate(" + d3.mouse(this)[0] + "," + d3.mouse(this)[1] + ")");
		   	  	focus.select("text").text(formatValue(scaleY.invert(d3.mouse(this)[1]))+" "+measureUnit + " ["+scaleX.invert(d3.mouse(this)[0]).toLocaleString()+"]");
		    });
		//console.log(v);
		/*for(var i=0;i<v.alarms.length;i++)
			console.log(v.alarms[i]);
		*/
	    legend.append("rect")
		.attr("x", width + 10)
		.attr("y", this.group.length * 20)
		.attr("width", 8)
		.attr("height", 8)
		.style("fill","none")
		.style("stroke", v.color)
		.style("stroke-width",1)
	    
	    legend.append("rect")
		.attr("id", "cbl_"+v.name)
		.attr("x", width + 10)
		.attr("y", this.group.length * 20)
		.attr("width", 8)
		.attr("height", 8)
		.style("fill", v.color)
		.style("opacity", 0.7)
		
		legend.append("rect")
		.attr("id", "cd_"+v.name)
		.attr("x", width + 10)
		.attr("y", this.group.length * 20)
		.attr("width", 8)
		.style("fill","transparent")
		.attr("height", 8)
		.on("click",function(){
			v.visible=!v.visible;
			$("#l_"+v.name).fadeToggle( "slow", "linear" );
			$("#cbl_"+v.name).fadeToggle( "slow", "linear" );	
			thisGraph.updateGraph(minX,maxX);
			});
		
		legend.append("text")
		.attr("x", width + 20)
		.attr("y", (this.group.length * 20) + 9)
		.text(v.desc);

		this.group.push(v);
	}

	//Calcula  minimos y máximos de los valores.
	
	
	this.updateGraph = function (d1, d2) {
		if (d1 && d2) {
			//console.log("updating " + measureUnit +" with date");
			minX = d1;
			maxX = d2;
		} else {
			//console.log("updating " + measureUnit);
			maxX = d3.max(this.group, function (v) {
					if (!v.visible)
						return -Number.MAX_VALUE 
					else
						return d3.max(v.data, function (e) {
							return Number(e.timestamp)
					});
				});
			minX = d3.min(this.group, function (v) {
					if (!v.visible)
						return Number.MAX_VALUE 
					else
					return d3.min(v.data, function (e) {
						return Number(e.timestamp)
					});
				});
		}
		scaleX.domain([minX, maxX]);
		//console.log("scaleX[" + minX+":"+ maxX+"]");
		
		//Se calculan los maximos y minimos valores
		var maxY = d3.max(this.group, function (v) {
				if (v.visible)
					return d3.max(v.data, function (e) {
					if ((Number(e.timestamp) > maxX) || (Number(e.timestamp) < minX))
						return - Number.MAX_VALUE;  //ERROR al asignar MIN VALUE ??
					return Number(e.value);
				});
			});

			
		var minY = d3.min(this.group, function (v) {
				if (!v.visible)
					return Number.MAX_VALUE 
					else
				return d3.min(v.data, function (e) {
					if ((Number(e.timestamp) > maxX) || (Number(e.timestamp) < minX))
						return Number.MAX_VALUE;
					return Number(e.value);
				});
			});
		
		//console.log(maxY);
		//console.log(minY);
		
		if(maxY==minY)	{
			if(maxY==0){
			scaleY.domain([0.5, -0.5]);
			}else{
			var margen=maxY/10;
			scaleY.domain([maxY+margen, minY-margen]);}
		}
		else{
			var margen=(maxY-minY)/10;
			scaleY.domain([maxY+margen, minY-margen]);
		}

		//Se actualizan los ejes
		var t = svg.transition(); //.duration(1000);
		t.select(".y.axis").call(yAxis);
		t.select(".x.axis").call(xAxis);

		//Se actualizan los valores de cada uno de los valores
		for (var g = 0; g < this.group.length; g++){
			this.group[g].drawLine.attr("d", this.line(this.group[g].data));
		}

		$(window).trigger('resize');
		

	}

	$(window).on("resize", function () {
		var targetWidth = $(container).width();
		gr.attr("width", targetWidth)
		.attr("height", (targetWidth + margin.left + margin.right) / aspectRatio);
	});
	
	
}
