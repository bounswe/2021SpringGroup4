# <p align="center"> API Documentation </p>
## Register

This endpoint is the registration interface to the system, running on the default Django SQLite backend. Returns the registration page for a GET request. For a POST request,
it validates the information, encrypts the valuable fields such as password and saves the user to the database and returns an HttpResponse containing
the meta-information of the newly registered user. If any errors occur, returns a response describing what went wrong. 

URL: *to be added*

    'GET':
        Returns the registration page.
        
    'POST':
        Validates the information and registers the user into the database if valid, returns a descriptive error if not
        Use the following JSON format to issue POST requests to this endpoint
        JSON Format : { 'username': "",                 string, the username selected by the user
                        'password': "",                 string, the password selected by the user
                        'email':"",                     string, the email address the user would like to register with
                        'description':"",               string, a description about the user, can be NULL
                        'age':"",                       int, age of the user, can be NULL
                        'location':"",                  string, location provided by the user, can be NULL
                       }
                       
     RESPONSE STATUS CODES
        GET:
            HTTP_200_OK : Successfully returns the registration webpage.
        POST: 
            HTTP_201_CREATED : Successfully created the user in the database.
            HTTP_400_BAD_REQUEST : Something was wrong with the provided information and no user was created. This could be due to the username already existing in the database, 
            empty fields etc. 

**@author:** Tolga Kerimoğlu

## Find Nearby Places

This endpoint is used for searching places nearby a given location related to the given keyword. Returns the search page for a GET request. For a POST request,
it first connects to the Google's Geocode API to transform this location in text format into coordinates. Afterwards, using these coordinates 
and the keywords provided, retrieves nearby information of nearby locations and passes it to the Django template 
'search_places.html' where the data is processed and displayed to the user with address and ratings information displayed.

URL: *to be added.*

    'GET':
        Returns the html for the search form.
    'POST':
        Using the location information provided by the user, first connects to the Google's Geocode API
        to transform this location in text format into coordinates. Afterwards, using these coordinates 
        and the keywords provided, retrieves nearby information of nearby locations and passes it to the 
        Django template 'search_places.html' where the data is processed and displayed to the user. 

        JSON Format : { 'location': "",                 string, identifies the location
                        'keyword': "",                 string, contains relevant keywords about what type of place to search for
                        }
     RESPONSE STATUS CODES
        GET:
            HTTP_200_OK : Successfully conducted the search and returned matching places.
        POST: .
            HTTP_400_BAD_REQUEST : Something was wrong with the provided information. Most likely an empty search.
**@author:** Tolga Kerimoğlu
