# USER API SPEC

## Register user
Endpoint: POST /api/users/admin

Request Body:
```json
{
  "name": "String", 
  "email" : "String", 
  "password" : "String", 
  "role" : "integer", 
  "no_hp" : "String" ,
  "gender": "String" 
}

```

Response Body (Success):

```json
{
  "data": "OK"
}
```

Response Body (Failed):

```json
{
  "data": "Data Not Valid"
}
```

## Create data user Baru
Endpoint: POST /api/users

Request Body:

```json

{
  "name": "String",
  "email" : "String",
  "tempat_lahir": "String",
  "tanggal_lahir": "Date",
  "gender": "String",
  "kategori": "String",
  "instansi": "String",
  "anamnesa" : "String",
  "diagnosa" : "String",
  "terapi": "String",
  "jumlah_obat" : "number" //date rekam medis password
}

```

Response Body (Success) :

```json
{
  "data": {
    "name": "String",
    "email": "String",
    "no_rm": "String",
    "tanggal" : "String"
  }
}
```

Response Body : (Failed)
```json
{
  "data": "Data Not Valid"
}
```

## Login
Endpoint: POST /api/auth/login

Request Body:
```json
{
  "email" : "String",
  "password" : "String"
}
```

Response Body (success):

```json
{
  "data": {
    "nama": "String",
    "email": "email",
    "role": "number",
    "token": "TOKEN",
    "tokenExpiredAt": "date"
  }
  
}
```


## Get User

Endpoint: GET /api/user/current

Request Header:
- X-API-TOKEN: token

Response Body: (Success)

```json
{
  "data": {
    "name": "String",
    "email": "String",
    "role": "integer",
    "gender": "String",
    "instansi": "String",
    "tempat_lahir": "String",
    "tanggal_lahir": "Date",
    "no_rm": "String"
  }
}
```

## Get All User

Endpoint: GET /api/users

Request Header:
- X-API-TOKEN: token

Response Body: (Success)

```json
{
  "data": [
    {
        "name": "String",
        "email" : "String",
        "role" : "integer",
        "no_hp" : "String" ,
        "gender": "String",
        "instansi" : "String",
        "ttl" : "String",
        "no_rm" : "String"
      }
    ],
  "paging" : {
    "currentPage": 0,
    "totalPage": 10,
    "size": 10
  }
}
```

Response Body: (Failed, 401)
```json
{
  "errors": "Unauthorized"
}
```

## Logout User
Endpoint: DELETE /api/auth/logout

Request Header:
- X-API-TOKEN: token

Response Body: (Success)
```json
{
  "data": "OK"
}
```

## Searching by Name
Endpoint: GET /api/user

Query Param :
- name : String,user first name, using like query, optional
- no_rm : String, user no rekam medis, using like query, optional
- page : Int, start from zero
- size : Integer, default 10

Request Header:
- X-API-TOKEN: token


Response Body: (Success)

```json
{
  "data": [
    {
      "no_rm": "Random String",
      "name": "first name",
      "ttl": "last name",
      "instansi": "personal email"
    }
  ],
  "paging": {
    "currentPage": 0,
    "totalPage": 10,
    "size": 10
  }
}
```

Response Body : (Failed)
```json
{
  "data": "Unauthorized"
}
```


