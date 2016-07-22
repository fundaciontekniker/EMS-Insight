function dataVar(name, color) {
	this.name = name;
	this.color = color;
}

function Graph(container, measureUnit) {
	this.group = [];
	this.backAreas = [];
	
	maxX = null;
	minX = null;
	maxY = null;
	minY = null;
    
var margin = {
		top : 10,
		right : 150,
		bottom : 10,
		left : 60
	}, aspectRatio = 3 / 1, conW = 800 // $(window).width(),

	width = conW - margin.left - margin.right, height = (conW / aspectRatio)
			- margin.top - margin.bottom;

	var gr = d3.select(container).append("svg")
			.attr("viewBox","0 0 " + (width + margin.left + margin.right) + " "+ (height + margin.top + margin.bottom))
			.attr("preserveAspectRatio", "xMinYMin")
			.attr("class", measureUnit)

	var svg = gr.append("g")
			.attr("transform","translate(" + margin.left + "," + margin.top + ")");
			
	svg.append("defs").append("clipPath")
			.attr("id", "clip")
			.append("rect")
			.attr("width", width)
			.attr("height", height);
			

	var path = svg.append("g").attr("clip-path", "url(#clip)");

	var scaleX = d3.time.scale().range([ 0, width ]).domain([0,1]);
	var scaleY = d3.scale.linear().range([ 0, height ]).domain([0,1]);
	var yAxis = d3.svg.axis().scale(scaleY).ticks(10).orient("left");
	var xAxis = d3.svg.axis().scale(scaleX).ticks(10).orient("bottom");

	svg.append("g").attr("class", "x axis").attr("transform",
			"translate(0," + height + ")").call(xAxis);

	svg.append("g").attr("class", "y axis").attr("title", "ok").call(yAxis)
			.append("text").attr("transform", "translate(5,10)").attr("class",
					"y label").text(measureUnit);

	// add legend
	var legend = svg.append("g").attr("class", "legend");

	this.line = d3.svg.line()
	.defined(function(d) {return !isNaN(d.value);})
	.x(function(d) {return scaleX(Number(d.timestamp));})
	.y(function(d) {return scaleY(Number(d.value));});

	this.area = d3.svg.area()
	.defined(function(d) {return isNaN(d.value);})
	.x(function(d) {return scaleX(Number(d.timestamp));})
	.y0(function(d) {return scaleY(Number(d3.min(scaleY.domain())));})
	.y1(function(d) {return scaleY(Number(d3.max(scaleY.domain())));});
	
	this.espaceArea = function (values) {
		var min=d3.min(scaleY.domain());
		var max=d3.max(scaleY.domain());
		var step=(max-min)/3;
		
		var prevTime = values[0].timestamp;
		var path = "M" + scaleX(values[0].timestamp) + "," + scaleY(min)+"V" + scaleY(max-step*values[0]);
		
		for (var i = 0; i < values.length; i++) {
			path = path + "H" + scaleX(values[i].timestamp)+"V" + scaleY(Number(max-step*values[i]));
		}
		
		path=path+"V" + scaleY(min);
		return path
	};
	this.eventArea = function (values) {
		if (values.length==0)
		return null;
		var minX=d3.min(scaleX.domain());
		var maxX=d3.max(scaleX.domain());
		var minY=d3.min(scaleY.domain());
		var maxY=d3.max(scaleY.domain());
		var step=(maxY-minY)/3;
		
		var path = "M" + scaleX(minX) + "," + scaleY(minY);
		
		if(values[0].value==0)	//0
			path=path+"V" + scaleY(maxY);
		else if(values[0].value>1)	//2 o 3
			path =path+ "V" + scaleY(minY+step*(values[0].value-1));	
				
		for (var i = 0; i < values.length; i++) {
			path = path + "H" + scaleX(values[i].timestamp)+"V" + scaleY(minY+step*values[i].value);
		}
		
		path=path+"H" + scaleX(maxX)+"V"+scaleY(minY);
		return path
	};

	// Se añade variable, linea y leyenda
	this.addArea = function(v) {
		v.drawArea = path.append("path").attr("class", "area").attr("id","a_" + v.name).attr("style","opacity:0.2;stroke:"+v.color+";fill:" + v.color);
		v.legend=legend.append("g").attr("x", width + 10);
			v.legend.append("rect").attr("width", 10).attr("height", 10).style("fill", v.color);
			v.legend.append("text").text(v.name).attr("x",10).attr("y", 9);
		this.backAreas.push(v);
	}
	
	// Se añade variable, linea y leyenda
	this.addVar = function(v) {
		if(v.type=="evento" || v.type=="espacio")
			v.drawArea = path.append("path").attr("class", "area").attr("id","a_" + v.name).attr("style", "opacity:0.2;stroke:"+v.color+";fill:" + v.color);
		else 
			v.drawLine = path.append("path").attr("class", "line").attr("id","l_" + v.name).attr("style", "stroke:" + v.color);
		legend.append("rect").attr("x", width + 10).attr("y",this.group.length * 20).attr("width", 10).attr("height", 10).style("fill", v.color)
		legend.append("text").attr("x", width + 20).attr("y",(this.group.length * 20) + 9).text(v.name);
		this.group.push(v);
	}

	// Calcula minimos y máximos de los valores.

	this.updateGraph = function(d1, d2) {
		if (d1 && d2) {
			// console.log("updating " + measureUnit +" with date");
			minX = d1;
			maxX = d2;
		} else {
			// console.log("updating " + measureUnit);
			maxX = d3.max(this.group, function(v) {
				return d3.max(v.data, function(e) {
					return Number(e.timestamp)
				});
			});
			minX = d3.min(this.group, function(v) {
				return d3.min(v.data, function(e) {
					return Number(e.timestamp)
				});
			});
		}
		scaleX.domain([ minX, maxX ]);
		// console.log("scaleX[" + minX+":"+ maxX+"]");

		// Se calculan los maximos y minimos valores
		maxY = d3.max(this.group, function(v) {
			return d3.max(v.data, function(e) {
				if ((Number(e.timestamp) > maxX)
						|| (Number(e.timestamp) < minX))
					return -Number.MAX_VALUE; // ERROR al asignar MIN VALUE ??
				return Number(e.value);
			});
		});

		/*
		 * var max=Number.MIN_VALUE; for(var i=0;i<this.group.length;i++){
		 * for(var j=0;j<this.group[i].data.length;j++){
		 * if(this.group[i].data[j].value>max){ max=this.group[i].data[j].value; } } }
		 */

		minY = d3.min(this.group, function(v) {
			return d3.min(v.data, function(e) {
				if ((Number(e.timestamp) > maxX)
						|| (Number(e.timestamp) < minX))
					return Number.MAX_VALUE;
				return Number(e.value);
			});
		});

		if (maxY == minY) {
			if (maxY == 0 || !maxY) {
				scaleY.domain([ 0.5, -0.5 ]);
			} else {
				var margen = maxY / 10;
				scaleY.domain([ maxY + margen, minY - margen ]);
			}
		} else {
			var margen = (maxY - minY) / 10;
			scaleY.domain([ maxY + margen, minY - margen ]);
		}

		// Se actualizan los ejes
		var t = svg.transition(); // .duration(1000);
		t.select(".y.axis").call(yAxis);
		t.select(".x.axis").call(xAxis);

	
		// Se actualizan los valores de cada uno de los valores
		var vAC=0;  //Visible Area Count
		for ( var g = 0; g < this.backAreas.length; g++) {
			if(this.backAreas[g].type=="evento"){
				if(this.backAreas[g].data.length>0){
					this.backAreas[g].drawArea.attr("d",this.eventArea(this.backAreas[g].data));
					this.backAreas[g].legend.attr("transform","translate(0,"+(vAC * 15)+")");
					this.backAreas[g].legend.attr("opacity","1");
					vAC++;
				}else{
					this.backAreas[g].legend.attr("display","none");
				}
			}
			else if(this.backAreas[g].type=="espacio"){
				this.backAreas[g].drawArea.attr("d",this.espaceArea(this.backAreas[g].data));
			}else{
				this.backAreas[g].drawLine.attr("d", this.line(this.backAreas[g].data));
			}
		}
		
		// Se actualizan los valores de cada uno de los valores
		for ( var g = 0; g < this.group.length; g++) {
			if(this.group[g].type=="evento"){
				this.group[g].drawArea.attr("d",this.eventArea(this.group[g].data));
			}
			else if(this.group[g].type=="espacio"){
				this.group[g].drawArea.attr("d",this.espaceArea(this.group[g].data));
			}else{
				this.group[g].drawLine.attr("d", this.line(this.group[g].data));
			}
		}

		$(window).trigger('resize');
	}


	
	$(window).on(
			"resize",
			function() {
				var targetWidth = $(container).width();
				gr.attr("width", targetWidth).attr(
						"height",
						(targetWidth + margin.left + margin.right)
								/ aspectRatio);
			});
}
