import datetime
import time
import socket
from dateutil import parser
import threading

## A function to start sending time

def startSendingTIme(slave_client):

    while True:

        print("Current datetime is : ", datetime.datetime.now())

        send_datetime = datetime.datetime.now() + datetime.timedelta(minutes=10)

        print("Send datetime is : ", send_datetime)

        slave_client.send(str(send_datetime).encode())

        print("Recent time sent successfully",
                                          end = "\n\n")
        time.sleep(5)

## A function to start receiving time

def startReceivingTime(slave_client):

    while True:

        sync_time = parser.parse(slave_client.recv(1024).decode())

        print("Synchronized time at the client is: " + \
                                    str(sync_time),
                                    end = "\n\n")
        
## A function to initiate slave client

def initiateSlaveClient(port = 8080):

    slave_client = socket.socket()

    slave_client.connect(('127.0.0.1', port))

    ## Start thread to send time
    print("Starting to send time to server\n")

    send_time_thread = threading.Thread(target=startSendingTIme, args=(slave_client,))

    send_time_thread.start()

    ## Start thread to receive time
    print("Starting to receive time from server\n")

    receive_time_thread = threading.Thread(target=startReceivingTime, args=(slave_client,))

    receive_time_thread.start()


if __name__ == "__main__":

    initiateSlaveClient(port = 8080)
