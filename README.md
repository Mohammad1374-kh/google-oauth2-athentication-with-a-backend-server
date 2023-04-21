# google-oauth2-athentication-with-a-backend-server
Signup & Login with Google as Authentication server ; getting the user's data from Google
Data fetched from google in this project: The User's Email, picture of google account, Locale , Full name

This project is the implementation of Google Authentication with a backend server :
https://developers.google.com/identity/sign-in/web/backend-auth

Modules & Applied techs:

* (Google) Oauth2 client
* Spring Data Jpa
* Embedded H2 DB
* JpaSpecification
* Exception Handling By ControllerAdvice
* internationalization (Resource Bundle) to show the messages to the user based on their locale in google account

Notes:
* Postman used as the frontend
* To run the project, add application-dev.yml file and use your own googel Client-id and Client-secret and change the active profile to dev 



 توسعه دهندگان عزیز ایرانی : برای run کردن و اجرای کامل پروژه باید از آی پی خارجی استفاده کنید وگرنه در مسیر اجرا زمانی که از بک به سرور گوگل به آدرس https://www.googleapis.com/oauth2/v1/certs ریکوئست زده می شود ( از اونجایی که آی پی های ایران از طرف گوگل بلاک می شوند و از اوجایی که هر چی سنگه مال پای لنگه!) با ارور 403 مواجه می شید. برای رفع این مشکل می تونید از پراکسی و یا شکن (https://shecan.ir/tutorials/) استفاده کنید. فقط دقت کنید که قبل از clone کردن پروژه حتما شکن یا پراکسی را ست کنید تا از مشکلات عدیده بعدی جلوگیری بشه.   
