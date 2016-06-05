# Jar-Guessing-Game

Welcome to the jar game! This is like a digital version of those "Guess how many are in the jar" games.
The jar requires two things: an item to be put in it, and a max amount to put in the jar.
A new, random jar will be produced every game, with any number of items between 0 and the max number specified in the jar.
Your job is to guess how much is in the jar in the least amount of guesses. Good luck!

When you first play the game, it will be a little complicated to get set up. However, once it's set up, it's really
easy to run it again.

***To first set up the game:***
1.	Download the full src folder, keep note of the location (something like C:\Users\User one\Downloads).
2.	Open a command prompt. (Make sure to run it as an administrator, otherwise will not work).
3.	Type the prompt "java -version" into the command prompt and make note of the version number.
4.	Open Control Panel -> System and Security -> System. Go to Advanced System Settings.
	Click on Environment Variables on the bottom right, then the variable Path. Click edit.
   	Type the following at the very end of the variable value box (DO NOT DELETE ANYTHING):
     	;C:\Program Files\Java\jdk1.8.0_73\bin (substitute jdk1.8.0_73 with whatever your version is)
     	Hit okay and go back to the command prompt.
5.   	Type the prompt "cd *location*\src\com\teamtreehouse\jargame" with *location* being the location of the src folder
     	(You will have to put quote marks around any folder that has a space in its name, eg: C:\Users\"User one"\)
6.   	Type the prompt "javac *.java"
7.   	Type the prompt "cd .. && cd .. && cd .." (You should now be in src folder)
8.   	Type the prompt "java com.teamtreehouse.jargame.Game"

***When you first run it, it will come up with a long error message. Ignore everything except the bottom two lines.***

After you finish all this, if you ever want to run the game again, do the following:
1.   	Open a command prompt.
2.	Type the prompt "cd *location*\src" with *location* being the location of the src folder
     	(You will have to put quote marks around any folder that has a space in its name, eg: C:\Users\"User one"\)
3.	Type the prompt "java com.teamtreehouse.jargame.Game"
