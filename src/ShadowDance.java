import bagel.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * adapted from solution for SWEN20003 Project 1, Semester 2, 2023
 *
 * @author Thi Hong Minh Dao
 */
public class ShadowDance extends AbstractGame {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW DANCE";
    private final Image BACKGROUND_IMAGE = new Image("res/background.png");
    public final static String FONT_FILE = "res/FSO8BITR.TTF";
    private final static int TITLE_X = 220;
    private final static int TITLE_Y = 250;
    private final static int INS_X_OFFSET = 100;
    private final static int INS_Y_OFFSET = 190;
    private final static int SCORE_LOCATION = 35;
    private final Font TITLE_FONT = new Font(FONT_FILE, 64);
    private final Font INSTRUCTION_FONT = new Font(FONT_FILE, 24);
    private final Font SCORE_FONT = new Font(FONT_FILE, 30);
    private static final String INSTRUCTIONS = "SELECT LEVELS WITH\n NUMBER KEYS\n\n\n1         2           3";
    private static final String END_INSTRUCTIONS = "PRESS SPACE TO RETURN TO LEVEL SELECTION";
    private static final int CLEAR_SCORE_LEVEL_1 = 150;
    private static final int CLEAR_SCORE_LEVEL_2 = 400;
    private static final int CLEAR_SCORE_LEVEL_3 = 350;

    private static final String CLEAR_MESSAGE = "CLEAR!";
    private static final String TRY_AGAIN_MESSAGE = "TRY AGAIN";
    private static Accuracy accuracy;
    private static final ArrayList<Lane> lanes = new ArrayList<>();
    private static final ArrayList<Enemy> enemies = new ArrayList<>();
    private int numLanes = 0;
    private int score = 0;
    private static int currFrame = 0;
    private boolean started = false;
    private boolean finished = false;
    private static Guardian guardian;
    private boolean read = false;

    private boolean paused = false;
    private int level;

    public ShadowDance() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }

    public static ArrayList<Lane> getLanes() {
        return lanes;
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowDance game = new ShadowDance();
        game.run();
    }

    public static Accuracy getAccuracy() {
        return accuracy;
    }


    private void readCsv(String csvFile) {
        if (!read) {
            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                String textRead;
                while ((textRead = br.readLine()) != null) {
                    String[] splitText = textRead.split(",");

                    if (splitText[0].equals("Lane")) {
                        // reading lanes
                        String laneType = splitText[1];
                        int pos = Integer.parseInt(splitText[2]);
                        Lane lane = new Lane(laneType, pos);
                        lanes.add(lane);
                    } else if (splitText[0].equals("Special")) {
                        String type = splitText[1];
                        int frame = Integer.parseInt(splitText[2]);
                        for (Lane lane : lanes) {
                            if (lane.getType().equals("Special")) {
                                switch (type) {
                                    case "SlowDown":
                                        lane.addNote(new SlowDownNote(type, frame, lane.getLocation()));
                                        break;
                                    case "SpeedUp":
                                        lane.addNote(new SpeedUpNote(type, frame, lane.getLocation()));
                                        break;
                                    case "DoubleScore":
                                        lane.addNote(new DoubleScoreNote(type, frame, lane.getLocation()));
                                        break;
                                }
                            }
                        }
                    } else {
                        // reading notes
                        String dir = splitText[0];
                        String type = splitText[1];
                        int appearanceFrame = Integer.parseInt(splitText[2]);
                        for (Lane lane : lanes) {
                            if (lane.getType().equals(dir)) {
                                if (type.equals("Normal")) {
                                    lane.addNote(new NormalNote(dir, appearanceFrame, lane.getLocation()));
                                } else if (type.equals("Hold")) {
                                    lane.addNote(new HoldNote(dir, appearanceFrame, lane.getLocation()));
                                } else if (type.equals("Bomb")) {
                                    lane.addNote(new BombNote(dir, appearanceFrame, lane.getLocation()));
                                }
                            }
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    // reset the game
    public void reset() {
        read = false;
        started = false;
        finished = false;
        currFrame = 0;
        for (Lane lane : lanes) {
            lane.clearNotes();
        }
        Note.speed = 2;
        lanes.clear();
        enemies.clear();
        score = 0;
    }

    // set up the game
    public void gameSetUp(Input input) {
        for (Lane lane : lanes) {
            score += lane.update(input, accuracy);
        }

        accuracy.update();
        finished = checkFinished();

    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {

        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);

        if (input.wasPressed(Keys.NUM_1)) {
            accuracy = new Accuracy();
            readCsv("res/level1.csv");
            level = 1;
        }
        if (input.wasPressed(Keys.NUM_2)) {
            accuracy = new Accuracy();
            level = 2;
            readCsv("res/level2.csv");
        }
        if (input.wasPressed(Keys.NUM_3)) {
            accuracy = new Accuracy();
            level = 3;
            readCsv("res/level3.csv");
            guardian = new Guardian();
        }

        if (!started) {
            // starting screen
            TITLE_FONT.drawString(GAME_TITLE, TITLE_X, TITLE_Y);
            INSTRUCTION_FONT.drawString(INSTRUCTIONS,
                    TITLE_X + INS_X_OFFSET, TITLE_Y + INS_Y_OFFSET);

            if (input.wasPressed(Keys.NUM_1) || input.wasPressed(Keys.NUM_2) || input.wasPressed(Keys.NUM_3)) {
                started = true;
            }
        } else if (finished) {
            // end screen
            read = true;
            if ((score >= CLEAR_SCORE_LEVEL_1 && level == 1) || (score >= CLEAR_SCORE_LEVEL_2 && level == 2) ||
                    score >= CLEAR_SCORE_LEVEL_3 && level == 3) {
                TITLE_FONT.drawString(CLEAR_MESSAGE,
                        WINDOW_WIDTH / 2 - TITLE_FONT.getWidth(CLEAR_MESSAGE) / 2,
                        WINDOW_HEIGHT / 2);
            } else {
                TITLE_FONT.drawString(TRY_AGAIN_MESSAGE,
                        WINDOW_WIDTH / 2 - TITLE_FONT.getWidth(TRY_AGAIN_MESSAGE) / 2,
                        WINDOW_HEIGHT / 2);
            }
            INSTRUCTION_FONT.drawString(END_INSTRUCTIONS, WINDOW_WIDTH / 2 - INSTRUCTION_FONT.getWidth(END_INSTRUCTIONS) / 2, 500);
            if (input.wasPressed(Keys.SPACE)) {
                reset();
            }
        } else {
            // gameplay
            SCORE_FONT.drawString("Score " + score, SCORE_LOCATION, SCORE_LOCATION);
            if (level == 1 || level == 2) {
                currFrame++;
                gameSetUp(input);
            } else if (level == 3) {
                currFrame++;
                guardian.draw();
                if (currFrame % 600 == 0) {
                    enemies.add(new Enemy());
                }
                for (Enemy enemy : enemies) {
                    enemy.draw();
                }
                guardian.update(input);
                gameSetUp(input);
            }
        }

    }

    public static int getCurrFrame() {
        return currFrame;
    }

    // Check if the game is finished
    private boolean checkFinished() {
        for (Lane lane : lanes) {
//            System.out.println(lane.);
            if (!lane.isFinished()) {
                return false;
            }
        }
        return true;
    }

    public static ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public static Guardian getGuardian() {
        return guardian;
    }
}