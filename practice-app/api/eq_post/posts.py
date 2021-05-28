from django import forms

class EquipmentPost(forms.Form):
    username = forms.CharField(label='Username', max_length=30)
    title = forms.CharField(label='Title', max_length=50)
    description = forms.CharField(label='Description', max_length=250)
    location = forms.CharField(label='Location', max_length=60)