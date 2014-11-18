#!/bin/bash

function moan(){
  echo -e "$1" 1>&2
  exit 1
}

cd "$( dirname "$0" )"

sbt -Dversion=${BUILD_NUMBER} clean update package

cd target

function prepare_for_publish() {
    mkdir s3
    cp scala-2.11/*jar s3
}

if [[ "${TRAVIS_BRANCH}" == 'master' && "${TRAVIS_PULL_REQUEST}" == 'false' ]]; then
    prepare_for_publish || moan 'Failed to prepare_for_publish'
fi