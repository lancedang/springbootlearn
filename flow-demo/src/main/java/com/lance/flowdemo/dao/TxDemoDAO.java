package com.lance.flowdemo.dao;


import com.lance.flowdemo.entity.TxDemo;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

@DAO
public interface TxDemoDAO {

    String TABLE_NAME = " tx_demo ";

    String INSERT_COLUMNS = "name";

    String SELECT_COLUMNS = "id," + INSERT_COLUMNS;

    @ReturnGeneratedKeys
    @SQL("insert into " + TABLE_NAME + " (" + INSERT_COLUMNS + ") values(" +
            ":t.name)")
    Integer save(@SQLParam("t") TxDemo txDemo);


}
