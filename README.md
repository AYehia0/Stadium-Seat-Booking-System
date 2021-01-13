# Stadium Seat Booking System

This is a simple school task app to book seats for matches using sqlite.


# Problem description 
###### (didn't follow everything)

This is an automated system that can be developed in Java and will be
useful to the people for booking their seats in the stadium through the
online method. Every detail of the stadium should be mentioned
properly including the number of seats, seat availability, price of the seat,
category of seats.
Admin will manage all the details related to the stadium and matches
that would take place in the stadiums and will have to update each detail.
When the user books a seat, he will get a unique seat number.
This application will require to contain the details of all the matches
taking place in a particular stadium.

#### Bugs/Bad

To be honest idk, how i made such a thing, it's bad practice everywhere. And it's not finished yet, i will visit it oneday after i gain more knowledge about how to org things together and how softwares are made, for now i will leave it like this...

- If you digged so deep you may find a way to ```lockeddatabase error```
- I didn't add a way to delete things related to ```Seat``` table, so it will seem confusing.
- Some of the code isn't DRY
- The design itself isn't something related to real life aka bad


# UML 
![uml](/pics/UML_class.png) 


# Skills before starting

* [Software Design - Implementing Our Design](https://www.youtube.com/watch?v=6thjSbJcoUc)

* [Database Design Course - Learn how to design and plan a database for beginners](https://www.youtube.com/watch?v=ztHopE5Wnpc)

* [SQL Tutorial - Full Database Course for Beginners](https://www.youtube.com/watch?v=HXV3zeQKqGY)

# To Run and Compile on Linux
  ## To Compile :

	$ javac -cp ".:./externel/sqlite-jdbc-3.5.8.jar" src/* -d .

  ## To Run (run the main class only):

	$ java -cp ".:./externel/sqlite-jdbc-3.5.8.jar" stadium.Main



# Notes 

In case of locked database, kill anything that currently uses the database :  $ fuser -k db.sqlite 

# ToDo 
###### (probably won't do, busy)

- [ ] Create a table for seat types.
- [ ] Add more control for the seat table.
- [ ] Add Prices to the database.
- [ ] The is a bug, a fatel one, can you find it out ?
- [ ] Add more flexibility/options to the Booking section. 
- [ ] Add a GUI.
- [ ] Inhance the searching security.
- [X] Add an admin module to monitor the system and change the database.
- [X] Fix the redundancy in code ```(shitty code, i am trying to finish fast)```
 
