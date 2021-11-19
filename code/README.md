## **Quick and Dirty API Documentation**

Deployed application lives at http://3.67.188.187:8000/

### **Register** http://3.67.188.187:8000/api/auth/register/ [POST]

**Example Request:** 

```json
{
    "username": "tolga",
    "email":"tolga23@gmail.com",
    "password": "123456"
}
```

**Return Success: [HTTP_201_CREATED]**

```json

{
    "email": "tolga23@gmail.com",
    "username": "tolga"
}
```

**Return Failure: [HTTP_400_BAD_REQUEST]**
```json
{
    "email": [
        "user with this email already exists."
    ],
    "username": [
        "user with this username already exists."
    ]
}
```
---
### **Login** http://3.67.188.187:8000/api/auth/login/ [POST]

**Example Request:** 

```json
{
    "username": "tolga",
    "email":"tolga23@gmail.com",
    "password": "123456"
}
```

**Return Success: [HTTP_200_OK]**

```json
{
    "refresh": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoicmVmcmVzaCIsImV4cCI6MTYzNzM5ODIyOCwiaWF0IjoxNjM3MzExODI4LCJqdGkiOiJkOGE1ODA4OTI4NDE0MjUwODcyOGVhM2EzYzBlMDYzYyIsInVzZXJfaWQiOjF9.ec5ZTWDNup9Hu_eOk0o3UptNKnezolpi7vQaiAn5Dh8",
    "access": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjM3MzE1NDI4LCJpYXQiOjE2MzczMTE4MjgsImp0aSI6IjI3MWU3YzE3ODRjZTRhNDBiMTYzODNhZGM5YmI5Nzc3IiwidXNlcl9pZCI6MX0.l_fMHvT-8rDsScN6E7fYXEgG-F2QNHntt5zp7EqlaTs"
}
```

**Return Failure: [HTTP_401_UNAUTHORIZED]**
```json
{
    "detail": "No active account found with the given credentials"
}
```
---
### **List All Events** http://3.67.188.187:8000/api/events [GET] 

**Return: [HTTP_200_OK]**

```json
[
    {
        "@context": "https://www.w3.org/ns/activitystreams",
        "id": 1,
        "creator": "tolga",
        "created": "2021-11-19T11:57:09.158546+03:00",
        "type": "Event",
        "body": {
            "title": "BestEvent",
            "description": "",
            "date": "2021-10-31",
            "time": "15:00:00",
            "duration": "02:00:00",
            "location": "Istanbul",
            "sportType": "",
            "maxPlayers": 10,
            "applicants": [],
            "participants": [],
            "followers": [],
            "comments": []
        }
    },
    {
        "@context": "https://www.w3.org/ns/activitystreams",
        "id": 2,
        "creator": "tolga",
        "created": "2021-11-19T11:57:16.493761+03:00",
        "type": "Event",
        "body": {
            "title": "BestEvent2",
            "description": "",
            "date": "2021-10-31",
            "time": "15:00:00",
            "duration": "02:00:00",
            "location": "Istanbul",
            "sportType": "",
            "maxPlayers": 10,
            "applicants": [],
            "participants": [],
            "followers": [],
            "comments": []
        }
    }
]
```
---
### **Create Event** http://3.67.188.187:8000/api/events/ [POST] **authorization required**

**Example Request: **

```json
{
    "title": "BestEvent3",
    "location": "Istanbul",
    "maxPlayers": "10",
    "date": "2021-10-31",
    "time": "15:00",
    "duration": "02:00"
}
```

**Return Success: [HTTP_200_OK]**
```json
{
    "@context": "https://www.w3.org/ns/activitystreams",
    "id": 3,
    "creator": "tolga",
    "created": "2021-11-19T12:02:25.474757+03:00",
    "type": "Event",
    "body": {
        "title": "BestEvent3",
        "description": "",
        "date": "2021-10-31",
        "time": "15:00",
        "duration": "02:00",
        "location": "Istanbul",
        "sportType": "",
        "maxPlayers": 10,
        "applicants": [],
        "participants": [],
        "followers": [],
        "comments": []
    }
}
```

**Return Failure: [HTTP_400_BAD_REQUEST]**
```json
{
    "title": [
        "This field is required."
    ],
    "location": [
        "This field is required."
    ],
    "maxPlayers": [
        "This field is required."
    ],
    "date": [
        "This field is required."
    ],
    "time": [
        "This field is required."
    ],
    "duration": [
        "This field is required."
    ]
}
```
### **Add/Remove Applicant/Participant/Follower to Event** http://3.67.188.187:8000/api/events/<event_id>/ [POST] **authorization required -> must be event owner**

**Example Request: **

```json
{
    "participants":
    {
        "add": ["tolga", "irfan"],
        "remove": []
    },
    "applicants":
    {
        "add": [],
        "remove": ["tolga"]
    },
    "followers":
    {
        "add": [],
        "remove": []
    }          
}
```
