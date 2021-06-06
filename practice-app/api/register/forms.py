from django import forms

class RegistrationForm(forms.Form):

    # Required attributes
    username = forms.CharField(label='Username', max_length=30)
    password = forms.CharField(label='Password', max_length=256) 
    email = forms.EmailField(label='E-mail', max_length=50)
    fullname = forms.CharField(label='Full Name', max_length=50)

    # Attributes that can be left blank
    description = forms.CharField(label='Description', max_length=250, required = False)
    age = forms.IntegerField(label='Age', required = False)
    location = forms.CharField(label='Location', max_length=60, required = False)
    profile_picture = forms.ImageField(label='Profile Picture', required = False)
    phone = forms.CharField(label='Phone', max_length=50, required = False)    
