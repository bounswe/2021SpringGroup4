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

## NBA Teams

This endpoint is used for checking information for selected NBA team. Returns a selection page for a GET request. For a POST request, when a user selects a team and submit, 
it takes information using NBA-api and shows them at a new page. Also, POST request can be used by adding the abbreavion of the desired team to the url at selection page (/team/cle) to take information without selecting a team and submitting at the page.

'team.html' where data is taken and used;
'list_team.html' where data is processed and shown to the user.

URL: *to be added*

	'GET':
		Returns the html page with a choice field showing all available teams and a submit button to select a team.
	'POST':
		It connects to NBA-api and takes the data about desired NBA team. Then, this data is displayed at new page.
		
	JSON Format : { 'team_code': "", string, identifies the desired team for searching }
	
	RESPONSE STATUS CODES
        GET:
            HTTP_200_OK : Successfully returns the team selection webpage.
        POST: 
            HTTP_200_OK : Successfully gets a response after selecting a team.

**@author:** Berkay Gümüş


**@author:** Yağmur Selek
## Create Event Post 

This endpoint is for creating an event post. It returns the Event Post Page for a GET request. For a POST request,
it validates the information entered as 'event name' should be different than the events that currently residing in our SQLite database, 'date' and 'time' fields should be selected as future, than saves the event to the database and returns an HttpResponse containing
the message says 'Event creation is successfull', if the request was valid. If any errors occur, returns a response default response page describing the error.


    'GET':
        Returns the  event creation page.
    'POST':
        Validates the necessary informations are filled and check for validity of that informations to record the event to our database if it is valid
        JSON Format : { 'eventName': "",                 string, the eventname entered by the event creator, also a primary key for db table
                        'sportType': "",                 string, the sporttype entered by the user
                        'numOfPlayers ':"",              int, the number of players wanted for that event given by the event creator
                        'description':"",                string, a description about the event created by event creator, this can be NULL
                        'date':"",                       date(year:int, month:int, day: int): , a valid date that can be selected 
                        'time':"",                       time (hour: int, min: int), a valid time that can be selected 
    RESPONSE STATUS CODES
      GET:
            HTTP_200_OK : Successfully returns the event post page.
      POST: 
            HTTP_202_CREATED : Successfully added the event post desired to the database.
            HTTP_400_BAD_REQUEST : Serializer ERROR
            HTTP_101_BAD_REQUEST : SportType field is not provided 
            HTTP_100_BAD_REQUEST : An eventName must be provided.
            HTTP_102_BAD_REQUEST : You can not enter a past date or time
           
           


URL: http://group4-practiceapp.eba-hs5hejqp.us-west-2.elasticbeanstalk.com/event_post/
**@author:** Yağmur Selek

## Current Weather Condition of Istanbul

This project return the Current Weather Condition of Istanbul. This API get Temparature, Weather condution for example suny,rainy,clear sky ... and small image about weather condution from cities name.
URL: *to be added*

    'GET' or 'POST':
        Returns the Current Weather condition of Istanbul.

     RESPONSE STATUS CODES
        GET:
	    HTTP_400_BAD_REQUEST : It Can't return correctly

            HTTP_200_OK : Successfully returns the Current Weather Condition of Istanbul.

**@author:** İHSAN MERT ATALAY
       URL: http://group4-practiceapp.eba-hs5hejqp.us-west-2.elasticbeanstalk.com/hava/
       
       
## Formula 1

This endpoint is used for checking the current Formula 1 driver standings and searching driver information by driver name. Returns the standings page and a search option for a GET request to endpoint /formula1/. Returns a HTML page that gives a link for the standings page for a GET request to endpoint /formula1/driver_info/. For a POST request to endpoint /formula1/driver_info/, it gets the data from Ergast F1 API and filters the information of the driver whose name/surname or name and surname is given, case-insensitively. Then, this information is passed to the 'driver_information.html' where the data is displayed which are searched driver's name, permanent number, nationality, date of birth and a Wikipedia link for more information.

URL: http://group4-practiceapp.eba-hs5hejqp.us-west-2.elasticbeanstalk.com/formula1/

    'GET' - endpoint http://localhost:8000/formula1/:
        Returns the html for the current formula 1 driver standings and 
        a search option of information about a driver from the upper table.
    
    'GET' - endpoint http://localhost:8000/formula1/driver_info/:
        Returns the html of the results page without a data, 
        so shows a message to user and a link for the standings page.
    
    'POST' - endpoint http://localhost:8000/formula1/driver_info/:
        Returns the html for the information about the driver that is provided by the user. 
        
        JSON Format : { 'driver_name': "",      string, identifies the name of the driver}

**@author:** Ece Dilara Aslan

