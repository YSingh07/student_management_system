package ui;

import javax.swing.*;
import java.awt.*;

public class TeacherDashboard extends JFrame {

    private long teacherId;

    public TeacherDashboard(long teacherId) {

        this.teacherId = teacherId;

        // ---------------- FRAME ----------------

        setTitle("Teacher Dashboard");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ---------------- MAIN PANEL ----------------

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));

        // ---------------- HEADER ----------------

        JLabel heading = new JLabel(
                "TEACHER DASHBOARD",
                SwingConstants.CENTER
        );

        heading.setFont(
                new Font("Arial", Font.BOLD, 28)
        );

        heading.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 10, 20, 10
                )
        );

        mainPanel.add(heading, BorderLayout.NORTH);

        // ---------------- INFO ----------------

        JLabel teacherInfo = new JLabel(
                "Logged in Teacher ID: " + teacherId,
                SwingConstants.CENTER
        );

        teacherInfo.setFont(
                new Font("Arial", Font.PLAIN, 18)
        );

        mainPanel.add(
                teacherInfo,
                BorderLayout.CENTER
        );

        // ---------------- LOGOUT ----------------

        JButton logoutButton = new JButton("Logout");

        logoutButton.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        logoutButton.addActionListener(e -> logout());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(logoutButton);

        mainPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        // ---------------- FRAME ----------------

        add(mainPanel);

        setVisible(true);
    }


    // =================================================
    // LOGOUT
    // =================================================

    private void logout() {

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Logout",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {

            new LoginFrame();

            dispose();
        }
    }


    // =================================================
    // MAIN
    // =================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new TeacherDashboard(2);

        });
    }
}