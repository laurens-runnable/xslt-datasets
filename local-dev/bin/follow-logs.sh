#!/usr/bin/env bash

cd "${BASH_SOURCE%/*}/.." || exit

files="-f docker-compose.yml"
if [ $1 ]
then
    files="${files} -f $1.yml"
fi

docker-compose $files logs -f
