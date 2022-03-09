# iti0301-2022-game

ITI0301 Tarkvaraarenduse projekt - mäng


**How to launch our game? (for project state at 09.03.2022)**

**_Step 1_**

We use two separate gitlab projects for our game.
- One for game itself aka client part (available here https://gitlab.cs.ttu.ee/illast/iti0301-2022-game)
- And one for server part (available here https://gitlab.cs.ttu.ee/alpari/iti0301-2022-server)
- Make sure both are cloned :)

**_Step 2_**
- While in IDEA open Server and Client projects in different windows.
- In server project run **iti0301-2022-server\core\src\com\mygdx\gameserver\server\KryoServer.java**.

This will launch the server on localhost. 
To verify server runs correctly ensure it has printed "Server is up!" in IDEA console :)

**_Step 3_**
- In client project run **iti0301-2022-game\desktop\src\com\mygdx\game\desktop\DesktopLauncher.java**.

This will launch the game.
To verify you are connected to the server check server console outputs.

- To test multiplayer functions run multiple **DesktopLauncher.java** simultaneously.

- Game controls: WASD and arrows, mouse for aiming.

**_Step 4_**

-Enjoy the game
