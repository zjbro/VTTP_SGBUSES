package vttp2022.project.repositories;

public interface Queries {
    public static final String SQL_SELECT_AND_COUNT_USERS_BY_NAME =
        "select count(*) as user_count from users where username = ? and password = sha1(?)";

    public static final String SQL_INSERT_NEW_BOOKMARK =
        "insert into bookmarks (bus_stop_code, description,username) values (?, ?, ?)";

    public static final String SQL_SELECT_ALL_FROM_BOOKMARK =
        "select * from bookmarks where username = ?";

    public static final String SQL_SELECT_ALL_BY_BUS_STOP_CODE =
        "select * from bus_stops where bus_stop_code = ?";

    public static final String SQL_INSERT_NEW_USERS =
        "insert into users (username, password) values (?, sha1(?))";

    // public static final String SQL_INSERT_BUS_STOP_DETAILS =
    //     "insert into bus_stops (bus_stop_code, description) values (?,?)";
        
}
    
 