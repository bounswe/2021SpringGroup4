from django import forms

class RegistrationForm(forms.Form):
    username = forms.CharField(label='Username', max_length=30)
    password = forms.CharField(label='Password', max_length=256) 
    email = forms.EmailField(label='E-mail', max_length=50)
    # to do: integrate badges
    # to do: integrate events
    # to do: token authentication
    description = forms.CharField(label='Description', max_length=250)
    age = forms.IntegerField(label='Age')
    location = forms.CharField(label='Location', max_length=60)