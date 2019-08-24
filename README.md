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

### 01.1. View lunch menu
- User can view lunch menu from all restaurants by 
<br/>GET SERVER_PATH/rest/restaurants/

Request:
 <pre>curl "SERVER_PATH/rest/restaurants/"</pre>

Response example for 2 restaurants each have menu with 2 dishes:
 <pre>
    [{
      "id": 100001,
      "dateTime": "2015-05-31T20:00:00",
      "name": "Dish_1",
      "price": 25,
      "restaurant": 1
   },
      {
      "id": 100002,
      "dateTime": "2015-05-31T20:00:00",
      "name": "Dish_2",
      "price": 5,
      "restaurant": 1
   },
     {
     "id": 100003,
     "dateTime": "2015-05-31T20:00:00",
     "name": "Dish_1",
     "price": 10,
     "restaurant": 2
   },
     {
     "id": 100004,
     "dateTime": "2015-05-31T20:00:00",
     "name": "Dish_2",
     "price": 15,
     "restaurant": 2
   }]
   </pre>


### 01.2. Vote for restaurant 
- User can vote for restourant by POST SERVER_PATH/rest/vote/{restaurant_id} 

Request:
 <pre>curl "SERVER_PATH/rest/vote/34"</pre>

### 01.3. View vote history (Optional) 
- User can view the his votes GET SERVER_PATH/rest/history/ 

Request:
 <pre>curl "SERVER_PATH/rest/history"</pre>

 
 
<br/><br/><br/>

## 02. Admin flow 

### 02.1. Add restaurant
- Admin can add new restaurant by 
<br/>POST SERVER_PATH/rest/restaurants/

Request: 
<pre>
curl -s -X POST -d '{"name":"New Restaurant"}' 
-H 'Content-Type:application/json;charset=UTF-8' 
SERVER_PATH/rest/restaurants/`
</pre>

### 02.2. Add today menu dish for restaurant 
- Admin can add new dishes by restaurant_id 
<br/>POST SERVER_PATH/rest/restaurants/{restaurant_id}

### 02.3. Remove today dish 
- Admin can remove today dishes by its id
- Only today  dish can be removed.
<br/>DELETE SERVER_PATH/rest/dish/{dish_id}

### 02.4. View today menu for all restourants 
- Admin can view all today's menu
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

