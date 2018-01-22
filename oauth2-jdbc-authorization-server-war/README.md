# oauth2-authorization-server

ENDPOINTS:
=====================
    1. Get access token
    The client sends a request for authorization and authorization server responds with an access token and a refresh token.
    
        Request example:
            Url: http://localhost:8090/oauth/token (POST)
            Params:
                grant_type = password
                username = admin
                password = 123456
            Authorization Type: Basic
                username (client id)    = api3ClientId
                password (client secret)= secret-key-for-api3
          
        Expected result:
            {
                "access_token": "ca247a82-e365-46a1-9a26-fc8f27a4ef90",
                "token_type": "bearer",
                "refresh_token": "6c40bf13-8cbe-4943-aec1-811915e4b163",
                "expires_in": 599,
                "scope": "read write api3"
            }
    
    2. Get access token by using refresh_token
    We can send a request for access_token with refresh token. This time we don’t need username and password.
    
        Request example:
            Url: http://localhost:8090/oauth/token (POST)
            Params:
                grant_type = refresh_token
                refresh_token = bbd18495-96cf-46ed-98bd-b3d2fad658f3
            Authorization Type: Basic
                username (client id)    = api3ClientId
                password (client secret)= secret-key-for-api3
        
        Expected result:
            {
                "access_token": "5c353a2c-4f17-4397-8613-881b9dcff0fb",
                "token_type": "bearer",
                "refresh_token": "2d66bff0-4384-445c-9a80-f12a79370cc3",
                "expires_in": 599,
                "scope": "read write api3"
            }
        (p/s: refresh_token is also refreshed with a new value when calling refresh token request)
    
    3. Revoke access token & refresh token 
    When logout we need to revoke access_token & refresh_token to avoid other issues. And this could be completed with access token like below:
        Request example:
            Url: http://localhost:8090/oauth/token (GET)
            Params:
                Authorization = Bearer 5c353a2c-4f17-4397-8613-881b9dcff0fb

    4. when we have the access token, we can get access to the resource with that access token.
        Request example:
            Url: http://localhost:8080/api/getAllProducts (GET)
            Params:
                Authorization = Bearer 5c353a2c-4f17-4397-8613-881b9dcff0fb

