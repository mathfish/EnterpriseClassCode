package thompson.library.system.services;

import org.junit.Before;
import org.junit.Test;
import thompson.library.system.daos.DaoManager;
import thompson.library.system.daos.PatronDao;
import thompson.library.system.dtos.Dto;
import thompson.library.system.utilities.EntryExistsException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LibraryServicesImplTest {

    private PatronDao patronDao;
    private LibraryServicesImpl impl;
    public LibraryServicesImplTest(){}

    @Before
    public void setUpMocks(){
        DaoManager daoManager = mock(DaoManager.class);
        patronDao = mock(PatronDao.class);
        when(daoManager.getPatronDao()).thenReturn(patronDao);
        impl = new LibraryServicesImpl(daoManager);
    }

    @Test // Test if creat patron method is reached. Functionality already tested in lower level class tests
    public void createPatronTest1(){
        try {
            impl.createPatron("testFirst","testLast","testcity","AA",22222,"testAddress",null, "test@email.test",
                    4444444444L, false, "1234PW");
        } catch (EntryExistsException e) {
            e.printStackTrace();
        }
        verify(patronDao).insertPatron(any());
    }

    @Test(expected = EntryExistsException.class) //Test exception is throw if insert patron occurs when already exists
    public void createPatronTest2() throws EntryExistsException {
        Dto dto = mock(Dto.class);
            when(patronDao.getPatron(anyString())).thenReturn(dto);
            impl.createPatron("testFirst","testLast","testcity","AA",22222,"testAddress",null, "test@email.test",
                    4444444444L, false, "1234PW");
    }

}
