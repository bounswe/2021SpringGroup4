# Generated by Django 3.2.8 on 2021-12-07 13:06

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('authentication', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='user',
            name='profile_picture',
            field=models.URLField(blank=True, max_length=300, null=True),
        ),
    ]