
function dataVar(name, color,pred_color) {
	this.data = [];
	this.pred_data = [];
	this.name = name;
	this.color = color;
	this.pred_color=pred_color;
}

function Graph(container, measureUnit) {
	console.log(measureUnit);
	this.group = [];

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
 
	 var focus = svg.append("g")
     .attr("class", "focus")
     .style("display", "none");

	 focus.append("circle")
     	.attr("r", 4.5);

	 focus.append("text")
     	.attr("x", 9)
     	.attr("dy", ".35em");
	
	 var formatValue = d3.format(",.2f");
	 
	// add legend
	var legend = svg.append("g").attr("class", "legend");

	this.line = d3.svg.line()
		.defined(function (d) {return !isNaN(d.value);})
		.x(function (d) {
			//console.log(scaleX(Number(d.timestamp)));
			return Number(scaleX(d.timestamp));
		})
		.y(function (d) {
			//console.log(scaleY(Number(d.value)));
			return Number(scaleY(d.value));
		});

	this.predInt=path.append("rect")
	.attr("id", "predInt")
	.attr("x",width-10)
	.attr("width", 10)
	.attr("y",0 )
	.attr("height", height)
	.attr("style", "fill:rgba(0,10,100,0.05)");
	
	//Se añade variable, linea y leyenda
	this.addVar = function (v) {
		
		v.drawLine = path.append("path")
			.attr("class", "line")
			.attr("id", "l_" + v.name)
			.attr("style", "stroke:" + v.color)
			.on("mouseover",function(){focus.style("display", null); })
		    .on("mouseout",function() {focus.style("display", "none"); })
		   .on("mousemove",function(){
		    	focus.attr("transform", "translate(" + d3.mouse(this)[0] + "," + d3.mouse(this)[1] + ")");
		   	  	focus.select("text").text(formatValue(scaleY.invert(d3.mouse(this)[1]))+" "+measureUnit + " ["+scaleX.invert(d3.mouse(this)[0]).toLocaleString()+"]");
		    });
		
		v.drawPredLine = path.append("path")
		.attr("class", "line")
		.attr("id", "pl_" + v.name)
		.attr("style", "stroke:" + v.pred_color)
		.on("mouseover",function(){focus.style("display", null); })
		.on("mouseout",function() {focus.style("display", "none"); })
		.on("mousemove",function(){
		  	focus.attr("transform", "translate(" + d3.mouse(this)[0] + "," + d3.mouse(this)[1] + ")");
			focus.select("text").text(formatValue(scaleY.invert(d3.mouse(this)[1]))+" "+measureUnit + " ["+scaleX.invert(d3.mouse(this)[0]).toLocaleString()+"]");
		});;
				
		
		
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
				$("#pl_"+v.name).fadeToggle( "slow", "linear" );
				$("#cbl_"+v.name).fadeToggle( "slow", "linear" );			
				});

		legend.append("text")
		.attr("x", width + 20)
		.attr("y", (this.group.length * 20) + 9)
		.text(v.name);

		this.group.push(v);
	}

	//Calcula  minimos y máximos de los valores.
	
	this.updateGraph = function (zoom) {
		
		//console.log("updating " + measureUnit);
		maxX_P = d3.max(this.group, function (v) {
				return d3.max(v.pred_data, function (e) {
					return Number(e.timestamp)
				});
		});			
		
		maxX_R = d3.max(this.group, function (v) {
					return d3.max(v.data, function (e) {
						return Number(e.timestamp)
					});
				});
		
		minX_R = d3.min(this.group, function (v) {
			return d3.min(v.data, function (e) {
				return Number(e.timestamp)
			});
		});
		
		minX_P= d3.min(this.group, function (v) {
					return d3.min(v.pred_data, function (e) {
						return Number(e.timestamp)
				});
		});
		
		var psi= Number(maxX_P-maxX_R)
		maxX=Number(maxX_P);
		if(zoom==1)
			minX=Number(maxX_R-(19*psi)); 
		else if (zoom==2)
			minX=Number(maxX_R-(9*psi)); 
		else
			minX=Number(minX_R);
		scaleX.domain([minX,maxX]); 
		
		
		//Se calculan los maximos y minimos valores
		var maxY = d3.max(this.group, function (v) {
			var maxR =d3.max(v.data, function (e) {if(minX<e.timestamp && e.timestamp<maxX)return Number(e.value);});
			var maxP =d3.max(v.pred_data, function (e) {if(minX<e.timestamp && e.timestamp<maxX)return Number(e.value);});
			if (maxR>maxP)
				return maxR;
			else
				return maxP;
		});
		
	
		var minY = d3.min(this.group, function (v) {
			var minR =d3.min(v.data, function (e) {if(minX<e.timestamp && e.timestamp<maxX)return Number(e.value);});
			var minP =d3.min(v.pred_data, function (e) {if(minX<e.timestamp && e.timestamp<maxX)return Number(e.value);});
			if (minR<minP)
				return minR;
			else
				return minP;
		});
		
		
		if(maxY==minY)	{
			if(maxY==0){
			scaleY.domain([0.5, -0.5]);
			}else{
			var margen=maxY/10;
			scaleY.domain([maxY+margen, minY-margen]);
			//console.log("["+(maxY+margen)+","+ (minY-margen)+"]");
			}
		}
		else{
			var margen=(maxY-minY)/10;
			scaleY.domain([maxY+margen, minY-margen]);
			//console.log("["+(maxY+margen)+","+ (minY-margen)+"]");
		}

		//Se actualizan los ejes
		var t = svg.transition(); //.duration(1000);
		t.select(".y.axis").call(yAxis);
		t.select(".x.axis").call(xAxis);

		//Se actualizan los valores de cada uno de los valores
		
		var lastRealDate=0;
		for (var g = 0; g < this.group.length; g++){
			this.group[g].drawLine.attr("d", this.line(this.group[g].data));
			var lastReal=this.group[g].data[Number(this.group[g].data.length-1)];
			if(lastRealDate<lastReal.timestamp)
				lastRealDate=lastReal.timestamp;
			var predValCopy=this.group[g].pred_data;
			predValCopy.unshift(lastReal);
			this.group[g].drawPredLine.attr("d", this.line(predValCopy));
		}

		this.predInt.attr("x",scaleX(lastRealDate)).attr("width",100);
		
		
		
		$(window).trigger('resize');
	}

	$(window).on("resize", function () {
		var targetWidth = $(window).width();
		gr.attr("width", targetWidth)
		.attr("height", (targetWidth + margin.left + margin.right) / aspectRatio);
	});
}
