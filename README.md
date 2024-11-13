## Getting Started

Make sure Docker is installed on your local machine.

Run `docker compose up` to start application in docker.

Open [http://localhost:8080](http://localhost:8080) with your browser to see the result.

## Build logic

`gradlew build` command triggers next.js `npm run build`
and places it's static files under `src/main/java/resources/static` folder

<b>There is no need to run separately front end and back end. Both of them will run on same port</b>

## Dependencies

This project is using:

* Docker
* Spring boot
* Next.js
* H2 database


## Nice to have

Currently there is no authorization validation logic (no JWT, etc..)