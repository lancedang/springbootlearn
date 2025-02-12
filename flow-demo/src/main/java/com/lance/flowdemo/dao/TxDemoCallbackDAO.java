package com.lance.flowdemo.dao;


import com.lance.flowdemo.entity.TxDemoCallback;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

@DAO
public interface TxDemoCallbackDAO {

    String TABLE_NAME = " tx_demo_callback ";

    String INSERT_COLUMNS = "message";

    String SELECT_COLUMNS = "id," + INSERT_COLUMNS;

    @ReturnGeneratedKeys
    @SQL("insert into " + TABLE_NAME + " (" + INSERT_COLUMNS + ") values(" +
            ":t.message)")
    Integer save(@SQLParam("t") TxDemoCallback txDemoCallback);


}
