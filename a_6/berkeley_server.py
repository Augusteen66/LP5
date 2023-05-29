import datetime
import time
import socket
from dateutil import parser
import threading

client_data = {}


## Nested function for startConnecting
def startReceivingClockTimes(connector, address):

    while True:

        clock_time_string = connector.recv(1024).decode()
        clock_time = parser.parse(clock_time_string)
        clock_time_diff = datetime.datetime.now() - clock_time

        print("Current server time : ", datetime.datetime.now())

        client_data['address'] = {
            "clock_time": clock_time,
            "time_difference": clock_time_diff,
            "connector": connector
        }

        print("Client data updated with : ", str(address))

        time.sleep(5)

## Connecting with clients
def startConnecting(master_server): ## master_server is the socket.listen object

    while True:

        master_server_connector, addr = master_server.accept()

        print("Raw slave address: ", str(addr))

        slave_address = str(addr[0]) + ":" + str(addr[1])

        print(slave_address + " got connected successfully")

        current_thread = threading.Thread(target = startReceivingClockTimes,
                                          args = (master_server_connector, slave_address))
        
        current_thread.start()

## Function to get average clock difference
def getAverageClockDiff():

    current_client_data = client_data.copy()

    time_difference_list = list(client['time_difference'] for address, client in current_client_data.items())

    sum_of_clock_differences = sum(time_difference_list, datetime.timedelta(0,0))

    average_of_clock_differences = sum_of_clock_differences / (len(current_client_data)+1)

    return average_of_clock_differences

## Function to synchronise all clocks
def synchroniseAllClocks():

    while True:

        print("New synchronization cycle started.")
        print("Number of clients to be synchronized: " + \
                                    str(len(client_data)))
        
        if len(client_data) > 0:

            average_of_clock_differences = getAverageClockDiff()
            print("Average of clock differences is : ", average_of_clock_differences)

            for client_addr, client in client_data.items():

                try:

                    #synchronised_time = datetime.datetime.now() + average_of_clock_differences

                    synchronised_time = client['clock_time'] + average_of_clock_differences

                    client['connector'].send(str(synchronised_time).encode())
                
                except Exception as e:

                    print("Something went wrong while " + \
                          "sending synchronized time " + \
                          "through " + str(client_addr))
                    
        else :

            print("No client data." + \
                        " Synchronization not applicable.")
            
        print("\n\n")

        time.sleep(5)


## Function to start the server
def initiateClockServer(port = 8080):

    master_server = socket.socket()
    #master_server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

    master_server.bind(('', port))

    master_server.listen(10)

    print("Clock server started...\n")
 
    # start making connections
    print("Starting to make connections...\n")

    start_conn_thread = threading.Thread(target=startConnecting, args=(master_server, ))
    start_conn_thread.start()

    ## Synchronise
    sync_thread = threading.Thread(target=synchroniseAllClocks, args=())
    sync_thread.start()

if __name__ == "__main__":

    initiateClockServer(port = 8080)