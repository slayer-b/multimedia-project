package com.multimedia.dao;

import com.multimedia.model.beans.Counter;
import common.dao.GenericDAOHibernate;

public class CounterDAO extends GenericDAOHibernate<Counter, Long> {

    public CounterDAO() {
        super(Counter.class);
    }

}
