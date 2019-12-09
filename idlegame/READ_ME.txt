<----------------Project Valkyrie ---------------->

By:
    Alex Jaeger
	Trey Clark

Project Valkyrie is a simple game where the player starts with no money and no power
in a small nation surrounded by three larger nations.

Here the user is only able to take out a free newspaper ad, which is where
our game starts. As the players ad runs, they will begin to accrue units,
which add to there total power. Once certain power requirements are met they
will unlock the ability to have their army do certain actions
gaining the player money.

After collecting enough power, the player can tell their armies to take over
nearby nations.

=== RESOURCES Pane ===
The Resouces pane is always on the left of the user's screen and shows the
player's current power and money as well as the player's current army size.


<----------------TABS---------------->
There are four tabs available to the user.
=== RECRUIT ===
The Recruit tab contains methods to gain troops. Troops added to the player's
army directly add power. The primary cost of actions in this tab is money.

=== ARMY ===
The Army tab contains actions the player can have their army do to generate
money. These actions require a certain amount of power to unlock before
you can have your army do them.

=== UPGRADES ===
The Upgrades tab includes upgrades the player can purchase to increase
their power or money output.

=== MAP ===

The Map tab shows the current map of the world. Here the player can see what
areas are under their control. The map tab also allows the player to go to war
with other nations, should they meet the power requirements.

<----------------VICTORY---------------->
The player wins the game when they take the final Nation.


<-------------- Technology Used ------------------->
- SnappyDB is a Key-Value database to store the resources persistently.
- The Application is divided into fragments in order to reduce code complexity
- A viewmodel stores the data and each fragment can access this to store its data.
- Multiple threads of execution to handle tick and autosaving

<-------------- Future Work ------------------------>
In the future, we would like to improve upon the UI and get rid of the hard coded
values and provided a better theme. We also want to store more information such as
conquered nations persistently in the database.
