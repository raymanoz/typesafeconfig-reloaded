#!/bin/bash

function moan(){
  echo -e "$1" 1>&2
  exit 1
}

cd "$( dirname "$0" )"

./jcompilo.sh

repo=https://api.bintray.com/content/raymanoz/repo
version_url=${repo}/com/unsprung/careless/careless/${BUILD_NUMBER}
artifact="reloaded-"${BUILD_NUMBER}

cd target

function prepare_for_publish() {
    mkdir s3
    cp scala-2.11/*jar s3
}

if [[ "${TRAVIS_BRANCH}" == 'master' && "${TRAVIS_PULL_REQUEST}" == 'false' ]]; then
    prepare_for_publish || moan 'Failed to prepare_for_publish'
fi