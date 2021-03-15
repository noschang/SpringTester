FROM quay.io/maksymbilenko/oracle-xe-11g

COPY build-files/sql-scripts/create-users.sql /docker-entrypoint-initdb.d/

# Copy a hacked version of the original file
COPY build-files/entrypoint.sh /