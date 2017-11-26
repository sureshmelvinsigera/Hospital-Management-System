
public class Room {
    
    private int room_id;
    private String room_name;

    public Room() {
    }

    public Room(String room_name) {
        this.room_name = room_name;
    }

    public void createTable() {
        Database.executeQuery("DROP TABLE IF EXISTS Room");
        Database.executeQuery("CREATE TABLE Room (room_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " room_name STRING)");
    }

    public void insert(String room_name) {
        Database.executeQuery("INSERT INTO Room values('" + room_name + "');");
    }

    public void insert() {
        Database.executeQuery("INSERT INTO Room values('" + room_name + "');");
    }
}
