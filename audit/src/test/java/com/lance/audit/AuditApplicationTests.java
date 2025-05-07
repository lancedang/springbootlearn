package com.lance.audit;

import com.lance.audit.entity.BookEntity;
import com.lance.audit.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuditApplicationTests {

	@Autowired
	private BookService bookService;

	@Test
	public void insert1() throws InterruptedException {
		BookEntity entity = new BookEntity();
		entity.setId(1L);
		entity.setName("sss");

		new Thread(() -> {
			System.out.println("线程1先执行");
			bookService.addBookPlus(entity);
		}).start();

		new Thread(() -> {
			try {
				System.out.println("线程2后执行");
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			bookService.addBookPlus(entity);
		}).start();

		TimeUnit.MINUTES.sleep(10);

	}

}
