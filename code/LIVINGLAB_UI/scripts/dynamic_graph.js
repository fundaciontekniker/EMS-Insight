//Añadirá un gráfico al container
function Graph(container, datePicker) {

	this.minX = null;
	this.maxX = null;
	this.minY = null;
	this.maxY = null;

	var colors = ["blue", "green", "red", "yellow", "black", "pink"];
	var fill_colors = ["lightblue", "lightgreen", "lightcoral", "lightyellow", "gray", "lightpink"];
	var lineMap = new DataMap();

	this.clear = function () {
		console.log("clearing graph data[" + container + "]");
		this.minX = null;
		this.maxX = null;
		this.minY = null;
		this.maxY = null;
		lineMap = new DataMap();
		$("#drawPlace").empty();
	}

	this.changeData = function (valueList, name, type) {
		console.log("change data[" + name + "]");
		//Calcular valores maximos y minimos de los ejes
		for (var i = 0; i < valueList.length; i++) {
			var a = Number(valueList[i].timestamp);
			if (this.minX == null ||this.minX > a) {
				this.minX = a;
			}
			if (this.maxX == null || this.maxX < a) {
				this.maxX = a;
			}

			if(!isNaN(valueList[i].value)){
			var c = Number(valueList[i].value);
			if (this.minY == null || this.minY > c) {
				this.minY = c;
			}
			if (this.maxY == null || this.maxY < c) {
				this.maxY = c;
			}}
		}

		//holgura
		if (this.minY == this.maxY) {
			this.maxY = this.maxY + 1
		}

		console.log("Rango Y:" + this.minY + ":" + this.maxY);
		console.log("Rango X:" + new Date(this.minX) + ":" + new Date(this.maxX));

		x.domain([this.minX, this.maxX]);
		y.domain([this.minY, this.maxY]);

		if (datePicker) {
			var d1 = new Date(this.minX);
			var d2 = new Date(this.maxX);
			datePicker.setDates(d1, d2);
		}

		if (lineMap.put(name, valueList, type)) {
			if (type == "area") {
			console.log("Adding area " + name)
			drawPlace.append("path")
				.attr("id", name)
				.attr("clip-path", "url(#clipper)")
				.style("stroke","none")
				.style("fill",  fill_colors[lineMap.values().length - 1])
			}else{//Line por defecto
			console.log("Adding line " + name);
			drawPlace.append("path")
				.attr("id", name)
				.attr("clip-path", "url(#clipper)")
				.style("stroke", colors[lineMap.values().length - 1])
				.style("fill", "none")
			}
			
			//Area de errores
			console.log("Adding area " + name + "_err");
			drawPlace.append("path")
			.attr("id", name + "_err")
			.attr("clip-path", "url(#clipper)")
			.style("stroke","none")
			.style("fill", "grey")
			.style("opacity", 0.2)			
		};

		this.refresh()
	}

	this.changeDate = function () {
		console.log("change date");
		x.domain([datePicker.d1.getTime(), datePicker.d2.getTime()]);
		this.refresh();
	}

	this.refresh = function () {
		console.log("refreshing");
		var t = svg.transition() //.duration(1000);
			t.select(".y.axis").call(yAxis);
		t.select(".x.axis").call(xAxis);
		var lines = lineMap.values();
		for (var i = 0; i < lines.length; i++) {
			console.log(lines[i]);
			if(lines[i].type=="area")
				t.select("#" + lines[i].name).attr("d", this.area(lines[i].values));
			else
				t.select("#" + lines[i].name).attr("d", this.line(lines[i].values));
			
			var err = this.errorArea(lines[i].values);
			console.log(err);
			if (err != "")
				t.select("#" + lines[i].name + "_err").attr("d", err);
			/*	}
			if(lines[i].type=="area"){
			t.select("#" + lines[i].name).attr("d", this.area(lines[i].values));
			}*/
		}
	}

	this.clear();
	$(container).empty();

	var margin = {
		top : 10,
		right : 10,
		bottom : 10,
		left : 40
	},
	width = 600 - margin.left - margin.right,
	height = 200 - margin.top - margin.bottom;

	var x = d3.time.scale()
		.range([0, width]);

	var y = d3.scale.linear()
		.range([height, 0]);

	var xAxis = d3.svg.axis()
		.scale(x)
		.orient("bottom")
		//.tickFormat(d3.time.format("%Y-%m-%d %H:%M:%S.%f "));

		var yAxis = d3.svg.axis()
		.scale(y)
		.orient("left");

	this.line = d3.svg.line()
		.defined(function(d){
			return !isNaN(d.value)
		})
		.x(function (d) {
			return x(d.timestamp);
		})
		.y(function (d) {
			return y(d.value);
		});

		
		
	this.area = d3.svg.area()
		.defined(function(d){
			return !isNaN(d.value)
		})
		.x(function (d) {
			return x(Number(d.timestamp));
		})
		.y0(function (d) {
			var v = Number(d.value);
			if (v > 0)
				return y(this.minY);
			else
				return y(v);
		})
		.y1(function (d) {
			var v = Number(d.value);
			if (v > 0)
				return y(v);
			else
				return y(this.maxY);
		});

	/*this.liyne = function (values) {
		var path = "";
		var error = false;
		for (var i = 0; i < values.length; i++) {
			if (isNaN(values[i].value)) {
				if (error) {}
				else {
					error = true;
				}
			} else {
				if (error) {	
					error = false;
					path = path + "M" + x(values[i].timestamp) + "," + y(values[i].value)					
				} else {
					if (path="")
						path = path + "M" + x(values[i].timestamp) + "," + y(values[i].value)
					else
						path = path + "L" + x(values[i].timestamp) + "," + y(values[i].value)
				}
			}
		}
		return path
	}*/

	this.errorArea = function (values) {
		var path = "";
		var error = false;
		var prevTime = values[0].timestamp;
		for (var i = 0; i < values.length; i++) {
			if (isNaN(values[i].value)) {
				if (error) { //Continua el error
					path = path + "H" + x(values[i].timestamp)
				} else {
					error = true; //Comienza el error
					path = path + "M" + x(prevTime) + "," + y(this.minY) + "V" + y(this.maxY) + "H" + x(values[i].timestamp)
				}
			} else {
				if (error) {
					error = false;
					path = path +"H" + x(values[i].timestamp)+"V"+y(this.minY) //Finaliza el error
				}
			}
			prevTime = values[i].timestamp;
		}
		if (error)
			path=path+"V" + y(this.minY)
		return path
	}
	
	/*this.errorArea = d3.svg.area()
		.defined(function(d){
			return isNaN(d.value)
		})
		.x(function (d) {
			return x(Number(d.timestamp));
		})
		.y0(function (d) {
				return y(this.minY);
			})
		.y1(function (d) {
			return y(this.maxY);
		});*/

	var svg = d3.select(container).append("svg")
		//.attr("width", $(this).parent().width())//width + margin.left + margin.right)
		//.attr("height", height + margin.top + margin.bottom)
		.attr("id", "graph")
		.attr("viewBox", "0 0 " + (width + margin.left + margin.right) + " " + (height + margin.top + margin.bottom))
		.attr("preserveAspectRatio", "xMinYMin")
		.append("g")
		.attr("transform", "translate(" + margin.left + "," + margin.top + ")");

	svg.append("clipPath")
	.attr("id", "clipper")
	.append("svg:rect")
	.attr("width", width)
	.attr("height", height);

	svg.append("g")
	.attr("class", "x axis")
	.attr("transform", "translate(0," + height + ")")
	.call(xAxis);

	svg.append("g")
	.attr("class", "y axis")
	.call(yAxis)
	.append("text")
	.attr("transform", "rotate(-90)")
	.attr("y", 6)
	.attr("dy", ".71em")
	.style("text-anchor", "end")

	var drawPlace = svg.append("g")
		.attr("id", "drawPlace");

	var aspect = width / height;
	chart = $("#graph");
	$(window).on("resize", function () {
		var targetWidth = chart.parent().width();
		chart.attr("width", targetWidth);
		chart.attr("height", (targetWidth + margin.left + margin.right) / aspect);
	})

	$(document).on('pageload', function () {
		$(window).trigger('resize');
	});
	$(document).on('pagechange', function () {
		$(window).trigger('resize');
	});
}

function ElectricGraph(container, datePicker) {

	this.minX = null;
	this.maxX = null;
	this.minY = null;
	this.maxY = null;

	var colors = ["blue", "green", "red", "yellow", "black", "pink"];
	var fill_colors = ["lightblue", "lightgreen", "lightcoral", "lightyellow", "gray", "lightpink"];

	var enerMap = new HashMap();

	this.line = d3.svg.line()
		.x(function (d) {
			return x(Number(d.timestamp));
		})
		.y(function (d) {
			return y(Number(d.value));
		});

	this.clear = function () {
		this.minX = null;
		this.maxX = null;
		this.minY = null;
		this.maxY = null;
		$("#drawPlace").empty();
		enerMap = new HashMap();
	}

	this.changeData = function (measureList, vList) {
		console.log("change data[" + name + "]");

		//Inicilizamos arrays
		for (var j = 0; j < vList.length; j++) {
			var field = vList[j].varPhyType;
			enerMap.put(field, []);
		}

		//Calcular valores maximos y minimos de los ejes
		for (var i = 0; i < measureList.length; i++) {

			var a = Number(measureList[i].timestamp);
			if (this.minX == null || this.minX > a) {
				this.minX = a;
			}
			if (this.maxX == null || this.maxX < a) {
				this.maxX = a;
			}

			for (var j = 0; j < vList.length; j++) {
				var c = Number(measureList[i][vList[j].varPhyType]);
				if (this.minY > c) {
					this.minY = c;
				}
				if (this.maxY < c) {
					this.maxY = c;
				}

				var val = new Value();
				val.value = c;
				val.timestamp = a;
				enerMap.get(vList[j].varPhyType).push(val);
			}
		}

		console.log("Rango Y:" + this.minY + ":" + this.maxY);
		console.log("Rango X:" + new Date(this.minX) + ":" + new Date(this.maxX));

		x.domain([this.minX, this.maxX]);
		y.domain([this.minY, this.maxY]);

		if (datePicker) {
			var d1 = new Date(this.minX);
			var d2 = new Date(this.maxX);
			datePicker.setDates(d1, d2);
		}

		var enerValues = enerMap.values();
		for (var i = 0; i < enerValues.length; i++) {
			console.log("Dibujando linea [" + enerValues[i].key + "]");
			drawPlace.append("path")
			.attr("id", enerValues[i].key)
			.attr("clip-path", "url(#clipper)")
			.style("stroke", colors[i])
			.style("fill", "none")
			//.style("opacity", 0.5)
			
		legend.append("rect")
		.attr("x", width + 10)
		.attr("y", i * 20)
		.attr("width", 10)
		.attr("height", 10)
		.style("fill", colors[i])

		legend.append("text")
		.attr("x", width+20)
		.attr("y", (i * 20) + 9)
		.text(enerValues[i].key);
		}

		this.refresh()
	}

	this.changeDate = function () {
		console.log("change date");
		x.domain([datePicker.d1.getTime(), datePicker.d2.getTime()]);
		y.domain([this.minY, this.maxY]);
		this.refresh();
	}

	this.refresh = function () {
		console.log("refreshing");
		var t = svg.transition() //.duration(1000);
			t.select(".y.axis").call(yAxis);
		t.select(".x.axis").call(xAxis);

		var lines = enerMap.values();
		for (var i = 0; i < lines.length; i++) {
			//t.select("#" + lines[i].key).attr("d", line(lines[i].value));
			t.select("#" + lines[i].key).attr("d", this.line(lines[i].value));
		}
	}

	this.clear();
	$(container).empty();

	var margin = {
		top : 10,
		right : 150,
		bottom : 10,
		left : 40
	},
	width = 800 - margin.left - margin.right,
	height = 150 - margin.top - margin.bottom;

	var x = d3.time.scale()
		.range([0, width]);

	var y = d3.scale.linear()
		.range([height, 0]);

	var xAxis = d3.svg.axis()
		.scale(x)
		.orient("bottom")
		//.tickFormat(d3.time.format("%Y-%m-%d %H:%M:%S.%f "));

		var yAxis = d3.svg.axis()
		.scale(y)
		.orient("left");

	var svg = d3.select(container).append("svg")
		//.attr("width", $(this).parent().width())//width + margin.left + margin.right)
		//.attr("height", height + margin.top + margin.bottom)
		.attr("id", "graph")
		.attr("viewBox", "0 0 " + (width + margin.left + margin.right) + " " + (height + margin.top + margin.bottom))
		.attr("preserveAspectRatio", "xMinYMin")
		.append("g")
		.attr("transform", "translate(" + margin.left + "," + margin.top + ")");

	svg.append("clipPath")
	.attr("id", "clipper")
	.append("svg:rect")
	.attr("width", width)
	.attr("height", height);

	svg.append("g")
	.attr("class", "x axis")
	.attr("transform", "translate(0," + height + ")")
	.call(xAxis);

	svg.append("g")
	.attr("class", "y axis")
	.call(yAxis)
	.append("text")
	.attr("transform", "rotate(-90)")
	.attr("y", 6)
	.attr("dy", ".71em")
	.style("text-anchor", "end")

	// add legend
	var legend = svg.append("g").attr("class", "legend")
	
	var drawPlace = svg.append("g")
		.attr("id", "drawPlace");

	var aspect = width / height;
	chart = $("#graph");
	$(window).on("resize", function () {
		var targetWidth = chart.parent().width();
		chart.attr("width", targetWidth);
		chart.attr("height", (targetWidth + margin.left + margin.right) / aspect);
	})

	$(document).on('pageload', function () {
		$(window).trigger('resize');
	});
	$(document).on('pagechange', function () {
		$(window).trigger('resize');
	});
}

