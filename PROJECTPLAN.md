# Project Plan

### Week 2
- [X] Create project repository on GitHub
- [X] Create project structure in IntelliJ and push
- [X] Add link to list of indie projects in student repo
- [X] Complete Problem Statement
- [X] Weekly journal entry / time log

### Week 3
- [X] Research possible Web Services/APIs to use
- [X] List technologies, versions and how they will be used
- [X] Write project plan
- [X] Document user stories and select MVP stories
- [X] Confirm MVP stories meet Ent Java indie project objectives
- [X] Design screens - make sure all MVP user stories are covered
- [X] Triple-check for Checkpoint 1
- [X] Weekly journal entry / time log

### Week 4 - Class topic is Hibernate
- [X] First cut at database design
- [X] Create the config files for the DB connection info (dev and test)
- [X] Create the dev version of the database
- [X] Create a test version of the database for unit testing
- [X] Create unit tests
- [X] Create Entities
  - [X] Cat
  - [X] Person
  - [X] Event
  - [X] Medical
- [X] Create DAOs
  - [X] CatDao
  - [X] PersonDao
  - [X] EventDao
  - [X] MedicalDao
  - [X] GenericDao
- [X] Create JSPs
  - [X] index.jsp
  - [X] head.jsp
  - [X] header.jsp
  - [X] footer.jsp
  - [X] success.jsp
  - [X] error.jsp
  - [X] unauthorized.jsp
  - [X] logout.jsp
  - [X] view-cats.jsp
  - [X] view-fosters.jsp
  - [x] add-cat.jsp
  - [X] add-event.jsp
  - [X] add-medical.jsp
  - [X] add-person.jsp
  - [X] edit-person.jsp
  - [X] view-cats.jsp
  - [X] view-events.jsp
  - [X] view-medical.jsp
  - [X] view-persons.jsp
- [X] Create Controllers
  - [X] AddCat
  - [X] AddEvent
  - [X] AddMedical
  - [X] AddPerson
  - [X] EditCat
  - [X] EditEvent
  - [X] EditMedical
  - [X] EditPerson
  - [X] DeleteCat
  - [X] DeleteEvent
  - [X] DeleteMedical
  - [X] DeletePerson
  - [X] ViewCat
  - [X] ViewEvent
  - [X] ViewMedical
  - [X] ViewPerson
- [X] Weekly journal entry / time log

### Week 7
#### Checkpoint 2 is Due: Database designed and created, at least one DAO with full CRUD (create, read, update, delete) implemented with Hibernate, DAO is fully unit tested, Log4J is implemented (no System.out.printlns)
- [X] Double-check all checkpoint 2 items (above) are complete and visible in GitHub
- [X] Set up authentication
- [X] Create project DB on AWS
- [X] Update project config files for AWS as needed
- [X] Deploy project to AWS
- [X] Add deployed link to indie project list in student repo
- [X] Weekly journal entry / time log

### Week 9 (Start of the team project)
#### Checkpoint 3 is Due: Deployed to AWS, at least one JSP that displays data from the database is implemented, authentication implemented, add AWS deployed app link to indie project list in student repo.
- [X] Double-check all checkpoint 3 items (above) are complete and visible in github
- [X] Create SSL certificates
- [X] User authentication and authorization
  - [X] Create AWS Cognito user pool
  - [x] Create Auth controller
  - [X] Create Login controller
  - [X] Create auth package and include Cognito classes
- [X] Incorporate API
- [X] Add search functionality using JavaScript
- [X] Validate forms using Hibernate
- [X] Configure nav menu based on user type and current page
- [X] Weekly journal entry / time log

### Week 14
- [X] Functional testing
  - [X] Admin User
    - [X] Can edit profile
    - [X] Can add / edit / delete cat
    - [X] Can add / edit / delete person
    - [X] Can add / edit / delete event
    - [X] Can add / edit / delete medical
    - [X] Can view and search cats
    - [X] Can view and search people
    - [X] Can view and search events
    - [X] Can view and search medical
    - [X] Can reset password
  - [X] Non-Admin User
    - [X] Can edit profile
    - [X] Can add / edit / delete cat
    - [X] Can add / edit / delete medical
    - [X] Can view and search cats
    - [X] Can view and search people
    - [X] Can view and search events
    - [X] Can view and search medical
    - [X] Can reset password
  - [X] Cognito users are created when a new person is added
  - [X] Cognito users are removed when a person is deleted
  - [X] Duplicate email addresses are not allowed
  - [X] When person is deleted, the personId for their cats is set to null
  - [X] When cat is deleted, the catId for their medical is set to null (duplicate cat names?)

### Week 15
- [X] Implement feedback from week 14 review
- [ ] Final presentation
- [ ] Create video, add video link to README.md
- [X] Finalize all documentation
- [X] Code quality check
- [ ] Weekly journal entry / time log

### Week 16
- [ ] Final touches before code complete
- [ ] Weekly journal entry / time log