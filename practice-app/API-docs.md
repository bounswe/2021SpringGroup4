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

**@author:** Tolga Kerimoğlu


## Covid19 Case Reports

This endpoint is used for searching covid19 case reports all over the world. Returns a total confirmed case,deaths and recoveries of the world , rankings of confirmed cases using connection to the CovidPy API , also displays a search form for a GET request. For a POST request, it connects to the Covid API and it retrieves specific country data. Using the countrycode from the form, it retrieves confirmed,death and recovery data of the specific country and passes it to the Django template 'covid_country_report.html' where data is displayed to the user.

'covid_reports.html' where data is taken, used, processed and shown to the user
'covid_country_report.html' wher data processed and shown to the user


    'GET':
        Returns the html page for the case reports (confirmed, death, recovered) all over the world, 
        Returns the html page for top 20 rankings according to confirmed cases or deaths
        Displays a search form 
    'POST':
        Using the country code information , provided by the user, it connects to the CovidPy API,
        to take the country data. Retrieves the data and passes it to the Django template "covid_country_report.html"
        where the data displayed to the user.
        JSON Format : { 'countrycode': "", string, identifies the country code user search for it }

**@author:** Yiğit Sarıoğlu


## Equipment Post

This endpoint is the equipment posting interface on the system. It returns the equipment posting page for a GET request. For a POST request, it checks if the required fields are filled. Then, it adds the equipment post to the database.

URL: *to be added*

    'GET':
        Returns the equipment posting page.

    'POST':
        Checks the fields and adds the equipment post in the database, returns an error if not
        Use the following JSON format to issue POST requests to this endpoint
        JSON Format : { 'username': "",                 string, the username selected by the user
                        'title': "",                    string, the title selected by the user
                        'description':"",               string, a description about the user, can be NULL
                        'location':"",                  string, location provided by the user, can be NULL
                       }
                       
     RESPONSE STATUS CODES
        GET:
            HTTP_200_OK : Successfully returns the equipment post page.
        POST: 
            HTTP_201_CREATED : Successfully added the post in the database.
            HTTP_400_BAD_REQUEST : Something was wrong with the provided information and no post was added.

**@author:** Salih Furkan Akkurt

## Random Article

This endpoint is for getting an article off Wikipedia on a sports arena. It only has a GET request. It uses a predefined list of arenas and shows a random article.

URL: *to be added*

    'GET':
        Returns the random article on the page.

     RESPONSE STATUS CODES
        GET:
            HTTP_200_OK : Successfully returns the article.
            HTTP_400_BAD_REQUEST : The returned article was empty somehow.
       
**@author:** Salih Furkan Akkurt
