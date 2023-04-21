# google-oauth2-athentication-with-a-backend-server
Signup & Login with Google as Authentication server ; giving the user data from Google
Data fetched from google : The User's Email, picture of google account, Locale , Full name

This project is the implementation of Google Authentication with a backend server :
https://developers.google.com/identity/sign-in/web/backend-auth

Modules & Applied techs:
*(Google) Oauth2 client
*Spring Data Jpa
*Embedded H2 DB
*JpaSpecification
*Exception Handling By ControllerAdvice
* internationalization (Resource Bundle) to show the messages to the user based on their locale in google account

Notes:
*Postman used as the frontend
* To run the project, add application-dev.yml file and use your own googel Client-id and Client-secret and change the acctive profile to dev
