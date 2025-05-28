<h1>Sleep Logger</h1>

This project is to manage sleep data of a user. 

It has data such as the time user goes to bed, the time user wakes up, total time in bed and morning mood.

Also, it has a feature that calculates the average of the last 30 days of those data. So, the average time the user went to bed in the last month, the average time in bed and also how many days the morning mood was GOOD, OK and BAD.

<h2>Endpoints</h2>

<h3>GET sleep log</h3>

curl --location 'http://localhost:8080/sleep-logs/1?sleepDate=2025-05-24'

Response:
```json
{
    "id": 70,
    "sleepDate": "2025-05-24",
    "goToBedTime": "22:55",
    "wakeUpTime": "03:00",
    "totalTimeInBed": "PT4H5M",
    "morningMood": "GOOD"
}
 ```
<h3>GET month averages</h3>

curl --location 'http://localhost:8080/sleep-logs/month-averages/1'

Response:
```json
{
    "initialDate": "2025-04-28",
    "finalDate": "2025-05-28",
    "goToBedTimeAverage": "23:27",
    "wakeUpTimeAverage": "06:00",
    "totalTimeInBedAverage": "PT6H43M40S",
    "morningMoodFrequency": {
        "BAD": 1,
        "GOOD": 1,
        "OK": 3
    }
}
 ```
<h3>POST sleep log</h3>

curl --location 'http://localhost:8080/sleep-logs' \
--header 'Content-Type: application/json' \
--data '{
    "sleepDate": "2025-05-24",
	"goToBedTime": "22:55",
    "wakeUpTime": "03:00",
    "morningMood": "GOOD",
    "userId": "1"
}'

Request Body:
```json
{
    "sleepDate": "2025-05-24",
	"goToBedTime": "22:55",
    "wakeUpTime": "03:00",
    "morningMood": "GOOD",
    "userId": "1"
}
 ```
Response:
```json
{
    "id": 70,
    "sleepDate": "2025-05-24",
    "goToBedTime": "22:55",
    "wakeUpTime": "03:00",
    "totalTimeInBed": "PT4H5M",
    "morningMood": "GOOD"
}
 ```
