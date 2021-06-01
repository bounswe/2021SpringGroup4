from django import forms

class SearchForm(forms.Form):

    country = forms.CharField(label='Country', max_length=30)
