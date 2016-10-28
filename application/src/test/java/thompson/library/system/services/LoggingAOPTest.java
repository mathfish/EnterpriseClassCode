package thompson.library.system.services;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import thompson.library.system.daos.PatronDao;
import thompson.library.system.dtos.PatronDto;
import thompson.library.system.utilities.LibraryConfig;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {LibraryConfig.class})
public class LoggingAOPTest {
    Appender mockAppender;
    ArgumentCaptor<LoggingEvent> captor;

    @Autowired
    PatronDao patronDao;

    @Before
    public void setup() {
        mockAppender = mock(Appender.class);
        captor = ArgumentCaptor.forClass(LoggingEvent.class);
        final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.addAppender(mockAppender);
    }

    @Test
    public void testLoggingAop(){
        final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.addAppender(mockAppender);

        PatronDto dto = patronDao.getPatron("jd@email.com");
        assertEquals("doe",dto.getLastname());

        verify(mockAppender, times(4)).doAppend(captor.capture());
        final LoggingEvent loggingEvent = captor.getValue();
        assertThat(loggingEvent.getLevel(), is(Level.DEBUG));
        String[] msg = loggingEvent.getFormattedMessage().split(":");
        assertEquals("Time spent in PatronDao.getPatron(..)",msg[0].trim());
    }
}
