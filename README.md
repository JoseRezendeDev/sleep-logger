This project is to manage sleep of a user. 

It has data such as the time user goes to bed, the time user wakes up, total time in bed and morning mood.

It has GET and POST endpoints to read and create this data.

Also, it has another GET endpoint that calculates the average of the last 30 days of those data. So, the average time the user went to bed in the last month, the average time in bed and also how many days the morning mood was GOOD, OK and BAD.

GET http:localhost:8080/sleep-log/{userId}
Response:
{
"sleepDate": "2025-05-21",
"goToBedTime": "22:00:00",
"wakeUpTime": "07:36:00",
"totalTimeInBed": "PT9H36M",
"morningMood": "OK"
}

GET http:localhost:8080/sleep-log/month-averages/{userId}
Response:
{
"initialDate": "2025-04-21",
"finalDate": "2025-05-21",
"goToBedTimeAverage": "22:00:00",
"wakeUpTimeAverage": "07:36:00",
"totalTimeInBedAverage": "PT9H36M",
"morningMoodFrequency": {
"OK": 1,
"BAD": 0,
"GOOD": 0
}
}

POST http:localhost:8080/sleep-log
Request Body:
{
"goToBedTime": "22:00",
"wakeUpTime": "07:36",
"morningMood": "OK",
"userId": "1"
}
Response:
{
"sleepDate": "2025-05-21",
"goToBedTime": "22:00:00",
"wakeUpTime": "07:36:00",
"totalTimeInBed": "PT9H36M",
"morningMood": "OK"
}