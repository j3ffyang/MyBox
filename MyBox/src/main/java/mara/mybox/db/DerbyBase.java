package mara.mybox.db;

import java.io.File;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import mara.mybox.tools.ConfigTools;
import mara.mybox.tools.NetworkTools;
import mara.mybox.value.AppVariables;
import static mara.mybox.value.AppVariables.DerbyFailAsked;
import static mara.mybox.value.AppVariables.logger;
import static mara.mybox.value.AppVariables.message;
import mara.mybox.value.CommonValues;
import org.apache.derby.drda.NetworkServerControl;

/**
 * @Author Mara
 * @CreateDate 2018-10-15 9:31:28
 * @License Apache License Version 2.0
 */
public class DerbyBase {

    public static String mode = "embedded";
    public static String host = "localhost";
    public static int port = 1527;
    public static String driver, protocol;
    protected static final String ClientDriver = "org.apache.derby.jdbc.ClientDriver";
    protected static final String embeddedDriver = "org.apache.derby.jdbc.EmbeddedDriver";
    protected static final String create = ";user=" + CommonValues.AppDerbyUser + ";password="
            + CommonValues.AppDerbyPassword + ";create=true";
    public static final String login = ";user=" + CommonValues.AppDerbyUser + ";password="
            + CommonValues.AppDerbyPassword + ";create=false";
    public static DerbyStatus status;
    public static long lastRetry = 0;
    public static long BatchSize = 500;

    public enum DerbyStatus {
        Embedded, Nerwork, Starting, NotConnected, EmbeddedFailed, NerworkFailed
    }

    protected String Table_Name, Create_Table_Statement;
    protected List<String> Keys;

    public boolean init(Connection conn) {
        try {
            if (conn == null) {
                return false;
            }
//            logger.debug(Create_Table_Statement);
            conn.createStatement().executeUpdate(Create_Table_Statement);
            return true;
        } catch (Exception e) {
            failed(e);
//            logger.debug(e.toString());
            return false;
        }
    }

    public boolean init() {
        try ( Connection conn = DriverManager.getConnection(protocol + dbHome() + login)) {
            conn.createStatement().executeUpdate(Create_Table_Statement);
            return true;
        } catch (Exception e) {
            failed(e);
//            // logger.debug(e.toString());
            return false;
        }
    }

    public ResultSet query(String sql) {
        try {
            ResultSet resultSet;
            try ( Connection conn = DriverManager.getConnection(protocol + dbHome() + login)) {
                conn.setReadOnly(true);
                resultSet = conn.createStatement().executeQuery(sql);
            }
            return resultSet;
        } catch (Exception e) {
            failed(e);
//            // logger.debug(e.toString());
            return null;
        }
    }

    public int update(String sql) {
        try {
            int ret;
            try ( Connection conn = DriverManager.getConnection(protocol + dbHome() + login)) {
                ret = conn.createStatement().executeUpdate(sql);
            }
            return ret;
        } catch (Exception e) {
            failed(e);
            logger.debug(e.toString());
            logger.debug(sql);
            return -1;
        }
    }

    public boolean drop(Connection conn) {
        try {
            if (conn == null) {
                return false;
            }
            String sql = "DROP TABLE " + Table_Name;
            conn.createStatement().executeUpdate(sql);
//            logger.debug(Create_Table_Statement);
            return true;
        } catch (Exception e) {
            failed(e);
//            // logger.debug(e.toString());
            return false;
        }
    }

    public boolean drop() {
        try ( Connection conn = DriverManager.getConnection(protocol + dbHome() + login)) {
            String sql = "DROP TABLE " + Table_Name;
            conn.createStatement().executeUpdate(sql);
            return true;
        } catch (Exception e) {
            failed(e);
//            // logger.debug(e.toString());
            return false;
        }
    }

    public boolean clear(Connection conn) {
        try {
            if (conn == null) {
                return false;
            }
            String sql = "DELETE FROM " + Table_Name;
            conn.createStatement().executeUpdate(sql);
//            logger.debug(Create_Table_Statement);
            return true;
        } catch (Exception e) {
            failed(e);
//            // logger.debug(e.toString());
            return false;
        }
    }

    public boolean clear() {
        try ( Connection conn = DriverManager.getConnection(protocol + dbHome() + login)) {
            String sql = "DELETE FROM " + Table_Name;
            conn.createStatement().executeUpdate(sql);
            return true;
        } catch (Exception e) {
            failed(e);
//            // logger.debug(e.toString());
            return false;
        }
    }


    /*
        Static methods
     */
    public static String dbHome() {
        return AppVariables.MyBoxDerbyPath.getAbsolutePath();
    }

    public static String readMode() {
        String value = ConfigTools.readValue("DerbyMode");
        String modeValue;
        if (value != null) {
            modeValue = "client".equals(value.toLowerCase()) ? "client" : "embedded";
        } else {
            modeValue = "embedded";
        }
        return modeValue;
    }

    public static String startDerby() {
        try {
            Class.forName(embeddedDriver).getDeclaredConstructors()[0].newInstance();
            Class.forName(ClientDriver).getDeclaredConstructors()[0].newInstance();
            if ("client".equals(readMode())) {
                return networkMode();
            } else {
                return embeddedMode();
            }
        } catch (Exception e) {
            logger.debug(e.toString());
            return e.toString();
        }
    }

    public static String embeddedMode() {
        try {
            String lang = Locale.getDefault().getLanguage().toLowerCase();
            if (!canEmbeded()) {
                status = DerbyStatus.NotConnected;
                return MessageFormat.format(
                        message(lang, "DerbyNotAvalibale"), AppVariables.MyBoxDerbyPath);
            }
            if (isServerStarted(port)) {
                shutdownDerbyServer();
            }
            driver = embeddedDriver;
            protocol = "jdbc:derby:";
            logger.debug("Driver: " + driver);
            mode = "embedded";
            ConfigTools.writeConfigValue("DerbyMode", mode);
            status = DerbyStatus.Embedded;
            return message(lang, "DerbyEmbeddedMode");
        } catch (Exception e) {
            logger.debug(e.toString());
            status = DerbyStatus.EmbeddedFailed;
            return e.toString();
        }
    }

    // Derby will run only at localhost.
    // To avoid to let user have to configure security and network for it.
    public static String networkMode() {
        try {
            if (status == DerbyStatus.Starting) {
                String lang = Locale.getDefault().getLanguage().toLowerCase();
                return message(lang, "BeingStartingDerby");
            }
            String lang = Locale.getDefault().getLanguage().toLowerCase();
            if (startDerbyServer()) {
                driver = ClientDriver;
                protocol = "jdbc:derby://" + host + ":" + port + "/";
                mode = "client";
                status = DerbyStatus.Nerwork;
                ConfigTools.writeConfigValue("DerbyMode", mode);
                logger.debug("Driver: " + driver);
                return MessageFormat.format(message(lang, "DerbyServerListening"), port + "");

            } else if (canEmbeded() && status != DerbyStatus.EmbeddedFailed) {
                return embeddedMode();

            } else {
                status = DerbyStatus.NotConnected;
                return MessageFormat.format(
                        message(lang, "DerbyNotAvalibale"), AppVariables.MyBoxDerbyPath);
            }
        } catch (Exception e) {
            status = DerbyStatus.NerworkFailed;
            logger.debug(e.toString());
            return e.toString();
        }
    }

    public static boolean startDerbyServer() {
        try {
            boolean portUsed = NetworkTools.isPortUsed(port);
            int uPort = port;
            if (portUsed) {
                if (DerbyBase.isServerStarted(port)) {
                    logger.debug("Derby server is already started in port " + port + ".");
                    return true;
                } else {
                    uPort = NetworkTools.findFreePort(port);
                }
            }
            NetworkServerControl server = new NetworkServerControl(InetAddress.getByName(host),
                    uPort, CommonValues.AppDerbyUser, CommonValues.AppDerbyPassword);
            status = DerbyStatus.Starting;
            server.start(null);
//            server.setTraceDirectory("d:/tmp");
            server.trace(false);
            if (isServerStarted(server)) {
                port = uPort;
                logger.debug("Derby server is listening in port " + port + ".");
                status = DerbyStatus.Nerwork;
                return true;
            } else {
                status = DerbyStatus.NerworkFailed;
                return false;
            }
        } catch (Exception e) {
            logger.debug(e.toString());
            status = DerbyStatus.NerworkFailed;
            return false;
        }
    }

    public static boolean shutdownDerbyServer() {
        try {
            boolean portUsed = NetworkTools.isPortUsed(port);
            if (!portUsed) {
                return true;
            }
            NetworkServerControl server = new NetworkServerControl(InetAddress.getByName(host),
                    port, CommonValues.AppDerbyUser, CommonValues.AppDerbyPassword);
            server.shutdown();
            status = DerbyStatus.NotConnected;
            return true;
        } catch (Exception e) {
            logger.debug(e.toString());
            return false;
        }
    }

    public static boolean isServerStarted(int uPort) {
        try ( Connection conn = DriverManager.getConnection(
                "jdbc:derby://" + host + ":" + uPort + "/" + dbHome() + login)) {
            port = uPort;
            status = DerbyStatus.Nerwork;
            return true;
        } catch (Exception e) {
//            logger.debug(e.toString());
            return false;
        }
    }

    public static boolean canEmbeded() {
        try ( Connection conn = DriverManager.getConnection("jdbc:derby:" + dbHome() + create)) {
            return true;
        } catch (Exception e) {
//            logger.debug(e.toString());
            status = DerbyStatus.EmbeddedFailed;
            return false;
        }
    }

    public static boolean isServerStarted(NetworkServerControl server) {
        boolean started = false;
        int count = 10, wait = 300;
        while (!started && (count > 0)) {
            try {
                count--;
                server.ping();
                started = true;
            } catch (Exception e) {
//                failed(e);
//                logger.debug(e.toString());
                try {
                    Thread.currentThread().sleep(wait);
                } catch (Exception ex) {
                }
            }
        }
        return started;
    }

    public static List<String> tables(Connection conn) {
        List<String> tables = new ArrayList<>();
        String sql = "SELECT TABLENAME FROM SYS.SYSTABLES WHERE TABLETYPE='T'";
        try ( Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                tables.add(resultSet.getString("TABLENAME"));
            }
        } catch (Exception e) {
            failed(e);
            logger.debug(e.toString());
        }
        return tables;
    }

    public static List<String> columns(Connection conn, String tablename) {
        List<String> columns = new ArrayList<>();
        String sql = "SELECT columnname, columndatatype FROM SYS.SYSTABLES t, SYS.SYSCOLUMNS c "
                + " where t.TABLEID=c.REFERENCEID AND tablename='" + tablename.toUpperCase() + "'"
                + " order by columnnumber";
        try ( Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                columns.add(resultSet.getString("columnname").toLowerCase()
                        + ", " + resultSet.getString("columndatatype"));
            }
        } catch (Exception e) {
            failed(e);
            logger.debug(e.toString());
        }
        return columns;
    }

    public static List<String> columnNames(Connection conn, String tablename) {
        List<String> columns = new ArrayList<>();
        String sql = "SELECT columnname  FROM SYS.SYSTABLES t, SYS.SYSCOLUMNS c "
                + " where t.TABLEID=c.REFERENCEID AND tablename='" + tablename.toUpperCase() + "'"
                + " order by columnnumber";
        try ( Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                columns.add(resultSet.getString("columnname").toLowerCase());
            }
        } catch (Exception e) {
            failed(e);
            logger.debug(e.toString());
        }
        return columns;
    }

    public static String tableDefinition(Connection conn, String tablename) {
        String s = "";
        for (String column : columns(conn, tablename)) {
            s += column + "\n";
        }
        return s;
    }

    public static String tableDefinition(String tablename) {
        try ( Connection conn = DriverManager.getConnection(protocol + dbHome() + login)) {
            String s = "";
            for (String column : columns(conn, tablename)) {
                s += column + "\n";
            }
            return s;
        } catch (Exception e) {
            failed(e);
            logger.debug(e.toString());
            return null;
        }
    }

    public static List<String> indexes(Connection conn) {
        List<String> indexes = new ArrayList<>();
        String sql = "SELECT CONGLOMERATENAME FROM SYS.SYSCONGLOMERATES";
        try ( Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                indexes.add(resultSet.getString("CONGLOMERATENAME"));
            }
        } catch (Exception e) {
            failed(e);
            logger.debug(e.toString());
        }
        return indexes;
    }

    public static List<String> views(Connection conn) {
        List<String> tables = new ArrayList<>();
        try {
            String sql = "SELECT TABLENAME FROM SYS.SYSTABLES WHERE TABLETYPE='V'";
            try ( Statement statement = conn.createStatement();
                     ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    tables.add(resultSet.getString("TABLENAME"));
                }
            }
        } catch (Exception e) {
            failed(e);
            logger.debug(e.toString());
        }
        return tables;
    }

    public static boolean initTables() {
        logger.debug("Protocol: " + protocol + dbHome());
        try ( Connection conn = DriverManager.getConnection(protocol + dbHome() + create)) {
            List<String> tables = tables(conn);
//            logger.debug(tables);
            if (!tables.contains("String_Values".toUpperCase())) {
                new TableStringValues().init(conn);
            }
            if (!tables.contains("image_scope".toUpperCase())) {
                new TableImageScope().init(conn);
            }
            if (!tables.contains("System_Conf".toUpperCase())) {
                new TableSystemConf().init(conn);
            }
            if (!tables.contains("User_Conf".toUpperCase())) {
                new TableUserConf().init(conn);
            }
            if (!tables.contains("Alarm_Clock".toUpperCase())) {
                new TableAlarmClock().init(conn);
            }
            if (!tables.contains("image_history".toUpperCase())) {
                new TableImageHistory().init(conn);
            }
            if (!tables.contains("Convolution_Kernel".toUpperCase())) {
                new TableConvolutionKernel().init(conn);
            }
            if (!tables.contains("Float_Matrix".toUpperCase())) {
                new TableFloatMatrix().init(conn);
            }
            if (!tables.contains("visit_history".toUpperCase())) {
                new TableVisitHistory().init(conn);
            }
            if (!tables.contains("media_list".toUpperCase())) {
                new TableMediaList().init(conn);
            }
            if (!tables.contains("media".toUpperCase())) {
                new TableMedia().init(conn);
            }
            if (!tables.contains("Browser_History".toUpperCase())) {
                new TableBrowserHistory().init(conn);
            }
            if (!tables.contains("Browser_Bypass_SSL".toUpperCase())) {
                new TableBrowserBypassSSL().init(conn);
            }
            if (!tables.contains("Color_Data".toUpperCase())) {
                new TableColorData().init(conn);
            }
            if (!tables.contains("Geography_Code".toUpperCase())) {
                new TableGeographyCode().init(conn);
            }
            if (!tables.contains("Location".toUpperCase())) {
                new TableLocation().init(conn);
            }

            if (!tables.contains("Geography_Code".toUpperCase())) {
                try {
                    new TableGeographyCode().init(conn);

                    try ( Statement statement = conn.createStatement()) {
                        statement.executeUpdate(TableGeographyCode.Create_Index_levelIndex);
                        statement.executeUpdate(TableGeographyCode.Create_Index_codeIndex);
                        statement.executeUpdate(TableGeographyCode.Create_Index_gcidIndex);
                    }
                } catch (Exception e) {
                }
            }

            if (!tables.contains("Epidemic_Report".toUpperCase())) {
                try {
                    new TableEpidemicReport().init(conn);

                    try ( Statement statement = conn.createStatement()) {
                        statement.executeUpdate(TableEpidemicReport.Create_Index_DatasetTimeDesc);
                        statement.executeUpdate(TableEpidemicReport.Create_Index_DatasetTimeAsc);
                        statement.executeUpdate(TableEpidemicReport.Create_Index_TimeAsc);
                        statement.executeUpdate(TableEpidemicReport.CreateStatisticView);
                    }
                } catch (Exception e) {
                }
            }

            if (!tables.contains("Query_Condition".toUpperCase())) {
                new TableQueryCondition().init(conn);
            }
            if (!tables.contains("String_Value".toUpperCase())) {
                new TableStringValue().init(conn);
            }
            return true;
        } catch (Exception e) {
//            logger.debug(e.toString());
            return false;
        }
    }

    public static boolean dropTables() {
        try ( Connection conn = DriverManager.getConnection(protocol + dbHome() + login)) {
            new TableSystemConf().drop(conn);
            new TableUserConf().drop(conn);
            new TableAlarmClock().drop(conn);
            new TableImageHistory().drop(conn);
            new TableConvolutionKernel().drop(conn);
            new TableFloatMatrix().drop(conn);
            new TableVisitHistory().drop(conn);
            new TableImageScope().drop(conn);
            new TableStringValues().drop(conn);
            new TableMediaList().drop(conn);
            new TableMedia().drop(conn);
            new TableBrowserHistory().drop(conn);
            new TableBrowserBypassSSL().drop(conn);
            new TableColorData().drop(conn);
            new TableLocation().drop(conn);
            try {
                conn.createStatement().executeUpdate("DROP VIEW Epidemic_Report_Statistic_View");
            } catch (Exception e) {
            }
            new TableEpidemicReport().drop(conn);
            new TableGeographyCode().drop(conn);
            new TableQueryCondition().drop(conn);
            new TableStringValue().drop(conn);
            return true;
        } catch (Exception e) {
//            // logger.debug(e.toString());
            return false;
        }
    }

    public static boolean clearData() {
        try ( Connection conn = DriverManager.getConnection(protocol + dbHome() + login)) {
            new TableUserConf().clear(conn);
            new TableAlarmClock().clear(conn);
            new TableImageHistory().clear(conn);
            new TableConvolutionKernel().clear(conn);
            new TableFloatMatrix().clear(conn);
            new TableVisitHistory().clear(conn);
            new TableImageScope().clear(conn);
            new TableStringValues().clear(conn);
            new TableMediaList().clear(conn);
            new TableMedia().clear(conn);
            new TableBrowserHistory().clear(conn);
            new TableBrowserBypassSSL().clear(conn);
            new TableColorData().clear(conn);
            new TableLocation().clear(conn);
            conn.createStatement().executeUpdate("DROP VIEW Epidemic_Report_Statistic_View");
            new TableEpidemicReport().clear(conn);
            new TableGeographyCode().clear(conn);
            new TableQueryCondition().clear(conn);
            new TableStringValue().clear(conn);
            return true;
        } catch (Exception e) {
//            // logger.debug(e.toString());
            return false;
        }
    }

    public static void failed(Exception exception) {
        try {
            if (exception == null) {
                return;
            }
            if (exception.toString().contains("java.sql.SQLNonTransientConnectionException")) {
//                logger.debug(exception.toString());
                status = DerbyStatus.NotConnected;
//                long now = new Date().getTime();
//                if (now - lastRetry > 3000) {
//                    lastRetry = new Date().getTime();
//                    startDerby();
//                }
                if (DerbyFailAsked) {
                    return;
                }
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("MyBox");
                alert.setContentText(MessageFormat.format(message("DerbyNotAvalibale"), AppVariables.MyBoxDerbyPath));
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                ButtonType buttonRetry = new ButtonType(AppVariables.message("Retry"));
                ButtonType buttonISee = new ButtonType(AppVariables.message("ISee"));
                alert.getButtonTypes().setAll(buttonRetry, buttonISee);
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.setAlwaysOnTop(true);
                stage.toFront();
                DerbyFailAsked = true;

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonRetry) {
                    startDerby();
                }
            }
        } catch (Exception e) {

        }
    }

    public static int tableSize(String table) {
        String sql = " SELECT count(*) FROM " + table;
        return size(sql);
    }

    public static int size(String sql) {
        try ( Connection conn = DriverManager.getConnection(protocol + dbHome() + login)) {
            conn.setReadOnly(true);
            return size(conn, sql);
        } catch (Exception e) {
            failed(e);
            return 0;
        }
    }

    public static int size(Connection conn, String sql) {
        int size = 0;
        try ( ResultSet results = conn.createStatement().executeQuery(sql)) {
            if (results.next()) {
                size = results.getInt(1);
            }
        } catch (Exception e) {
            failed(e);
            logger.debug(e.toString());
            logger.debug(sql);
        }
        return size;

    }

    public static String stringValue(String value) {
        if (value == null) {
            return null;
        }
        return value.replaceAll("'", "''");
    }

//    CALL SYSCS_UTIL.SYSCS_EXPORT_TABLE ('MARA', 'LOCATION', 'D:\MyBox\src\main\resources\fetch\db\Location.del_en', null, null,  'UTF-8');
    public static void exportData(String table, String file) {
        if (file == null) {
            return;
        }
        File f = new File(file);
        if (f.exists()) {
            f.delete();
        }
        try ( Connection conn = DriverManager.getConnection(protocol + dbHome() + login);) {
            PreparedStatement ps = conn.prepareStatement("CALL SYSCS_UTIL.SYSCS_EXPORT_TABLE (?,?,?,?,?,?)");
            ps.setString(1, null);
            ps.setString(2, table.toUpperCase());
            ps.setString(3, file);
            ps.setString(4, null);
            ps.setString(5, null);
            ps.setString(6, "UTF-8");
            ps.execute();
        } catch (Exception e) {
            failed(e);

        }
    }

//    CALL SYSCS_UTIL.SYSCS_IMPORT_TABLE ('MARA', 'LOCATION', 'D:\MyBox\src\main\resources\fetch\db\Location.del',null, null,  'UTF-8', 1);
    public static void importData(String table, String file, boolean replace) {
        if (file == null) {
            return;
        }
        File f = new File(file);
        if (!f.exists()) {
            return;
        }
        try ( Connection conn = DriverManager.getConnection(protocol + dbHome() + login);) {
            PreparedStatement ps = conn.prepareStatement("CALL SYSCS_UTIL.SYSCS_IMPORT_TABLE (?,?,?,?,?,?,?)");
            ps.setString(1, null);
            ps.setString(2, table.toUpperCase());
            ps.setString(3, file);
            ps.setString(4, null);
            ps.setString(5, null);
            ps.setString(6, "UTF-8");
            ps.setInt(7, replace ? 1 : 0);
            ps.execute();
        } catch (Exception e) {
            failed(e);

        }
    }


    /*
        get/set
     */
    public String getTable_Name() {
        return Table_Name;
    }

    public void setTable_Name(String Table_Name) {
        this.Table_Name = Table_Name;
    }

    public String getCreate_Table_Statement() {
        return Create_Table_Statement;
    }

    public void setCreate_Table_Statement(String Create_Table_Statement) {
        this.Create_Table_Statement = Create_Table_Statement;
    }

    public List<String> getKeys() {
        return Keys;
    }

    public void setKeys(List<String> Keys) {
        this.Keys = Keys;
    }

}
