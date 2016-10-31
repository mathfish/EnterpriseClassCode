package thompson.library.system.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thompson.library.system.daos.*;

import thompson.library.system.dtos.*;


import java.util.Calendar;

@Component
public class BranchServicesImpl implements BranchServices {
    private static final Logger logger = LoggerFactory.getLogger(BranchServicesImpl.class);
    private DaoManager daoManager;

    @Autowired
    BranchServicesImpl(DaoManager daoManager){
        this.daoManager = daoManager;
    }

    /**
     *
     * Used to return an item back to the branch of the library system. Will check to see if it is reserved and
     * fulfill the most recent reservation, as well as email the patron who requested the object. Will also update the
     * checkout row if all items are returned. Tallying fines and determining when the total checkout is overdue would
     * be done in other processing
     */
    @Override
    public void returnItem(BranchItemDto branchItemDto) {

        Calendar calendar = Calendar.getInstance();
        java.sql.Date today = new java.sql.Date(calendar.getTime().getTime());

        BranchItemCheckoutDao branchItemCheckoutDao = daoManager.getBranchItemCheckoutDao();
        BranchItemCheckoutDto brItemCheckoutDto = branchItemCheckoutDao.getBranchItemCheckout(branchItemDto);
        if(today.after(brItemCheckoutDto.getDueDate())){
            if(!brItemCheckoutDto.isRenew()) {
                brItemCheckoutDto.setOverdue(true);
            } else if(brItemCheckoutDto.getDueDate().after(brItemCheckoutDto.getRenewDate())){
                brItemCheckoutDto.setOverdue(true);
            }
        }

        // Transaction block for database changes and functional processing
        BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput = branchItemCheckoutDao.updateBranchItemCheckout(brItemCheckoutDto);
        int numItems = branchItemCheckoutDao.getNumberOfItemsReturnedFromCheckout(itemReturnOutput);
        CheckoutDto checkoutDto = daoManager.getCheckoutDao().getCheckout(itemReturnOutput);
        if(numItems == checkoutDto.getNumberofitems()){
            itemReturnOutput.setReturned(true);
            daoManager.getCheckoutDao().updateCheckout(itemReturnOutput);
        }

        ReservationDto reservationDto = daoManager.getReservationDao().fulfillReservation(itemReturnOutput);
        BranchItemDao branchItemDao = daoManager.getBranchItemDao();
        if(reservationDto != null){
            itemReturnOutput.setReserved(true);
            branchItemDao.updateBranchItem(itemReturnOutput);
            itemReturnOutput.setPatronid(reservationDto.getPatronid());
            Dto dto = daoManager.getPatronDao().getPatron(itemReturnOutput);
            String msg = " Dear " + dto.getFirstname() + " " + dto.getLastname() + ", your reservation with id "
                  + reservationDto.getReservationid() + " has been fulfilled. You can pickup your item";
            emailPatron(dto,msg);
        } else{
            branchItemDao.updateBranchItem(itemReturnOutput);
        }

        itemReturnOutput.completeReturn();

        // transaction end.
    }

    /**
     *
     * Method to email patron a message. Using logging as a proxy for that functionality
     */
    @Override
    public void emailPatron(Dto dto, String message) {
        //log as proxy to email patron
        logger.info("Send email to {} with message: {}", dto.getEmail(), message);
    }
}
