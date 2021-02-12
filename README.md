Compiled with Java 8.211 JDK Make sure this is set in the IDE

1. Download and install postgres 11
2. psql -U postgres
3. create database addb template 'template0' encoding 'utf8';
4. create user whoever with encrypted password 'whatever';
5. grant all privileges on database addb to whoever;
ALTER DATABASE name OWNER TO new_owner

mvn liquibase:status
mvn liquibase:update

Database is ready to go.


To install Postgres on Mac

```
brew tap petere/postgresql
brew search postgresql
brew install postgresql@11
```

If you need to have postgresql@11 first in your PATH run:
  echo 'export PATH="/usr/local/opt/postgresql@11/bin:$PATH"' >> /Users/croe10/.bash_profile

For compilers to find postgresql@11 you may need to set:
  export LDFLAGS="-L/usr/local/opt/postgresql@11/lib"
  export CPPFLAGS="-I/usr/local/opt/postgresql@11/include"

For pkg-config to find postgresql@11 you may need to set:
  export PKG_CONFIG_PATH="/usr/local/opt/postgresql@11/lib/pkgconfig"


To have launchd start postgresql@11 now and restart at login:
  brew services start postgresql@11
Or, if you don't want/need a background service you can just run:
  pg_ctl -D /usr/local/var/postgresql@11 start

To login for the first time (on a MAC) and set up accounts, db, etc,  I used this commands

```
sudo psql -U croe10 postgres
postgres=# create database addb;
CREATE DATABASE
postgres=# create user ad with encrypted password 'I82frogs';
CREATE ROLE
postgres=# grant all privileges on database addb to ad;
GRANT
postgres=#
```



