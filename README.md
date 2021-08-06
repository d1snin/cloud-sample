[![License](https://img.shields.io/badge/License-BSD%203--Clause-blue.svg)](https://opensource.org/licenses/BSD-3-Clause)
![Lines of code](https://img.shields.io/tokei/lines/github/d1snin-dev/cloud-sample)

# *cloud-sample*

*An instance of a cloud storage system written in Java using Netty framework. Uses the TCP protocol to transfer data
between the server and the client.*

# *Project Description*

*This project is my course work on the fact of Java development, it uses the Netty Framework to work with the network
and the TCP protocol, implies working with files and storing them in the cloud (on a remote server), this is far from
how Google Cloud or Dropbox works, but why not.*

# *Contributing*

*I accept Pull Requests with your changes, it would be cool. Use `./gradlew shadowJar` to build the fat jar (for all
modules)
and `./gradlew test` to run tests.*

# ***TODO:***

- [ ] Make cloud management more elastic.
- [ ] Some parts and methods are not in the places where they really should be.
- [ ] Better error tolerance. There will be errors if, for example, something does not correspond to the client's logic.
- [ ] Using Kotlin in a project.