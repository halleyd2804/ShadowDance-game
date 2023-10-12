import bagel.*;

import java.util.ArrayList;

/**
 * Class for the lanes which notes fall down
 */
public class Lane {
    private static final int HEIGHT = 384;
    private static final int TARGET_HEIGHT = 657;
    private final String type;
    private final Image image;
    private final ArrayList<Note> notes = new ArrayList<>();
    private Keys relevantKey;
    private final int location;
    private int currNote = 0;
    public int getLocation(){
        return location;
    }

    public Lane(String dir, int location) {
        this.type = dir;
        this.location = location;
        image = new Image("res/lane" + dir + ".png");
        switch (dir) {
            case "Left":
                relevantKey = Keys.LEFT;
                break;
            case "Right":
                relevantKey = Keys.RIGHT;
                break;
            case "Up":
                relevantKey = Keys.UP;
                break;
            case "Down":
                relevantKey = Keys.DOWN;
                break;
            case "Special":
                relevantKey = Keys.SPACE;
                break;
        }
    }

    public String getType() {
        return type;
    }

    /**
     * updates all the notes in the lane
     */
    public int update(Input input, Accuracy accuracy) {
        draw();


        for (Note note : notes) {
            note.update();
        }

        if (currNote < notes.size()) {
            int score = notes.get(currNote).checkScore(input, accuracy, TARGET_HEIGHT, relevantKey);
            if (notes.get(currNote).isCompleted()) {
                currNote++;
                return score;
            }
        }

        return Accuracy.NOT_SCORED;
    }

    public void addNote(Note n) {
        notes.add(n);
    }


    /**
     * Finished when all the notes have been pressed or missed
     */
    public boolean isFinished() {
        for (int i = 0; i < notes.size(); i++) {
            if (!notes.get(i).isCompleted()) {
                return false;
            }
        }
        return true;
    }

    /**
     * draws the lane and the notes
     */
    public void draw() {
        image.draw(location, HEIGHT);

        for (int i = currNote; i < notes.size(); i++) {
            notes.get(i).draw(location);
        }
    }

}
