import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/*
 Potential future updates :
 - Change UI
 - Fix Calendar Entry System :
    Short fixes:
       - displays buttons with the entry dates
       - shows real-time the description / notes made for that date
       - remove entry button on top right
       - edit entry button
       - export list

    Long term QOL fixes:
        - full interactive calendar display with year selection

  - add another program functionality:
    - Notebooks
        - categories to separate notebooks
        - possible AI integration to help
 */

public class FocusPlannerGUI {

    private JFrame frame;
    private JTextArea entryDisplayArea;
    private List<Entry> entryList;
    private JLabel timeLabel;  // Add timeLabel at the class level
    private Timer timer;

    public FocusPlannerGUI() {
        entryList = new ArrayList<>();

        // Set up the frame
        frame = new JFrame("FocusPlanner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);

        // Set up the menu panel
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(1, 6));


        JButton dateTimeButton = new JButton("Display Current Date & Time");
        JButton addEntryButton = new JButton("Add Calendar Entry");
        JButton printEntriesButton = new JButton("Print Entries");
        JButton pomodoroTimerButton = new JButton("Pomodoro Timer");
        JButton exitButton = new JButton("Exit");

        // menuPanel.add(dateTimeButton);
        menuPanel.add(addEntryButton);
        menuPanel.add(printEntriesButton);
        menuPanel.add(pomodoroTimerButton);
        menuPanel.add(exitButton);

        // Add menu panel to frame
        frame.getContentPane().add(menuPanel, BorderLayout.NORTH);

        // Set up the display area
        entryDisplayArea = new JTextArea();
        entryDisplayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(entryDisplayArea);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Create a panel for the time display
        JPanel timePanel = new JPanel();
        timeLabel = new JLabel("Time: ");
        timePanel.add(timeLabel);

        // Add the time panel to the frame (at the bottom)
        menuPanel.add(timePanel);

        // Set up the timer for real-time clock
        final DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        ActionListener timerListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Date date = new Date();
                String time = timeFormat.format(date);
                timeLabel.setText("Time: " + time);
            }
        };
        timer = new Timer(1000, timerListener); // Update every second
        timer.setInitialDelay(0); // Start immediately
        timer.start();

        // Add action listeners for buttons
        /*dateTimeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayCurrentDateTime();
            }
        });
         */

        addEntryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCalendarEntry();
            }
        });

        printEntriesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printEntries();
            }
        });

        pomodoroTimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               pomodoroTimer();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }

        /*
    private void displayCurrentDateTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        entryDisplayArea.append("Current Date and Time: " + formattedDateTime + "\n");
    }
    */


    private void addCalendarEntry() {
        String day = JOptionPane.showInputDialog(frame, "Enter the day:");
        String month = JOptionPane.showInputDialog(frame, "Enter the month:");
        String year = JOptionPane.showInputDialog(frame, "Enter the year:");
        String description = JOptionPane.showInputDialog(frame, "Enter the description:");

        if (day != null && month != null && year != null && description != null) {
            Entry entry = new Entry();
            entry.setDay(Integer.parseInt(day));
            entry.setMonth(month);
            entry.setYear(Integer.parseInt(year));
            entry.setDescription(description);

            entryList.add(entry);

            entryDisplayArea.append("Added Entry: " + entry + "\n");
        }
    }

    private void printEntries() {
        if (entryList.isEmpty()) {
            entryDisplayArea.append("No entries available.\n");
        } else {
            entryDisplayArea.append("Displaying all entries:\n");
            for (Entry entry : entryList) {
                entryDisplayArea.append(entry + "\n");
            }
        }
    }

    /*
    TO DO:
    - Pomodoro timer reset back to 25 minutes
    - pop up that confirms the user if they want a short break
    - goes to break with 5 minutes
    - after 5 minutes prompts user if they want a long session (25 minutes) or short session (15 minutes) & closes the break tab

    QOL future updates :
    - real time switching between breaks and sessions instead of opening a new tab
     */

    private void pomodoroTimer() {
        // Panel for Pomodoro timer
        JPanel pomodoroPanel = new JPanel();
        JLabel pomodoroLabel = new JLabel("00:25:00");
        pomodoroPanel.add(pomodoroLabel);

        JButton breakButton = new JButton("Break");
        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");
        pomodoroPanel.add(breakButton);
        pomodoroPanel.add(startButton);
        pomodoroPanel.add(stopButton);




        Timer timer = new Timer(1000, new ActionListener() {
            int seconds = 1500; // 25 minutes = 1500 seconds

            @Override
            public void actionPerformed(ActionEvent e) {
                if (seconds > 0) {
                    seconds--; // Decrement the timer
                    int hours = seconds / 3600;
                    int minutes = (seconds % 3600) / 60;
                    int secs = seconds % 60;
                    pomodoroLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, secs));
                } else {
                    ((Timer) e.getSource()).stop(); // Stop the timer when it reaches zero
                    pomodoroLabel.setText("00:00:00"); // Optional: Display zero time

                }

            }

        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.start(); // Start the time
            }
        });

        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timer.stop(); // Stops the time
            }
        });

        breakButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                breakTimer();
            }
        });

        JOptionPane.showMessageDialog(frame, pomodoroPanel, "Pomodoro Timer", JOptionPane.PLAIN_MESSAGE);
    }

    public void breakTimer(){
        JPanel breakPanel = new JPanel();
        JLabel breakLabel = new JLabel("00:05:00");
        JButton startBreakButton = new JButton("Start");
        JButton stopBreakButton = new JButton("Stop");
        breakPanel.add(breakLabel);
        breakPanel.add(startBreakButton);
        breakPanel.add(stopBreakButton);

        Timer timer = new Timer(1000, new ActionListener() {
            int seconds = 300; // 5 minutes = 300 seconds

            @Override
            public void actionPerformed(ActionEvent e) {
                if (seconds > 0) {
                    seconds--; // Decrement the timer
                    int hours = seconds / 3600;
                    int minutes = (seconds % 3600) / 60;
                    int secs = seconds % 60;
                    breakLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, secs));
                } else {
                    ((Timer) e.getSource()).stop(); // Stop the timer when it reaches zero
                    breakLabel.setText("00:00:00"); // Optional: Display zero time

                }

            }

        });

        startBreakButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.start(); // Start the time
            }
        });

        stopBreakButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timer.stop(); // Stops the time
            }
        });
        JOptionPane.showMessageDialog(frame, breakPanel, "Pomodoro Timer", JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FocusPlannerGUI();
            }
        });

    }
}

