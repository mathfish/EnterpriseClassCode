package thompson.library.system.services;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import thompson.library.system.dtos.BranchItemCheckoutDto;
import thompson.library.system.dtos.BranchItemDto;
import thompson.library.system.dtos.CheckoutDto;
import thompson.library.system.utilities.LibraryConfig;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LibraryConfig.class)
@Transactional
public class BranchServicesTest {
    Appender mockAppender;
    ArgumentCaptor<LoggingEvent> captor;

    @Autowired
    JdbcOperations jdbcOperations;

    @Autowired
    BranchServices branchServices;

    private BranchItemDto branchItemDto = mock(BranchItemDto.class);

    @Before
    public void setup(){
        mockAppender = mock(Appender.class);
        captor = ArgumentCaptor.forClass(LoggingEvent.class);
    }

    //Test covers all functionality of service
    @Test
    public void testReturnItemIsReservedOverdueNotAllReturned(){
        final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.addAppender(mockAppender);

        //database already populated with branchitem id 2 and checkoutid 1 for intersection entity
        when(branchItemDto.getBranchitemid()).thenReturn(2);
        branchServices.returnItem(branchItemDto);

        BranchItemCheckoutDto branchItemCheckoutDto =
        jdbcOperations.queryForObject("SELECT * FROM branchitemcheckout WHERE branchitemid = ? AND checkoutid = ?",
                (rs, rowNum) -> {
                    return new BranchItemCheckoutDto(rs.getInt("checkoutid"), rs.getInt("branchitemid"), rs.getBoolean("overdue"),
                            rs.getDate("duedate"), rs.getBoolean("renew"), rs.getDate("renewdate"), rs.getBoolean("returned"), rs.getDate("returndate"));
                }, 2, 1);

        assertTrue(branchItemCheckoutDto.isReturned());
        assertTrue(branchItemCheckoutDto.isOverdue());

        CheckoutDto checkoutDto =
        jdbcOperations.queryForObject("SELECT * FROM checkout WHERE checkoutid = ?",
                (rs, rowNum) -> {
                    return new CheckoutDto(rs.getInt("checkoutid"), rs.getInt("patronid"), rs.getTimestamp("checkoutdate"),
                            rs.getInt("numberofitems"), rs.getBoolean("overdue"), rs.getBoolean("itemsreturned"));
                }, 1);

        assertFalse(checkoutDto.isItemsreturned());

        BranchItemDto branchItemDto =
        jdbcOperations.queryForObject("SELECT * FROM branchitem WHERE branchitemid = ?",
                (rs, rowNum) -> {
                    return new BranchItemDto(rs.getInt("branchitemid"), rs.getBoolean("checkedout"), rs.getBoolean("reserved"),
                            rs.getBoolean("inTransit"), rs.getInt("currentlocation"), rs.getInt("branchid"));
                }, 2);

        assertFalse(branchItemDto.isCheckedout());
        assertTrue(branchItemDto.isReserved());

        verify(mockAppender, Mockito.atLeastOnce()).doAppend(captor.capture());
        LoggingEvent loggingEvent = captor.getValue();
        if(loggingEvent.getLevel().equals(Level.INFO)){
            assertTrue(loggingEvent.getFormattedMessage().startsWith("Send email to"));
        }

    }

}
