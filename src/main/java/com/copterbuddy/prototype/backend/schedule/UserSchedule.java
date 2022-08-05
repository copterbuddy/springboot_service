package com.copterbuddy.prototype.backend.schedule;

import com.copterbuddy.prototype.backend.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserSchedule {

    @Autowired
    private UserService userService;

    //1 => second
    //2 => minute
    //3 => hour
    //4 => day
    //5 => month
    //6 => year

    /**
     * Every minute (UTC Time)
     */
    @Scheduled(cron = "0 * * * * *", zone = "Asia/Bangkok")
    public void testEveryMinute() {
        log.info("Hello, What's up?");
    }

    /**
     * Everyday at 00:00 (UTC Time)
     */
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Bangkok")
    public void testEveryMidnight() {
        log.info("Hello, What's up?");
    }

    /**
     * Everyday at 10:00 (UTC Time)
     */
    @Scheduled(cron = "0 50 10 * * *")
    public void testEveryNineAM() {
        log.info("Hey Hoo!!");
    }

    /**
     * Everyday at 23:00 (Thailand Time)
     */
    @Scheduled(cron = "0 00 23 * * *", zone = "Asia/Bangkok")
    public void testEveryBeforeMidnight() {
        log.info("Hey Hoo!!");
    }
}
