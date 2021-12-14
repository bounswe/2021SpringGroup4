# Generated by Django 3.2.8 on 2021-12-11 19:27

from django.conf import settings
from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
        ('events', '0005_auto_20211210_1814'),
    ]

    operations = [
        migrations.CreateModel(
            name='Badge',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('type', models.CharField(choices=[('1', 'Skilled'), ('2', 'Friendly'), ('3', 'Master'), ('4', 'Novice'), ('5', 'Top Organizer'), ('6', 'Sore Loser'), ('7', 'Crybaby'), ('8', 'Gentleman')], max_length=20)),
                ('event', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='badges', to='events.event')),
                ('giver', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='badges_granted', to=settings.AUTH_USER_MODEL)),
                ('owner', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='badges', to=settings.AUTH_USER_MODEL)),
            ],
        ),
    ]