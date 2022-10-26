In this implementation, we have different agents with different behaviors:
•	Planter agent
o	A new plant is randomly planted every 5 seconds.
•	Seeker agent
o	Search environment
o	Discover the location of food
o	Announcing the food to the collector agents by message
o	This agent is not able to carry food.
•	Collector agent
o	Transport a maximum of one plant and deliver it to the warehouse
o	Collecting a plant that they come across on the way if they do not carry a plant
o	Storing the location of the plant that they come across on the way if they are carrying a plant
o	Going to the location of the plant announced by the search agent and collecting it
•	Area agent
o	Control and create the environment

The goal of this implementation is to collect the most plant in the shortest time.
For execution, there are two folders, agents and behaviors, where there are classes of agents including starter in the agents folder and behavior class of agents in the behaviors folder, it can be executed as hardcode in runConfig with the command -gui -agents myAgent:agents.Starter.
More details are included in the pdf file.
