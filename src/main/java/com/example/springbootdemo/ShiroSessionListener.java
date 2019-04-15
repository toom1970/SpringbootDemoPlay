package com.example.springbootdemo;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class ShiroSessionListener implements SessionListener {
    private final AtomicInteger sessionCount = new AtomicInteger(0);
    Logger logger = Logger.getLogger("SessionListener");

    @Override
    public void onStart(Session session) {
        //count+1
        sessionCount.incrementAndGet();
        logger.info("session start");
    }

    @Override
    public void onStop(Session session) {
        //count-1
        sessionCount.decrementAndGet();
        logger.info("session stop");
    }

    @Override
    public void onExpiration(Session session) {
        //会话过期 count-1
        sessionCount.decrementAndGet();
        logger.info("session expiration");
    }

    public AtomicInteger getSessionCount() {
        return sessionCount;
    }
}
