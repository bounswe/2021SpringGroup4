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
