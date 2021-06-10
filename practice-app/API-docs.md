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

[URL](http://group4-practiceapp.eba-hs5hejqp.us-west-2.elasticbeanstalk.com/eq_post/)

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

[URL](http://group4-practiceapp.eba-hs5hejqp.us-west-2.elasticbeanstalk.com/random_article/)

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

       
## Search User

This endpoint is the user search interface to the system, running on the default Django SQLite backend. Returns an html page with a search bar for a GET request. For a POST request, it checks if input is valid and looks for the user in the database (username and input must exactly be the same). Returns an error response if user not found.
Returns a profile page of the user containing user information. Blank sections say “Not provided”.
Likewise, if the user hasn’t provided a profile picture yet, which they cannot for now, makes use of an external API named UI Avatars to retrieve a rounded image with the user’s full name initials in it.

URL: http://group4-practiceapp.eba-hs5hejqp.us-west-2.elasticbeanstalk.com/search_user/

    'GET':
        Returns an html page including a search bar to search a user by their 
        username.
 
        An example GET request from terminal:
        
            curl http://127.0.0.1:8000/api/search_user/
 
    'POST':
        Checks if such a user exists. 
        Returns a user profile page if so, and an error response if not.
 
        Use the following JSON format to issue POST requests to this endpoint
        JSON Format : { 'input': ""  string, the username you want to search }
 
        An example POST request from terminal:
            
            curl -X POST 
                 -H "Content-type:application/json" 
                 --data "{\"input\":\"<your search here>\"}" 
                 http://127.0.0.1:8000/api/search_user/

**@author:** Irfan Bozkurt



## Holidays

This endpoint is for checking holidays for selected country. Gets the date and country information from user by GET request and it firstly tests the validity of this date. Then,  Gets the holidays for the input country by google calender url as JSON format. Determines whether this date is a holiday and if it is, returns the holiday information and the next date which is not holiday by POST request. Also, if there is an wrong input, Determines source of the error and returns the proper error message by POST request. Finally,The returned data is displayed in html file.

URL: http://group4-practiceapp.eba-hs5hejqp.us-west-2.elasticbeanstalk.com/holidays/

    'GET':
        Returns an html page including a holidays form (day,month,year,country) to get input by user.

 
    'POST':
        By spesific country name which is provided by the user,  determines the google calender url and gets holidays  
        for this country as json format. By date which is also provided by the user, checks whether this date is in the
        holidays list. For holiday dates, Calculates the next not holiday date and displays the proper information. 
        For the wrong inputs, it handles this cases and displays error massage.
        
      
       JSON Format : { 'input_error': "",                 boolean, identifies whether there is an error
                       'legal_date': "",                  boolean, identifies whether the input date is legal
                       'requested_date': "",              string, input date
                       'is_holiday': "",                  boolean, identifies whether the input date is a holiday
                       'holiday_name': "",                string, holiday name for the input date, if it is not '-'
                       'next_day': "",                    string, next not holiday date, if it is not '-'
                       'error_message': "",               string, type of error, if it is not '-'
                        }
     RESPONSE STATUS CODES
        GET:
            HTTP_200_OK : Successfully gets the input of the date and the country.

        POST: 
            HTTP_200_OK : Successfully proceeded the getting holidays information and input format is legal.
            HTTP_400_BAD_REQUEST : There is a problem in the input such as illegal date, not country in the list, or empty input 
**@author:** Mehmet Hilmi Dündar


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
