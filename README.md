# Restaurant Rest API
Voting system for restaurant with REST API using Hibernate/Spring/SpringMVC.
<br/><br/><br/>

## Task
The task is:

Build a voting system for deciding where to have lunch.

- 2 types of users: admin and regular users
- Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
- Menu changes each day (admins do the updates)
- Users can vote on which restaurant they want to have lunch at
- Only one vote counted per user
- If user votes again the same day:
- If it is before 11:00 we asume that he changed his mind.
- If it is after 11:00 then it is too late, vote can't be changed
- Each restaurant provides new menu each day.

<br/><br/><br/>

## 01. User flow 

<pre>NOTE 1. The app home directory is "voteapp".
SERVER_PATH for instance http://localhost:8080/voteapp</pre>

<pre>NOTE 2. Authentication. Every user must login to use app.
Use for instance: curl --user user1@gmail.com:password 
</pre>

<pre>NOTE 3. Price is in cents. I.e 1000 cents = $10.00 
</pre>

### 01.1. View lunch menu
- User can view lunch menu from all restaurants by
- For user dishes sorted by price. 
<br/>GET http://localhost:8080/voteapp/rest/

Request:
 <pre>curl -s --user user1@gmail.com:password "http://localhost:8080/voteapp/rest/"</pre>

Response example for 2 restaurants each have menu with 3 dishes:
 <pre>
[
{"id":100005,"name":"Cafe Pushkin","registered":"2019-08-16T13:00:00",
"dishes":[
{"id":100007,"name":"Summer squash soup with creamed feta biscuits","price":875,"date":"2019-08-16"},
{"id":100008,"name":"Girolle, lemon & parsley risotto","price":1500,"date":"2019-08-16"},
{"id":100009,"name":"Mediterranean salad with quinoa, beetroot, datterini & olives","price":2250,"date":"2019-08-16"}
]},
{"id":100006,"name":"White Rabbit","registered":"2019-08-16T13:00:00",
"dishes":[
{"id":100016,"name":"Squash tabbouleh ","price":1275,"date":"2019-08-16"},
{"id":100017,"name":"The Ivy vegetarian Shepherds Pie","price":1433,"date":"2019-08-16"},
{"id":100018,"name":"Linguine primavera ","price":1600,"date":"2019-08-16"}
]}
]
</pre>


### 01.2. Vote for restaurant 
- User can vote for restaurant by POST http://localhost:8080/voteapp/rest/{restaurant_id}/vote

Request:
 <pre>curl -X POST -s --user user3@gmail.com:password "http://localhost:8080/voteapp/rest/100005/vote"</pre>

Response example:
<pre>
if added - Http status - 200 OK
if for second vote time is after 11:00 - Http status 409 Conflict
</pre> 


### 01.3. View vote history 
- User can view his votes from all days by GET http://localhost:8080/voteapp/rest/votes

Request:
 <pre>curl -s --user user1@gmail.com:password "http://localhost:8080/voteapp/rest/votes"</pre>
 
Response example:
<pre>
[
{"id":100019,"user_id":100003,"restaurant_id":100005,"date":"2019-08-26"},
{"id":100020,"user_id":100003,"restaurant_id":100005,"date":"2019-08-27"}
]
</pre>

### 01.4. View restaurant menu by date 
- User can view his vote's menu for restaurant by date GET http://localhost:8080/voteapp/rest/{restaurant_id}/{date}
- It can be used with vote history to get menu for a specific day. 

Request:
 <pre>curl -s --user user1@gmail.com:password "http://localhost:8080/voteapp/rest/100005/2019-08-26"</pre>

Response example:
<pre>
[
{"id":100010,"name":"Summer squash soup with creamed feta biscuits","price":875,"date":"2019-08-26"},
{"id":100011,"name":"Girolle, lemon & parsley risotto","price":1500,"date":"2019-08-26"},
{"id":100012,"name":"Mediterranean salad with quinoa, beetroot, datterini & olives","price":2250,"date":"2019-08-26"}
]
</pre>
 
<br/><br/><br/>



## 02. Admin flow 

<pre>NOTE 4. Only admins can use administration section. 
Please authenticate admin with email admin@gmail and password admin
Use for instance: curl --user admin@gmail.com:admin 
</pre>

### 02.1. View all restaurants with today menu.
- Admin can view all today's menu, empty menu (what not included for regular user) or with dishes.
- Empty restaurant menu need in case to add new dish there using restaurant id. 
<br/>GET http://localhost:8080/voteapp/rest/restaurants/

Request:
 <pre>curl -s --user admin@gmail.com:admin "http://localhost:8080/voteapp/rest/restaurants/"</pre>
 
Response example for 3 restaurants:
 <pre> 
 {
 "Restaurant{id=100005, dateTime=2019-08-16T13:00, name='Cafe Pushkin'}":
 [
 {"id":100007,"name":"Summer squash soup with creamed feta biscuits","price":875},
 {"id":100008,"name":"Girolle, lemon & parsley risotto","price":1500},
 {"id":100009,"name":"Mediterranean salad with quinoa, beetroot, datterini & olives","price":2250}
 ],
 "Restaurant{id=100004, dateTime=2019-08-16T13:00, name='The Ivy'}":
 [],
 "Restaurant{id=100006, dateTime=2019-08-16T13:00, name='White Rabbit'}":
 [
 {"id":100016,"name":"Squash tabbouleh ","price":1275},
 {"id":100017,"name":"The Ivy vegetarian Shepherds Pie","price":1433},
 {"id":100018,"name":"Linguine primavera ","price":1600}
 ]
 }
</pre>


### 02.2. Add restaurant
- Admin can add new restaurant by 
<br/>POST http://localhost:8080/voteapp/rest/restaurants/

Request: 
<pre>
curl -s --user admin@gmail.com:admin -X POST -d '{"name":"New Restaurant"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voteapp/rest/restaurants/
</pre>

Response example:
<pre>
{"id":100028,"name":"New Restaurant","dateTime":"2019-09-11T20:24:09.2290605"}
</pre>


### 02.3. Edit restaurant
- Admin can update restaurant by by it's id
<br/>PUT http://localhost:8080/voteapp/rest/restaurants/{id}

Request: 
<pre>
curl -s --user admin@gmail.com:admin -X PUT -d '{"id":100006, "name":"Updated Restaurant"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voteapp/rest/restaurants/100006
</pre>

Response example:
<pre>
{"id":100006,"name":"Updated Restaurant","dateTime":"2019-08-16T13:00:00"}
</pre>


### 02.4. Remove restaurant
- Admin can remove restaurant by it's id 
<br/>DELETE http://localhost:8080/voteapp/rest/restaurants/{id}

Request: 
<pre>
curl -s --user admin@gmail.com:admin -X DELETE http://localhost:8080/voteapp/rest/restaurants/100006
</pre>


### 02.5. Add today menu dish for restaurant 
- Admin can add new dishes by restaurant_id 
<br/>POST http://localhost:8080/voteapp/rest/restaurants/{restaurant_id}/dishes

Request: 
<pre>
curl -s --user admin@gmail.com:admin -X POST -d '{"name":"New Dish", "price":4050}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voteapp/rest/restaurants/100006/dishes
</pre>

Response example:
<pre>
{
"id":100026,
"name":"New Dish",
"price":2050,
"restaurant":{"id":100006,"name":"White Rabbit","dateTime":"2019-08-16T13:00:00"},
"date":"2019-09-12"
}
</pre>

### 02.6. Edit today menu dish for restaurant 
- Admin can edit dish by dish_id
- Only today dish can be edited 

<br/>PUT http://localhost:8080/voteapp/rest/restaurants/dishes/{dish_id}

Request: 
<pre>
curl -s --user admin@gmail.com:admin -X PUT -d '{"id":100022, "name":"Updated Dish", "price":1010}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voteapp/rest/restaurants/dishes/100022
</pre>

<pre>
{
"id":100022,
"name":"Updated Dish",
"price":1010,
"restaurant":{"id":100006,"name":"White Rabbit","dateTime":"2019-08-16T13:00:00"}
}
</pre>

### 02.7. Remove today dish 
- Admin can remove today dishes by its id
- Only today  dish can be removed.

<br/>DELETE http://localhost:8080/voteapp/rest/restaurants/dishes/{dish_id}

Request: 
<pre>
curl -s --user admin@gmail.com:admin -X DELETE http://localhost:8080/voteapp/rest/restaurants/dishes/100022
</pre>


### 02.8. View today votes for all restaurants
- Admin can view today votes for all restaurant by 
<br/>GET http://localhost:8080/voteapp/rest/restaurants/votes/

Request:
<pre>curl -s --user admin@gmail.com:admin http://localhost:8080/voteapp/rest/restaurants/votes/
</pre>
 
Response example:
 <pre> 
{
"Restaurant{id=100005, dateTime=2019-08-16T13:00, name='Cafe Pushkin'}":2,
"Restaurant{id=100006, dateTime=2019-08-16T13:00, name='White Rabbit'}":1
} 
 </pre>


### 02.9. View votes by date 
- Admin can view votes history for restaurants by date 
<br/>GET http://localhost:8080/voteapp/rest/restaurants/votes/{date}/

Request:
<pre>curl -s --user admin@gmail.com:admin http://localhost:8080/voteapp/rest/restaurants/votes/2019-08-27
</pre>

Response example:
 <pre> 
[
{"id":100024,"restaurant":{"id":100005,"name":"Cafe Pushkin","dateTime":"2019-08-16T13:00:00"},
"date":"2019-08-27",
"votes":2},
{"id":100025,"restaurant":{"id":100006,"name":"White Rabbit","dateTime":"2019-08-16T13:00:00"},
"date":"2019-08-27",
"votes":1}]
]
 </pre>

### 02.10. Vote History for restaurant
- Admin can view vote history for restaurant by 
<br/>GET http://localhost:8080/voteapp/rest/restaurants/{id}/votes/

Request:
<pre>curl -s --user admin@gmail.com:admin http://localhost:8080/voteapp/rest/restaurants/100005/votes
</pre>

Response example:
- in response there are no restaurant information, because all data for same restaurant. 
- restaurant_id can be removed, but left for checking. 
 <pre> 
[
{"restaurant_id":100005,"date":"2019-08-26","votes":1},
{"restaurant_id":100005,"date":"2019-08-27","votes":2}
]
</pre>

<br/><br/><br/>

<br/><br/><br/>

## 03. Registration

User can register, view and update his profile.

Assume that admins for restaurant updates will be added by 
Super Admin directly into database.    

### 03.1. Regular user can register.
- User can register
<br/>POST http://localhost:8080/voteapp/rest/profile/register

Request: 
<pre>
curl -s -X POST -d '{"name":"UserName","email":"useremail@gmail","password":"newPassword"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voteapp/rest/profile/register
</pre>

Response example:
 <pre> 
 {
 "id":100026,
 "name":"UserName",
 "email":"useremail@gmail",
 "password":"newPassword",
 "registered":"2019-09-12T15:57:28.5393328",
 "roles":["ROLE_USER"]
 }
 </pre>


### 03.2. User can view his profile 
- User has access only to his profile

<br/>GET http://localhost:8080/voteapp/rest/profile

Request:
<pre>curl -s --user user1@gmail.com:password http://localhost:8080/voteapp/rest/profile
</pre>
Response example:
 <pre> 
{
"id":100001,
"name":"User1",
"email":"user1@gmail.com",
"password":"{noop}password",
"registered":"2019-09-12T16:00:09.402",
"roles":["ROLE_USER"]
}
</pre>

### 03.3. User can update his profile 
- User has access to update only to his profile

<br/>PUT http://localhost:8080/voteapp/rest/profile

Request: 
<pre>
curl -s --user user1@gmail.com:password -X PUT -d '{"name":"UserName", "password":"newPassword"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voteapp/rest/profile
</pre>

<br/><br/><br/>

<br/><br/><br/>

## 04. Project Notes

### 04.1. Transfer Object

##### UserToIn
- There is UserToIn for input user object for update profile.
- It's assumed that user can't change his email and role and have
ability to change name and password only.  

##### DishTo
- DishTo using for transfer data to remote interfaces i.e. for client, 
with idea to provide restaurant info once and all today related dished 
without unnecessary information of restaurant and date added.

##### HistoryTo
- This DTO for returning history with restaurant info.

##### VotesStatistics 
- This is for grouping in Data-JPA by restaurant if using only Vote objects (slow).
- It can be removed if all history statistics will be based on History and Vote objects (fast).

### 04.2. UserService
- User service is needed for authentication.





  




