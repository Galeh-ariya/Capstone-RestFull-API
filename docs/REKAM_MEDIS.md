# REKAM MEDIS API SPEC

## Create data rekam medis
Endpoint: POST /api/rm/{no_rm}

Request Header:
 - X-API-TOKEN : token

Request Body:

```json
{
  "anamnesa": "String",
  "diagnosa": "String",
  "terapi": "String",
  "jumlah_obat" : "number"
}
```

Response Body (Success):

```json
{
  "data": {
    "no_rm": "String",
    "anamnesa": "String",
    "diagnosa": "String",
    "terapi": "String",
    "jumlah_obat" : "number"
  }
}
```
Response Body (Failed):
```json
{
  "data": "Data Not Valid"
  
}
```

## Get Rekam Medis
Endpoint: GET /api/rm/{no_rm)

Request Header:
- X-API-TOKEN : token

Response Body (Success):

```json
{
  "data" : {
    "tanggal": "date",
    "anamnesa": "String",
    "diagnosa": "String",
    "terapi": "String",
    "jumlah_obat" : "number"
  }
}
```

Response Body (Failed):
```json
{
  "data": "Not Authorized"
  
}
```



