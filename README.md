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
