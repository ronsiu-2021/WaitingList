# WaitingList 
Author: ronsiu-712
Date:   07/23/20
Description
-----------


This file utilize hashtable to store 200 customer data with nested class structure to achieve data abstraction. 
It also implements queue ADT to create a waiting-List system for the employee to handle customer data and the waiting-list for service.


How to compile
--------------

     To compile this program, simply navigate to the directory containing the
source files for Waiting and CustomerData, then type 
    
    javac Waiting.java

How to run
----------

    To run the program, type the program type name followed 
    by the program name. For example:

    java Waiting

Normal output
-------------

    Normal output is printed to stdout. An example of normal output would be 
    the following:

[userName]$ java WaitingList
Loading customer has completed
Hello! Nice to meet you!!!
Please enter any command | type "exit" when finish.

Command for program
-------------------
   This program will follow certain instruction. User can type 'help' to reveal
   applicable command. The commands to interact with the program are:
   
'reg'     for for registering new customer to dataSet. 
'search'  for searching customer record inside the dataSet. 
'remove'  for removing customer record from dataSet. 
'find'    for finding customer's position from waiting-list. 
'add'     for adding customer to waiting-list.  
'call'    for removing the first customer from the waiting-list.    
'next'    for showing the next customer from waiting-list. 
'count'   for showing the number of customer in waiting-list. 
'exit'    for exiting the program and save changes to dataSet. 

Remarks
-------
This program is only for demostration. No real personal information were used for this program. 

