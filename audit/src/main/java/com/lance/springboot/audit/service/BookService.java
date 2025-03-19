package com.lance.springboot.audit.service;

import com.lance.springboot.audit.dao.BookDAO;
import com.lance.springboot.audit.entity.BookEntity;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class BookService {

    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private RedissonClient redisClient;

    public BookEntity addBook(BookEntity bookEntity) {
        BookEntity entity = bookDAO.save(bookEntity);
        return entity;
    }


    //验证高并发场景下的幂等机制：吸收：外层Transaction事务需要和内存分布式锁粒度保持一致
    //可以编程式实现transaction事务
    @Transactional
    public void addBookPlus(BookEntity bookEntity) {
        RLock lock = redisClient.getLock(bookEntity.getId() + "");
        System.out.println("开始锁住=" + bookEntity.getId());

        try {
            lock.lock();
            BookEntity entity = bookDAO.save(bookEntity);


            System.out.println("插入成功，未提交=" + bookEntity.getId());
        } catch (Exception e) {
            log.error("错误，", e);
        } finally {
            lock.unlock();
        }

        //模拟还未提交场景
        try {
            TimeUnit.SECONDS.sleep(30);
            System.out.println("插入成功，提交结束=" + bookEntity.getId());

        } catch (Exception e) {
            log.error("中断，", e);
        }finally {

        }
    }
}
