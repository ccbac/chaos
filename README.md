[![CircleCI](https://circleci.com/gh/ccbac/chaos.svg?style=shield)](https://circleci.com/gh/ccbac/chaos)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/0546163dd1374024b9d70a31a6cfa499)](https://www.codacy.com/app/CCBAC/chaos?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=ccbac/chaos&amp;utm_campaign=Badge_Grade)

# Chaos

DSLink to provide values for testing and demonstration.

## Development

### Running

```sh
./gradlew run -Dexec.args="-b http://localhost:8080/conn"
```

## Building and running distributions

1. Run `./gradlew build distZip`
2. Navigate into `build/distributions`
3. Extract the distribution tarball/zip
4. Navigate into the extracted distribution
5. Run `./bin/chaos -b http://localhost:8080/conn`

Note: `http://localhost:8080` is the url to the DSA broker that needs to have been installed prior.

## Running tests

To run all the tests in `src/test`, simply run
```sh
./gradlew test
```
