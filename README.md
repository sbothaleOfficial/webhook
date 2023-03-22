Add a user:

`curl --request POST \
--url http://localhost:8000/post \
--header 'Content-Type: application/json' \
--data '{
"id": 1,
"name": "Suraj",
"location": "Wani"
}'`


Add a user with missing fields (should return a 400 Bad Request):

`curl --request POST \
--url http://localhost:8000/post \
--header 'Content-Type: application/json' \
--data '{
"id": 2,
"name": "",
"location": ""
}'`

Retrieve all users:

`curl --request GET \
--url http://localhost:8000/get`

Retrieve a user by ID:

`curl --request GET \
--url http://localhost:8000/get?id=1`

Retrieve users by name:

`curl --request GET \
--url 'http://localhost:8000/get?name=Suraj'`

Retrieve users by location:

`curl --request GET \
--url 'http://localhost:8000/get?location=Wani'`

Retrieve users by name and location:

`curl --request GET \
--url 'http://localhost:8000/get?name=Suraj&location=Wani'
`
