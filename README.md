# Vessel Tracker

This small repo is a coding exercise for a job interview. The project
was to create a small REST service with Spring boot that receives
position update for vessels and responds with the received update extended
with the reception date.

`POST /update` accepts a payload in the format below:

```json
{
 "vessel": {
   "name": "Skinney",
   "country": "Iceland"
 },
 "position": {
   "date": "2020-08-20",
   "latitude": 64.2497,
   "longitude": 15.2020,
   "speed": 3.1415
 }
}
```
Server will store the received data and respond in the format below:
```json
{
 "vessel": {
   "name": "Skinney",
 },
 "position": {
   "date": "2020-08-20",
   "latitude": 64.2497,
   "longitude": 15.2020,
   "speed": 3.1415,
   "received_date": "2020-08-20",
 }
}
```
(Note that vessel country will be omitted from response, and `received_date` is added).

I chose to persist the data in a tiny SQLite database.

The endpoint `GET /vessels` will return an array of `vessel` objects,
representing all vessels that have at least one position registered.

`GET /positions?vessel={name}&country={country}` will return all positions
for the specified vessel, ordered by date in descending order.

Note: This project uses `java.util.Date` for update and received date,
although `java.time.Instant` probably makes more sense.