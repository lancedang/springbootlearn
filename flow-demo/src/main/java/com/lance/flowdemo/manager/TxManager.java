package com.lance.flowdemo.manager;

import com.lance.flowdemo.entity.TxDemo;
import com.lance.flowdemo.entity.TxDemoCallback;
import com.lance.flowdemo.service.TxDemoCallbackService;
import com.lance.flowdemo.service.TxDemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@Slf4j
public class TxManager {

    @Autowired
    private TxDemoService txDemoService;

    @Autowired
    private TxDemoCallbackService txDemoCallbackService;

    @Transactional
    public void addTx(TxDemo txDemo) {
        log.info("tx task begin");


        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void suspend() {
                log.info("suspend");
            }

            @Override
            public void resume() {
                log.info("resume");
            }

            @Override
            public void flush() {
                log.info("flush");
            }

            @Override
            public void beforeCommit(boolean readOnly) {
                log.info("beforeCommit");
            }

            @Override
            public void beforeCompletion() {
                log.info("beforeCompletion");
            }

            @Override
            public void afterCommit() {
                String transactionName = TransactionSynchronizationManager.getCurrentTransactionName();
                log.info("afterCommit and save new table TxDemoCallback, transactionName={}", transactionName);
                TxDemoCallback callback = new TxDemoCallback();
                callback.setMessage("callback1");
                txDemoCallbackService.saveTxDemoCallback(callback);
            }

            @Override
            public void afterCompletion(int status) {
                log.info("afterCompletion status={}", status);
                if (TransactionSynchronization.STATUS_COMMITTED == status) {
                    log.info("afterCompletion commit success");
                } else if (TransactionSynchronization.STATUS_ROLLED_BACK == status) {
                    log.info("afterCompletion roll back happen");
                }
            }
        });

        //1）save第一张表
        String transactionName = TransactionSynchronizationManager.getCurrentTransactionName();
        log.info("save first table TxDemoCallback, transactionName={}", transactionName);

        txDemoService.saveTxDemo(txDemo);

    /*    //2）save第二张表
        TxDemoCallback callback = new TxDemoCallback();
        callback.setMessage("callback1");

        txDemoCallbackService.saveTxDemoCallback(callback);
*/
    }
}
