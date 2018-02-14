application.controller('navigation', function ($rootScope, $http, $location, inventoryService, LoginService) {
    var self = this;


    $rootScope.$on('authorized', function(){
        inventoryService.get({id: LoginService.getCurrentUser().id}, function(response){
            $rootScope.inventory = response;
        });
    });

    $rootScope.$on('unauthorized', function(){
        console.log("unauthorized broadcast");
        $rootScope.user = {};
        $rootScope.authenticated = false;
        LoginService.setCurrentUser(null);
    });

    self.isAuthenticated = function(){
        return LoginService.getCurrentUser() != null;
    }


    self.logout = function (){
        $http.post('logout', {}).finally(function () {
           $rootScope.authenticated = false;
           $rootScope.user = {};
           $location.path('/');
        });
    }



});