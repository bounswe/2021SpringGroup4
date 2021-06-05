from django import forms
class DateInput(forms.DateInput):
    input_type='date'
class TimeInput(forms.TimeInput):
    input_type='time'

class EventPost(forms.Form):
    eventName = forms.CharField(label='EventName', max_length=30)
    sportType = forms.CharField(label='SportType', max_length=50)
    numOfPlayers = forms.IntegerField(label='NumOfPlayers')
    description = forms.CharField(label='Description', max_length=250)
    date= forms.DateField(widget=DateInput)
    time= forms.DateField(widget=TimeInput)
   