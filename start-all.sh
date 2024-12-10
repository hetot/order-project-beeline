#!/usr/bin/bash

set -euxo pipefail

docker compose down

./gradlew clean
./gradlew :order-acceptor:build
./gradlew :order-processor:build

docker images -q order-project-processor | grep -q . && docker rmi order-project-processor
docker images -q order-project-acceptor | grep -q . && docker rmi order-project-acceptor
docker volume prune

docker compose up -d