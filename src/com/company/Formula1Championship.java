package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class Formula1Championship extends JFrame implements ChampionshipManager, ActionListener {                       //class implements Jframe which allows to create a GUI and import methods from Championship manager and adds a actionlistner which allows a button input

    JLabel dateLabel = new JLabel("");                                                                             //creates a label for the date to be displayed.
    JFrame frame;
    JPanel panel, buttonPanel, searchPanel, tabelPanel;                                                                 //Creates different panels.
    JButton searchButton, raceButton, ascOrder, desOrder, fpos, resetButton, raceHist, boostRace;                       //creates different buttons.
    JTable table;                                                                                                       //creates a table
    TextField sBar;                                                                                                     //creates a search bar display
    File driverInfo = new File("driverInfo.txt");                                                              //creates a file where the data can be saved and edited
    File dataReset = new File("resetData.txt");                                                                //creates a read only file where when the user resets it reads from this file and saves in the driverinfo file.
    JLabel[] finalPosLabel = new JLabel[10];                                                                            //creates a array of the drivers labels and sets the value to 10.
    private ArrayList<Formula1Driver> driverDet = new ArrayList<>();                                                    //creates a arraylist where it saves the data about the drivers in.
    String dates[] = {"05/12/2021", "12/11/2021", "19/12/2021", "26/12/2021", "02/01/2022", "09/01/2022", "16/01/2022", "23/01/2022", "30/01/2022", "06/02/2022"};               //creates an array of the different dates when the race will occur.
    int raceTracker = 0;                                                                                                                                                         //creates a global variable where it creates a global variable which is set to 0



    public static void main(String[] args) throws IOException {
        new Formula1Championship();                                                                                     //runs this method when the main is run.


    }

    public Formula1Championship() throws IOException {

        frame = new JFrame("Formula1Driver");                                                                      //creates a new frame for gui
        frame.setSize(1400, 800);                                                                          //adjusts the height of the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                                                           //closes the menu when pressed X
        frame.setResizable(false);                                                                                      //does not allow the user to resize the table
        frame.setLayout(null);                                                                                          //sets the automatic panel layout to null and allows me to set the panels manually.

        //yellow panel - need to add a search and reset button

        sBar = new TextField("");                                                                                       //creates a new text-field for search bar and sets the text to null or blank.
        sBar.setPreferredSize(new Dimension(100, 20));                                                     //sets the search bars size
        sBar.setEditable(true);                                                                                         //lets the user add text to the search bar.
//        String usrInput = sBar.getText();
//        sBar.addActionListener(this);

        panel = new JPanel();                                                                                           //creates a new panel and changes the layout size and colour in the following lines.
        frame.add(panel);
        panel.add(sBar);
        panel.setBackground(Color.black);
        panel.setBounds(0, 0, 1400, 80);


        searchButton = new JButton("Search");                                                                      //creates a button for searching the driver info
        searchButton.addActionListener(this);                                                                        //gets the button input
        panel.add(searchButton);
        resetButton = new JButton("Reset data");
        panel.add(resetButton);
        resetButton.addActionListener(this);

        //orange panel with button - needs no buttons

        buttonPanel = new JPanel();                                                                                     //creates a new panel.
        frame.add(buttonPanel);
        buttonPanel.setBounds(0, 80, 500, 800);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        //Jlabel for date

        dateLabel.setText("");
        buttonPanel.add(dateLabel);
        dateLabel.setText("");

        for (int i = 0; i < finalPosLabel.length; i++) {                                                                //populates the label with driver positions
            finalPosLabel[i] = new JLabel();
            buttonPanel.add(finalPosLabel[i]);
        }


        searchPanel = new JPanel();                                                                                     //creates a new panel
        frame.add(searchPanel);
        searchPanel.setBounds(500, 700, 900, 80);
        searchPanel.setBackground(Color.black);


        raceButton = new JButton("Race");                                                                          //creates a new button
        raceButton.setPreferredSize(new Dimension(80, 40));                                                //sets the button height and width
        searchPanel.add(raceButton);                                                                                    //adds the button to panel.
        raceButton.addActionListener(this);


        ascOrder = new JButton("Sort by Descending order");
        ascOrder.setPreferredSize(new Dimension(200, 40));
        searchPanel.add(ascOrder);
        ascOrder.addActionListener(this);


        desOrder = new JButton("Sort by Ascending order");
        desOrder.setPreferredSize(new Dimension(200, 40));
        searchPanel.add(desOrder);
        desOrder.addActionListener(this);

        fpos = new JButton("Sort by First Position");
        fpos.setPreferredSize(new Dimension(200, 40));
        searchPanel.add(fpos);
        fpos.addActionListener(this);

        raceHist = new JButton("Race History");
        raceHist.setPreferredSize(new Dimension(100, 40));
        searchPanel.add(raceHist);
        raceHist.addActionListener(this);


        //this is for the table - no buttons needed

        tabelPanel = new JPanel();                                                                                      //creates a new panel
        frame.add(tabelPanel);                                                                                          //adds the panel to the frame

        //boosted race button

        boostRace = new JButton("Boosted Race");
        tabelPanel.add(boostRace);
        boostRace.setLayout(null);
        boostRace.setBounds(350, 100, 180, 30);
        boostRace.setBackground(Color.green);
        boostRace.setOpaque(true);
        boostRace.addActionListener(this);

        //tablePanel layout

        tabelPanel.setBounds(500, 80, 900, 620);                                                     //creates the new table and sets it layout and background.
        tabelPanel.setBackground(new Color(191, 255, 252));
        tabelPanel.setLayout(null);

        //jTable code       //        https://www.youtube.com/watch?v=wniqpx8OQxo . initialising j table was done using this video.
        Object[][] info = {{"", "", "", "", "", "", "", "", ""},                                                        //creates the different number of rows to add the data from the saved data.
                {"", "", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", "", ""}};


        String[] names = {"First Name",                                                                                 //creates the labels for the
                "Last Name",
                "Country",
                "Team",
                "First Position",
                "Second Position",
                "Third Position",
                "Race Counts",
                "Total Points"};




        table = new JTable(info, names);                                                                                //sets the data into the table which I have set above
        table.setPreferredScrollableViewportSize(new Dimension(900, 180));
//        table.setFillsViewportHeight(true);
        table.setBackground(Color.pink);                                                                                //sets the tables background colour.
        table.setFont(new Font("Serif", Font.CENTER_BASELINE, 11));                                          //sets the font size
        table.setEnabled(false);                                                                                        //does not allow the user to edit the data from gui.
        table.setRowHeight(30);                                                                                         //sets the users row height.


        //scroll pane
        JScrollPane sPane = new JScrollPane(table);
        tabelPanel.add(sPane);                                                                                          //adds the table panel to scroll pane
        sPane.setBounds(0, 140, 900, 320);
        frame.setVisible(true);                                                                                         //makes the frame visible to the user
        tableFill();                                                                                                    //calls in theses two methods when the program has been launched
        load();
    }

    @Override
    public void actionPerformed(ActionEvent e) {                                                                        //checks when a button is pressed and runs the method which is associated with that button.
        if (e.getSource() == raceButton) {
            try {
                launchRace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == resetButton) {
            try {
                resetValues();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == ascOrder) {
            try {
                ascOrder();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == desOrder) {
            try {
                decOrder();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == fpos) {
            try {
                fposCount();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == searchButton) {
            srchBar();
        } else if (e.getSource() == raceHist) {
            raceHistory();
        } else if (e.getSource() == boostRace) {
            try {
                boostRace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

    }


    @Override
    public void tableFill() throws FileNotFoundException {                                                              //fills the table in with the data which is saved in text file.
        Scanner dScanner = new Scanner(driverInfo);
        for (int x = 0; x < 10; x++) {
            for (int i = 0; i < 9; i++) {
                table.setValueAt(dScanner.nextLine(), x, i);
            }
        }

    }

    @Override
    public void load() throws FileNotFoundException {                                                                   //loads the data from the text file and sets to the program as it goes through the text file one by one.
        Scanner input = new Scanner(driverInfo);

        while (input.hasNext()) {

            driverDet.add(new Formula1Driver(input.nextLine(), input.nextLine(), input.nextLine(), input.nextLine(), Integer.parseInt(input.nextLine()), Integer.parseInt(input.nextLine()), Integer.parseInt(input.nextLine()), Integer.parseInt(input.nextLine()), Integer.parseInt(input.nextLine())));

        }
        System.out.println("Loaded");

    }

    @Override
    public void launchRace() throws IOException {                                                                       //this method when pressed launches the race
        ArrayList<Integer> driPosition = new ArrayList<>();
        int x = 0;

        if (driverDet.get(x).getRacesCount() < 10) {
            for (int i = 0; i < 10; i++) {
                finalPosLabel[i].setText("");
            }
            Collections.shuffle(driPosition);
            for (int i = 0; i < 10; i++) {
                driPosition.add(i);
            }
            Collections.shuffle(driPosition);
            for (int finalPos = 0; finalPos < driverDet.size(); finalPos++) {
//
                switch (finalPos) {                                                                                     //a switch case which checks all the drivers final positions, and according to it sets them to different cases
                    case 0:                                                                                             //the 1st position in the race
                        driverDet.get(driPosition.get(finalPos)).setfPosCount(1);                                       //adds 1 to the first position counter.
                        driverDet.get(driPosition.get(finalPos)).setRacesCount(1);                                      //adds 1 to the race counter.
                        driverDet.get(driPosition.get(finalPos)).setTotPoints(25);                                      //adds 25 points to the total points to the driver who finished first.
                        break;
                    case 1:                                                                                             //the second position
                        driverDet.get(driPosition.get(finalPos)).setsPosCount(1);                                       //adds 1 point to second position counter
                        driverDet.get(driPosition.get(finalPos)).setRacesCount(1);                                      //adds 1 point to the race counter.
                        driverDet.get(driPosition.get(finalPos)).setTotPoints(18);                                      //adds 18 points to the total points to the driver who finished second
                        break;
                    case 2:                                                                                             //the third position
                        driverDet.get(driPosition.get(finalPos)).settCount(1);                                          //adds 1 point to third position counter
                        driverDet.get(driPosition.get(finalPos)).setRacesCount(1);                                      //adds 1 point to the race counter.
                        driverDet.get(driPosition.get(finalPos)).setTotPoints(15);                                      //adds 15 points to the total points to the driver who finished second.
                        break;
                    case 3:                                                                                             //the fourth position
                        driverDet.get(driPosition.get(finalPos)).setRacesCount(1);                                      //adds 1 point to the race counter.
                        driverDet.get(driPosition.get(finalPos)).setTotPoints(12);                                      //adds 12 points to the total points.
                        break;
                    case 4:                                                                                             //the 5th position
                        driverDet.get(driPosition.get(finalPos)).setRacesCount(1);                                      //adds 1 point to the race counter.
                        driverDet.get(driPosition.get(finalPos)).setTotPoints(10);                                      //adds 10 points to the total points.
                        break;
                    case 5:                                                                                             //the 6th position
                        driverDet.get(driPosition.get(finalPos)).setRacesCount(1);                                      //adds 1 point to the race counter.
                        driverDet.get(driPosition.get(finalPos)).setTotPoints(8);                                       //adds 8 points to the total points.
                        break;
                    case 6:                                                                                             //the 7th position
                        driverDet.get(driPosition.get(finalPos)).setRacesCount(1);                                      //adds 1 point to the race counter.
                        driverDet.get(driPosition.get(finalPos)).setTotPoints(6);                                       //adds 6 points to the total points.
                        break;
                    case 7:                                                                                             //the 8th position
                        driverDet.get(driPosition.get(finalPos)).setRacesCount(1);                                      //adds 1 point to the race counter.
                        driverDet.get(driPosition.get(finalPos)).setTotPoints(4);                                       //adds 4 points to the total points.
                        break;
                    case 8:                                                                                             //the 9th position
                        driverDet.get(driPosition.get(finalPos)).setRacesCount(1);                                      //adds 1 point to the race counter.
                        driverDet.get(driPosition.get(finalPos)).setTotPoints(2);                                       //adds 2 points to the total points.
                        break;
                    case 9:                                                                                             //the 10th position
                        driverDet.get(driPosition.get(finalPos)).setRacesCount(1);                                      //adds 1 point to the race counter.
                        driverDet.get(driPosition.get(finalPos)).setTotPoints(1);                                       //adds 1 points to the total points.
                        break;

                }

                finalPosLabel[finalPos].setText("<html>" + "<br>" + "Driver Name:" + driverDet.get(driPosition.get(finalPos)).getFname() + " " + driverDet.get(driPosition.get(finalPos)).getLname()
                        + "<br>" + "Driver Country: " + driverDet.get(driPosition.get(finalPos)).getDlocation()
                        + "<br>" + "Total Points: " + driverDet.get(driPosition.get(finalPos)).getTotPoints());         //adds the driver details to the label after the race has been launched.


            }
            dateLabel.setText(" Date: " + dates[raceTracker]);                                                          //adds the date along with the race.
            raceTracker++;                                                                                              //everytime the user races the counter is incremented by 1.
            System.out.println("race has been launched");                                                               //prints the race has been launched to let the user know the race has been launched on console.
            saveData();
            tableFill();
            ascOrder();
        } else {
            JOptionPane.showMessageDialog(null, "Maximum race limit reached", "Race Completed", JOptionPane.INFORMATION_MESSAGE);

        }
    }

    @Override
    public void saveData() throws IOException {                                                                         //saved the current data to the txt file
        try (PrintWriter sDetails = new PrintWriter(new FileWriter("driverInfo.txt"))) {                       //location of where the data will be saved.
            int x;
            PrintWriter track = new PrintWriter(new FileWriter("raceTracker.txt"));                            //the race tracter to keep track of which race the user is on.
            track.println(Integer.toString(raceTracker));
            track.close();

            for (x = 0; x < driverDet.size(); x++) {                                                                    //goes through the txt file line by line and retrieves the following data
                sDetails.println(driverDet.get(x).getFname());
                sDetails.println(driverDet.get(x).getLname());
                sDetails.println(driverDet.get(x).getDlocation());
                sDetails.println(driverDet.get(x).getDteam());
                sDetails.println(driverDet.get(x).getfPosCount());
                sDetails.println(driverDet.get(x).getsPosCount());
                sDetails.println(driverDet.get(x).gettCount());
                sDetails.println(driverDet.get(x).getRacesCount());
                sDetails.println(driverDet.get(x).getTotPoints());

            }
        }
    }

    @Override                                                                                                           //this retrieves the data which was set to the backup file and clears anything which was saved in the arraylist and the read and write txt file.
    public void resetValues() throws IOException {

        //enter reset data when a button is clicked

        FileReader fixedData = null;
        FileWriter writeData = null;
        try {
            fixedData = new FileReader((dataReset));
            writeData = new FileWriter((driverInfo));
            int ch;
            while ((ch = fixedData.read()) != -1) {
                writeData.write(ch);
            }
        } catch (Exception e) {

        } finally {
            fixedData.close();
            writeData.close();
        }
        for (int i = 0; i < 10; i++) {
            finalPosLabel[i].setText("");
        }
        dateLabel.setText("");
        raceTracker = 0;
        driverDet.clear();
        load();
        tableFill();
        JOptionPane.showMessageDialog(null, "You have cleared the previous saved data", "Data Reset", JOptionPane.INFORMATION_MESSAGE);    //prints a message which lets the user know the data has been reset


    }

    @Override
    public void ascOrder() throws IOException {                                                                         //sorts the driver by descending order
        Collections.sort(driverDet, new Comparator<Formula1Driver>() {
            @Override
            public int compare(Formula1Driver Driver1, Formula1Driver Driver2) {
                if (Driver2.getTotPoints() == Driver1.getTotPoints()) {
                    return Driver2.getfPosCount() - Driver1.getfPosCount();                                             //if the total points are equal it will check if the driver has a first position win and sort it according to that.

                } else {
                    return Driver2.getTotPoints() - Driver1.getTotPoints();
                }

            }

        });
        saveData();                                                                                                     //calls the saveData and tableFill method.
        tableFill();

    }

    @Override                                                                                                           //sorts the drives by ascending order based by their total points.
    public void decOrder() throws IOException {
        Collections.sort(driverDet, new Comparator<Formula1Driver>() {
            @Override
            public int compare(Formula1Driver Driver1, Formula1Driver Driver2) {
                if (Driver2.getTotPoints() == Driver1.getTotPoints()) {
                    return Driver2.getfPosCount() - Driver1.getfPosCount();

                } else {
                    return Driver1.getTotPoints() - Driver2.getTotPoints();
                }

            }

        });
        saveData();
        tableFill();

    }

    @Override
    public void fposCount() throws IOException {                                                                        //sorts out the user by first position wins.
        Collections.sort(driverDet, new Comparator<Formula1Driver>() {
            @Override
            public int compare(Formula1Driver Driver1, Formula1Driver Driver2) {
                if (Driver2.getfPosCount() == Driver1.getfPosCount()) {
                    return Driver2.getTotPoints() - Driver1.getTotPoints();

                } else {
                    return Driver2.getfPosCount() - Driver1.getfPosCount();
                }

            }

        });
        saveData();
        tableFill();

    }

    @Override                                                                                                           //method for search bar which contains no code.
    public void srchBar() {


    }


    @Override
    public void raceHistory() {                                                                                         //when clicked the user can see the race dates race number of when and which dates the race took place.
        dateLabel.setText("");
        for (int i = 0; i < finalPosLabel.length; i++) {
            finalPosLabel[i].setText("");
        }
        for (int i = 0; i < raceTracker; i++) {
            finalPosLabel[i].setText("<html>" + dates[i] + " " + "Race" + " "+(i+1)+ "<br>"+"<br>");                    //print the dates.
        }
    }
//https://stackoverflow.com/questions/9330394/how-to-pick-an-item-by-its-probability - for the probability i referenced to this link.
    @Override
    public void boostRace() throws IOException {
        ArrayList<Formula1Driver> startPos = new ArrayList<>();

        ArrayList<Integer> driPosition = new ArrayList<>();

        int x = 0;

        if (driverDet.get(x).getRacesCount() < 10) {

        Collections.shuffle(driPosition);

        for (int i = 0; i < 10; i++) {

            driPosition.add(i);
        }
        for (int i = 0; i < 10; i++) {

            startPos.add(driverDet.get(driPosition.get(i)));
        }
            for (int i = 0; i < 10; i++) {

                finalPosLabel[i].setText("");
            }
            Random random = new Random();
            double randomNumber = Math.random();
            randomNumber = Math.round(randomNumber * 100.0) / 100.0;
            //40% chance
        if (randomNumber > 0.6 && randomNumber <= 1.0) {
            finalPosLabel[0].setText("<html>" + "<br>" + "Driver Name:" + startPos.get(0).getFname() + " " + startPos.get(0).getLname()
                    + "<br>" + "Driver Country: " + startPos.get(0).getDlocation());
            startPos.get(0).setfPosCount(1);
            startPos.get(0).setRacesCount(1);
            startPos.get(0).setTotPoints(25);
            startPos.remove(0);
        }
        //30% chance
        if (randomNumber > 0.3 && randomNumber <= 0.6) {
            finalPosLabel[1].setText("<html>" + "<br>" + "Driver Name:" + startPos.get(1).getFname() + " " + startPos.get(1).getLname()
                    + "<br>" + "Driver Country: " + startPos.get(1).getDlocation());
            startPos.get(1).setfPosCount(1);
            startPos.get(1).setRacesCount(1);
            startPos.get(1).setTotPoints(25);
            startPos.remove(1);
        }
        //10% chance= 3rd position
        if (randomNumber > 0.2 && randomNumber <= 0.3) {
            finalPosLabel[2].setText("<html>" + "<br>" + "Driver Name:" + startPos.get(2).getFname() + " " + startPos.get(2).getLname()
                    + "<br>" + "Driver Country: " + startPos.get(2).getDlocation());
            startPos.get(2).setfPosCount(1);
            startPos.get(2).setRacesCount(1);
            startPos.get(2).setTotPoints(25);
            startPos.remove(2);

        }
        //10% chance= 4th position
        if (randomNumber > 0.10 && randomNumber <= 0.2) {
            finalPosLabel[3].setText("<html>" + "<br>" + "Driver Name:" + startPos.get(3).getFname() + " " + startPos.get(3).getLname()
                    + "<br>" + "Driver Country: " + startPos.get(3).getDlocation());
            startPos.get(3).setfPosCount(1);
            startPos.get(3).setRacesCount(1);
            startPos.get(3).setTotPoints(25);
            startPos.remove(3);
        }
        // 2% chance
        if (randomNumber > 0.08 && randomNumber <= 0.10) {
            finalPosLabel[4].setText("<html>" + "<br>" + "Driver Name:" + startPos.get(4).getFname() + " " + startPos.get(4).getLname()
                    + "<br>" + "Driver Country: " + startPos.get(4).getDlocation());
            startPos.get(4).setfPosCount(1);
            startPos.get(4).setRacesCount(1);
            startPos.get(4).setTotPoints(25);
            startPos.remove(4);
        }
        // 2% chance
        if (randomNumber > 0.06 && randomNumber <= 0.08) {
            finalPosLabel[5].setText("<html>" + "<br>" + "Driver Name:" + startPos.get(5).getFname() + " " + startPos.get(5).getLname()
                    + "<br>" + "Driver Country: " + startPos.get(5).getDlocation());
            startPos.get(5).setfPosCount(1);
            startPos.get(5).setRacesCount(1);
            startPos.get(5).setTotPoints(25);
            startPos.remove(5);
        }
        // 2% chance
        if (randomNumber > 0.04 && randomNumber <= 0.06) {
            finalPosLabel[6].setText("<html>" + "<br>" + "Driver Name:" + startPos.get(6).getFname() + " " + startPos.get(6).getLname()
                    + "<br>" + "Driver Country: " + startPos.get(6).getDlocation());
            startPos.get(6).setfPosCount(1);
            startPos.get(6).setRacesCount(1);
            startPos.get(6).setTotPoints(25);
            startPos.remove(6);

        }
        // 2% chance
        if (randomNumber > 0.02 && randomNumber <= 0.04) {
            finalPosLabel[7].setText("<html>" + "<br>" + "Driver Name:" + startPos.get(7).getFname() + " " + startPos.get(7).getLname()
                    + "<br>" + "Driver Country: " + startPos.get(7).getDlocation());
            startPos.get(7).setfPosCount(1);
            startPos.get(7).setRacesCount(1);
            startPos.get(7).setTotPoints(25);
            startPos.remove(7);
        }
        // 2% chance
        if (randomNumber > 0.00 && randomNumber <= 0.02) {
            finalPosLabel[8].setText("<html>" + "<br>" + "Driver Name:" + startPos.get(8).getFname() + " " + startPos.get(8).getLname()
                    + "<br>" + "Driver Country: " + startPos.get(8).getDlocation());
            startPos.get(8).setfPosCount(1);
            startPos.get(8).setRacesCount(1);
            startPos.get(8).setTotPoints(25);
            startPos.remove(8);
        }

        Collections.shuffle(driPosition);

        for (int finalPos = 0; finalPos < startPos.size(); finalPos++) {

                if (finalPos == 0){
                    startPos.get(finalPos).setsPosCount(1);
                    startPos.get(finalPos).setRacesCount(1);
                    startPos.get(finalPos).setTotPoints(18);
                }else if (finalPos==1){
                        startPos.get(finalPos).settCount(1);
                        startPos.get(finalPos).setRacesCount(1);
                        startPos.get(finalPos).setTotPoints(15);
                }else if (finalPos==2){
                        startPos.get(finalPos).setRacesCount(1);
                        startPos.get(finalPos).setTotPoints(12);
                }else if (finalPos==3){
                        startPos.get(finalPos).setRacesCount(1);
                        startPos.get(finalPos).setTotPoints(10);
                }else if (finalPos==4){
                        startPos.get(finalPos).setRacesCount(1);
                        startPos.get(finalPos).setTotPoints(8);
                }else if (finalPos == 5){
                        startPos.get(finalPos).setRacesCount(1);
                        startPos.get(finalPos).setTotPoints(6);

                }else if (finalPos == 6){
                        startPos.get(finalPos).setRacesCount(1);
                        startPos.get(finalPos).setTotPoints(4);

                }else if (finalPos == 7){
                        startPos.get(finalPos).setRacesCount(1);
                        startPos.get(finalPos).setTotPoints(2);

                }else if (finalPos == 8){
                        startPos.get(finalPos).setRacesCount(1);
                        startPos.get(finalPos).setTotPoints(1);

                }

            finalPosLabel[finalPos + 1 ].setText("<html>" + "<br>" + "Driver Name:" + startPos.get(finalPos).getFname() + " " + startPos.get(finalPos).getLname()
                    + "<br>" + "Driver Country: " + startPos.get(finalPos).getDlocation());
        }
            dateLabel.setText(" Date: " + dates[raceTracker]);                                                          //adds the date along with the race.
            raceTracker++;                                                                                              //everytime the user races the counter is incremented by 1.
            System.out.println("race has been launched");                                                               //prints the race has been launched to let the user know the race has been launched on console.
            saveData();
            tableFill();
            ascOrder();
        }else{
            JOptionPane.showMessageDialog(null, "Maximum race limit reached", "Race Completed", JOptionPane.INFORMATION_MESSAGE);

        }
    }
}