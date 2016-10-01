package thompson.library.system.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thompson.library.system.daos.*;

import thompson.library.system.dtos.*;

import java.util.Calendar;


public class BranchServicesImpl implements BranchServices {
    private static final Logger logger = LoggerFactory.getLogger(BranchServicesImpl.class);
    private DaoManager daoManager;

    BranchServicesImpl(){
        this.daoManager = DaoManagerFactory.getDaoManager();
    }

    BranchServicesImpl(DaoManager daoManager){
        this.daoManager = daoManager;
    }

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
            PatronDto patronDto = daoManager.getPatronDao().getPatron(itemReturnOutput);
            String msg = " Dear " + patronDto.getFirstname() + " " + patronDto.getLastname() + ", your reservation with id "
                  + reservationDto.getReservationid() + " has been fulfilled. You can pickup your item";
            emailPatron(patronDto,msg);
        } else{
            branchItemDao.updateBranchItem(itemReturnOutput);
        }

        itemReturnOutput.completeReturn();

        // transaction end.


//        BranchItemDao branchItemDao = daoManager.getBranchItemDao();
//
//        if(!patronDto.getPatronid().isPresent()){
//            try {
//                patronDto = daoManager.getPatronDao().getPatron(patronDto.getEmail());
//            } catch (NonUniqueResultException e) {
//                logger.error("Non-unique result for patron with email {}", patronDto.getEmail());
//                throw new IllegalStateException("SQL returned unexpected non-unique result for patron with email: " + patronDto);
//            }
//        }
//
//        BranchItemDao.ReturnItemOutput returnItemOutput = branchItemDao.returnItem(branchItemDto,patronDto);
//        if(returnItemOutput.isFulfillReturn()) {
//            ReservationDao reservationDao = daoManager.getReservationDao();
//            int resvID = reservationDao.fulfillReservation(returnItemOutput);
//            String msg = " Dear " + patronDto.getFirstname() + " " + patronDto.getLastname() + ", your reservation with id "
//                    + resvID + " has been fulfilled. You can pickup your item";
//            emailPatron(patronDto, msg);
//        }
//        returnItemOutput.completeReturn();
    }

    @Override
    public void emailPatron(PatronDto patronDto, String message) {
        //log as proxy to email patron
        logger.info("Send email to {} with message: {}", patronDto.getEmail(), message);
    }
}
