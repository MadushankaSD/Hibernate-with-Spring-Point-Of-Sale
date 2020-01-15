package io.github.madushanka.pos.dao;

import org.hibernate.Session;

public interface SuperDAO {
    Session getSession();
}
