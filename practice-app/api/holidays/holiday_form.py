from django import forms

country_names=(
    ("Turkey", "Turkey"),
    ("United States", "United states"),
    ("Germany", "Germany"),
    ("Azerbaijan", "Azerbaijan"),
    ("France", "France"),
    ("Spain", "Spain"),
    ("Italy", "Italy"),
    ("Russia", "Russia"),
    
)


class HolidayForm(forms.Form):
    day = forms.IntegerField(label='day',max_value=31, min_value=1, required=False)
    month = forms.IntegerField(label='month',max_value=12,min_value=1)
    year = forms.IntegerField(label='year',max_value=2022, min_value=2021)
    country_code = forms.ChoiceField(label='Country Name', choices=country_names) 


