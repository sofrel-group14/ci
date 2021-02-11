# Continuous Integration Server

A continuous integration server written in Java. The server is implemented as a REST API with a frontend which can be found 
[here](http://axelelmarsson.se). The REST API was implemented using Spring Boot and we made the builds persistant using MongoDB. The MongoDB is hosted on MongoDB Atlas. There currently exist 3 enpoints:

1. /logs - which contains all the builds that have been pushed to CI Server
2. /log/{id} - Which Contains the details of a specific build.
3. /build - which is used by the webhook to build and test a specific branch

<!-- Add more info here later when we know if we use for example Spring Boot, MongoDB, etc. -->

# What it is
A continuous integration server that supports:
* compiling the code
* running tests
* notifying users of results

## How to build

**TODO:** Add info on how to build.

```
git clone git@github.com:sofrel-group14/ci.git

#create a application.properties file in src/main/resources with appropriate properties for your mongoDB database

mvn spring-boot:run

```

## Tools used

1. Uses Java (JDK 15) with Maven as build system and JUnit for testing.
2. Uses Spring Boot as a way to serve the builds/logs.
3. Uses MongoDB for persistance storage of the builds/logs.
4. Uses MongoDB Atlas as a hosting platform for the MongoDB database.


<!-- Add more info here later when we know if we use for example Spring Boot, MongoDB, etc. -->

### Necessary downloads

1. [Download JDK 15](https://www.oracle.com/se/java/technologies/javase-downloads.html)
2. Possibly [MongoDB](https://www.mongodb.com/try/download/community) if you prefer running it locally



# How to contribute

## Code

Code should follow the [Google Java Styleguide](https://google.github.io/styleguide/javaguide.html).

**Each feature has to be associated with at least one test**. See the [Testing](#testing) section for details. This is one of the mandatory grading criterias (P4).

Each public class and method has to be [documented](https://www.monperrus.net/martin/how-to-write-good-API-documentation) using Javadoc. This is one of the mandatory grading criterias (P5).

## Issues

Issues should be categorized using the existing labels (bug, documentation, enhancement, etc.).

~~They should also immediately be linked to the DECIDE project board (specifically the backlog column, i.e. the one chosen by default), so that they are visible there as well.~~  
**NOTE:** Since we didn't really use the DECIDE-board last assignment, a few of us discussed that maybe it just adds extra unnecessary overhead. Though, if some of you want to use it we can of course do so, in that case we will remove the strikethrough. When we all agree, we will either remove this part altogether or remove the strikethrough.

**IMPORTANT**: Assign yourself to an issue before you start working on it. This is to avoid having two or more people accidentally doing "double work". Make sure to coordinate with the assignee of an issue before starting to work on it yourself.

## Commits

Commit messages should always be written in an editor and not directly on the command line. This is to avoid the pitfall of short and non-descriptive commit messages.

```
git commit -m "Short message"       # bad
git commit                          # good
```

Note that you can [change](https://git-scm.com/book/en/v2/Customizing-Git-Git-Configuration#_core_editor) which editor git will open, in case you don't like the default one. To switch to *nano*, for example, use: `git config --global core.editor nano`.

That said, if a short message suffices to explain the commit, that is perfectly fine.

Commit messages should be linked to corresponding issues where applicable (this is done by referencing the issue number as `#<issue number>`). Note that commits should almost always be related to some issue (see grading criteria P8). If there's no related issue, one should probably be created before committing.

Commit messages should be written in *active present tense* ("fix" instead of "fixed", "add" instead of "added", "make" instead of "made", etc.), and should follow a prefix-convention, with the following prefixes:
* feat - for new features/functionality
* fix - for bug fixes (**NOTE** that all commits with bug fixes should contain a corresponding test that would have caught that bug, see grading criteria P4)
* doc - for documentation (this includes code comments)
* refactor - for refactoring
* format - for changing formatting such as newlines, indentation, capitalization, etc.
* test - for editing or adding new tests (to already existing features, see paragraph below)
* misc - for anything not covered by prefixes above

**Note** that each commit that adds new functionality or fixes a bug must also contain corresponding tests for that functionality or that bug (in the same commit).

**Examples:**

```
(feat) Implement LIC 7 along with corresponding unit tests

Fixes: #12
```

```
(test) Add additional unit test for LIC 7

This covers a previously untested edge case where X < 0. Related to #12.
```

## Branches

Branches should be linked to issues through their names, as:  
`issue-<issue number>-short-description-of-branch`

**Example:** `issue-12-lic-7` if issue 12 is related to implementing LIC 7.

(This naming convention adheres to [widely used community conventions](https://github.com/agis/git-style-guide#branches).)

## Pull requests

* Make sure to mention the issue that a pull request targets. This will close the issue automatically, when the branch is merged ([source](https://docs.github.com/en/github/managing-your-work-on-github/linking-a-pull-request-to-an-issue#linking-a-pull-request-to-an-issue-using-a-keyword)).
* At least two people have to code review before merging.
* When you write something to someone and want to make sure they see it, be sure to @mention their name so they get a notification (this is to avoid missing replies in threads).

Example:
```
This PR implements LIC 0.

Fixes: #6
```

# Testing

* All tests **must** be accompanied by a description in natural language.
* Functionality and corresponding tests should be added in the same commit.
* Bug fixes and corresponding test(s) (that would have caught the bug) should be added in the same commit.
* Take extra care to consider edge cases when constructing tests.
* Prefer several smaller unit tests over few but large tests.

# Statement of contribution
Taqui and Yannis created the base for the REST API including Spring Boot and MongoDB integration. Taqui and Telo started with the test for the DB and the CI flow. Andreas worked on the main CI features which is compiling the code and running tests. Axel worked on the frontend and a way to notify the users of the results. 

## What we're proud of
We are really proud of our frontend since it quite good looking and gives a clear way to check builds. Also, we estimate that >90% of our branch names, commit messages etc. are consistent with the conventions in the README.

