# Customizable toy store
So you want to open an online toy store to sell your sweet customizable car toys? 
Well you need a REST API to place those orders right? Have a few weird constraints?  No problem!

# Requirements
* Endpoint to order customized toy kits
* Parts for each kit need to be provisioned and restocked as needed based on orders
* Customers demand a high level of exclusivity and thus don't want to see the same kit within 100 miles!
* Kit and part pricing need to be configurable through a secure endpoint accessible only by our 'experience liaisons
* Non default parts have an associated surcharge

# Endpoints
This project provides a solution for this via the following endpoints. 
Spring boot will start a server running on port 8080.

## Sessions
This is a prototype, so no user management is included. However, base resources are provisioned and loaded fresh each run time.
This includes session tokens. To access any of these endpoints you'll need to include one of these two session tokens in the request header as `x-session-token`.

### Normal guy
```json
{
  "token": "dc8a1fe1640d8a4f8847cb7c01765c2076019dee",
  "permissions": ["read", "buy"]
}
```

### Experience Liaison
```json
{
  "token": "bc8a1fe1640d8a4f8847cb7c01765c2076019dee",
  "permissions": ["read", "buy", "write"]
}
```

## Customers
You'll need some valid customers to place those orders! What better place to post customer information than a github readme?
Use either of these customer ids in the create order request!

```json
{
  "id": 1,
  "fullName": "Russ"
}
```

```json
{
  "id": 2,
  "fullName": "Marsha"
}
```

## Create order
* /orders
* POST
* Headers
    - x-session-token
    - application/json
    
### Request
```json
{
	"customerId": 1,
	"shippingAddress": "145 fake street rd Alexandria 20120",
	"kits": [{
			"kitTypeId": 1,
			"name": "my customized toy!",
			"powerSourcePartId": 4,
			"bodyPartId": 9
		},
		{
			"kitTypeId": 2,
			"name": "super custom toy 2",
			"enginePartId": 7,
			"powerSourcePartId": 3
		}
	]
}
```

### Response
* 201 CREATED

```json
{
    "id": 1
}
```

## Get order
* /orders/{id}
* POST
* Headers
    - x-session-token
    - application/json
    
### Response
* 200

```json
{
    "id": 1,
    "customerId": 1,
    "totalPrice": 2495,
    "shippingAddress": "145 fake street rd Alexandria 20120"
}
```

## Get all part type categories
* /part-type-category
* GET
* Headers
    - x-session-token
    - application/json
    
### Response
* 200
```json
{
    "partTypeCategories": [
        {
            "id": 5,
            "category": "body"
        },
        {
            "id": 2,
            "category": "color"
        },
        {
            "id": 4,
            "category": "engine"
        },
        {
            "id": 3,
            "category": "finish"
        },
        {
            "id": 1,
            "category": "power source"
        },
        {
            "id": 6,
            "category": "wheels"
        }
    ]
}
```

## Get all part types
* /part-types
* GET
* Headers
    * x-session-token
    * application/json
    
### Response
* 200
```json
{
    "partTypes": [
        {
            "id": 11,
            "name": "18 inch wheels",
            "surcharge": 90,
            "categoryId": 6
        },
        {
            "id": 12,
            "name": "21 inch wheels",
            "surcharge": 120,
            "categoryId": 6
        },
        {
            "id": 2,
            "name": "blue",
            "surcharge": 10,
            "categoryId": 2
        },
        {
            "id": 6,
            "name": "gloss",
            "surcharge": 25,
            "categoryId": 3
        },
        {
            "id": 10,
            "name": "jacked up",
            "surcharge": 70,
            "categoryId": 5
        },
        {
            "id": 9,
            "name": "low profile",
            "surcharge": 87,
            "categoryId": 5
        },
        {
            "id": 5,
            "name": "matte",
            "surcharge": 10,
            "categoryId": 3
        },
        {
            "id": 4,
            "name": "nuclear",
            "surcharge": 150,
            "categoryId": 1
        },
        {
            "id": 1,
            "name": "red",
            "surcharge": 10,
            "categoryId": 2
        },
        {
            "id": 3,
            "name": "self satisfaction",
            "surcharge": 8,
            "categoryId": 1
        },
        {
            "id": 8,
            "name": "v12",
            "surcharge": 175,
            "categoryId": 4
        },
        {
            "id": 7,
            "name": "v8",
            "surcharge": 117,
            "categoryId": 4
        }
    ]
}
```

## Get all kit types
* /kit-types
* GET
* Headers
    * x-session-token
    * application/json

### Response
* 200
```json
{
    "kitTypes": [
        {
            "id": 1,
            "name": "stock car",
            "basePrice": 2145,
            "defaultBodyPartId": 9,
            "defaultEnginePartId": 8,
            "defaultWheelsPartId": 11,
            "defaultPowerSourcePartId": 4,
            "defaultColorPartId": 2,
            "defaultFinishPartId": 5
        },
        {
            "id": 2,
            "name": "truck",
            "basePrice": 350,
            "defaultBodyPartId": 10,
            "defaultEnginePartId": 7,
            "defaultWheelsPartId": 12,
            "defaultPowerSourcePartId": 3,
            "defaultColorPartId": 1,
            "defaultFinishPartId": 6
        }
    ]
}
```

## Patch kit type base price
* /kit-types/{id}
* PATCH
* Headers
    * x-session-token (Requires the experience liaison session token)
    * application/json
    
### Request
```json
{"basePrice":2145.00}
```
#### May throw
* 403 bad request
* 404 not found

### Response
* 204 No Content

## Patch part type surcharge
* /part-types/{id}
* PATCH
* Headers
    * x-session-token (Requires the experience liaison session token)
    * application/json
    
### Request
```json
{"surcharge":2345.00}
```
#### May throw
* 403 bad request
* 404 not found

### Response
* 204 No Content

# Note
Resources created or altered during the runtime will revert to their provisioned state on a restart.
The changes are not persisted on shutdown, instead everything is provisioned from json resources and loaded into a mock memory store for demo purposes.

# Logger is chatty
Watch console output for insight into the order lifecycle and provisioning.

# Data model
Here is an approximation of the data model used to accomplish this.
 
![alt text](http://i.imgur.com/bHZB9w3.jpg "Data model")


 
