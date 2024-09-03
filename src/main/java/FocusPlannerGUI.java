import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;



public class FocusPlannerGUI {

    private JFrame frame;
    private JTextArea entryDisplayArea;
    private List<Entry> entryList;

    public FocusPlannerGUI() {
        entryList = new ArrayList<>();

        // Set up the frame
        frame = new JFrame("FocusPlanner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);

        // Set up the menu panel
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(5, 1));


        JButton dateTimeButton = new JButton("Display Current Date & Time");
        JButton addEntryButton = new JButton("Add Calendar Entry");
        JButton printEntriesButton = new JButton("Print Entries");
        JButton pomodoroTimerButton = new JButton("Pomodoro Timer");
        JButton exitButton = new JButton("Exit");

        menuPanel.add(dateTimeButton);
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

        // Add action listeners for buttons
        dateTimeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayCurrentDateTime();
            }
        });

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

    private void displayCurrentDateTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        entryDisplayArea.append("Current Date and Time: " + formattedDateTime + "\n");
    }

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

    private void pomodoroTimer() {
        // Panel for Pomodoro timer
        JPanel pomodoroPanel = new JPanel();
        JLabel pomodoroLabel = new JLabel("00:25:00");
        pomodoroPanel.add(pomodoroLabel);

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
                    ((Timer)e.getSource()).stop(); // Stop the timer when it reaches zero
                    pomodoroLabel.setText("00:00:00"); // Optional: Display zero time

                }
            }
        });

        timer.start(); // Start the timer
        JOptionPane.showMessageDialog(frame, pomodoroPanel, "Pomodoro Timer", JOptionPane.PLAIN_MESSAGE);
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

