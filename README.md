# Security-Alert-System
Security Alert System based on Pub/Sub Middleware Environment

To test this system based on rabbitmq:
1. create message: 
  localhost:8001/messageSender/create/{type}
  type is the emergency type: Robbery, Gun Shot, Sexual Assault, Fire, Flooding, Earthquake
2. create queue:
  localhost:8001/messageReceiver/createQueue
3. send message:
  localhost:8001/messageSender/send
4. read message:
  localhost:8001/messageReceiver/getMsg

You can also add clients and get all the clients by using following url:
localhost:8001/client/addClients/{number}
localhost:8001/client/getClients
