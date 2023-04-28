package GUI.Race;

import factory.RaceBuilder;
import game.arenas.Arena;
import game.arenas.exceptions.RacerTypeException;
import game.racers.Racer;
import utilities.EnumContainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Objects;
/** @author Or Hava 208418483
 */
public class Race extends JFrame {
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
    private static Race race ;
    private static final RaceBuilder builder = RaceBuilder.getInstance();
    private static final ArrayList<Racer> racers = new ArrayList<>();
    private int arenaLength = 1000;

    private final ArrayList<String> racersImages = new ArrayList<>();
    private static final ArrayList<JPanel> racersJPanel = new ArrayList<>();
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

                for(Component c : race.PanelArena.getComponents()) {
                    if((c instanceof JPanel)) {
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
                        if(racerType.equals("Bicycle") || racerType.equals("Airplane") || racerType.equals("Car")){
                            racer = builder.buildWheeledRacer(racerClass, name, maxSpeed, acceleration, col, 2);

                        }
                        else{
                            racer = builder.buildRacer(racerClass, name, maxSpeed, acceleration, col);

                        }

                        arena.addRacer(racer);
                        arena.initRace();
                        racers.add(racer);
                    }


                } catch (RacerTypeException ex) {
                    JOptionPane.showMessageDialog(race, "racer don't belong to this arena, Choose another racer.");
                    return;
                } catch (Exception ex) {
                    System.out.println("Error Choosing Racer");
                }



                racersImages.add(Objects.requireNonNull(comboBoxRacer.getSelectedItem()) + Objects.requireNonNull(comboBoxRacerColor.getSelectedItem()).toString());
                loadImageRacer(Objects.requireNonNull(comboBoxRacer.getSelectedItem()) + comboBoxRacerColor.getSelectedItem().toString(),racersNumber+1);




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
                }
                else{



                    int arenaWidth = (int) arena.getLength();
                    int racerPanelWidth = racersJPanel.get(0).getWidth();
                    int startingX = (arenaWidth - racerPanelWidth) / 2;
                    for (JPanel jPanel : racersJPanel) {
                        jPanel.setLocation(startingX, jPanel.getY());
                    }

                    setLocationRelativeTo(null);
                    setVisible(true);
                    raceStarted = true;

                    Thread raceThread = new Thread(() -> {
                        int panelArenaLength = race.PanelArena.getWidth()-race.PanelRacer1.getWidth();
                        int arenaLength = (int) arena.getLength();
                        double ratio = (double) panelArenaLength / (double) arenaLength;
                        Timer timer = new Timer(30, new ActionListener() {
                            int i = 0;

                            @Override
                            public void actionPerformed(ActionEvent e1) {
                                i=0;
                                for (int i = 0; i < racersJPanel.size(); i++) {
                                    JPanel jPanel = racersJPanel.get(i);
                                    jPanel.setLocation((int) (racers.get(i).getCurrentLocation().getX() * ratio), jPanel.getY());
                                    jPanel.repaint();
                                    System.out.println("ratio: " + (int) ratio);
                                    System.out.println("getCurrentLocation: " + (int) (racers.get(i).getCurrentLocation().getX() * ratio));
                                    System.out.println("racer place: " + (int) (racers.get(i).getCurrentLocation().getX() * ratio) + " index: "+ i);
                                }

                                if (arena.getCompleatedRacers().size() + arena.getDisabledRacers().size() == racers.size()) {
                                    ((Timer) e1.getSource()).stop();

                                    for (int i = 0; i < racersJPanel.size(); i++) {
                                        JPanel jPanel = racersJPanel.get(i);
                                        jPanel.setLocation((int) (racers.get(i).getCurrentLocation().getX() * ratio), jPanel.getY());
                                        jPanel.repaint();
                                    }

                                    raceFinished = true;
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
                String[] DataNames = { "Racer name", "Current speed", "Max speed", "Current X location", "Finished" };
                String[][] Data = new String[racersNumber][5];
                int i = 0;
                for (Racer racer : arena.getCompleatedRacers()) {
                    Data[i][0] = racer.getName();
                    Data[i][1] = "" + racer.getCurrentSpeed();
                    Data[i][2] = "" + racer.getMaxSpeed();
                    Data[i][3] = "" + racer.getCurrentLocation().getX();
                    Data[i][4] = "Yes";
                    i++;
                }

                for (Racer racer : arena.getActiveRacers()) {
                    Data[i][0] = racer.getName();
                    Data[i][1] = "" + racer.getCurrentSpeed();
                    Data[i][2] = "" + racer.getMaxSpeed();
                    Data[i][3] = "" + racer.getCurrentLocation().getX();
                    Data[i][4] = "No";
                    i++;
                }
                for (Racer racer : arena.getBrokenRacers()) {
                    Data[i][0] = racer.getName();
                    Data[i][1] = "" + racer.getCurrentSpeed();
                    Data[i][2] = "" + racer.getMaxSpeed();
                    Data[i][3] = "" + racer.getCurrentLocation().getX();
                    Data[i][4] = "No";
                    i++;
                }
                for (Racer racer : arena.getDisabledRacers()) {
                    Data[i][0] = racer.getName();
                    Data[i][1] = "" + racer.getCurrentSpeed();
                    Data[i][2] = "" + racer.getMaxSpeed();
                    Data[i][3] = "" + racer.getCurrentLocation().getX();
                    Data[i][4] = "No";
                    i++;
                }

                JTable table = new JTable(Data, DataNames);
                table.setPreferredScrollableViewportSize(table.getPreferredSize());
                JScrollPane scrollPane = new JScrollPane(table);

                JPanel tabPan = new JPanel();
                // tabPan.setLayout(new GridLayout(1,0));
                tabPan.add(scrollPane);

                if (infoTable != null)
                    infoTable.dispose();
                infoTable = new JFrame("Racers information");
                infoTable.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                infoTable.setContentPane(tabPan);
                infoTable.pack();
                infoTable.setVisible(true);
            }
        });
    }

    public static  void  main(String[] args){

       race = new Race();

        ImageIcon icon = new ImageIcon("src\\GUI\\Race\\Pictures\\AerialArena.jpg"); // replace with the path to your image file

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





    }


    /**
     * @param ArenaType which arena to put in the panel arena
     */
    private static void loadImageArena(String ArenaType) {
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
