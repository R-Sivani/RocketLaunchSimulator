package RocketLaunch;

import java.util.Scanner;

public class RocketLaunchSimulator {
    private static String stage = "Pre-Launch";
    private static int fuel = 100;
    private static int altitude = 0;
    private static int speed = 0;
    private static boolean checksComplete = false;
    private static boolean missionComplete = false;
    private static final int STAGE_1_DURATION = 100; 
    private static final int STAGE_2_DURATION = 200;  
    private static final int TOTAL_MISSION_DURATION = STAGE_1_DURATION + STAGE_2_DURATION;
    private static final int ORBIT_ALTITUDE = 1000;  // assuming orbit is achieved at 1000 km

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Rocket Launch Simulator");

        while (!missionComplete) {
            System.out.print("Enter command: ");
            String input = scanner.nextLine().trim();
            if (input.equals("start_checks")) {
                startChecks();
            } else if (input.equals("launch")) {
                if (checksComplete) {
                    launch();
                } else {
                    System.out.println("Pre-Launch checks are not complete.");
                }
            } else if (input.startsWith("fast_forward")) {
                String[] parts = input.split(" ");
                if (parts.length == 2) {
                    try {
                        int seconds = Integer.parseInt(parts[1]);
                        fastForward(seconds);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number of seconds.");
                    }
                } else {
                    System.out.println("Usage: fast_forward X");
                }
            } else {
                System.out.println("Unknown command. Use start_checks, launch, or fast_forward X.");
            }
        }

        scanner.close();
    }

    private static void startChecks() {
        System.out.println("All systems are 'Go' for launch.");
        checksComplete = true;
    }

    private static void launch() {
        for (int i = 1; i <= TOTAL_MISSION_DURATION; i++) {
            updateStatus(i);
            printStatus(i);

            if (missionComplete) {
                return;
            }
        }
    }

    private static void fastForward(int seconds) {
        for (int i = 1; i <= seconds; i++) {
            updateStatus(i);

            if (missionComplete) {
                return;
            }
        }
        System.out.println("Fast-forwarded " + seconds + " seconds.");
    }

    private static void updateStatus(int currentSecond) {
        if (fuel <= 0) {
            System.out.println("Mission Failed due to insufficient fuel.");
            missionComplete = true;
            return;
        }

        fuel -= 1;
        altitude += 10;
        speed += 100;

        if (currentSecond == STAGE_1_DURATION) {
            System.out.println("Stage 1 complete. Separating stage. Entering Stage 2.");
            stage = "2";
            speed = 8000; 
        }

        if (altitude >= ORBIT_ALTITUDE) {
            System.out.println("Orbit achieved! Mission Successful.");
            missionComplete = true;
        }
    }

    private static void printStatus(int second) {
        System.out.println("Second " + second + ": Stage: " + stage +
                ", Fuel: " + fuel + "%, Altitude: " + altitude + " km, Speed: " + speed + " km/h");
    }
}