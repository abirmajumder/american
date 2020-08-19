<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Business Lines</title>
<link rel='stylesheet' href='css/bootstrap.min.css' type='text/css' />
<script src='js/jquery-1.9.1.js' type='text/javascript'></script>
<script src='js/bootstrap.min.js' type='text/javascript'></script>
<script src='js/angular.min.js' type='text/javascript'></script>
<script src='js/common.js' type='text/javascript'></script>
<script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAkU3ZQYNsNHcTjUCRkfvq0OmcIkCJ9_Fc&callback=initMap&libraries=&v=weekly"
	defer></script>
<script>
		
		var app = angular.module( 'address_matcher',[]);
		app.controller( 'address_matcher_controller', function($scope,$http) {
			$scope.s = ${capsl};
			$scope.addresses = ${addresses};
			
			$scope.setSearch = function() {
				//alert("called " + JSON.stringify($scope.sadd) + " >> " + $scope.sadd.location); // .location
				/* $scope.s.location = $scope.sadd.location; */
				$scope.s = JSON.parse($scope.sadd);
				
			}
			
			$scope.search = function() {
				$http.post( "lookup", $scope.s).then( 
					function(response) {
						//alert( JSON.stringify( response ) );
						$scope.s = response.data;
					}, 
					function(err) { 
						//alert(JSON.stringify(err)); 
					} 
				)
				//initMap("mapTest",36.1138614, -115.1693514, 'Indigo Lounge');
			}
			
			$scope.matchAddress = function() {
				$http.post( "matchAddress", $scope.s).then( 
					function(response) {
						//alert( JSON.stringify( response ) );
						$scope.m = response.data;
						/* $scope.m.foreach(function(addr,ind) {
							initMap("map_" + addr.place_id, addr.geometry.location.lat, addr.geometry.location.lng, addr.name);
						}); */
						/* var addr;
						for( ind in response.data ) {
							alert(ind);
							addr = response.data[ind];
							alert( 'addr.place_id ' + addr.place_id + " addr.geometry.location.lat " + addr.geometry.location.lat + " " + addr.geometry.location.lng );
							var mapId = "map_" + addr.place_id; 
						} */
						initMap("map_ChIJveJ6yhkd5kcRYOYQY8v4-lc", 48.8615639 , 2.7911583, "DLAND");
					}, 
					function(err) { 
						alert(JSON.stringify(err)); 
					} 
				)
			}
			
			$scope.initing = function() {
				$(".places").each(function(itm,ind) {
					var placeId = $(this).html();
					var lat = $("#lat_" + placeId).html();
					var lng = $("#lng_" + placeId).html();
					var name = $("#name_" + placeId).html();
					//alert(placeId + " " + lat + " " + lng + " " + name);
					initMap( "map_" + placeId, parseFloat(lat) , parseFloat(lng), name);
				});
				//initMap("map_ChIJveJ6yhkd5kcRYOYQY8v4-lc", 48.8615639 , 2.7911583, "DLAND");
				//initMap("map_${cd.place_id}", ${cd.geometry.location.lat} , ${cd.geometry.location.lng}, ${cd.name});
			}

		});
		
		function initMap( mapId, latVal, lngVal, location ) {
			//alert(mapId + " " + latVal + " " + lngVal + " " + location);
			  const myLatLng = {
			    lat: latVal,
			    lng: lngVal
			  };
			  const map = new google.maps.Map(document.getElementById(mapId), {
			    zoom: 20,
			    center: myLatLng
			  });
			  new google.maps.Marker({
			    position: myLatLng,
			    map,
			    title: location
			  });
			}
	</script>
</head>
<body ng-app="address_matcher"
	ng-controller="address_matcher_controller" ng-init="initing()">
	<div class='container-fluid'>
		<div class='row'>
			<!-- <select ng-model='sadd.location' class='form-control' ng-change="setSearch()" >
				<option ng-repeat="x in addresses" value="{{x.location}}">{{x.location}}</option>
			</select> -->
			<div class='col-md-12'>
				<h5>Pre Populated Addresses</h5>
				<select ng-model='sadd' class='form-control' ng-change="setSearch()">
					<option ng-repeat="x in addresses" value="{{x}}">{{x.location}}</option>
				</select>
			</div>
		</div>
		<form:form action="matchAddress" modelAttribute="md">
			<div class='row'>
				<div class='col-md-12'>
					<h5>Address Search Options</h5>
				</div>
				<div class='col-md-6'>
					<!-- <input type="text" ng-model='s.location' class='form-control' placeholder="Location Name" /> -->
					<form:input type="text" ng-model='s.location' path="location"
						cssClass='form-control' placeholder="Location Name" />
				</div>
				<div class='col-md-6'>
					<!-- <input type="text" ng-model='s.account' class='form-control' placeholder="Account Name"/> -->
					<form:input type="text" ng-model='s.account' path='account'
						cssClass='form-control' placeholder="Account Name" />
				</div>
			</div>
			<div class='row' style="margin-top: 5px;">
				<div class='col-md-6'>
					<!-- <input type="text" ng-model='s.addressLine' class='form-control' placeholder="Address Line" /> -->
					<form:input type="text" ng-model='s.addressLine' path='addressLine'
						cssClass='form-control' placeholder="Address Line" />
				</div>
				<div class='col-md-6'>
					<form:input type="text" ng-model='s.city' path='city'
						cssClass='form-control' placeholder="City" />
					<!-- <input type="text" ng-model='s.city' class='form-control' placeholder="City"/> -->
				</div>
			</div>
			<div class='row' style="margin-top: 5px;">
				<div class='col-md-6'>
					<form:input type="text" ng-model='s.state' path='state'
						cssClass='form-control' placeholder="State" />
					<!-- <input type="text" ng-model='s.state' class='form-control' placeholder="State" /> -->
				</div>
				<div class='col-md-6'>
					<form:input type="text" ng-model='s.country' path='country'
						cssClass='form-control' placeholder="Country" />
					<!-- <input type="text" ng-model='s.country' class='form-control' placeholder="Country"/> -->
				</div>
			</div>

			<div class='row' align="center"
				style="margin-top: 10px; margin-bottom: 10px;">
				<input type="button" ng-click="search()" class='btn btn-info btn-sm'
					value="Search" /> <input type="submit" class='btn btn-info btn-sm'
					value="Match Address" />
				<!-- <input type="button" ng-click="matchAddress()" class='btn btn-info btn-sm' value="Match Address" /> -->
			</div>
		</form:form>
		<c:if test="${ not empty matches }">
			<c:forEach items="${matches}" var="can">
				<div class='col-md-6'>
					<h5>
						<span style="font-weight: bold;">Address : </span>${can.formatted_address}</h5>
					<h5>
						<span style="font-weight: bold;">Location : </span><span
							id='name_${can.place_id}'>${can.name}</span>
					</h5>
					<h5>
						<span style="font-weight: bold;">Place Id : </span><span
							class='places'>${can.place_id}</span>
					</h5>
					<h5>
						<span style="font-weight: bold;">Lat / Lng : </span><span
							id='lat_${can.place_id}'>${can.geometry.location.lat}</span> / <span
							id='lng_${can.place_id}'>${can.geometry.location.lng}</span>
					</h5>
				</div>
				<div class='col-md-6' id="map_${can.place_id}"
					style="height: 250px;"></div>
			</c:forEach>
		</c:if>
		<c:if test="${ not empty tracer }">
			<div class="row">
				<div class="col-md-12"><h4>Address Match Trace</h4></div>
			</div>		
			<c:forEach items="${tracer}" var='tcr' varStatus="loop">
				<div class="row">
					<div class="col-md-12"><h5 style="color: blue;" >${tcr}</h5></div>
					<div class="col-md-12">
						<c:choose>
							<c:when test="${ not empty trace[tcr] and trace[tcr] ne '[ ]' }">
								<textarea rows="5" cols="5" class="form-control"
									style="border: none; font-size: 12px;">${trace[tcr]}</textarea>							
							</c:when>
							<c:otherwise>
								<span style="color: red;">No Result</span>
							</c:otherwise>
						</c:choose>
						
					</div>
				</div>
			</c:forEach>
		</c:if>
	</div>
</body>
</html>

