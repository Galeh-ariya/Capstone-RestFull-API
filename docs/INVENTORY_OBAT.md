# API SPEC INVENTORY OBAT

## CREATE DATA OBAT
Endpoint: POST /api/obat

Request Body:

```json
{
  "name_obat": "String",
  "expired_at": "Date",
  "min_stock": "Integer",
  "stock": "Integer"
}
```

Response Body (Success):

```json
{
  "data": {
    "name_obat": "String",
    "expired_at": "Date",
    "min_stock": "Integer",
    "stock": "Integer",
    "used_total" : "Integer"
  }
}
```

Response Body (Failed):

```json
{
  "data": "Your data is Broken"
}
```


# List Obat
Endpoint: GET /api/obat

Response Body
```json
{
  "data": [
    {
      "name_obat": "String",
      "expired_at": "Date",
      "min_stock": "Integer",
      "stock": "Integer",
      "used_total" : "Integer"
    },
    {
      "name_obat": "String",
      "expired_at": "Date",
      "min_stock": "Integer",
      "stock": "Integer",
      "used_total" : "Integer"
    }
  ]
}
```

## Update Obat
Endpoint: PUT "/api/obat/{id_obat}"

Request Body:
```json
{
  "name_obat": "String",
  "expired_at": "Date",
  "min_stock": "Integer",
  "stock": "Integer",
  "used_total" : "Integer"
}
```

Response Body (Success):

```json
{
  "data": "Updated successfully"
}
```

## Search Obat By Name
Endpoint: GET "/api/obat/"

Response Body:

```json
{
  "data": [
  {
    "id_obat": "Integer",
    "name_obat": "String",
    "expired_at": "Date",
    "min_stock": "Integer",
    "stock": "Integer",
    "used_total" : "Integer"
  },
  {
    "name_obat": "String",
    "expired_at": "Date",
    "min_stock": "Integer",
    "stock": "Integer",
    "used_total" : "Integer"
  }
]
}
```

Response Body (Not Found):

```json
{
  "data": [
  {
    "name_obat": "String",
    "expired_at": "Date",
    "min_stock": "Integer",
    "stock": "Integer",
    "used_total" : "Integer"
  },
  {
    "name_obat": "String",
    "expired_at": "Date",
    "min_stock": "Integer",
    "stock": "Integer",
    "used_total" : "Integer"
  }
]
}
```