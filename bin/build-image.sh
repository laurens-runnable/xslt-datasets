#!/usr/bin/env bash

cd "${BASH_SOURCE%/*}/.." || exit

./mvnw clean package && \
  docker build . -t runnable/dataset-visualizer
