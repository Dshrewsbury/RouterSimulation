RouterSimulation
================

This program will simulate a network router. Through its interface, you can: add/remove routes, ping, and look up a route to a specific IP address.


In main, String cmd will have the syntax of one of four commands as follows:

ADD prefix=X.X.X.X/Y, route=Z
ADD prefix=W/W, route=Z
DEL prefix=X.X.X.X/Y, route=Z
LOOKUP dest=X.X.X.X
W will be 0
X will be an integer between 0-255 (inclusive)
Y will be an integer between 0-32 (inclusive)
Z will be a non-negative integer or the string "NULL"

For example, ADD prefix=209.170.250.0/24, route=1 adds the network 209.170.250.0 with a 24-bit net mask (255.255.255.0) to the router through interface 1.



Functions:


public int parseCmd(String cmd): Accepts a one line command and parses out the prefix and net mask or the destination depending on the command. 
The method will return zero or one (0 for unexecutable command, 1 for a successful command) in all cases EXCEPT when a Lookup command has been given in which case it will return the correct route 
(n for route n, 0 if it is a NULL route and -99 only if there is no match).

