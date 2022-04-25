# iti0301-2022-game

ITI0301 Tarkvaraarenduse projekt - mäng


**How to launch our game? (for project state on 25.04.2022)**

**_Step 1_**

We use two separate gitlab projects for our game.
- One for game itself aka client part (available here https://gitlab.cs.ttu.ee/illast/iti0301-2022-game)
- And one for server part (available here https://gitlab.cs.ttu.ee/alpari/iti0301-2022-server)
- Make sure both are cloned :)


**NB!**

Since now our server runs on the dedicated TalTech server you do not need to clone project's server part
if you do not want to run/test server (localhost server) on your machine.

So in this case all you need to do is to clone client part.

**_How to connect to our game's TalTech server?_**

In **core/src/com/mygdx/game/client/KryoClient.java** please ensure **static String ip** value is set to **"193.40.156.122"**, not **"localhost"**.


**_Step 2_**
- While in IntelliJ IDEA (or any other IDE) open Server and Client projects in different windows.
- In server project run **iti0301-2022-server\core\src\com\mygdx\gameserver\server\KryoServer.java**.

This will launch the server on **localhost**. 
To verify server runs correctly ensure it has printed "Server is up!" in IDE console :)

**_Step 3_**
- In client project run **iti0301-2022-game\desktop\src\com\mygdx\game\desktop\DesktopLauncher.java**.

This will launch the game.
To verify you are connected to the server check server console outputs.

- To test multiplayer functions run multiple **DesktopLauncher.java** simultaneously.

- Game controls: WASD and arrows, mouse for aiming.

**_Step 4_**

- Enjoy the game
