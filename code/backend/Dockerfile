FROM python:3.9
ENV PYTHONUNBUFFERED=1
WORKDIR /app
COPY requirements.txt /app/requirements.txt

RUN pip install -r requirements.txt

COPY . /app/backend


CMD python backend/manage.py runserver 0.0.0.0:8000

# Install GDAL dependencies
RUN apt-get update
RUN apt-get install -y libgdal-dev g++ --no-install-recommends && \
    apt-get clean -y

# Update C env vars so compiler can find gdal
ENV CPLUS_INCLUDE_PATH=/usr/include/gdal
ENV C_INCLUDE_PATH=/usr/include/gdal
