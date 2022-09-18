# Introduction

The project demonstrated a simple web service with Java original libraries and some extra third party libs.

APIs from the service now supporting basic USER OPERATION and MANAGEMENT. Implementations are described in the following section.

# Design and Implementation

## Discussion 

This demo does not really clarify number of users to serve and how much latency are acceptable. So some assumptions can be made roughly. 

1. Thousands of users are using concurrently and all data are managed carefully. Therefore all shared data should be controlled by lock or similar things. Even though Java has already provided sufficient way to avoid concurrent issue like ConcurrentHashMap, but for multiple operation, they still require extra locks.
2. Token generation should use user info, salt and other info like timestamp to calculate. 
3. To check expiration of token, it is not necessary to have extra thread to check on each second. Just setup lazy validation, an extra space to record expiration time should be enough. 
4. Since all data are computed in memory, password should be stored irreversible like they stored in disk.
5. Data procession should consider cascading staff.
## APIs

1. /create_user: create a new user.

curl --location --request POST '127.0.0.1:11688/create_role' \
--header 'Content-Type: application/json' \
--data-raw '{
"role":"test"

}'

2. /delete_user: Delete a user. Roles and Token should be removed accordingly.

curl --location --request POST '127.0.0.1:11688/delete_user' \
--header 'Content-Type: application/json' \
--data-raw '{

    "name":"jfal;jf"
}'

3. /create_role: create a new role.

curl --location --request POST '127.0.0.1:11688/create_user' \
--header 'Content-Type: application/json' \
--data-raw '{

    "name": "hehe",
    "password": "pwd"
}'

4. /delete_role: Delete a role. Remove role from the users who have assigned this role.

curl --location --request POST '127.0.0.1:11688/delete_role' \
--header 'Content-Type: application/json' \
--data-raw '{
"role":"test"
}'

5. /assign_role: Assign a role to a user.

curl --location --request POST '127.0.0.1:11688/assign_role' \
--header 'Content-Type: application/json' \
--data-raw '{
"name":"hehe",
"role":"test"
}'


6. /auth: Genrate a token for a user and use it later.

curl --location --request POST '127.0.0.1:11688/auth' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "hehe",
    "password": "pwd"
}'

7. /invalidate: Remove token from user, related funcs are unavailable until new token are signed.

curl --location --request POST '127.0.0.1:11688/invalidate' \
--header 'Content-Type: application/json' \
--data-raw '{

    "token":"fd9a331e94a13aa8c3165b4e0fb12d72"
}'

8. /check_role: Check user's role by using token and role. 

curl --location --request POST '127.0.0.1:11688/check_role' \
--header 'Content-Type: application/json' \
--data-raw '{
"token":"b61a3c77456882d7b546a6fad89302c5",
"role":""
}'

9. /all_roles: Return all roles that belongs to the specified users.

curl --location --request POST '127.0.0.1:11688/all_roles' \
--header 'Content-Type: application/json' \
--data-raw '{
"token":"96c52c834617bafe13ac1625811c88f"
}'

# Test

Test contains two parts: Unit tests to ensure all service functions are able to running correctly and integration tests to make sure all system are implemented as expected.

Address: http://localhost:11688

# Dependencies

Maven dependencies contains following:

1. jetty-server

Jetty is a Java web server and Java Servlet container. 
2. fastjson
Fastjson provides functions for object serialization and deserialization, much easier than java original method.

3. junit-jupiter-api

Junit is a popular testing libray for developer testing functions easily. In this project, all service functions are tested with JUNIT.



