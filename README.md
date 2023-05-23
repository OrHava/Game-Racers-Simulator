# Game-Racers-Simulator
Race Manager - README
This Java code represents a race manager application that allows users to build and manage races with different racers in an arena. The application provides a graphical user interface (GUI) for the users to interact with. Below is an overview of the code structure and functionality.

# Code Overview
The code consists of several event listeners and helper methods to handle user interactions and manage the race. Here are the key components and their functionalities:

### 1. Race() Constructor
This constructor initializes the race manager and sets up event listeners for various buttons in the GUI.
It handles actions related to building the arena, adding racers, and starting the race.
### 2. buildArenaButton Mouse Listener
When the "Build Arena" button is pressed, it triggers the event listener.
It checks if the race has already finished and resets the race if necessary.
It retrieves input values for the arena length and maximum number of racers from the corresponding text fields.
It validates the input values and shows error messages if they are invalid.
It determines the chosen arena type from the combo box selection.
It loads the corresponding arena image based on the chosen arena type.
It uses the race builder to build the arena object based on the chosen arena type, length, and maximum racers.
It fires a property change event to notify the listeners that the arena has been built.
### 3. addRacerButton Mouse Listener
When the "Add Racer" button is pressed, it triggers the event listener.
It performs various checks and validations before adding a racer to the race.
It checks if the race has already finished and shows an error message if it has.
It checks if the race has already started and shows an error message if it has.
It checks if the arena has been built and shows an error message if it hasn't.
It checks if the maximum number of racers has been reached and shows an error message if it has.
It retrieves input values for the racer's name, maximum speed, and acceleration from the corresponding text fields.
It validates the input values and shows error messages if they are invalid.
It determines the chosen racer type and color from the combo box selections.
It uses the race builder to build a racer object based on the chosen racer type, name, maximum speed, acceleration, and color.
It adds the racer to the arena and initializes the race.
It adds the racer to the list of racers and updates the racers combo box with the new racer's information.
It loads the corresponding racer image based on the chosen racer type and color.
It increments the racer count.
### 4. startRaceButton Mouse Listener
When the "Start Race" button is pressed, it triggers the event listener.
It performs various checks before starting the race.
It checks if the arena has been built and if racers have been added, showing an error message if necessary.
It checks if the race has already finished and shows an error message if it has.
It checks if the race has already started and shows an error message if it has.
It calculates the starting position for each racer based on the arena width and racer panel width.
It sets the starting location for each racer's panel.
It makes the race manager GUI visible and sets the race started flag to true.
It fires a property change event to notify the listeners that the race has started.
It creates a new thread that continuously updates the racers' positions on the GUI based on the race progress.
It stops the thread and marks the race as finished when all racers have completed the race or are disabled.
It fires a property change event to notify the listeners that the race has finished.
Usage

### 5. showInfoButton Mouse Listener
When the "Show Info" button is pressed, it displays real-time race information in a separate window.
It creates a new thread that continuously updates the information based on the race progress.
The information includes details about completed racers, active racers, broken racers, disabled racers, and the current ranking of racers.
### 6. comboBoxRacers Action Listener
When the selection in the racers combo box changes, this event listener is triggered.
It retrieves the selected racer's information and updates the corresponding input fields and combo boxes accordingly.
The racer's type, color, name, maximum speed, and acceleration are populated in the UI.
### 7. CopyRacerBtn Mouse Listener
When the "Copy Racer" button is pressed, it creates a copy of the selected racer and adds it to the race.
It performs various validations and displays appropriate error messages if necessary.
The copied racer's type, color, name, maximum speed, and acceleration are set based on the selected racer.
### 8. CarRaceBuilderBtn Mouse Listener
When the "Car Race Builder" button is pressed, it builds a race with a specified number of default car racers.
It creates an instance of the race builder and initializes the arena and racers.
The arena is built with the specified length, and the racers are added to the race based on the user's input.


To use the race manager application, follow these steps:

### Build the Arena:

Choose the arena type from the combo box.
Enter the arena length and maximum number of racers.
Click the "Build Arena" button to create the arena.
Add Racers:

Choose the racer type and color from the respective combo boxes.
Enter the racer's name, maximum speed, and acceleration.
Click the "Add Racer" button to add the racer to the race.
Repeat the above steps to add more racers.
Start the Race:

Click the "Start Race" button to begin the race.
The racers' panels will move across the arena on the GUI.
The race will finish when all racers have completed the race or are disabled.
Note: Error messages will be displayed if any invalid inputs or incorrect actions are performed.

# Contributing
This code is provided as a reference implementation for a race manager application. Feel free to modify and enhance it according to your specific needs.

If you have any suggestions or improvements, please create a pull request or submit an issue on the GitHub repository.

# License
This code is licensed under the MIT License.
