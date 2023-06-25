# spring-boot-blogging-app
A mini blogging app built using Java 11 and Spring Boot 2.7.

Following key features are implemented in the application
1. Users can use their Google accounts to sign-in into the app. On user login basic user information like email and userName will be 
   persisted into a db.
2. User information need not be passed in api calls since the basic identifier will be fetched from spring security context
3. Users can create/get/edit/delete posts, add or delete comments and mark post as favorite or un-favorite
4. User can also follow or unfollow other users

**Database Diagram**
<img width="3126" alt="image" src="https://github.com/Afshan-Khan-49/spring-boot-blogginf-app/blob/670adce1a408a79b0b6e0da01b6835db8fe91efb/src/main/resources/images/ER_Diagram.png">