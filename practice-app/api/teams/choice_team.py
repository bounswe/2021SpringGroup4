from django import forms

class ChoiceTeam(forms.Form):
    team_field = forms.ChoiceField(label='Team', choices=[])

    def __init__(self, teams=None, *args, **kwargs):
        super(ChoiceTeam, self).__init__(*args, **kwargs)
        if teams:
            self.fields['team_field'].choices = [
                (str(v['abbreviation']), str(v['full_name']))
                for k, v in enumerate(teams)
            ]