package ui;

import model.Student;
import service.StudentService;

import model.Subject;
import service.SubjectService;

import model.Mark;
import service.MarkService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class AdminDashboard extends JFrame {

    // ================= COLORS =================

    private final Color SIDEBAR_COLOR =
            new Color(35, 45, 65);

    private final Color BACKGROUND_COLOR =
            new Color(245, 247, 250);

    private final Color BUTTON_COLOR =
            new Color(55, 70, 95);


    // ================= CARD LAYOUT =================

    private CardLayout cardLayout;
    private JPanel mainPanel;



    // =================================================
    // STUDENT FIELDS
    // =================================================

    private StudentService studentService;

    private JTextField idField;
    private JTextField nameField;
    private JTextField ageField;
    private JTextField genderField;
    private JTextField courseField;
    private JTextField semesterField;
    private JTextField emailField;
    private JTextField attendanceField;

    private JTable studentTable;
    private DefaultTableModel tableModel;


    // =================================================
    // SUBJECT FIELDS
    // =================================================

    private SubjectService subjectService;

    private JTextField subjectIdField;
    private JTextField subjectNameField;
    private JTextField subjectSemesterField;
    private JTextField subjectCourseField;

    private JTable subjectTable;
    private DefaultTableModel subjectTableModel;


    // =================================================
    // MARKS FIELDS
    // =================================================

    private MarkService markService;

    private JTextField markIdField;
    private JTextField markStudentIdField;
    private JTextField markSubjectIdField;
    private JTextField marksField;
    private JComboBox<String> marksFilterCombo;

    private JTable markTable;
    private DefaultTableModel markTableModel;


    // =================================================
    // CONSTRUCTOR
    // =================================================

    public AdminDashboard() {

        studentService = new StudentService();
        subjectService = new SubjectService();
        markService = new MarkService();

        setTitle(
                "Student Management System - Admin Dashboard"
        );

        setSize(1200, 700);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        setLayout(
                new BorderLayout()
        );


        // =================================================
        // SIDEBAR
        // =================================================

        JPanel sidebar = new JPanel();

        sidebar.setPreferredSize(
                new Dimension(220, 700)
        );

        sidebar.setBackground(
                SIDEBAR_COLOR
        );

        sidebar.setLayout(
                new GridLayout(7, 1, 0, 10)
        );


        // ================= TITLE =================

        JLabel title =
                new JLabel("  ADMIN PANEL");

        title.setForeground(
                Color.WHITE
        );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );

        sidebar.add(title);


        // ================= BUTTONS =================

        JButton dashboardButton =
                createButton("Dashboard");

        JButton studentsButton =
                createButton("Students");

        JButton subjectsButton =
                createButton("Subjects");

        JButton marksButton =
                createButton("Marks");

        JButton logoutButton =
                createButton("Logout");


        sidebar.add(dashboardButton);
        sidebar.add(studentsButton);
        sidebar.add(subjectsButton);
        sidebar.add(marksButton);
        sidebar.add(logoutButton);


        // =================================================
        // MAIN PANEL
        // =================================================

        cardLayout = new CardLayout();

        mainPanel =
                new JPanel(cardLayout);


        // ================= CREATE SCREENS =================

        JPanel dashboardPanel =
                createDashboardPanel();

        JPanel studentPanel =
                createStudentPanel();

        JPanel subjectPanel =
                createSubjectPanel();

        JPanel marksPanel =
                createMarksPanel();


        // ================= ADD SCREENS =================

        mainPanel.add(
                dashboardPanel,
                "dashboard"
        );

        mainPanel.add(
                studentPanel,
                "students"
        );

        mainPanel.add(
                subjectPanel,
                "subjects"
        );

        mainPanel.add(
                marksPanel,
                "marks"
        );


        // =================================================
        // ADD TO FRAME
        // =================================================

        add(
                sidebar,
                BorderLayout.WEST
        );

        add(
                mainPanel,
                BorderLayout.CENTER
        );


        // =================================================
        // BUTTON ACTIONS
        // =================================================

        dashboardButton.addActionListener(e -> {
	    
	    updateDashboardStats();
            cardLayout.show(
                    mainPanel,
                    "dashboard"
            );
        });


        studentsButton.addActionListener(e -> {

            cardLayout.show(
                    mainPanel,
                    "students"
            );

            loadStudents();
        });


        subjectsButton.addActionListener(e -> {

            cardLayout.show(
                    mainPanel,
                    "subjects"
            );

            loadSubjects();
        });


        marksButton.addActionListener(e -> {

            cardLayout.show(
                    mainPanel,
                    "marks"
            );

            loadMarks();
        });


        logoutButton.addActionListener(e -> {

            int result =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to logout?",
                            "Logout",
                            JOptionPane.YES_NO_OPTION
                    );


            if (result ==
                    JOptionPane.YES_OPTION) {
		
		new LoginFrame();
                dispose();
            }
        });
    }


    // =================================================
    // CREATE BUTTON
    // =================================================

    private JButton createButton(String text) {

        JButton button =
                new JButton(text);

        button.setForeground(
                Color.WHITE
        );

        button.setBackground(
                BUTTON_COLOR
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        15
                )
        );

        button.setFocusPainted(false);

        return button;
    }


    // =================================================
    // DASHBOARD PANEL
    // =================================================

    private JLabel totalStudentsValueLabel;



    
private JPanel createDashboardPanel() {

    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(BACKGROUND_COLOR);

    JLabel heading = new JLabel("Dashboard");
    heading.setFont(new Font("Arial", Font.BOLD, 28));
    heading.setBorder(
        BorderFactory.createEmptyBorder(20, 25, 20, 20)
    );

    panel.add(heading, BorderLayout.NORTH);

    JPanel cards = new JPanel(
        new FlowLayout(FlowLayout.LEFT, 25, 20)
    );
    cards.setBackground(BACKGROUND_COLOR);

    // Total Students card
    totalStudentsValueLabel = new JLabel("0");
    totalStudentsValueLabel.setFont(
        new Font("Arial", Font.BOLD, 32)
    );

    cards.add(
        createCard("Total Students", totalStudentsValueLabel)
    );

    // Other cards for now
    cards.add(createCard("Total Subjects", String.valueOf(subjectService.getSubjectCount())));
    cards.add(createCard("Marks Records", String.valueOf(markService.getMarkCount())));

    panel.add(cards, BorderLayout.CENTER);

    // Load the actual student count
    updateDashboardStats();

    return panel;
}



    // =================================================
    // STUDENT PANEL
    // =================================================

    private JPanel createStudentPanel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(10, 10)
                );

        panel.setBackground(
                BACKGROUND_COLOR
        );


        // ================= HEADING =================

        JLabel heading =
                new JLabel(
                        "Student Management"
                );

        heading.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        heading.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        20,
                        10,
                        20
                )
        );


        // ================= FORM =================

        JPanel formPanel =
                new JPanel(
                        new GridLayout(
                                5,
                                4,
                                10,
                                10
                        )
                );

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        20,
                        10,
                        20
                )
        );


        // ================= STUDENT ID =================

        formPanel.add(
                new JLabel(
                        "Student ID (Only For Searching)"
                )
        );

        idField =
                new JTextField();

        formPanel.add(idField);


        // ================= NAME =================

        formPanel.add(
                new JLabel("Name")
        );

        nameField =
                new JTextField();

        formPanel.add(nameField);


        // ================= AGE =================

        formPanel.add(
                new JLabel("Age")
        );

        ageField =
                new JTextField();

        formPanel.add(ageField);


        // ================= GENDER =================

        formPanel.add(
                new JLabel("Gender")
        );

        genderField =
                new JTextField();

        formPanel.add(genderField);


        // ================= COURSE =================

        formPanel.add(
                new JLabel("Course")
        );

        courseField =
                new JTextField();

        formPanel.add(courseField);


        // ================= SEMESTER =================

        formPanel.add(
                new JLabel("Semester")
        );

        semesterField =
                new JTextField();

        formPanel.add(semesterField);


        // ================= EMAIL =================

        formPanel.add(
                new JLabel("Email")
        );

        emailField =
                new JTextField();

        formPanel.add(emailField);


        // ================= ATTENDANCE =================

        formPanel.add(
                new JLabel("Attendance (%)")
        );

        attendanceField =
                new JTextField();

        formPanel.add(attendanceField);


        // ================= EMPTY SPACE =================

        formPanel.add(
                new JLabel("")
        );

        formPanel.add(
                new JLabel("")
        );


        // ================= BUTTONS =================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout()
                );


        JButton addButton =
                new JButton("Add");

        JButton searchButton =
                new JButton("Search");

        JButton updateButton =
                new JButton("Update");

        JButton deleteButton =
                new JButton("Delete");

        JButton clearButton =
                new JButton("Clear");


        buttonPanel.add(addButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);


        // ================= TOP PANEL =================

        JPanel topPanel =
                new JPanel(
                        new BorderLayout()
                );

        topPanel.add(
                heading,
                BorderLayout.NORTH
        );

        topPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        topPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );


        // ================= TABLE =================

        String[] columns = {

                "ID",
                "Name",
                "Age",
                "Gender",
                "Course",
                "Semester",
                "Email",
                "Attendance (%)"
        };


        tableModel =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {

                        return false;
                    }
                };


        studentTable =
                new JTable(tableModel);


        JScrollPane scrollPane =
                new JScrollPane(
                        studentTable
                );


        panel.add(
                topPanel,
                BorderLayout.NORTH
        );

        panel.add(
                scrollPane,
                BorderLayout.CENTER
        );


        // =================================================
        // ADD BUTTON
        // =================================================

        addButton.addActionListener(e -> {

            try {

                int id =
                        Integer.parseInt(
                                idField
                                        .getText()
                                        .trim()
                        );


                String name =
                        nameField
                                .getText()
                                .trim();


                int age =
                        Integer.parseInt(
                                ageField
                                        .getText()
                                        .trim()
                        );


                String gender =
                        genderField
                                .getText()
                                .trim();


                String course =
                        courseField
                                .getText()
                                .trim();


                int semester =
                        Integer.parseInt(
                                semesterField
                                        .getText()
                                        .trim()
                        );


                String email =
                        emailField
                                .getText()
                                .trim();


                // =========================
                // ATTENDANCE
                // =========================

                double attendance =
                        Double.parseDouble(
                                attendanceField
                                        .getText()
                                        .trim()
                        );


                // =========================
                // VALIDATE ATTENDANCE
                // =========================

                if (attendance < 0 ||
                        attendance > 100) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Attendance must be between 0 and 100.",
                            "Invalid Attendance",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }


                // =========================
                // CREATE STUDENT
                // =========================

                Student student =
                        new Student(
                                id,
                                name,
                                age,
                                gender,
                                course,
                                semester,
                                email,
                                attendance
                        );


                int succ =
                        studentService.addStudent(
                                student
                        );


                if (succ == 1) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Student added successfully!"
                    );

                } else if (succ == -1) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Student ID already exists."
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Student not added!!"
                    );
                }


                if (succ == 1) {

                    clearFields();
                    loadStudents();
                }


            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "ID, Age, Semester and Attendance must be numbers.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                );


            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to add student.\n"
                                + ex.getMessage(),
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });


        // =================================================
        // SEARCH BUTTON
        // =================================================

        searchButton.addActionListener(e -> {

            try {

                String idText =
                        idField
                                .getText()
                                .trim();


                if (idText.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Please enter Student ID.",
                            "Search",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }


                int id =
                        Integer.parseInt(idText);


                Student student =
                        studentService
                                .getStudentById(id);


                if (student == null) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Student not found!",
                            "Search Result",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    return;
                }


                // =========================
                // DISPLAY STUDENT DATA
                // =========================

                idField.setText(
                        String.valueOf(
                                student.getStudentId()
                        )
                );


                nameField.setText(
                        student.getName()
                );


                ageField.setText(
                        String.valueOf(
                                student.getAge()
                        )
                );


                genderField.setText(
                        student.getGender()
                );


                courseField.setText(
                        student.getCourse()
                );


                semesterField.setText(
                        String.valueOf(
                                student.getSemester()
                        )
                );


                emailField.setText(
                        student.getEmail()
                );


                attendanceField.setText(
                        String.valueOf(
                                student.getAttendance()
                        )
                );


                JOptionPane.showMessageDialog(
                        this,
                        "Student found!"
                );


            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid Student ID.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                );


            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Search failed.\n"
                                + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });


        // =================================================
        // UPDATE BUTTON
        // =================================================

        updateButton.addActionListener(e -> {

            try {

                int id =
                        Integer.parseInt(
                                idField
                                        .getText()
                                        .trim()
                        );


                String name =
                        nameField
                                .getText()
                                .trim();


                int age =
                        Integer.parseInt(
                                ageField
                                        .getText()
                                        .trim()
                        );


                String gender =
                        genderField
                                .getText()
                                .trim();


                String course =
                        courseField
                                .getText()
                                .trim();


                int semester =
                        Integer.parseInt(
                                semesterField
                                        .getText()
                                        .trim()
                        );


                String email =
                        emailField
                                .getText()
                                .trim();


                // =========================
                // ATTENDANCE
                // =========================

                double attendance =
                        Double.parseDouble(
                                attendanceField
                                        .getText()
                                        .trim()
                        );


                // =========================
                // VALIDATE ATTENDANCE
                // =========================

                if (attendance < 0 ||
                        attendance > 100) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Attendance must be between 0 and 100.",
                            "Invalid Attendance",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }


                // =========================
                // CREATE UPDATED STUDENT
                // =========================

                Student updatedStudent =
                        new Student(
                                id,
                                name,
                                age,
                                gender,
                                course,
                                semester,
                                email,
                                attendance
                        );


                int succ =
                        studentService.Update(
                                id,
                                updatedStudent
                        );


                if (succ == 1) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Student updated successfully!"
                    );

                    clearFields();
                    loadStudents();

                } else if (succ == -1) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Student not found!!!"
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Student not updated !!!"
                    );
                }


            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "ID, Age, Semester and Attendance must be numbers.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                );


            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to update student.\n"
                                + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });


        // =================================================
        // DELETE BUTTON
        // =================================================

        deleteButton.addActionListener(e -> {

            try {

                int selectedRow =
                        studentTable
                                .getSelectedRow();


                if (selectedRow == -1) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Please select a student from the table.",
                            "No Student Selected",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }


                int id =
                        Integer.parseInt(
                                studentTable
                                        .getValueAt(
                                                selectedRow,
                                                0
                                        )
                                        .toString()
                        );


                String name =
                        studentTable
                                .getValueAt(
                                        selectedRow,
                                        1
                                )
                                .toString();


                int result =
                        JOptionPane.showConfirmDialog(
                                this,
                                "Delete student:\n"
                                        + id
                                        + " - "
                                        + name
                                        + "?",
                                "Confirm Delete",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE
                        );


                if (result ==
                        JOptionPane.YES_OPTION) {

                    int deleteResult =
                            studentService
                                    .DeleteStudent(id);


                    if (deleteResult == 1) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Student deleted successfully!"
                        );

                    } else {

                        JOptionPane.showMessageDialog(
                                this,
                                "Student could not be deleted.",
                                "Delete Error",
                                JOptionPane.WARNING_MESSAGE
                        );
                    }


                    clearFields();
                    loadStudents();
                }


            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to delete student.\n"
                                + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });


        // =================================================
        // CLEAR BUTTON
        // =================================================

        clearButton.addActionListener(e -> {

            clearFields();
            loadStudents();

        });


        // =================================================
        // TABLE ROW SELECTION
        // =================================================

        studentTable
                .getSelectionModel()
                .addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int selectedRow =
                        studentTable
                                .getSelectedRow();


                if (selectedRow != -1) {

                    idField.setText(
                            studentTable
                                    .getValueAt(
                                            selectedRow,
                                            0
                                    )
                                    .toString()
                    );

                    idField.setEditable(false);


                    nameField.setText(
                            studentTable
                                    .getValueAt(
                                            selectedRow,
                                            1
                                    )
                                    .toString()
                    );


                    ageField.setText(
                            studentTable
                                    .getValueAt(
                                            selectedRow,
                                            2
                                    )
                                    .toString()
                    );


                    genderField.setText(
                            studentTable
                                    .getValueAt(
                                            selectedRow,
                                            3
                                    )
                                    .toString()
                    );


                    courseField.setText(
                            studentTable
                                    .getValueAt(
                                            selectedRow,
                                            4
                                    )
                                    .toString()
                    );


                    semesterField.setText(
                            studentTable
                                    .getValueAt(
                                            selectedRow,
                                            5
                                    )
                                    .toString()
                    );


                    emailField.setText(
                            studentTable
                                    .getValueAt(
                                            selectedRow,
                                            6
                                    )
                                    .toString()
                    );


                    attendanceField.setText(
                            studentTable
                                    .getValueAt(
                                            selectedRow,
                                            7
                                    )
                                    .toString()
                    );
                }
            }
        });


        return panel;
    }


    // =================================================
    // SUBJECT PANEL
    // =================================================

    private JPanel createSubjectPanel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.setBackground(
                BACKGROUND_COLOR
        );


        // =========================
        // HEADING
        // =========================

        JLabel title =
                new JLabel(
                        "Subject Management"
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        title.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        20,
                        10,
                        20
                )
        );


        // =========================
        // FORM PANEL
        // =========================

        JPanel formPanel =
                new JPanel(
                        new GridLayout(
                                2,
                                4,
                                10,
                                10
                        )
                );

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        20,
                        10,
                        20
                )
        );


        subjectIdField =
                new JTextField();

        subjectNameField =
                new JTextField();

        subjectSemesterField =
                new JTextField();

        subjectCourseField =
                new JTextField();


        formPanel.add(
                new JLabel(
                        "Subject ID (Only for Searching)"
                )
        );

        formPanel.add(
                subjectIdField
        );


        formPanel.add(
                new JLabel("Subject Name")
        );

        formPanel.add(
                subjectNameField
        );


        formPanel.add(
                new JLabel("Semester")
        );

        formPanel.add(
                subjectSemesterField
        );


        formPanel.add(
                new JLabel("Course")
        );

        formPanel.add(
                subjectCourseField
        );


        // =========================
        // BUTTON PANEL
        // =========================

        JButton addButton =
                new JButton("Add");

        JButton searchButton =
                new JButton("Search");

        JButton updateButton =
                new JButton("Update");

        JButton deleteButton =
                new JButton("Delete");

        JButton clearButton =
                new JButton("Clear");


        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout()
                );


        buttonPanel.add(addButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);


        // =========================
        // TOP PANEL
        // =========================

        JPanel topPanel =
                new JPanel(
                        new BorderLayout()
                );


        topPanel.add(
                title,
                BorderLayout.NORTH
        );

        topPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        topPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );


        // =========================
        // SUBJECT TABLE
        // =========================

        String[] columns = {

                "ID",
                "Subject Name",
                "Semester",
                "Course"
        };


        subjectTableModel =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {

                        return false;
                    }
                };


        subjectTable =
                new JTable(
                        subjectTableModel
                );


        JScrollPane scrollPane =
                new JScrollPane(
                        subjectTable
                );


        panel.add(
                topPanel,
                BorderLayout.NORTH
        );

        panel.add(
                scrollPane,
                BorderLayout.CENTER
        );


        // =========================
        // ADD BUTTON
        // =========================

        addButton.addActionListener(e -> {

            try {

                String name =
                        subjectNameField
                                .getText()
                                .trim();

                String semesterText =
                        subjectSemesterField
                                .getText()
                                .trim();

                String course =
                        subjectCourseField
                                .getText()
                                .trim();


                if (name.isEmpty() ||
                        semesterText.isEmpty() ||
                        course.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Please fill all fields.",
                            "Input Error",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }


                int semester =
                        Integer.parseInt(
                                semesterText
                        );


                Subject subject =
                        new Subject(
                                0,
                                name,
                                semester,
                                course
                        );


                subjectService.addSubject(
                        subject
                );


                JOptionPane.showMessageDialog(
                        this,
                        "Subject added successfully!"
                );


                clearSubjectFields();
                loadSubjects();


            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Semester must be a number.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE
                );


            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to add subject.",
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );

                ex.printStackTrace();
            }
        });


        // =========================
        // SEARCH BUTTON
        // =========================

        searchButton.addActionListener(e -> {

            try {

                String idText =
                        subjectIdField
                                .getText()
                                .trim();


                if (idText.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Enter Subject ID.",
                            "Input Error",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }


                long id =
                        Long.parseLong(idText);


                Subject subject =
                        subjectService
                                .getSubjectById(id);


                if (subject != null) {

                    subjectNameField.setText(
                            subject.getSubjectName()
                    );

                    subjectSemesterField.setText(
                            String.valueOf(
                                    subject.getSemester()
                            )
                    );

                    subjectCourseField.setText(
                            subject.getCourse()
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Subject not found.",
                            "Search",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }


            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Subject ID must be a number.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });


        // =========================
        // UPDATE BUTTON
        // =========================

        updateButton.addActionListener(e -> {

            try {

                String idText =
                        subjectIdField
                                .getText()
                                .trim();


                if (idText.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Select a subject first.",
                            "Update",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }


                long id =
                        Long.parseLong(idText);


                String name =
                        subjectNameField
                                .getText()
                                .trim();


                int semester =
                        Integer.parseInt(
                                subjectSemesterField
                                        .getText()
                                        .trim()
                        );


                String course =
                        subjectCourseField
                                .getText()
                                .trim();


                Subject subject =
                        new Subject(
                                id,
                                name,
                                semester,
                                course
                        );


                subjectService.updateSubject(
                        id,
                        subject
                );


                JOptionPane.showMessageDialog(
                        this,
                        "Subject updated successfully!"
                );


                clearSubjectFields();
                loadSubjects();


            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Subject ID and Semester must be numbers.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE
                );


            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to update subject.",
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );

                ex.printStackTrace();
            }
        });


        // =========================
        // DELETE BUTTON
        // =========================

        deleteButton.addActionListener(e -> {

            int selectedRow =
                    subjectTable.getSelectedRow();


            if (selectedRow == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a subject from the table.",
                        "No Subject Selected",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            long id =
                    Long.parseLong(
                            subjectTable
                                    .getValueAt(
                                            selectedRow,
                                            0
                                    )
                                    .toString()
                    );


            String name =
                    subjectTable
                            .getValueAt(
                                    selectedRow,
                                    1
                            )
                            .toString();


            int result =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Delete subject: "
                                    + name
                                    + "?",
                            "Confirm Delete",
                            JOptionPane.YES_NO_OPTION
                    );


            if (result ==
                    JOptionPane.YES_OPTION) {

                subjectService.deleteSubject(id);


                JOptionPane.showMessageDialog(
                        this,
                        "Subject deleted successfully!"
                );


                clearSubjectFields();
                loadSubjects();
            }
        });


        // =========================
        // CLEAR BUTTON
        // =========================

        clearButton.addActionListener(
                e -> clearSubjectFields()
        );


        // =========================
        // TABLE ROW SELECTION
        // =========================

        subjectTable
                .getSelectionModel()
                .addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int selectedRow =
                        subjectTable
                                .getSelectedRow();


                if (selectedRow != -1) {

                    subjectIdField.setText(
                            subjectTable
                                    .getValueAt(
                                            selectedRow,
                                            0
                                    )
                                    .toString()
                    );


                    subjectNameField.setText(
                            subjectTable
                                    .getValueAt(
                                            selectedRow,
                                            1
                                    )
                                    .toString()
                    );


                    subjectSemesterField.setText(
                            subjectTable
                                    .getValueAt(
                                            selectedRow,
                                            2
                                    )
                                    .toString()
                    );


                    subjectCourseField.setText(
                            subjectTable
                                    .getValueAt(
                                            selectedRow,
                                            3
                                    )
                                    .toString()
                    );
                }
            }
        });


        return panel;
    }


    // =================================================
    // MARKS PANEL
    // =================================================

    private JPanel createMarksPanel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.setBackground(
                BACKGROUND_COLOR
        );


        // =========================
        // HEADING
        // =========================

        JLabel title =
                new JLabel(
                        "Marks Management"
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        title.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        20,
                        10,
                        20
                )
        );


        // =========================
        // FORM
        // =========================

        JPanel formPanel =
                new JPanel(
                        new GridLayout(
                                2,
                                4,
                                10,
                                10
                        )
                );

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        20,
                        10,
                        20
                )
        );


        markIdField =
                new JTextField();

        markStudentIdField =
                new JTextField();

        markSubjectIdField =
                new JTextField();

        marksField =
                new JTextField();


        formPanel.add(
                new JLabel("Mark ID")
        );

        formPanel.add(
                markIdField
        );


        formPanel.add(
                new JLabel("Student ID")
        );

        formPanel.add(
                markStudentIdField
        );


        formPanel.add(
                new JLabel("Subject ID")
        );

        formPanel.add(
                markSubjectIdField
        );


        formPanel.add(
                new JLabel("Marks")
        );

        formPanel.add(
                marksField
        );


        // =========================
        // BUTTONS
        // =========================

        JButton addButton =
                new JButton("Add");

        JButton searchButton =
                new JButton("Search");

        JButton updateButton =
                new JButton("Update");

        JButton deleteButton =
                new JButton("Delete");

        JButton clearButton =
                new JButton("Clear");


        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER
                        )
                );


        buttonPanel.add(addButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
	
        // =========================
        // Filter marks
        // =========================

	marksFilterCombo = new JComboBox<>(new String[]{
    		"Default Order",
    		"Marks: Highest to Lowest",
   		 "Marks: Lowest to Highest"
	});

	JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	filterPanel.setBackground(BACKGROUND_COLOR);

	filterPanel.add(new JLabel("Sort By:"));
	filterPanel.add(marksFilterCombo);

        // =========================
        // TOP PANEL
        // =========================

        JPanel topPanel =
                new JPanel(
                        new BorderLayout()
                );


        topPanel.add(
                title,
                BorderLayout.NORTH
        );

        topPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        topPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

	topPanel.add(filterPanel, BorderLayout.SOUTH);	

        // =========================
        // MARK TABLE
        // =========================

        String[] columns = {

                "Mark ID",
                "Student ID",
                "Subject ID",
                "Marks"
        };


        markTableModel =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {

                        return false;
                    }
                };


        markTable =
                new JTable(
                        markTableModel
                );


        JScrollPane scrollPane =
                new JScrollPane(
                        markTable
                );


        panel.add(
                topPanel,
                BorderLayout.NORTH
        );

        panel.add(
                scrollPane,
                BorderLayout.CENTER
        );


        // =========================
        // ADD BUTTON
        // =========================

        addButton.addActionListener(e -> {

            try {

                long studentId =
                        Long.parseLong(
                                markStudentIdField
                                        .getText()
                                        .trim()
                        );


                long subjectId =
                        Long.parseLong(
                                markSubjectIdField
                                        .getText()
                                        .trim()
                        );


                int marks =
                        Integer.parseInt(
                                marksField
                                        .getText()
                                        .trim()
                        );


                if (marks < 0 ||
                        marks > 100) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Marks must be between 0 and 100.",
                            "Invalid Marks",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }


                Mark mark =
                        new Mark(
                                0,
                                studentId,
                                subjectId,
                                marks
                        );


                markService.addMark(mark);


                JOptionPane.showMessageDialog(
                        this,
                        "Mark added successfully!"
                );


                clearMarkFields();
                loadMarks();


            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Student ID, Subject ID and Marks must be numbers.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE
                );


            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to add mark.",
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );

                ex.printStackTrace();
            }
        });


        // =========================
        // SEARCH BUTTON
        // =========================

        searchButton.addActionListener(e -> {

            try {

                String markIdText =
                        markIdField
                                .getText()
                                .trim();


                String studentIdText =
                        markStudentIdField
                                .getText()
                                .trim();


                String subjectIdText =
                        markSubjectIdField
                                .getText()
                                .trim();


                // =====================================
                // CASE 1: NO SEARCH ID ENTERED
                // =====================================

                if (markIdText.isEmpty()
                        && studentIdText.isEmpty()
                        && subjectIdText.isEmpty()) {

                    loadMarks();

                    return;
                }


                // =====================================
                // CASE 2: MARK ID SEARCH
                // =====================================

                if (!markIdText.isEmpty()) {

                    long markId =
                            Long.parseLong(
                                    markIdText
                            );


                    Mark mark =
                            markService
                                    .getMarkById(
                                            markId
                                    );


                    markTableModel
                            .setRowCount(0);


                    if (mark != null) {

                        addMarkToTable(mark);


                        markStudentIdField.setText(
                                String.valueOf(
                                        mark.getStudentId()
                                )
                        );


                        markSubjectIdField.setText(
                                String.valueOf(
                                        mark.getSubjectId()
                                )
                        );


                        marksField.setText(
                                String.valueOf(
                                        mark.getMarks()
                                )
                        );

                    } else {

                        JOptionPane.showMessageDialog(
                                this,
                                "Mark not found.",
                                "Search",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }

                    return;
                }


                // =====================================
                // CASE 3: STUDENT + SUBJECT SEARCH
                // =====================================

                if (!studentIdText.isEmpty()
                        && !subjectIdText.isEmpty()) {

                    long studentId =
                            Long.parseLong(
                                    studentIdText
                            );


                    long subjectId =
                            Long.parseLong(
                                    subjectIdText
                            );


                    Mark mark =
                            markService
                                    .getMarkByStudentAndSubject(
                                            studentId,
                                            subjectId
                                    );


                    markTableModel
                            .setRowCount(0);


                    if (mark != null) {

                        addMarkToTable(mark);


                        markIdField.setText(
                                String.valueOf(
                                        mark.getMarkId()
                                )
                        );


                        marksField.setText(
                                String.valueOf(
                                        mark.getMarks()
                                )
                        );

                    } else {

                        JOptionPane.showMessageDialog(
                                this,
                                "No mark found for this student and subject.",
                                "Search",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }

                    return;
                }


                // =====================================
                // CASE 4: STUDENT ID SEARCH
                // =====================================

                if (!studentIdText.isEmpty()) {

                    long studentId =
                            Long.parseLong(
                                    studentIdText
                            );


                    List<Mark> results =
                            markService
                                    .getMarksByStudentId(
                                            studentId
                                    );


                    showMarkSearchResults(
                            results
                    );

                    return;
                }


                // =====================================
                // CASE 5: SUBJECT ID SEARCH
                // =====================================

                if (!subjectIdText.isEmpty()) {

                    long subjectId =
                            Long.parseLong(
                                    subjectIdText
                            );


                    List<Mark> results =
                            markService
                                    .getMarksBySubjectId(
                                            subjectId
                                    );


                    showMarkSearchResults(
                            results
                    );
                }


            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "IDs must contain numbers.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                );


            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to search marks.",
                        "Search Error",
                        JOptionPane.ERROR_MESSAGE
                );

                ex.printStackTrace();
            }
        });


        // =========================
        // UPDATE BUTTON
        // =========================

        updateButton.addActionListener(e -> {

            try {

                String markIdText =
                        markIdField
                                .getText()
                                .trim();


                String studentIdText =
                        markStudentIdField
                                .getText()
                                .trim();


                String subjectIdText =
                        markSubjectIdField
                                .getText()
                                .trim();


                String marksText =
                        marksField
                                .getText()
                                .trim();


                // =========================
                // CHECK EMPTY FIELDS
                // =========================

                if (markIdText.isEmpty()
                        || studentIdText.isEmpty()
                        || subjectIdText.isEmpty()
                        || marksText.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Please fill all fields.",
                            "Update",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }


                // =========================
                // CONVERT VALUES
                // =========================

                long markId =
                        Long.parseLong(
                                markIdText
                        );


                long enteredStudentId =
                        Long.parseLong(
                                studentIdText
                        );


                long enteredSubjectId =
                        Long.parseLong(
                                subjectIdText
                        );


                int marks =
                        Integer.parseInt(
                                marksText
                        );


                // =========================
                // VALIDATE MARKS
                // =========================

                if (marks < 0 ||
                        marks > 100) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Marks must be between 0 and 100.",
                            "Invalid Marks",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }


                // =========================
                // GET ORIGINAL RECORD
                // =========================

                Mark existingMark =
                        markService
                                .getMarkById(
                                        markId
                                );


                if (existingMark == null) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Mark ID does not exist.",
                            "Update Error",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }


                // =========================
                // CHECK MARK ID
                // =========================

                if (markId !=
                        existingMark.getMarkId()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Mark ID cannot be changed.",
                            "ID Cannot Be Changed",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }


                // =========================
                // CHECK STUDENT ID
                // =========================

                if (enteredStudentId !=
                        existingMark.getStudentId()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Student ID cannot be changed.\n\n"
                                    + "Student ID is a Foreign Key.",
                            "ID Cannot Be Changed",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }


                // =========================
                // CHECK SUBJECT ID
                // =========================

                if (enteredSubjectId !=
                        existingMark.getSubjectId()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Subject ID cannot be changed.\n\n"
                                    + "Subject ID is a Foreign Key.",
                            "ID Cannot Be Changed",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }


                // =========================
                // UPDATE ONLY MARKS
                // =========================

                int result =
                        markService.updateMark(
                                markId,
                                marks
                        );


                if (result == 1) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Mark updated successfully!"
                    );


                    clearMarkFields();
                    loadMarks();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Mark was not updated.",
                            "Update Failed",
                            JOptionPane.WARNING_MESSAGE
                    );
                }


            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Mark ID, Student ID, Subject ID and Marks must contain numbers.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                );


            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to update mark.\n"
                                + ex.getMessage(),
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );

                ex.printStackTrace();
            }
        });


        // =========================
        // DELETE BUTTON
        // =========================

        deleteButton.addActionListener(e -> {

            int selectedRow =
                    markTable.getSelectedRow();


            if (selectedRow == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a mark from the table.",
                        "No Mark Selected",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            long id =
                    Long.parseLong(
                            markTable
                                    .getValueAt(
                                            selectedRow,
                                            0
                                    )
                                    .toString()
                    );


            int result =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Delete this mark?",
                            "Confirm Delete",
                            JOptionPane.YES_NO_OPTION
                    );


            if (result ==
                    JOptionPane.YES_OPTION) {

                markService.deleteMark(id);


                JOptionPane.showMessageDialog(
                        this,
                        "Mark deleted successfully!"
                );


                clearMarkFields();
                loadMarks();
            }
        });


        // =========================
        // CLEAR BUTTON
        // =========================

        clearButton.addActionListener(
                e -> clearMarkFields()
        );

        // =========================
        // Filter BUTTON
        // =========================

	marksFilterCombo.addActionListener(e -> sortMarksTable());


        // =========================
        // TABLE ROW SELECTION
        // =========================

        markTable
                .getSelectionModel()
                .addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int selectedRow =
                        markTable
                                .getSelectedRow();


                if (selectedRow != -1) {

                    markIdField.setText(
                            markTable
                                    .getValueAt(
                                            selectedRow,
                                            0
                                    )
                                    .toString()
                    );


                    markStudentIdField.setText(
                            markTable
                                    .getValueAt(
                                            selectedRow,
                                            1
                                    )
                                    .toString()
                    );


                    markSubjectIdField.setText(
                            markTable
                                    .getValueAt(
                                            selectedRow,
                                            2
                                    )
                                    .toString()
                    );


                    marksField.setText(
                            markTable
                                    .getValueAt(
                                            selectedRow,
                                            3
                                    )
                                    .toString()
                    );
                }
            }
        });


        return panel;
    }


    // =================================================
    // DASHBOARD CARD
    // =================================================

    private JPanel createCard(
            String title,
            String value
    ) {

        JPanel card =
                new JPanel();


        card.setPreferredSize(
                new Dimension(
                        250,
                        130
                )
        );


        card.setBackground(
                Color.WHITE
        );


        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel titleLabel =
                new JLabel(title);


        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );


        JLabel valueLabel =
                new JLabel(value);


        valueLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        32
                )
        );


        card.add(
                Box.createVerticalStrut(20)
        );


        card.add(
                titleLabel
        );


        card.add(
                Box.createVerticalStrut(10)
        );


        card.add(
                valueLabel
        );


        return card;
    }
//overloading create card

private JPanel createCard(String title, JLabel valueLabel) {

    JPanel card = new JPanel();

    card.setPreferredSize(new Dimension(250, 130));
    card.setBackground(Color.WHITE);
    card.setLayout(
        new BoxLayout(card, BoxLayout.Y_AXIS)
    );

    JLabel titleLabel = new JLabel(title);
    titleLabel.setFont(
        new Font("Arial", Font.BOLD, 16)
    );

    card.add(Box.createVerticalStrut(20));
    card.add(titleLabel);
    card.add(Box.createVerticalStrut(10));
    card.add(valueLabel);

    return card;
}



    // =================================================
    // CLEAR STUDENT FIELDS
    // =================================================

    private void clearFields() {

        idField.setText("");
        nameField.setText("");
        ageField.setText("");
        genderField.setText("");
        courseField.setText("");
        semesterField.setText("");
        emailField.setText("");
        attendanceField.setText("");

        idField.setEditable(true);

        studentTable.clearSelection();
    }


    // =================================================
    // LOAD STUDENTS ON JTable
    // =================================================

    private void loadStudents() {

        tableModel.setRowCount(0);

        List<Student> students =
                studentService.getAllStudents();


        for (Student student : students) {

            tableModel.addRow(
                    new Object[]{

                            student.getStudentId(),

                            student.getName(),

                            student.getAge(),

                            student.getGender(),

                            student.getCourse(),

                            student.getSemester(),

                            student.getEmail(),

                            student.getAttendance()
                    }
            );
        }
    }


    // =================================================
    // LOAD SUBJECTS ON JTable
    // =================================================

    private void loadSubjects() {

        subjectTableModel.setRowCount(0);

        List<Subject> subjects =
                subjectService.getAllSubjects();


        for (Subject subject : subjects) {

            subjectTableModel.addRow(
                    new Object[]{

                            subject.getSubjectId(),

                            subject.getSubjectName(),

                            subject.getSemester(),

                            subject.getCourse()
                    }
            );
        }
    }


    // =================================================
    // CLEAR SUBJECT FIELDS
    // =================================================

    private void clearSubjectFields() {

        subjectIdField.setText("");
        subjectNameField.setText("");
        subjectSemesterField.setText("");
        subjectCourseField.setText("");

        subjectTable.clearSelection();
    }


    // =================================================
    // LOAD MARKS ON JTable
    // =================================================

    private void loadMarks() {

        markTableModel.setRowCount(0);

        List<Mark> marks =
                markService.getAllMarks();


        for (Mark mark : marks) {

            markTableModel.addRow(
                    new Object[]{

                            mark.getMarkId(),

                            mark.getStudentId(),

                            mark.getSubjectId(),

                            mark.getMarks()
                    }
            );
        }
    }


    // =================================================
    // ADD MARK TO JTable
    // =================================================

    private void addMarkToTable(
            Mark mark
    ) {

        markTableModel.addRow(
                new Object[]{

                        mark.getMarkId(),

                        mark.getStudentId(),

                        mark.getSubjectId(),

                        mark.getMarks()
                }
        );
    }


    // =================================================
    // SHOW MARK SEARCH RESULTS
    // =================================================

    private void showMarkSearchResults(
            List<Mark> results
    ) {

        markTableModel.setRowCount(0);


        if (results.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No marks found.",
                    "Search",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }


        for (Mark mark : results) {

            addMarkToTable(mark);
        }
    }


    // =================================================
    // CLEAR MARK FIELDS
    // =================================================

    private void clearMarkFields() {

        markIdField.setText("");
        markStudentIdField.setText("");
        markSubjectIdField.setText("");
        marksField.setText("");

        markTable.clearSelection();
    }

    // =================================================
    // Filter MARK FIELDS
    // =================================================

private void sortMarksTable() {

    if (markTableModel == null) {
        return;
    }

    java.util.List<Object[]> rows = new java.util.ArrayList<>();

    for (int i = 0; i < markTableModel.getRowCount(); i++) {

        Object[] row = new Object[markTableModel.getColumnCount()];

        for (int j = 0; j < row.length; j++) {
            row[j] = markTableModel.getValueAt(i, j);
        }

        rows.add(row);
    }

    String selected = (String) marksFilterCombo.getSelectedItem();

    if ("Marks: Highest to Lowest".equals(selected)) {

        rows.sort((a, b) ->
            Integer.compare(
                Integer.parseInt(b[3].toString()),
                Integer.parseInt(a[3].toString())
            )
        );

    } else if ("Marks: Lowest to Highest".equals(selected)) {

        rows.sort((a, b) ->
            Integer.compare(
                Integer.parseInt(a[3].toString()),
                Integer.parseInt(b[3].toString())
            )
        );
    }

    markTableModel.setRowCount(0);

    for (Object[] row : rows) {
        markTableModel.addRow(row);
    }
}

    // =================================================
    // update dashboard
    // =================================================

private void updateDashboardStats() {

    if (totalStudentsValueLabel != null) {
        totalStudentsValueLabel.setText(
            String.valueOf(studentService.getTotalStudents())
        );
    }
}



    // =================================================
    // MAIN
    // =================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            AdminDashboard dashboard =
                    new AdminDashboard();

            dashboard.setVisible(true);

        });
    }
}