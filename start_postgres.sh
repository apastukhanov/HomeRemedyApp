#!/bin/bash

docker build -t postgres_home_remedy_app .

docker run -d \
  --name home_remedy_app \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=qwerty \
  -e POSTGRES_DB=homeremedydb \
  -v postgres-data:/var/lib/postgresql/data \
  -p 5432:5432 \
  postgres:14