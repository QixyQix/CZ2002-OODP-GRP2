package ui;

public class TableUI {
    private static TableUI instance;

    /**
     * Returns the TableMgr instance and creates an instance if it does not exist
     * 
     * @return instance
     */
    public static TableUI getInstance() {
        if (instance == null) {
            instance = new TableUI();
        }
        return instance;
    }

    public void viewTableAvailability() {

    }
}
