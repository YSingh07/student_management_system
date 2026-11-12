package ui;

import model.User;
import service.LoginService;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private LoginService loginService;

    public LoginFrame() {

        loginService = new LoginService();

        // ---------------- FRAME ----------------

        setTitle("Student Management System - Login");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // ---------------- MAIN PANEL ----------------

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));

        // ---------------- TITLE ----------------

        JLabel titleLabel = new JLabel(
                "STUDENT MANAGEMENT SYSTEM",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        titleLabel.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 10, 20, 10
                )
        );

        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // ---------------- FORM PANEL ----------------

        JPanel formPanel = new JPanel(
                new GridLayout(3, 2, 10, 15)
        );

        formPanel.setBackground(
                new Color(245, 247, 250)
        );

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 60, 20, 60
                )
        );

        // Username

        JLabel usernameLabel = new JLabel("Username:");

        usernameField = new JTextField();

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);

        // Password

        JLabel passwordLabel = new JLabel("Password:");

        passwordField = new JPasswordField();

        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        // Login Button

        JButton loginButton = new JButton("LOGIN");

        loginButton.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        loginButton.setFocusPainted(false);

        formPanel.add(new JLabel(""));
        formPanel.add(loginButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // ---------------- LOGIN ACTION ----------------

        loginButton.addActionListener(e -> login());

        // Press Enter in password field

        passwordField.addActionListener(e -> login());

        // ---------------- FRAME ----------------

        add(mainPanel);

        setVisible(true);
    }


    // =================================================
    // LOGIN METHOD
    // =================================================

    private void login() {

        String username = usernameField.getText().trim();

        String password =
                new String(passwordField.getPassword());

        // ---------------- VALIDATION ----------------

        if (username.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Username and Password.",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // ---------------- DATABASE LOGIN ----------------

        User user = loginService.login(
                username,
                password
        );

        // ---------------- INVALID LOGIN ----------------

        if (user == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Username or Password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            passwordField.setText("");

            return;
        }

        // ---------------- ADMIN ----------------

        if (user.getRole().equals("ADMIN")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Welcome " + user.getName() + "!"
            );

            new AdminDashboard().setVisible(true);

            dispose();
        }

        // ---------------- TEACHER ----------------

        else if (user.getRole().equals("TEACHER")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Welcome " + user.getName() + "!"
            );

            // Teacher ID will be passed to TeacherDashboard
            new TeacherDashboard(
                    user.getUserId()
            ).setVisible(true);

            dispose();
        }
    }


    // =================================================
    // MAIN
    // =================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new LoginFrame();

        });
    }
}