# Book tracker Rest API


### Requirements
The application requires mysql database working on a standard port to run. Either install a server on your machine or use the attached `docker-compose.yml` to setup a containerized version.

### Running the application
Use `bootRun` task to start the application. By default the application will be available at port `8888`


### Defaults
By default the application accepts books with ISBN-10 and ISBN-13 format.

### Testing
Use `test` task to run unit and integration tests.
See `api-tests.json` file for Postman collection containing basic API examples.
