#!/bin/bash
FILE=/var/www/tiger-web-app/tictactoe-0.0.1-SNAPSHOT.jar
if test -f "$FILE"; then
    rm /var/www/tiger-web-app/tictactoe-0.0.1-SNAPSHOT.jar
fi