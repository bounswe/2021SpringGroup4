from django import forms

class SearchPlacesForm(forms.Form):
    location = forms.CharField(label='Location(Neighborhood, City)', max_length=60)
    keyword = forms.CharField(label='Keyword', max_length=256) 
