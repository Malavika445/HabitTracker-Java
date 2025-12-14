package com.habittracker;

import java.sql.*;
import java.util.Scanner;

public class HabitTrackerApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add Habit");
            System.out.println("2. View Habits");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addHabit(sc);
                    break;

                case 2:
                    viewHabits();
                    break;

                case 3:
                    System.out.println("Exiting...");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    public static void addHabit(Scanner sc) {

        System.out.print("Enter habit name: ");
        String name = sc.nextLine();

        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO habits (habit_name, status, created_date) VALUES (?, ?, CURDATE())";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, "Pending");

            ps.executeUpdate();
            System.out.println("Habit added successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void viewHabits() {

        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM habits");

            System.out.println("\nID | Habit | Status | Date");
            System.out.println("--------------------------------");

            while (rs.next()) {
                System.out.println(
                    rs.getInt("id") + " | " +
                    rs.getString("habit_name") + " | " +
                    rs.getString("status") + " | " +
                    rs.getDate("created_date")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

