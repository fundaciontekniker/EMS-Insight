
function dataVar(name, color) {
	this.data = [];
	this.name = name;
	this.color = color;
}

function Graph(container, measureUnit) {
	this.group = [];
	maxX = null;
	minX = null;

	var margin = {
		top : 10,
		right : 150,
		bottom : 10,
		left : 60
	},
	aspectRatio = 3 / 1,
	conW = 800 //$(window).width(),

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

	var path = svg.append("g");//.attr("clip-path", "url(#clip)");

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

	//Se añade variable, linea y leyenda
	this.addVar = function (v) {
		v.drawLine = path.append("path")
			.attr("class", "line")
			.attr("id", "l_" + v.name)
			.attr("style", "stroke:" + v.color);
		   

		legend.append("rect")
		.attr("x", width + 10)
		.attr("y", this.group.length * 20)
		.attr("width", 10)
		.attr("height", 10)
		.style("fill", v.color)

		legend.append("text")
		.attr("x", width + 20)
		.attr("y", (this.group.length * 20) + 9)
		.text(v.name);

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
					return d3.max(v.data, function (e) {
						return Number(e.timestamp)
					});
				});
			minX = d3.min(this.group, function (v) {
					return d3.min(v.data, function (e) {
						return Number(e.timestamp)
					});
				});
		}
		scaleX.domain([minX, maxX]);
		//console.log("scaleX[" + minX+":"+ maxX+"]");
		
		//Se calculan los maximos y minimos valores
		var maxY = d3.max(this.group, function (v) {
				return d3.max(v.data, function (e) {
					if ((Number(e.timestamp) > maxX) || (Number(e.timestamp) < minX))
						return - Number.MAX_VALUE;  //ERROR al asignar MIN VALUE ??
					return Number(e.value);
				});
			});
			
		/*var max=Number.MIN_VALUE;
		for(var i=0;i<this.group.length;i++){
			for(var j=0;j<this.group[i].data.length;j++){
				if(this.group[i].data[j].value>max){
					max=this.group[i].data[j].value;
				}
			}
		}*/
			
		var minY = d3.min(this.group, function (v) {
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
