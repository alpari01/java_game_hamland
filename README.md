**How to launch our game?**

**_Step 1_**

- Clone this project.
- Clone server https://gitlab.cs.ttu.ee/alpari/iti0301-2022-server.

**_Step 2_**

-Go to **KryoClient.java** on this project

- To play on the local server put:
    static int tcpPort = 27960;
    static int udpPort = 27960;
    static String ip = "localhost";

- To play on the TalTech server put:
    static int udpPort = 8080;
    static int tcpPort = 8081;
    static String ip = "193.40.156.122"; 

**_Step 3_**

- To play on the local server run **KryoServer.java** on the server project, then run **DesktopLauncher.java** on this project.

- To play on the TalTech server run **DesktopLauncher.java** on this project.

**_Step 4_**

- Game controls: WASD and arrows, mouse for aiming.
