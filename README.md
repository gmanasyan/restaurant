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
<br/>GET SERVER_PATH/rest/restaurants/

Request:
 <pre>curl "SERVER_PATH/rest/restaurants/"</pre>

Response example for 2 restaurants each have menu with 3 dishes:
 <pre> {
 "Restaurant{id=100006, dateTime=2019-08-16T13:00, name='White Rabbit'}":
 [
 {"id":100013,"name":"Squash tabbouleh ","price":1275},
 {"id":100014,"name":"The Ivy vegetarian Shepherds Pie","price":1433},
 {"id":100015,"name":"Linguine primavera ","price":1600}
 ],
 "Restaurant{id=100005, dateTime=2019-08-16T13:00, name='Cafe Pushkin'}":
 [
 {"id":100007,"name":"Summer squash soup with creamed feta biscuits","price":875},
 {"id":100008,"name":"Girolle, lemon & parsley risotto","price":1500},
 {"id":100009,"name":"Mediterranean salad with quinoa, beetroot, datterini & olives","price":2250}
 ]
 }
   </pre>


### + 01.2. Vote for restaurant 
- User can vote for restourant by POST SERVER_PATH/rest/restaurants/vote/{restaurant_id} 

Request:
 <pre>curl "SERVER_PATH/rest/restaurants/vote/34"</pre>

Response example:

true - vote has been added
false - vote did't added, out of time or error

### 01.3. View vote history (Optional) 
- User can view the his votes GET SERVER_PATH/rest/history/ 

Request:
 <pre>curl "SERVER_PATH/rest/history"</pre>

### 01.4. User can register (Optional) 
- User can register 

 
 
<br/><br/><br/>

## 02. Admin flow 

### + 02.1. Add restaurant
- Admin can add new restaurant by 
<br/>POST SERVER_PATH/rest/restaurants/

Request: 
<pre>
curl -s -X POST -d '{"name":"New Restaurant"}' 
-H 'Content-Type:application/json;charset=UTF-8' 
SERVER_PATH/rest/restaurants/`
</pre>

### + 02.2. Add today menu dish for restaurant 
- Admin can add new dishes by restaurant_id 
<br/>POST SERVER_PATH/rest/restaurants/{restaurant_id}

### 02.3. Remove today dish 
- Admin can remove today dishes by its id
- Only today  dish can be removed.
<br/>DELETE SERVER_PATH/rest/dish/{dish_id}

### 02.4. View all restourants with today menu.
- Admin can view all today's menu, empty or with dishes
<br/>GET SERVER_PATH/rest/restaurants/

### 02.5. Remove restaurant
- Admin can remove restaurant by 
<br/>DELETE SERVER_PATH/rest/restaurants/{id}

### 02.6. Vote History for restaurant (Optional)
- Admin can view vote history for restaurant by 
<br/>GET SERVER_PATH/rest/restaurants/history/{id}/
- View menu for each day and votes for this day.

### 02.7. Publish menu for restaurant (Optional) 
- Admin can publish menu for user by restaurant
- User start see this menu.
- No more edits will allowed because users start vote.
- publish all unpublished.
<br/>POST SERVER_PATH/rest/restaurants/publish

<br/><br/><br/>

