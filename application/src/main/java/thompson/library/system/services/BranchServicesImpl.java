package thompson.library.system.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thompson.library.system.daos.*;
import thompson.library.system.dtos.BranchItemDto;
import thompson.library.system.dtos.PatronDto;
import thompson.library.system.utilities.NonUniqueResultException;

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
    public void returnItem(BranchItemDto branchItemDto, PatronDto patronDto) {
        BranchItemDao branchItemDao = daoManager.getBranchItemDao();

        if(!patronDto.getPatronid().isPresent()){
            try {
                patronDto = daoManager.getPatronDao().getPatron(patronDto.getEmail());
            } catch (NonUniqueResultException e) {
                logger.error("Non-unique result for patron with email {}", patronDto.getEmail());
                throw new IllegalStateException("SQL returned unexpected non-unique result for patron with email: " + patronDto);
            }
        }

        BranchItemDao.ReturnItemOutput returnItemOutput = branchItemDao.returnItem(branchItemDto,patronDto);
        if(returnItemOutput.isFulfillReturn()) {
            ReservationDao reservationDao = daoManager.getReservationDao();
            int resvID = reservationDao.fulfillReservation(returnItemOutput);
            String msg = " Dear " + patronDto.getFirstname() + " " + patronDto.getLastname() + ", your reservation with id "
                    + resvID + " has been fulfilled. You can pickup your item";
            emailPatron(patronDto, msg);
        }
        returnItemOutput.completeReturn();
    }

    @Override
    public void emailPatron(PatronDto patronDto, String message) {
        //log as proxy to email patron
        logger.info("Send email to {} with message: {}", patronDto.getEmail(), message);
    }
}
