FROM mysql:8.0
ENV MYSQL_USER docker
ENV MYSQL_PASSWORD docker
ENV MYSQL_DATABASE sakila
ENV MYSQL_ROOT_PASSWORD docker
COPY sakila-schema.sql /docker-entrypoint-initdb.d/10-init.sql
COPY sakila-data.sql /docker-entrypoint-initdb.d/12-init.sql
