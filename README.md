<h1>Sleep Logger</h1>

This project is to manage sleep data of a user. 

It has data such as the time user goes to bed, the time user wakes up, total time in bed and morning mood.

Also, it has a feature that calculates the average of the last 30 days of those data. So, the average time the user went to bed in the last month, the average time in bed and also how many days the morning mood was GOOD, OK and BAD.

<h2>Endpoints</h2>

<h3>GET sleep log</h3>

curl --location 'http://localhost:8080/sleep-log/{userId}'

Response:
```json
{
    "sleepDate": "2025-05-21",
    "goToBedTime": "22:00:00",
    "wakeUpTime": "07:36:00",
    "totalTimeInBed": "PT9H36M",
    "morningMood": "GOOD"
}
 ```
<h3>GET month averages</h3>

curl --location 'http://localhost:8080/sleep-log/month-averages/{userId}'

Response:
```json
{
    "initialDate": "2025-04-21",
    "finalDate": "2025-05-21",
    "goToBedTimeAverage": "23:30:00",
    "wakeUpTimeAverage": "07:32:00",
    "totalTimeInBedAverage": "PT8H16M30S",
    "morningMoodFrequency": {
        "GOOD": 3,
        "OK": 0,
        "BAD": 1
    }
}
 ```
<h3>POST sleep log</h3>

curl --location 'http://localhost:8080/sleep-log' \
--header 'Content-Type: application/json' \
--data '{
    "sleepDate": "2025-05-07",
	"goToBedTime": "22:55",
    "wakeUpTime": "09:00",
    "morningMood": "OK",
    "userId": "1"
}'

Request Body:
```json
{
	"sleepDate": "2025-05-17",
	"goToBedTime": "22:00",
	"wakeUpTime": "07:36",
    "morningMood": "GOOD",
    "userId": "1"
}
 ```
Response:
```json
{
    "sleepDate": "2025-05-17",
    "goToBedTime": "22:00:00",
    "wakeUpTime": "07:36:00",
    "totalTimeInBed": "PT9H36M",
    "morningMood": "GOOD"
}
 ```
