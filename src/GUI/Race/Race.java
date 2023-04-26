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
    static Race race ;
    private static RaceBuilder builder = RaceBuilder.getInstance();
    private static ArrayList<Racer> racers;
    private int arenaLength = 1000;
    private int xPosition;
    ArrayList<String> racersImages = new ArrayList<String>();
    private int arenaHeight = 700;
    private int maxRacers = 8;
    private int racersNumber = 0;
    private Arena arena = null;
    private Timer timer;
    private boolean raceStarted = false;
    private JFrame infoTable = null;
    private boolean raceFinished = false;

    public Race() {

        buildArenaButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

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
                    if (chosenArena.equals("AerialArena")) {
                        arena = builder.buildArena("game.arenas.air.AerialArena", arenaLength, maxRacers);
                    } else if (chosenArena.equals("LandArena")) {
                        arena = builder.buildArena("game.arenas.land.LandArena", arenaLength, maxRacers);
                    } else if (chosenArena.equals("NavalArena")) {
                        arena = builder.buildArena("game.arenas.naval.NavalArena", arenaLength, maxRacers);
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
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
                racers = new ArrayList<>();
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
                utilities.EnumContainer.Color col = null;
                if (color.equals("Red"))
                    col = EnumContainer.Color.RED;
                else if (color.equals("Black"))
                    col = EnumContainer.Color.BLACK;
                else if (color.equals("Green"))
                    col = EnumContainer.Color.GREEN;
                else if (color.equals("Blue"))
                    col = EnumContainer.Color.BLUE;
                else if (color.equals("Yellow"))
                    col = EnumContainer.Color.YELLOW;

                String racerClass = null;
                if (racerType.equals("Helicopter"))
                    racerClass = "game.racers.air.Helicopter";
                else if (racerType.equals("Airplane"))
                    racerClass = "game.racers.air.Airplane";
                else if (racerType.equals("Car"))
                    racerClass = "game.racers.land.Car";
                else if (racerType.equals("Horse"))
                    racerClass = "game.racers.land.Horse";
                else if (racerType.equals("Bicycle"))
                    racerClass = "game.racers.land.Bicycle";
                else if (racerType.equals("SpeedBoat"))
                    racerClass = "game.racers.naval.SpeedBoat";
                else if (racerType.equals("RowBoat"))
                    racerClass = "game.racers.naval.RowBoat";

                try {
                    if(racerType.equals("Bicycle") || racerType.equals("Airplane") || racerType.equals("Car")){
                        Racer racer = builder.buildWheeledRacer(racerClass, name, maxSpeed, acceleration, col,2);
                        System.out.println("racer been added: " + racer.toString());
                        arena.addRacer(racer);
                        arena.initRace();
                        racers.add(racer);
                    }
                    else{
                        Racer racer = builder.buildRacer(racerClass, name, maxSpeed, acceleration, col);
                        System.out.println("racer been added: " + racer.toString());
                        arena.addRacer(racer);
                        arena.initRace();
                        racers.add(racer);
                    }


                } catch (RacerTypeException ex) {
                    JOptionPane.showMessageDialog(race, "racer don't belong to this arena, Choose another racer.");
                    return;
                } catch (Exception ex) {
                    System.out.println(ex);
                }



                racersImages.add(Objects.requireNonNull(comboBoxRacer.getSelectedItem()).toString()+ comboBoxRacerColor.getSelectedItem().toString());
                loadImageRacer(Objects.requireNonNull(comboBoxRacer.getSelectedItem()).toString()+ comboBoxRacerColor.getSelectedItem().toString(),racersNumber+1);




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
                    return;
                }
                else{



                    raceStarted = true;







                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            try {
                                arena.startRace();
                            } catch (InterruptedException ex) {
                                throw new RuntimeException(ex);
                            }
                            // Set the initial panel position and show the frame

                            setLocationRelativeTo(null);
                            setVisible(true);

                            raceStarted = true;
                            // Update the panel position in a loop with Thread.sleep()
                            new Thread(new Runnable() {
                                public void run() {
                                    try {
                                        while ((int) racers.get(0).getCurrentLocation().getX() <= getWidth()) {

                                            PanelRacer1.setLocation((int) racers.get(0).getCurrentLocation().getX() + 5, 0);
                                            PanelRacer1.repaint();
                                            System.out.println("Position: " + (int) racers.get(0).getCurrentLocation().getX());
                                            Thread.sleep(30);
                                        }
                                        raceFinished = true;
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();

                        }
                    });

//                    for (int i = 0; i < racersNumber; i++) {
//                        JLabel picLabel2 = new JLabel(racersImages[i]);
//                        if(racers.get(i)!=null){
//                            picLabel2.setLocation((int) racers.get(i).getLocation().getX() + 5,
//                                    (int) racers.get(i).getLocation().getY());
//                            picLabel2.setSize(70, 70);
//                            picLabel1.add(picLabel2);
//                        }
//
//                    }
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
                String[] columnNames = { "Racer name", "Current speed", "Max speed", "Current X location", "Finished" };
                String[][] data = new String[racersNumber][5];
                int i = 0;
                for (Racer r : arena.getCompleatedRacers()) {
                    data[i][0] = r.getName();
                    data[i][1] = "" + r.getCurrentSpeed();
                    data[i][2] = "" + r.getMaxSpeed();
                    data[i][3] = "" + r.getCurrentLocation().getX();
                    data[i][4] = "COMP";
                    i++;
                }

                for (Racer r : arena.getActiveRacers()) {
                    data[i][0] = r.getName();
                    data[i][1] = "" + r.getCurrentSpeed();
                    data[i][2] = "" + r.getMaxSpeed();
                    data[i][3] = "" + r.getCurrentLocation().getX();
                    data[i][4] = "ACT";
                    i++;
                }
                for (Racer r : arena.getBrokenRacers()) {
                    data[i][0] = r.getName();
                    data[i][1] = "" + r.getCurrentSpeed();
                    data[i][2] = "" + r.getMaxSpeed();
                    data[i][3] = "" + r.getCurrentLocation().getX();
                    data[i][4] = "BROK";
                    i++;
                }
                for (Racer r : arena.getDisabledRacers()) {
                    data[i][0] = r.getName();
                    data[i][1] = "" + r.getCurrentSpeed();
                    data[i][2] = "" + r.getMaxSpeed();
                    data[i][3] = "" + r.getCurrentLocation().getX();
                    data[i][4] = "DIS";
                    i++;
                }

                JTable table = new JTable(data, columnNames);
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
        race.setSize(1500,1000);
        race.setVisible(true);
        race.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        race.setIconImage(icon.getImage());





    }


    private static void loadImageArena(String ArenaType) {
        ImageIcon icon = new ImageIcon("src/GUI/Race/Pictures/"+ArenaType+".jpg");
        JLabel label = new JLabel();
        // Set the layout of PanelRace to null
        race.PanelArena.setLayout(null);
        // Set the bounds of the JLabel to cover the entire PanelArena
        label.setBounds(0, 0, race.PanelArena.getWidth(), race.PanelArena.getHeight());
        // Get the Image object from the ImageIcon
        Image image = icon.getImage();
        // Scale the Image to fit the size of the JLabel
        Image scaledImage = image.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        // Set the scaled Image as the Icon for the JLabel
        label.setIcon(new ImageIcon(scaledImage));


        // Add the JLabel object to the existing JPanel using the add() method
        race.PanelArena.add(label);
        // Set the size of PanelArena to match the size of the image
        race.PanelArena.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
       // race.PanelArena.setComponentZOrder(label, 1);
        race.PanelArena.revalidate();
        race.PanelArena.repaint();
        System.out.println("label size: " + label.getSize());
        System.out.println("PanelArena size: " + race.PanelArena.getSize());
        System.out.println("Parent container visibility: " + race.PanelArena.getParent().isVisible());
    }

    private static void loadImageRacer(String Typeandcolor, int panelIndex) {
        String imagePath = "src/GUI/Race/Pictures/" + Typeandcolor + ".png";
        ImageIcon icon = new ImageIcon(imagePath);
        JLabel label = new JLabel();
        JPanel panel = null;
        // Determine which panel to update based on the panelIndex argument
        switch (panelIndex) {
            case 1:
                panel = race.PanelRacer1;
                break;
            case 2:
                panel = race.PanelRacer2;
                break;
            case 3:
                panel = race.PanelRacer3;
                break;
            case 4:
                panel = race.PanelRacer4;
                break;
            case 5:
                panel = race.PanelRacer5;
                break;
            case 6:
                panel = race.PanelRacer6;
                break;
            case 7:
                panel = race.PanelRacer7;
                break;
            case 8:
                panel = race.PanelRacer8;
                break;
            case 9:
                panel = race.PanelRacer9;
                break;
            case 10:
                panel = race.PanelRacer10;
                break;
            case 11:
                panel = race.PanelRacer11;
                break;
            case 12:
                panel = race.PanelRacer12;
                break;
            case 13:
                panel = race.PanelRacer13;
                break;
            case 14:
                panel = race.PanelRacer14;
                break;
            case 15:
                panel = race.PanelRacer15;
                break;
            case 16:
                panel = race.PanelRacer16;
                break;
            case 17:
                panel = race.PanelRacer17;
                break;
            case 18:
                panel = race.PanelRacer18;
                break;
            case 19:
                panel = race.PanelRacer19;
                break;
            case 20:
                panel = race.PanelRacer20;
                break;


        }
        // Set the layout of the panel to null
        assert panel != null;
        panel.setLayout(null);
        // Set the bounds of the JLabel to cover the entire panel
        label.setBounds(0, 0, panel.getWidth(), panel.getHeight());
        // Get the Image object from the ImageIcon
        Image image = icon.getImage();
        // Scale the Image to fit the size of the JLabel
        Image scaledImage = image.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        // Set the scaled Image as the Icon for the JLabel
        label.setIcon(new ImageIcon(scaledImage));
        // Add the JLabel object to the panel using the add() method
        panel.removeAll();
        panel.add(label);
        // Set the size of the panel to match the size of the image
        panel.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        //panel.setComponentZOrder(label, 0);

        panel.revalidate();
        panel.repaint();
        System.out.println("label size: " + label.getSize());
        System.out.println("panel size: " + panel.getSize());
        System.out.println("Parent container visibility: " + panel.getParent().isVisible());
    }


    public static void MoveRacers(){



        race.PanelRacer1.setLocation(new Point(race.PanelRacer1.getX()+200, race.PanelRacer1.getY()));


    }


}
