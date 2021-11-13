FROM python:3.8-slim-buster
ENV PYTHONUNBUFFERED=1
WORKDIR /app
COPY requirements.txt ./backend/requirements.txt
RUN pip install -r backend/requirements.txt

COPY . ./backend/




RUN echo pwd

CMD python ./backend/manage.py runserver 0.0.0.0:8000


