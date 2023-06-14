package GUI.Race;

import factory.RaceBuilder;
import game.arenas.Arena;
import game.arenas.exceptions.RacerLimitException;
import game.arenas.exceptions.RacerTypeException;
import game.racers.Racer;
import utilities.EnumContainer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

/** @author Or Hava 208418483
 */
public class Race extends JFrame implements PropertyChangeListener {
    private JComboBox comboBoxArena;
    private JPanel Panel1;
    private JTextField textFieldArenaLeangth;
    private JTextField textFieldMaxRacers;
    private JButton buildArenaButton;
    private JComboBox comboBoxRacer;
    private JComboBox comboBoxRacerColor;
    private JTextField textFieldName;
    private JTextField textFieldMaxSpeed;
    private JTextField textFieldAcceleration;
    private JButton addRacerButton;
    private JButton startRaceButton;
    private JButton showInfoButton;
    private   JPanel PanelRaceTrack;
    private JLabel chooseArenaLabel;
    private JLabel arenaLeangthLabel;
    private JLabel maxRacersNumberLabel;
    private JLabel chooseRacerLabel;
    private JLabel chooseColorLabel;
    private JLabel racerNameLabel;
    private JLabel maxSpeedLabel;
    private JLabel accelerationLabel;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel PanelArena;
    private JPanel PanelRacer1;
    private JPanel PanelRacer2;
    private JPanel PanelRacer3;
    private JPanel PanelRacer4;
    private JPanel PanelRacer5;
    private JPanel PanelRacer6;
    private JPanel PanelRacer7;
    private JPanel PanelRacer8;
    private JPanel PanelRacer9;
    private JPanel PanelRacer10;
    private JPanel PanelRacer11;
    private JPanel PanelRacer12;
    private JPanel PanelRacer13;
    private JPanel PanelRacer14;
    private JPanel PanelRacer15;
    private JPanel PanelRacer16;
    private JPanel PanelRacer17;
    private JPanel PanelRacer18;
    private JPanel PanelRacer19;
    private JPanel PanelRacer20;
    private JComboBox <RacerInfo> comboBoxRacers;
    private JButton CopyRacerBtn;
    private JButton CarRaceBuilderBtn;
    private JTextField nRacersText;

    private static Race race ;
    private static  RaceBuilder builder = RaceBuilder.getInstance();
    private static  ArrayList<Racer> racers = new ArrayList<>();
    private int arenaLength = 1000;

    private ArrayList<String> racersImages = new ArrayList<>();
    private static  ArrayList<JPanel> racersJPanel = new ArrayList<>();
    private int maxRacers = 8;
    private int racersNumber = 0;
    private Arena arena = null;

    private boolean raceStarted = false;
    private JFrame infoTable = null;
    private boolean raceFinished = false;

    public Race() {

        buildArenaButton.addMouseListener(new MouseAdapter() {

            /**

             Handles the mouse pressed event for the buildArenaButton.
             Builds a new arena based on the user's input values.
             @param e the MouseEvent to be processed
             */
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);



                resetRace();

                for (Component c : race.PanelArena.getComponents()) {
                    if ((c instanceof JPanel)) {
                        race.PanelArena.setVisible(true);
                    }
                }
                if (raceStarted && !raceFinished) {
                    JOptionPane.showMessageDialog(race, "Race started! Please wait.");
                    return;
                }

                try {
                    arenaLength = Integer.parseInt(textFieldArenaLeangth.getText());
                    maxRacers = Integer.parseInt(textFieldMaxRacers.getText());
                    if (arenaLength < 100 || arenaLength > 3000 || maxRacers <= 0 || maxRacers > 20)
                        throw new Exception();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(race, "Invalid input values! Please try again.");
                    return;
                }

                String chosenArena = Objects.requireNonNull(comboBoxArena.getSelectedItem()).toString();
                try {
                    loadImageArena(Objects.requireNonNull(comboBoxArena.getSelectedItem()).toString());


                    switch (chosenArena) {
                        case "AerialArena" ->
                                arena = builder.buildArena("game.arenas.air.AerialArena", arenaLength, maxRacers);
                        case "LandArena" ->
                                arena = builder.buildArena("game.arenas.land.LandArena", arenaLength, maxRacers);
                        case "NavalArena" ->
                                arena = builder.buildArena("game.arenas.naval.NavalArena", arenaLength, maxRacers);
                    }
                    firePropertyChange("arenaBuilt", null, arena);
                } catch (Exception ex) {
                    System.out.println("Error Choosing Arena");
                }


            }
        });
        addRacerButton.addMouseListener(new MouseAdapter() {

            /**

             Handles the mouse pressed event for the addRacerButton.
             Adds a new racer to the arena if all necessary conditions are met.
             @param e the MouseEvent to be processed
             */
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);


                if (raceFinished) {
                    JOptionPane.showMessageDialog(race, "Race finished, Please make a new arena.");
                    return;
                }
                if (raceStarted) {
                    JOptionPane.showMessageDialog(race, "Race started, racers cannot be added now.");
                    return;
                }
                if (arena == null) {
                    JOptionPane.showMessageDialog(race, "Please build arena first!");
                    return;
                }
                if (racersNumber == maxRacers) {
                    JOptionPane.showMessageDialog(race, "No more racers can be added!");
                    return;
                }
                String name;
                double maxSpeed;
                double acceleration;

                try {
                    name = textFieldName.getText();
                    maxSpeed = Double.parseDouble(textFieldMaxSpeed.getText());
                    acceleration = Double.parseDouble(textFieldAcceleration.getText());
                    if (name.isEmpty() || maxSpeed <= 0 || acceleration <= 0)
                        throw new Exception();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(race, "Invalid input values! Please try again.");
                    return;
                }

                String racerType = (String) comboBoxRacer.getSelectedItem();

                String color = (String) comboBoxRacerColor.getSelectedItem();
                EnumContainer.Color col = null;
                if (color != null) {
                    col = switch (color) {
                        case "Red" -> EnumContainer.Color.RED;
                        case "Black" -> EnumContainer.Color.BLACK;
                        case "Yellow" -> EnumContainer.Color.YELLOW;
                        case "Blue" -> EnumContainer.Color.BLUE;
                        case "Green" -> EnumContainer.Color.GREEN;

                        default -> null;
                    };
                }

                String racerClass = null;
                if (racerType != null) {
                    racerClass = switch (racerType) {
                        case "Helicopter" -> "game.racers.air.Helicopter";
                        case "Bicycle" -> "game.racers.land.Bicycle";
                        case "RowBoat" -> "game.racers.naval.RowBoat";
                        case "Airplane" -> "game.racers.air.Airplane";
                        case "Car" -> "game.racers.land.Car";
                        case "Horse" -> "game.racers.land.Horse";
                        case "SpeedBoat" -> "game.racers.naval.SpeedBoat";

                        default -> null;
                    };
                }

                try {
                    if (racerType != null) {
                        Racer racer;
                        if (racerType.equals("Bicycle") || racerType.equals("Airplane") || racerType.equals("Car")) {
                            racer = builder.buildWheeledRacer(racerClass, name, maxSpeed, acceleration, col, 2);
                            firePropertyChange("racerAdded", null, racer);
                        } else {
                            racer = builder.buildRacer(racerClass, name, maxSpeed, acceleration, col);

                        }

                        arena.addRacer(racer);
                        arena.initRace();
                        racers.add(racer);

                        comboBoxRacers.addItem(new RacerInfo(racer.getName(), racer.getSerialNumber(), racer));


                    }


                } catch (RacerTypeException ex) {
                    JOptionPane.showMessageDialog(race, "racer don't belong to this arena, Choose another racer.");
                    return;
                } catch (Exception ex) {
                    System.out.println("Error Choosing Racer");
                }


                racersImages.add(Objects.requireNonNull(comboBoxRacer.getSelectedItem()) + Objects.requireNonNull(comboBoxRacerColor.getSelectedItem()).toString());
                loadImageRacer(Objects.requireNonNull(comboBoxRacer.getSelectedItem()) + comboBoxRacerColor.getSelectedItem().toString(), racersNumber + 1);


                racersNumber++;

            }
        });
        startRaceButton.addMouseListener(new MouseAdapter() {
            /**

             Handles the mouse pressed event for the startRaceButton.
             Starts the race if all necessary conditions are met.
             @param e the MouseEvent to be processed
             */
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                if (arena == null || racersNumber == 0) {
                    JOptionPane.showMessageDialog(race, "Please build arena first and add racers!");
                    return;
                }
                if (raceFinished) {
                    JOptionPane.showMessageDialog(race, "Race finished! Please build a new arena and add racers.");
                    return;
                }
                if (raceStarted) {
                    JOptionPane.showMessageDialog(race, "Race already started!");
                } else {


                    int arenaWidth = (int) arena.getLength();
                    int racerPanelWidth = racersJPanel.get(0).getWidth();
                    int startingX = (arenaWidth - racerPanelWidth) / 2;
                    for (JPanel jPanel : racersJPanel) {
                        jPanel.setLocation(startingX, jPanel.getY());
                    }

                    setLocationRelativeTo(null);
                    setVisible(true);
                    raceStarted = true;
                    firePropertyChange("raceStarted", null, null);
                    Thread raceThread = new Thread(() -> {
                        int panelArenaLength = race.PanelArena.getWidth() - race.PanelRacer1.getWidth();
                        int arenaLength = (int) arena.getLength();
                        double ratio = (double) panelArenaLength / (double) arenaLength;
                        Timer timer = new Timer(30, new ActionListener() {
                            int i = 0;

                            @Override
                            public void actionPerformed(ActionEvent e1) {
                                i = 0;
                                for (int i = 0; i < racersJPanel.size(); i++) {


                                    JPanel jPanel = racersJPanel.get(i);
                                    jPanel.setLocation((int) (racers.get(i).getCurrentLocation().getX() * ratio), jPanel.getY());
                                    jPanel.repaint();


                                }

                                if (arena.getCompleatedRacers().size() + arena.getDisabledRacers().size() == racers.size()) {
                                    ((Timer) e1.getSource()).stop();

                                    for (int i = 0; i < racersJPanel.size(); i++) {
                                        JPanel jPanel = racersJPanel.get(i);
                                        jPanel.setLocation((int) (racers.get(i).getCurrentLocation().getX() * ratio), jPanel.getY());
                                        jPanel.repaint();
                                    }

                                    raceFinished = true;
                                    firePropertyChange("raceFinished", null, null);
                                }
                            }
                        });

                        timer.start();
                        try {
                            arena.startRace();
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    });

                    raceThread.start();


                }

            }
        });


        showInfoButton.addMouseListener(new MouseAdapter() {
            /**

             Handles the mouse pressed event.
             Displays real-time information about the race in a separate window.
             Updates the information periodically every second.

             @param e the MouseEvent to be processed
             */
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (arena == null || racersNumber == 0) {
                    JOptionPane.showMessageDialog(race, "Please build arena first and add racers!");
                    return;
                }
                final boolean[] lastUpdate = {false};
                // Create a new thread to update the information in real time
                Thread infoThread = new Thread(() -> {
                    do {
                        ArrayList<Racer> completedRacers = arena.getCompleatedRacers();
                        ArrayList<Racer> activeRacers = arena.getActiveRacers();
                        ArrayList<Racer> brokenRacers = arena.getBrokenRacers();
                        ArrayList<Racer> disabledRacers = arena.getDisabledRacers();
                        ArrayList<Racer> ranking = getRanking();

                        int dataSize = completedRacers.size() + activeRacers.size() + brokenRacers.size() + disabledRacers.size() + ranking.size();
                        String[][] data = new String[dataSize][6];

                        int index = 0;
                        for (Racer racer : ranking) {
                            data[index][0] = racer.getName();
                            data[index][1] = String.valueOf(racer.getCurrentSpeed());
                            data[index][2] = String.valueOf(racer.getMaxSpeed());
                            data[index][3] = String.valueOf(racer.getCurrentLocation().getX());

                            if (completedRacers.contains(racer) && !disabledRacers.contains(racer)) {
                                data[index][4] = "Comp";
                            } else if (activeRacers.contains(racer)) {
                                data[index][4] = "Act";
                            } else if (brokenRacers.contains(racer)) {
                                data[index][4] = "Brok";
                            } else if (disabledRacers.contains(racer)) {
                                data[index][4] = "Failed";
                            }

                            data[index][5] = String.valueOf(ranking.indexOf(racer) + 1);
                            index++;
                        }

                        DefaultTableModel tableModel = new DefaultTableModel(data, new String[]{"Racer name", "Current speed", "Max speed", "Current X location", "Status", "Position"});
                        JTable table = new JTable(tableModel);

                        JScrollPane scrollPane = new JScrollPane(table);
                        scrollPane.setPreferredSize(new Dimension(300, 500)); // Set the desired size for the scroll pane

                        JPanel panel = new JPanel();
                        panel.add(scrollPane);

                        SwingUtilities.invokeLater(() -> {
                            if (infoTable != null) {
                                infoTable.dispose();
                            }
                            infoTable = new JFrame("Race Information");
                            infoTable.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            infoTable.setContentPane(panel);
                            infoTable.pack();
                            infoTable.setVisible(true);
                        });

                        if (raceFinished && !lastUpdate[0]) {
                            lastUpdate[0] = true;
                        }

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }

                    } while (!raceFinished || !lastUpdate[0]);
                });

                infoThread.setDaemon(true);
                infoThread.start();
            }
        });

        comboBoxRacers.addActionListener(new ActionListener() {
            /**

             Handles the action performed event.
             Retrieves the selected racer from the combo box.
             Retrieves the racer's name and number if available.
             Sets the corresponding combo box values based on the selected racer's description.
             Sets the text fields with the selected racer's name, maximum speed, and acceleration.
             @param e the ActionEvent to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<RacerInfo> source = (JComboBox<RacerInfo>) e.getSource();
                RacerInfo selectedRacer = (RacerInfo) source.getSelectedItem();


                String racerName = null;
                if (selectedRacer != null) {
                    racerName = selectedRacer.getName();
                }

                int racerNumber = 0;
                if (selectedRacer != null) {
                    racerNumber = selectedRacer.getNumber();
                }


             if(selectedRacer!=null){
                 if(selectedRacer.getRacer().describeRacer().contains("Airplane")){
                     comboBoxRacer.setSelectedIndex(0);
                 }
                 else if(selectedRacer.getRacer().describeRacer().contains("Helicopter")){
                     comboBoxRacer.setSelectedIndex(1);
                 }
                 else if(selectedRacer.getRacer().describeRacer().contains("Bicycle")){
                     comboBoxRacer.setSelectedIndex(2);
                 }
                 else if(selectedRacer.getRacer().describeRacer().contains("Car")){
                     comboBoxRacer.setSelectedIndex(3);
                 }
                 else if(selectedRacer.getRacer().describeRacer().contains("Horse")){
                     comboBoxRacer.setSelectedIndex(4);
                 }
                 else if(selectedRacer.getRacer().describeRacer().contains("RowBoat")){
                     comboBoxRacer.setSelectedIndex(5);
                 }
                 else if(selectedRacer.getRacer().describeRacer().contains("SpeedBoat")){
                     comboBoxRacer.setSelectedIndex(6);
                 }



                 if(selectedRacer.getRacer().getColor() == EnumContainer.Color.RED){
                     comboBoxRacerColor.setSelectedIndex(0);
                 }
                 else if(selectedRacer.getRacer().getColor() == EnumContainer.Color.GREEN){
                     comboBoxRacerColor.setSelectedIndex(1);
                 }
                 else if(selectedRacer.getRacer().getColor() == EnumContainer.Color.BLUE){
                     comboBoxRacerColor.setSelectedIndex(2);
                 }
                 else if(selectedRacer.getRacer().getColor() == EnumContainer.Color.BLACK){
                     comboBoxRacerColor.setSelectedIndex(3);
                 }
                 else if(selectedRacer.getRacer().getColor() == EnumContainer.Color.YELLOW){
                     comboBoxRacerColor.setSelectedIndex(4);
                 }
                 textFieldName.setText(selectedRacer.getRacer().getName());
                 textFieldMaxSpeed.setText(String.valueOf((int)selectedRacer.getRacer().getMaxSpeed()));
                 textFieldAcceleration.setText(String.valueOf((int)selectedRacer.getRacer().getAcceleration()));
             }




            }
        });
        CopyRacerBtn.addMouseListener(new MouseAdapter() {
            /**

             Handles the mouse press event.
             Retrieves the selected racer from the combo box.
             If no racer is selected, displays an error message and returns.
             Prints the selected racer.
             Checks if the race has already finished and displays a message.
             Checks if the race has already started and displays a message.
             Checks if the arena has been built and displays a message.
             Checks if the maximum number of racers has been reached and displays a message.
             Retrieves the name, maximum speed, and acceleration from the corresponding text fields.
             If the input values are invalid, displays an error message and returns.
             Retrieves the selected racer type and color from the combo boxes.
             Clones the selected racer and sets the color if available.
             Notifies property change listeners about the addition of the racer.
             Adds the racer to the arena, initializes the race, and adds it to the racers list.
             Adds the racer to the combo box as a new RacerInfo object.
             Adds the racer image file name to the racersImages list.
             Loads the racer image and increments the racer number.
             @param e the MouseEvent to be processed
             */
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                JComboBox<RacerInfo> comboBox = comboBoxRacers;
                RacerInfo selectedRacer = (RacerInfo) comboBox.getSelectedItem();

                if(comboBoxRacers.getSelectedItem()==null){
                    JOptionPane.showMessageDialog(race, "Choose Which Racer to Copy.");
                }
                else{

                    System.out.println(Objects.requireNonNull(comboBoxRacers.getSelectedItem()));


                    if (raceFinished) {
                        JOptionPane.showMessageDialog(race, "Race finished, Please make a new arena.");
                        return;
                    }
                    if (raceStarted) {
                        JOptionPane.showMessageDialog(race, "Race started, racers cannot be added now.");
                        return;
                    }
                    if (arena == null) {
                        JOptionPane.showMessageDialog(race, "Please build arena first!");
                        return;
                    }
                    if (racersNumber == maxRacers) {
                        JOptionPane.showMessageDialog(race, "No more racers can be added!");
                        return;
                    }
                    String name;
                    double maxSpeed;
                    double acceleration;

                    try {
                        name = textFieldName.getText();
                        maxSpeed = Double.parseDouble(textFieldMaxSpeed.getText());
                        acceleration = Double.parseDouble(textFieldAcceleration.getText());
                        if (name.isEmpty() || maxSpeed <= 0 || acceleration <= 0)
                            throw new Exception();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(race, "Invalid input values! Please try again.");
                        return;
                    }

                    String racerType = (String) comboBoxRacer.getSelectedItem();

                    String color = (String) comboBoxRacerColor.getSelectedItem();




                    try {
                        if (racerType != null) {
                            Racer racer = null;
                            if (selectedRacer != null) {
                                racer = selectedRacer.getRacer().clone();


                                if (color != null) {
                                    racer.setColor(EnumContainer.Color.valueOf(color.toUpperCase()));
                                }
                                firePropertyChange("racerAdded", null, racer);
                            }

                            arena.addRacer(racer);
                            arena.initRace();
                            racers.add(racer);


                            if (racer != null) {
                                comboBoxRacers.addItem(new RacerInfo(racer.getName(), racer.getSerialNumber(), racer));
                            }


                        }


                    } catch (RacerTypeException ex) {
                        JOptionPane.showMessageDialog(race, "racer don't belong to this arena, Choose another racer.");
                        return;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        System.out.println("Error Choosing Racer");
                    }


                    racersImages.add(Objects.requireNonNull(comboBoxRacer.getSelectedItem()) + Objects.requireNonNull(comboBoxRacerColor.getSelectedItem()).toString());
                    loadImageRacer(Objects.requireNonNull(comboBoxRacer.getSelectedItem()) + comboBoxRacerColor.getSelectedItem().toString(), racersNumber + 1);


                    racersNumber++;
                }

            }
        });
        CarRaceBuilderBtn.addMouseListener(new MouseAdapter() {
            /**

             Handles the mouse press event.
             Resets the race and retrieves the number of racers entered by the user.
             If the input is not a valid positive integer, displays an error message and returns.
             Checks if the race has already finished and resets it if necessary.
             Makes the race arena panels visible.
             Checks if the race has already started and is still in progress, displays a message and returns.
             Parses the arena length and maximum racers from the corresponding text fields.
             If the input values are invalid, displays an error message and returns.
             Loads the image for the land arena and builds the arena object using the builder.
             Notifies property change listeners about the completion of building the arena.
             Builds a default racer car object using the race builder.
             Adds the specified number of racers to the arena, initializes the race, and adds them to the racers list.
             Loads the racer image and increments the racer number.
             Prints the active racers in the arena.

             @param e the MouseEvent to be processed
             */
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                resetRace();
                int nRacers;

                String text = nRacersText.getText();
                if (!text.matches("\\d+")) {
                    JOptionPane.showMessageDialog(race, "Invalid input values! Please try again.");
                } else {

                    try {
                        nRacers = Integer.parseInt(text);
                        if(nRacers<=0 || nRacers >20){
                            JOptionPane.showMessageDialog(race, "Invalid input values! Please try again.");
                            return;
                        }

                        if (raceFinished) {


                            resetRace();
                        }


                        for (Component c : race.PanelArena.getComponents()) {
                            if ((c instanceof JPanel)) {
                                race.PanelArena.setVisible(true);
                            }
                        }
                        if (raceStarted && !raceFinished) {
                            JOptionPane.showMessageDialog(race, "Race started! Please wait.");
                            return;
                        }

                        try {
                            arenaLength = Integer.parseInt(textFieldArenaLeangth.getText());
                            maxRacers = Integer.parseInt(textFieldMaxRacers.getText());
                            if (arenaLength < 100 || arenaLength > 3000 || maxRacers <= 0 || maxRacers > 20)
                                throw new Exception();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(race, "Invalid input values! Please try again.");
                            return;
                        }

                        try {
                            loadImageArena("LandArena");
                            arena = builder.buildArena("game.arenas.land.LandArena", 1000, nRacers);


                            firePropertyChange("arenaBuilt", null, arena);
                        } catch (Exception ex) {
                            System.out.println("Error Choosing Arena");
                        }






                        RaceBuilder raceBuilder = RaceBuilder.getInstance();

                        Racer defaultRacerCar = raceBuilder.buildDefaultRacer("game.racers.land.Car");





                        for (int i = 0; i < nRacers; i++) {
                            Racer copyRacer = defaultRacerCar.clone();
                            arena.addRacer(copyRacer);
                            arena.initRace();
                            racers.add(copyRacer);

                            racersImages.add("CarRed" );
                            loadImageRacer("CarRed" , racersNumber + 1);
                            racersNumber++;

                        }
                        System.out.println(arena.getActiveRacers());


                    } catch (NumberFormatException | ClassNotFoundException | NoSuchMethodException |
                             InstantiationException | IllegalAccessException | InvocationTargetException |
                             RacerLimitException | RacerTypeException ee) {


                        ee.printStackTrace();

                    }

                }




            }
        });
    }


    /**

     Returns a list of all racers in the race, sorted according to their position in the ranking.

     The racers are collected from different categories: completed racers, active racers, broken racers, and disabled racers.

     The active racers are sorted based on their current location in the race track, in descending order.

     The position of each active racer is set accordingly.

     The final ranking consists of completed racers, sorted active racers, broken racers, and disabled racers in reverse order.

     @return an ArrayList of racers representing the overall ranking
     */
    public ArrayList<Racer> getRanking() {
        ArrayList<Racer> allRacers = new ArrayList<>();
        allRacers.addAll(arena.getCompleatedRacers());
        allRacers.addAll(arena.getActiveRacers());
        allRacers.addAll(arena.getBrokenRacers());
        allRacers.addAll(arena.getDisabledRacers());

        ArrayList<Racer> activeRacers = new ArrayList<>(arena.getActiveRacers());
        activeRacers.sort(Comparator.comparingDouble(racer -> racer.getCurrentLocation().getX()));
        Collections.reverse(activeRacers);

        for (int i = 0; i < activeRacers.size(); i++) {
            Racer racer = activeRacers.get(i);
            racer.setPosition(i + 1);
        }

        ArrayList<Racer> ranking = new ArrayList<>();
        ranking.addAll(arena.getCompleatedRacers());
        ranking.addAll(activeRacers);
        ranking.addAll(arena.getBrokenRacers());
        ArrayList<Racer> disabledRacers = new ArrayList<>(arena.getDisabledRacers());
        Collections.reverse(disabledRacers);
        ranking.addAll(disabledRacers);


        return ranking;
    }



    /**

     A static nested class representing information about a racer.

     It holds the racer's name, number, and a reference to the racer object.
     */

    static class RacerInfo {
        private final String name;
        private final int number;
        private Racer racer;
        /**

         Constructs a new RacerInfo object with the specified name, number, and racer.
         @param name the name of the racer
         @param number the number of the racer
         @param racer the racer object associated with this RacerInfo
         */
        public RacerInfo(String name, int number, Racer racer) {
            this.name = name;
            this.number = number;
            this.racer=racer;
        }
        /**


         @return the name of the racer
         */
        public String getName() {
            return name;
        }
        /**


         @return the number of the racer
         */
        public int getNumber() {
            return number;
        }
        /**


         @return a string representation of the RacerInfo object
         */
        @Override
        public String toString() {
            return "Name: " + name + ", ID: " + number;
        }
        /**


         @return the racer object associated with this RacerInfo
         */
        public Racer getRacer() {
            return racer;
        }
        /**
         Sets the racer object associated with this RacerInfo.
         @param racer the racer object to be set
         */
        public void setRacer(Racer racer) {
            this.racer = racer;
        }
    }

    /**
     The main entry point of the program.

     Creates a new instance of the Race class and sets up the GUI.

     Sets the content pane of the race frame, sets the title, size, and visibility of the frame.

     Sets the default close operation to exit the program when the frame is closed.

     Makes the panels in the race arena transparent.

     Sets the icon image of the frame using the provided image file path.

     Initializes the race object by invoking the init() method.

     @param args the command line arguments (not used)
     */
    public static  void  main(String[] args){

       race = new Race();

        ImageIcon icon = new ImageIcon("src/GUI/Race/Pictures/CarRed.png"); // replace with the path to your image file

        race.setContentPane(race.Panel1);
        race.setTitle("Hello Racers");
        race.setSize(1200,700);
        race.setVisible(true);
        race.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        for (Component c : race.PanelArena.getComponents()) {
            if (c instanceof JPanel) {
                ((JPanel) c).setOpaque(false);
            }
        }

        for(Component c : race.PanelArena.getComponents()) {

            if((c instanceof JPanel)) {
                race.PanelArena.setVisible(false);

            }
        }



        race.setIconImage(icon.getImage());
        race.init();


    }

    /**
     Initializes the object.
     Adds the current object as a property change listener to the builder.
     This allows the object to receive property change events from the builder and respond accordingly.
     */
    private void init() {
        builder.addPropertyChangeListener(this);
    }


    /**
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        switch (propertyName) {
            case "arenaBuilt" -> arena = (Arena) evt.getNewValue();
            case "racerAdded" -> {
                Racer racer = (Racer) evt.getNewValue();
                racers.add(racer);
            }
            case "raceStarted" -> {
                raceStarted = true;
                // Start the race thread here...
                startRaceThread();
            }
            case "raceFinished" -> {
                raceFinished = true;
                // Stop or interrupt the race thread here...
                stopRaceThread();
            }
        }
    }


    private Thread raceThread;
    /**

     Starts the race thread.
     The race thread is created and started, which runs in a loop until the race is finished.
     Inside the loop, it sleeps for 1000 milliseconds (1 second) and checks if the race has finished.
     If the thread is interrupted during sleep, the loop will be exited and the thread will stop.
     */
    private void startRaceThread() {
        raceThread = new Thread(() -> {

            while (!raceFinished) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                    break;
                }
            }


        });

        raceThread.start();
    }


    /**

     Stops the race thread if it is running.
     If the race thread is not null and still alive, it will be interrupted and the race thread variable will be reset to null.
     */
    private void stopRaceThread() {
        if (raceThread != null && raceThread.isAlive()) {
            raceThread.interrupt();
            raceThread = null;
        }
    }


    /**
     Resets the race by clearing all race-related data and resetting the race state.
     This method restores the initial state of the race, including race settings, racers, and the race arena.
     It also resets the serial number for racers and clears the racer selection ComboBox.
     */
    private void resetRace() {
        raceStarted = false;
        infoTable = null;
        arena = null;
        racersNumber = 0;
        racersImages = new ArrayList<>();
        racersJPanel = new ArrayList<>();
        racers = new ArrayList<>();
        builder = RaceBuilder.getInstance();
        comboBoxRacers.removeAllItems();
        Racer.setLastSerialNumber(1);
        for (Component c : race.PanelArena.getComponents()) {
            if (c instanceof JPanel) {
                c.setLocation(0, c.getY());
                ((JPanel) c).removeAll();
                ((JPanel) c).setOpaque(false);
            }
        }
        race.PanelArena.setVisible(false);
        race.PanelArena.revalidate();
        race.PanelArena.repaint();
        raceFinished = false;
    }




    /**
     Loads an image of an arena and displays it on the panel.

     @param ArenaType the type of the arena.
     This parameter should be a string representing the desired type of the arena.
     It should correspond to the filename of the image file without the extension. Example: "AerialArena".
     */
    private static void loadImageArena(String ArenaType) {

        JFrame frame = new JFrame("Your Frame Title");

        ImageIcon icon = new ImageIcon("src/GUI/Race/Pictures/"+ArenaType+".jpg");
        JLabel label = new JLabel();

        for(Component c : race.PanelArena.getComponents()) {
            if((c instanceof JLabel)) {
                race.PanelArena.remove(c);
            }
        }
        race.PanelArena.setLayout(null);
        label.setBounds(0, 0, race.PanelArena.getWidth(), race.PanelArena.getHeight());
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(scaledImage));


        race.PanelArena.add(label);
        race.PanelArena.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        race.PanelArena.revalidate();
        race.PanelArena.repaint();

    }


    /**
     * Loads an image of a racer and displays it on a specific panel.
     * @param Typeandcolor the type and color of racer This parameter should
     * be a string representing the desired type and color
     * of the racer. The format should be "type_color",
     * where "type" is the type of the racer and
     * "color" is the color of the racer. Example: "car_red".
     *
     * @param panelIndex panelIndex the index of the panel where the racer image should be displayed.
     * This parameter should be an integer indicating the index of the panel in the PANEL_RACERS array.
     *The index should be between 1 and 20 (inclusive).
     */
    private static void loadImageRacer(String Typeandcolor, int panelIndex) {

        JPanel[] PANEL_RACERS = {
                race.PanelRacer1, race.PanelRacer2, race.PanelRacer3, race.PanelRacer4,
                race.PanelRacer5, race.PanelRacer6, race.PanelRacer7, race.PanelRacer8,
                race.PanelRacer9, race.PanelRacer10, race.PanelRacer11, race.PanelRacer12,
                race.PanelRacer13, race.PanelRacer14, race.PanelRacer15, race.PanelRacer16,
                race.PanelRacer17, race.PanelRacer18, race.PanelRacer19, race.PanelRacer20
        };
        String imagePath = "src/GUI/Race/Pictures/" + Typeandcolor + ".png";
        ImageIcon icon = new ImageIcon(imagePath);
        JLabel label = new JLabel();
        JPanel panel = PANEL_RACERS[panelIndex - 1];
        racersJPanel.add(panel);


        panel.setLayout(null);
        label.setBounds(0, 0, panel.getWidth(), panel.getHeight());
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(scaledImage));
        panel.removeAll();
        panel.add(label);
        panel.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        panel.revalidate();
        panel.repaint();

    }




}
