package vttp2022.project.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static vttp2022.project.repositories.Queries.*;

@Repository
public class BusRepository {
    
    @Autowired
    private JdbcTemplate template;

    public boolean addBookmark(String busStopCode, String description, String username){
        Integer added = 0;
        added = template.update(SQL_INSERT_NEW_BOOKMARK, busStopCode, description, username);
        return added > 0;
    }
}
