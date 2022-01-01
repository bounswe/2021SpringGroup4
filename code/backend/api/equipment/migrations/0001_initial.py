# Generated by Django 3.2.8 on 2022-01-01 08:48

from django.conf import settings
from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
    ]

    operations = [
        migrations.CreateModel(
            name='EquipmentPost',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('title', models.CharField(max_length=100)),
                ('description', models.TextField(blank=True, max_length=500, null=True)),
                ('location', models.CharField(blank=True, max_length=500)),
                ('contact', models.CharField(blank=True, max_length=500)),
                ('image_url', models.CharField(blank=True, max_length=500, null=True)),
                ('sportType', models.CharField(blank=True, max_length=100, null=True)),
                ('owner', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='equipments', to=settings.AUTH_USER_MODEL)),
            ],
        ),
    ]
