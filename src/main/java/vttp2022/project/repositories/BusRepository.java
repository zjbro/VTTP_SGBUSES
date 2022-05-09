package vttp2022.project.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.project.model.Bookmark;

import static vttp2022.project.repositories.Queries.*;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BusRepository {
    
    @Autowired
    private JdbcTemplate template;

    public boolean addBookmark(String busStopCode, String description, String username){
        Integer added = 0;
        added = template.update(SQL_INSERT_NEW_BOOKMARK, busStopCode, description, username);
        return added > 0;
    }

    public String getDescription(String busStopCode){
        String description = null;
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_ALL_BY_BUS_STOP_CODE, busStopCode);
        while (rs.next()){
            description = rs.getString("description");
        }
        return description;
    }

    public List<Bookmark> getBookmarks(String username){
        List<Bookmark> bookmarks = new ArrayList<>();
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_ALL_FROM_BOOKMARK, username);
        while (rs.next()){
            Bookmark bMark = new Bookmark();
            bMark.setBusStopCode(rs.getString("bus_stop_code"));
            bMark.setDescription(rs.getString("description"));
            bookmarks.add(bMark);
        }
        return bookmarks;
    }

    public boolean addUser (String username, String password){
        Integer added = 0;
        added = template.update(SQL_INSERT_NEW_USERS, username, password);
        return added > 0;
    }

    // public boolean populateBusStops(String busStopCode, String description){
    //     Integer added = 0;
    //     added = template.update(SQL_INSERT_BUS_STOP_DETAILS, busStopCode, description);
    //     return added > 0;
    // }
    
}
