#!/bin/bash
kill $(ps aux | grep 'tictactoe-0.0.1-SNAPSHOT.jar' | grep -v grep | awk '{print $2}')
