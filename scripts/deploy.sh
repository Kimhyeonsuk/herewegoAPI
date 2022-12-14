#!/usr/bin/env bash

REPOSITORY=/home/herewego

cd $REPOSITORY

JAR_NAME=$(ls $REPOSITORY/ | grep '.jar' | tail -n 1)
echo ">jarname $JAR_NAME "
JAR_PATH=$REPOSITORY/$JAR_NAME
echo ">jarpath $JAR_NAME "
CURRENT_PID=$(pgrep -f *.jar)

if [ -z $CURRENT_PID ]
then
  echo "> Nothing to end."
else
  echo "> kill -9 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> $JAR_PATH deploy"
nohup java -jar $JAR_PATH --spring.profiles.active=dev > /dev/null 2> /dev/null < /dev/null &