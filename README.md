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

### + 01.1. View lunch menu
- User can view lunch menu from all restaurants by 
<br/>GET SERVER_PATH/rest/menu/

Request:
 <pre>curl "SERVER_PATH/rest/menu/"</pre>

Response example for 2 restaurants each have menu with 3 dishes:
 <pre>
 {
 "Restaurant{id=100005, dateTime=2019-08-16T13:00, name='Cafe Pushkin'}":
 [
 {"id":100007,"name":"Summer squash soup with creamed feta biscuits","price":875},
 {"id":100008,"name":"Girolle, lemon & parsley risotto","price":1500},
 {"id":100009,"name":"Mediterranean salad with quinoa, beetroot, datterini & olives","price":2250}
 ],
 "Restaurant{id=100006, dateTime=2019-08-16T13:00, name='White Rabbit'}":
 [
 {"id":100016,"name":"Squash tabbouleh ","price":1275},
 {"id":100017,"name":"The Ivy vegetarian Shepherds Pie","price":1433},
 {"id":100018,"name":"Linguine primavera ","price":1600}
 ]
 }
</pre>


### + 01.2. Vote for restaurant 
- User can vote for restourant by POST SERVER_PATH/rest/menu/vote/{restaurant_id} 

Request:
 <pre>curl "SERVER_PATH/rest/menu/vote/34"</pre>

Response example:

<pre>
true - vote has been added
false - vote did't added, out of time or error
</pre> 

### + 01.3. View vote history 
- User can view his votes from all days by GET SERVER_PATH/rest/menu/vote/history/ 

Request:
 <pre>curl "SERVER_PATH/rest/menu/vote/history"</pre>
 
Response example:
<pre>
[
{"id":100019,"user_id":100003,"restaurant_id":100005,"date":"2019-08-26"},
{"id":100020,"user_id":100003,"restaurant_id":100005,"date":"2019-08-27"}
]
</pre>

### + 01.4. View restaurant menu by date 
- User can view his vote's menu for restaurant by date GET SERVER_PATH/rest/menu/restaurant/{restaurant_id}/{date} 

Request:
 <pre>curl "SERVER_PATH/rest/menu/restaurant/restaurants/100005/2019-08-26"</pre>

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

### + 02.1. View all restourants with today menu.
- Admin can view all today's menu, empty (what not included for regular user) or with dishes.
- Empty restaurant menu need in case to add new dish there using restaurant id. 
<br/>GET SERVER_PATH/rest/restaurants/

Request:
 <pre>curl "SERVER_PATH/rest/restaurants/"</pre>
 
Response example for 2 restaurants each have menu with 3 dishes:
 <pre> 
 {
 "Restaurant{id=100005, dateTime=2019-08-16T13:00, name='Cafe Pushkin'}":
 [
 {"id":100007,"name":"Summer squash soup with creamed feta biscuits","price":875},
 {"id":100008,"name":"Girolle, lemon & parsley risotto","price":1500},
 {"id":100009,"name":"Mediterranean salad with quinoa, beetroot, datterini & olives","price":2250}
 ],
 "Restaurant{id=100006, dateTime=2019-08-16T13:00, name='White Rabbit'}":
 [
 {"id":100016,"name":"Squash tabbouleh ","price":1275},
 {"id":100017,"name":"The Ivy vegetarian Shepherds Pie","price":1433},
 {"id":100018,"name":"Linguine primavera ","price":1600}
 ]
 }
</pre>


### + 02.2. Add restaurant
- Admin can add new restaurant by 
<br/>POST SERVER_PATH/rest/restaurants/

Request: 
<pre>
curl -s -X POST -d '{"name":"The Ivy"}' 
-H 'Content-Type:application/json;charset=UTF-8' 
SERVER_PATH/rest/restaurants/`
</pre>

Response example:
 <pre>
{"id":100028,"name":"The Ivy","dateTime":"2019-09-11T20:24:09.2290605"}
</pre>pre>


### + 02.3. Edit restaurant
- Admin can update restaurant by by it's id
<br/>PUT SERVER_PATH/rest/restaurants/{id}

### + 02.4. Remove restaurant
- Admin can remove restaurant by it's id 
<br/>DELETE SERVER_PATH/rest/restaurants/{id}

### + 02.5. Add today menu dish for restaurant 
- Admin can add new dishes by restaurant_id 
<br/>POST SERVER_PATH/rest/restaurants/{restaurant_id}/dishes

### + 02.6. Edit today menu dish for restaurant 
- Admin can edit dishe by dish_id 
<br/>POST SERVER_PATH/rest/restaurants/dishes/{dish_id}

### + 02.7. Remove today dish 
- Admin can remove today dishes by its id
- Only today  dish can be removed.
<br/>DELETE SERVER_PATH/rest/restaurants/dishes/{dish_id}

### + 02.8. View today votes for all restaurants
- Admin can view today votes for all restaurant by 
<br/>GET SERVER_PATH/rest/restaurants/votes/
- View today votes for all restaurants.

### + 02.9. View votes by date 
- Admin can view votes history for restaurants by date 
<br/>GET SERVER_PATH/rest/restaurants/votes/{date}/
- View votes for particular day for all restaurants.

### + 02.10. Vote History for restaurant
- Admin can view vote history for restaurant by 
<br/>GET SERVER_PATH/rest/restaurants/{id}/votes/
- View votes for one restaurant by each day.

### 02.11. Publish menu for restaurant, or last time for edit.  (Optional)
- Set publish time for restaurantss menu. All menu will be visible at 8:00 o'clock.
- Admin can publish menu for user by restaurant
- User start see this menu.
- No more edits will allowed because users start vote.
- publish all unpublished.
<br/>POST SERVER_PATH/rest/restaurants/publish

<br/><br/><br/>

<br/><br/><br/>

## 03. Registration

User can register, view and update his profile.

Assume that admins for restaurant updates will be added by 
Super Admin directly into database.    

### + 03.1. Regular user can register.
- User can register
<br/>POST SERVER_PATH/rest/profile/register

### + 03.2. User can view his profile 
- User has access only to his profile
<br/>GET SERVER_PATH/rest/profile

### + 03.3. User can update his profile 
- User has access to update only to his profile
<br/>PUT SERVER_PATH/rest/profile




