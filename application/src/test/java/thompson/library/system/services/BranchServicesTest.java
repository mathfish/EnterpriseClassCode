package thompson.library.system.services;

import org.junit.Test;
import thompson.library.system.daos.*;
import thompson.library.system.dtos.*;

import java.util.Calendar;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;


public class BranchServicesTest {
    private DaoManager daoManager;
    private BranchItemDao branchItemDao;
    private BranchItemDto branchItemDto;
    private BranchItemCheckoutDto branchItemCheckoutDto;
    private CheckoutDto checkoutDto;
    private BranchItemCheckoutDao branchItemCheckoutDao;
    private BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput;
    private CheckoutDao checkoutDao;
    private ReservationDao reservationDao;
    private ReservationDto reservationDto;
    private PatronDao patronDao;
    private Dto dto;

    public BranchServicesTest(){
        daoManager = mock(DaoManager.class);
        patronDao = mock(PatronDao.class);
        dto = mock(Dto.class);
        branchItemDao = mock(BranchItemDao.class);
        branchItemDto = mock(BranchItemDto.class);
        branchItemCheckoutDto = mock(BranchItemCheckoutDto.class);
        branchItemCheckoutDao = mock(BranchItemCheckoutDao.class);
        itemReturnOutput = mock(BranchItemCheckoutDao.ItemReturnOutput.class);
        checkoutDao = mock(CheckoutDao.class);
        checkoutDto = mock(CheckoutDto.class);
        reservationDao = mock(ReservationDao.class);
        reservationDto = mock(ReservationDto.class);



        when(daoManager.getBranchItemCheckoutDao()).thenReturn(branchItemCheckoutDao);
        when(daoManager.getCheckoutDao()).thenReturn(checkoutDao);
        when(daoManager.getReservationDao()).thenReturn(reservationDao);
        when(daoManager.getBranchItemDao()).thenReturn(branchItemDao);
        when(daoManager.getPatronDao()).thenReturn(patronDao);

        when(branchItemCheckoutDao.getBranchItemCheckout(any())).thenReturn(branchItemCheckoutDto);
        when(branchItemCheckoutDao.updateBranchItemCheckout(any())).thenReturn(itemReturnOutput);
        when(checkoutDao.getCheckout(any())).thenReturn(checkoutDto);
        when(patronDao.getPatron(itemReturnOutput)).thenReturn(dto);

    }

    @Test
    public void testReturnItemNoReservationNotOverdueNotAllReturned(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        when(branchItemCheckoutDto.getDueDate()).thenReturn(new java.sql.Date(calendar.getTime().getTime()));
        when(branchItemCheckoutDao.getNumberOfItemsReturnedFromCheckout(any())).thenReturn(1);
        when(checkoutDto.getNumberofitems()).thenReturn(2);
        when(reservationDao.fulfillReservation(any())).thenReturn(null);

        BranchServicesImpl impl = new BranchServicesImpl(daoManager);
        impl.returnItem(branchItemDto);

        verify(branchItemCheckoutDto, times(0)).setOverdue(true);
        verify(itemReturnOutput, times(0)).setReturned(true);
        verify(itemReturnOutput, times(0)).setReserved(true);
        verify(branchItemDao,times(1)).updateBranchItem(itemReturnOutput);
        verify(itemReturnOutput, times(1)).completeReturn();

    }

    @Test
    public void testReturnItemReservationOverdueAllReturned(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        when(branchItemCheckoutDto.getDueDate()).thenReturn(new java.sql.Date(calendar.getTime().getTime()));
        when(branchItemCheckoutDto.isRenew()).thenReturn(false);
        when(branchItemCheckoutDao.getNumberOfItemsReturnedFromCheckout(any())).thenReturn(2);
        when(checkoutDto.getNumberofitems()).thenReturn(2);
        when(reservationDao.fulfillReservation(any())).thenReturn(reservationDto);
        when(dto.getFirstname()).thenReturn("test1");
        when(dto.getLastname()).thenReturn("test2");
        when(dto.getEmail()).thenReturn("test4");
        when(reservationDto.getReservationid()).thenReturn(3333);

        BranchServicesImpl impl = new BranchServicesImpl(daoManager);
        impl.returnItem(branchItemDto);

        verify(branchItemCheckoutDto, times(1)).setOverdue(true);
        verify(itemReturnOutput, times(1)).setReturned(true);
        verify(checkoutDao, times(1)).updateCheckout(itemReturnOutput);
        verify(itemReturnOutput, times(1)).setReserved(true);
        verify(itemReturnOutput, times(1)).completeReturn();
    }



}
