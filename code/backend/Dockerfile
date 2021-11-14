FROM python:3.8-slim-buster

ENV PYTHONUNBUFFERED=1

WORKDIR /app

COPY requirements.txt ./backend/requirements.txt

RUN apt update
RUN yes | apt install libpq-dev
RUN yes | apt install gcc

RUN pip install -r backend/requirements.txt

COPY . ./backend/

CMD python ./backend/manage.py runserver 0.0.0.0:8000
