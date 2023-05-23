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
                    while (true) {
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

                            if (completedRacers.contains(racer)) {
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

                        if (raceFinished && lastUpdate[0]) {
                            break;
                        }
                    }
                });

                infoThread.setDaemon(true);
                infoThread.start();
            }
        });

        comboBoxRacers.addActionListener(new ActionListener() {
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


                System.out.println("Selected Racer: " + racerName + " (Number: " + racerNumber + ")");

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
                            if (racerType.equals("Bicycle") || racerType.equals("Airplane") || racerType.equals("Car")) {
                                if (selectedRacer != null) {
                                    racer = builder.copyWheeledRacer(selectedRacer.getRacer());
                                    if (color != null) {
                                        racer.setColor(EnumContainer.Color.valueOf(color.toUpperCase()));
                                    }
                                    firePropertyChange("racerAdded", null, racer);
                                }

                            } else {
                                if (selectedRacer != null) {
                                    racer = builder.copyRacer(selectedRacer.getRacer());
                                    if (color != null) {
                                        racer.setColor(EnumContainer.Color.valueOf(color.toUpperCase()));
                                    }
                                    firePropertyChange("racerAdded", null, racer);
                                }

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
             * @param e the event to be processed
             */
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                resetRace();
                int nRacers=0;

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
                        System.out.println(defaultRacerCar.describeRacer());
                        for (int i = 0; i < nRacers; i++) {
                            Racer copyRacer = raceBuilder.copyWheeledRacer(defaultRacerCar);
                            arena.addRacer(copyRacer);
                            arena.initRace();
                            racers.add(copyRacer);

                            racersImages.add("CarRed" );
                            loadImageRacer("CarRed" , racersNumber + 1);
                            racersNumber++;

                        }


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
     * @return all racers
     */
        public ArrayList<Racer> getRanking() {
        ArrayList<Racer> allRacers = new ArrayList<>();
        allRacers.addAll(arena.getCompleatedRacers());
        allRacers.addAll(arena.getActiveRacers());
        allRacers.addAll(arena.getBrokenRacers());
        allRacers.addAll(arena.getDisabledRacers());

        allRacers.sort(Comparator.comparingDouble(racer -> racer.getCurrentLocation().getX()));
        Collections.reverse(allRacers);

        for (int i = 0; i < allRacers.size(); i++) {
            Racer racer = allRacers.get(i);
            racer.setPosition(i + 1);
        }

        return allRacers;
    }


    static class RacerInfo {
        private final String name;
        private final int number;
        private Racer racer;

        public RacerInfo(String name, int number, Racer racer) {
            this.name = name;
            this.number = number;
            this.racer=racer;
        }

        public String getName() {
            return name;
        }

        public int getNumber() {
            return number;
        }
        @Override
        public String toString() {
            return "Name: " + name + ", ID: " + number;
        }

        public Racer getRacer() {
            return racer;
        }

        public void setRacer(Racer racer) {
            this.racer = racer;
        }
    }


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

        raceThread.start();  // Start the race thread
    }

    private void stopRaceThread() {
        if (raceThread != null && raceThread.isAlive()) {
            raceThread.interrupt();  // Interrupt the race thread
            raceThread = null;  // Reset the race thread variable
        }
    }


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
     * @param ArenaType which arena to put in the panel arena
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
     * @param Typeandcolor the type and color of racer
     * @param panelIndex the index of the panel racer
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
