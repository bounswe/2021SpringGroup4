from django.db import models
from django.utils import timezone 
from django.contrib.auth.models import AbstractBaseUser, PermissionsMixin, BaseUserManager

class CustomUserManager(BaseUserManager):
    def create_user(self, email, username, first_name, password, **other_fields):

        if not email:
            raise ValueError('You must provide an email address in order to register.')
        
        if not username:
            raise ValueError('You must provide a username in order to register.')

        email = self.normalize_email(email) 
        user = self.model(email=email, username=username, 
                        first_name=first_name, **other_fields)
        user.set_password(password)
        user.save()
        return user
    
    def create_superuser(self, email, username, password, first_name="", **other_fields):
        
        if first_name == "":
            first_name = "admin_" + str(User.objects.filter(is_staff=True).count() + 1)

        other_fields.setdefault('is_staff', True)
        other_fields.setdefault('is_superuser', True)
        other_fields.setdefault('is_active', True)

        return self.create_user(email, username, first_name, password, **other_fields)


class User(AbstractBaseUser, PermissionsMixin):

    username = models.CharField(max_length=50, unique=True)
    created = models.DateTimeField(auto_now_add=True)
    email = models.EmailField(unique=True)
    age = models.IntegerField(blank=True, null=True) 
    about = models.TextField(max_length=500, blank=True, null=True)
    location = models.CharField(max_length=100, blank=True, null=True)
    is_staff = models.BooleanField(default=False)
    is_active = models.BooleanField(default=True)
    first_name = models.CharField(max_length=50)
    last_name = models.CharField(max_length=50, blank=True, null=True)
    objects = CustomUserManager()

    USERNAME_FIELD = 'username'
    REQUIRED_FIELDS = ['email']

    def __str__(self):
        return self.username

    class Meta:
        ordering = ['id']

