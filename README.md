# SQL_java_lab
Lab 10 for CSU's CS430 Database Administration class. 

### About
This code is designed to remotely connect to a relational database running on CSU's Computer Science Deptartment servers. 
This database has been previously populated by my own python and sql fill scripts, which were part of another assignment 
and are present to populate and restore the database for a fresh run of the program.

This code functions as a state machine, designed to query a specific part of the database as a command line utility. You 
can look up Library Members by ID, then look for books by ISBN number, Author or full to partial title matching via the built in SQL statments. The program runs off one large switch statment, so every behavior is defined and encapsulated by a state, and will run until the process is exited.

### USAGE

MUST BE LOGGED INTO CSU MACHINE WITH MariaDB ACCESS TO USE DATABASE.

In one terminal, log into database on CSU CS machine. In SQL prompt, run:

'''
source run.sql
'''

Which clears, creates and fills the database.

Then, in a seperate window (or after logging out of the database), run:

'''
./run.sh
'''

Which is a bash script that compiles and runs the java program. Must be logged into CSU CS machine, to use the SQL connector jar. 
