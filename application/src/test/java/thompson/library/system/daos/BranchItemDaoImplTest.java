package thompson.library.system.daos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import thompson.library.system.dtos.BranchItemDto;
import thompson.library.system.utilities.LibraryConfig;

import static junit.framework.TestCase.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LibraryConfig.class)
@Transactional
public class BranchItemDaoImplTest {

    @Autowired
    BranchItemDao branchItemDao;

    @Autowired
    JdbcOperations jdbcOperations;

    @Test
    public void testUpdateBranchItem() {
        String insert = "INSERT INTO branchitem(branchid, checkedout, reserved, intransit, currentlocation" +
                ") VALUES (?,?,?,?,?)";
        String query = "SELECT branchitemid FROM branchitem WHERE" +
                    " branchid =1 AND checkedout = true AND reserved = true AND intransit = true AND currentlocation = 1";

        jdbcOperations.update(insert, 1, true, true, true, 1);
        int id = jdbcOperations.queryForObject(query, Integer.class);

        BranchItemCheckoutDao.ItemReturnOutput itemReturnOutput = mock(BranchItemCheckoutDao.ItemReturnOutput.class);
        when(itemReturnOutput.isReserved()).thenReturn(false);
        when(itemReturnOutput.getBranchitemid()).thenReturn(id);

        branchItemDao.updateBranchItem(itemReturnOutput);

        BranchItemDto dto = jdbcOperations.queryForObject("SELECT * FROM branchitem WHERE branchitemid = ?",
                (rs, rowNum) -> {
                    return new BranchItemDto(rs.getInt("branchitemid"), rs.getBoolean("checkedout"), rs.getBoolean("reserved"),
                            rs.getBoolean("intransit"), rs.getInt("currentlocation"), rs.getInt("branchid"));
                }, id);

        assertFalse(dto.isCheckedout());
        assertFalse(dto.isReserved());
    }
}
