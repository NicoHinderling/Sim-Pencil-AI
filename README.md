Sim-Pencil-AI
=============

OVERVIEW
--------
Sim is a game where players take turns drawing lines from point to point. The object of the game is to avoid making a triangle with your own lines. The objective of Sim Pencil AI was to create a "Computer Player" (Source Code) that a human could play against. In order to make a decision, the computer would use a MinMax tree to gauge the possible outcomes a couple of moves ahead. This project was a fun application of MinMax trees and my first attempt at Artifical Intelligence.

IMPLEMENTATION
--------------
While there are five Java files in total, the inner workings of the AI are within Graph.java and Player.java. Graph.java contains a list of functions that allows the AI to interact with the Grid of Sim (Such as addEdge(), isEdge(), etc). Player.java then uses these functions to establish a base move, and then in conjunction with a MinMax tree, look 'forward' and evaluate what move would be most advantageous for it. The evaluation function values traces down the tree based off of whether it is less likely to encounter cycles and whether the human is more likely to encounter cycles.

DEMO
----
Note: My mouse wasn't recorded in the video, but I (human) choose the red lines
[![ScreenShot](https://raw.githubusercontent.com/NicoHinderling/Sim-Pencil-AI/master/Sim%20Screenshot.png)](https://www.youtube.com/watch?v=tT1r-gYFZh4)


Feel free to download the code and try it yourself!

DISCLAIMER
----------
The code for the visual component of Sim was written by Mads Rosendahl, University of Roskilde, Denmark (madsr@ruc.dk).
