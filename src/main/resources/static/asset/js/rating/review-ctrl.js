app = angular.module("admin-app",["ngRoute"]);

app.controller("product-ctrl", function($scope, $http){
	$scope.items = [];
	$scope.cates = [];
	$scope.form = {};
	
	$scope.initialize = function() {
		//load product
		$http.get("/api/reviews").then(resp => {
			$scope.items = resp.data;
			$scope.items.forEach(item => {
				item.createdDate = new Date(item.createdDate)
			})
		});
	}
	
	$scope.initialize();
	
	//them sp
	$scope.create = function() {
		var item = angular.copy($scope.form);
		console.log("2", item);
		$http.post(`/api/reviews`, item).then(resp => {
			resp.data.createdDate = new Date(resp.data.createdDate)
			$scope.items.push(resp.data);
			alert("Them thanh cong!");
		}).catch(error => {
			alert("Loi roi!");
			console.log("error", error);
		});
	}
});