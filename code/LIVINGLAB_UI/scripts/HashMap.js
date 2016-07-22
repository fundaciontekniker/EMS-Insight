//Coleccion clave->valor

function HashMap() {
	function Pair(key, value) {
		this.key = key;
		this.value = value;
	}

	var map = [];
	
	this.get = function (key) {
		for (var i = 0; i < map.length; i++) {
			if (map[i].key == key) {
				return map[i].value;
			}
		}
		return null;
	}
	
	this.put = function (key, value) {
		for (var i = 0; i < map.length; i++) {
			if (map[i].key == key) {
				map[i].value = value;
				return false;
			}
		}
		map.push(new Pair(key, value));
		return true;
	}
	
	this.keys = function () {
		var k=[];
		for (var i = 0; i < map.length; i++) 
				k.push(map[i].key);	
		return k;
	}
	
	function sort(){
		map.sort(function(a,b) {					
	    var x = a.key.toLowerCase();
	    var y = b.key.toLowerCase();
	    return x < y ? -1 : x > y ? 1 : 0;
		});
	}
	
	this.values = function () {
		sort();
		var v=[];
		for (var i = 0; i < map.length; i++) 
				v.push(map[i].value);	
		return v;
	}
}

//Coleccion clave->valor

function DataMap() {
	function DataCol(name,values,type) {
		this.name = name;
		this.type = type;
		this.values = values;
	}

	var map = [];
	this.get = function (name) {
		for (var i = 0; i < map.length; i++) {
			if (map[i].name == name) {
				return map[i];
			}
		}
		return null;
	}
	
	this.put = function (name,values,type) {
		for (var i = 0; i < map.length; i++) {
			if (map[i].name == name) {
				map[i].type = type;
				map[i].values = values;
				return false;
			}
		}
		map.push(new DataCol(name,values,type));
		return true;
	}
	
	this.values = function () {
		return map;
	}
}

