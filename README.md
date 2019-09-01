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
- User can vote for restourant by POST SERVER_PATH/rest/menu/vote/{restaurant_id} 

Request:
 <pre>curl "SERVER_PATH/rest/menu/vote/34"</pre>

Response example:

true - vote has been added
false - vote did't added, out of time or error

### 01.3. View vote history (Optional) 
- User can view the his votes GET SERVER_PATH/rest/history/ 

Request:
 <pre>curl "SERVER_PATH/rest/history"</pre>



 
<br/><br/><br/>

## 02. Admin flow 

### + 02.1. View all restourants with today menu.
- Admin can view all today's menu, empty or with dishes
<br/>GET SERVER_PATH/rest/restaurants/

### + 02.2. Add restaurant
- Admin can add new restaurant by 
<br/>POST SERVER_PATH/rest/restaurants/

Request: 
<pre>
curl -s -X POST -d '{"name":"New Restaurant"}' 
-H 'Content-Type:application/json;charset=UTF-8' 
SERVER_PATH/rest/restaurants/`
</pre>

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

### 02.9. View votes by date (Optional)
- Admin can view votes history for restaurants by date 
<br/>GET SERVER_PATH/rest/restaurants/votes/{date}/
- View votes for particular day for all restaurants.

### 02.10. Vote History for restaurant (Optional)
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

