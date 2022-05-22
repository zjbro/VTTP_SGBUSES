package vttp2022.project.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vttp2022.project.model.Bookmark;

import static vttp2022.project.repositories.Queries.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BusRepository {
    
    @Autowired
    private JdbcTemplate template;

    public boolean addBookmark(String busStopCode, String description, String username){
        Integer added = 0;
        Optional<String> optBookmark = Optional.empty();
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_FROM_BOOKMARK_BY_CODE_AND_NAME,busStopCode, username);
        while (rs.next()){
            optBookmark = Optional.of(rs.getString("bus_stop_code"));
        }
        if(optBookmark.isEmpty()){
        added = template.update(SQL_INSERT_NEW_BOOKMARK, busStopCode, description, username);
        return added > 0;
        } else 
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

    public boolean deleteBookmark (String busStopCode, String username){
        Integer added = 0;
        added = template.update(SQL_DELETE_BOOKMARKS, busStopCode, username);
        return added > 0;
    }

    public boolean addUser (String username, String password){
        Integer added = 0;
        Optional<String> optName = Optional.empty();    
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_ALL_BY_USERNAME, username);
        while (rs.next()){
            optName = Optional.of(rs.getString("username"));
        }
        if(optName.isEmpty()){
        added = template.update(SQL_INSERT_NEW_USERS, username, password);
        return added > 0;
        } else {
        return added > 0;
        }
    }

    @Transactional
    public boolean deleteUser (String username){
        Integer removeBookmarks = template.update(SQL_DELETE_BOOKMARKS_BY_USER, username);
        List<Bookmark> bookmarks = getBookmarks(username);
        if (bookmarks.size()==0){
            Integer removeUser = template.update(SQL_DELETE_USER, username);
            return removeUser > 0;
        } else {
            return removeBookmarks < 0;
        }

    }



    // public boolean populateBusStops(String busStopCode, String description){
    //     Integer added = 0;
    //     added = template.update(SQL_INSERT_BUS_STOP_DETAILS, busStopCode, description);
    //     return added > 0;
    // }
    
}
