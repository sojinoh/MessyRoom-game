WHAT IS MESSY ROOM?

:My friend’s dirty room motivated me to makeMessy Room game. The game aims to clean the entire room with the vacuum cleaner by avoiding the cockroaches. Vacuum cleaner is controllable with the arrow keys and the ‘z’ key is used to clean the room. There are several items that help cleaning the room. Some items disappear after a certain time. When the first stage is cleaned, the number of cockroaches increases.



USED ALGORITHM?

:Messy Room was made with Java, and BlueJ program was used for coding. Cockroaches, items, and lives were written in a separate class from the motion class of the game. They were put in a arrayList for the ease of adding and removing . To detect the collision between the vacuum and items or the cockroaches, separate boolean methods were written. All the items were added and removed at a certain time using the itemTimer. To check if the game is over (the entire room is cleaned or there are no lives left) two booleans, gameover and missionComplete, were used. Motion class implemented KeyListener and MouseListener to detect the press of ‘z’ key and the mouse.