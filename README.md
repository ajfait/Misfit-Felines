## Problem Statement
___

Misfit Felines is a foster-based cat rescue and does not have a physical location. As such, fosters are often communicating with the rescue via email, messenger, text, etc. There is currently no central repository for all of the fosters to communicate with the rescue or with each other. Many things need to be communicated such as contact information for the foster, photos and bios of the cats for Petfinder, and vaccination records.

Since the rescue has grown tremendously since it was founded, there is a need to centralize all of this information and make it easier for the rescue and its fosters / volunteers to access the most updated information regarding each foster and cat. This application will serve as a database of both fosters and cats. Rescue administrators will be able to view, edit, and remove both. Fosters will be able to edit their own contact information, add, edit, and remove their own cats, and view information on other fosters / volunteers and cats in the rescue.

## Technology Stack
___

1. InteliJ IDE
2. Maven build and dependency management
3. MySql / AWS RDS relational database
4. AWS Elastic Beanstalk cloud deployment and hosting
5. Log4j 2 logging
6. JUnit unit testing
7. Jakarta EE web application framework
8. AWS Cognito user authentication and authorization
9. Bootstrap 5 front end styling
10. Hibernate object relational mapper and validator
11. The Cat API
12. Gson JSON parsing
13. JSTL & EL JSP logic and templating
14. Nimbus JWT handling
15. Tomcat deployment
16. GitHub versioning
17. Postman API testing

### Testing Strategy

Full CRUD methods are tested on each entity during the build process.

### Deployment Process

After making code changes locally, Maven is used to build the application and JUnit runs the unit tests. The Tomcat server is used to verify the changes before deploying them to AWS Elastic Beanstalk. Code commits are then pushed to GitHub with meaningful commit messages.

### Code Review

First code review was with Matt Brophy. Second code review is scheduled with Porter Taylor. Ongoing code reviews are completed by Paula Waite, specifically with checkpoints 1, 2, and 3.

### Screenshots

TBD


