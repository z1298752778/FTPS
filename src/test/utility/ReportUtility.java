package test.utility;

import com.datasweep.compatibility.ui.grid.DsGrid;
import com.rockwell.mes.clientfw.pmc.impl.view.activities.GridDataDictActivity;
import com.rockwell.mes.commons.base.ifc.services.IFunctionsEx;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import test.model.MaterialTraceabilityModel;
import test.utility.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportUtility {

    // 平台function
//    static IFunctionsEx functions = PCContext.getFunctions();

    public List<MaterialTraceabilityModel> getMaterialTraceability(String materialCode, String materialName, String subBatchNumber, String batchNumber) {
        List<MaterialTraceabilityModel> materialTraceabilityList = new ArrayList<>();
        String sql = "SELECT part_number FROM PART  ";

        try (Connection connection = new DatabaseConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

//            preparedStatement.setString(1, materialCode);


            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("part_number"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return materialTraceabilityList;
    }
}