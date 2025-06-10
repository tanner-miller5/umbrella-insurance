package com.umbrella.insurance.core.models.databases.v1;

import java.io.*;
import java.sql.*;
import java.util.regex.Pattern;

import static com.umbrella.insurance.core.constants.Database.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;

import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Database {
    static Logger logger = LoggerFactory.getLogger(Database.class);

    public static final String CREATE_DATABASE_BY_NAME = """
        CREATE DATABASE ?
    """;
    public static final String DROP_DATABASE_BY_NAME = """
        DROP DATABASE ? WITH (FORCE)
    """;

    public static final String DOES_DATABASE_EXIST = """
        SELECT COUNT(datname) FROM pg_database WHERE datname=?        
    """;

    public static final String DOES_TABLE_EXIST = """
        SELECT COUNT(*) FROM ?
    """;

    public static final String DOES_TABLE_CONSTRAINT_EXIST = """
        SELECT COUNT(*)
        FROM INFORMATION_SCHEMA.table_constraints
        WHERE CONSTRAINT_NAME = '?'
    """;

    public static Connection createConnectionWithEnv(EnvironmentEnum env) {
        Connection connection = null;
        switch (env) {
            case TEST:
                //connection = Database.createConnectionForTestDatabase();
                //break;
            case TEST2:
                //connection = Database.createConnectionForTest2Database();
                //break;
            case DEV:
                //connection = Database.createConnectionForDevDatabase();
                //break;
            case QA:
                //connection = Database.createConnectionForQaDatabase();
                //break;
            case PROD:
                //connection = Database.createConnection();
                //break;
            default:
                connection = Database.createConnection();
                break;
        }
        return connection;
    }
    public static Connection createConnection() {
        try {
            Connection connection = null;
            String dbPassword = System.getenv().get(SPRING_DATASOURCE_PASSWORD);
            String dbUserId = System.getenv().get(SPRING_DATASOURCE_USERNAME);
            String dbConnectionUrl = System.getenv().get(SPRING_DATASOURCE_URL);
            Class.forName(DB_DRIVER_CLASS);
            connection = DriverManager.getConnection(dbConnectionUrl, dbUserId, dbPassword);
            return connection;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public static void closeConnection(Connection connection) {
        try {
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
    public static void createDatabaseByName(String databaseName, Connection connection) throws SQLException {
        boolean b = Pattern.matches("[a-zA-Z0-9_]*", databaseName);
        if (b) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(CREATE_DATABASE_BY_NAME.replace("?", databaseName));
        } else {
            throw new SQLException("INVALID DATABASE NAME");
        }
    }

    public static boolean doesDatabaseExist(String databaseName, Connection connection) throws SQLException {
        int count = 0;
        try {
            boolean b = Pattern.matches("[a-zA-Z0-9_]*", databaseName);
            if (b) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        DOES_DATABASE_EXIST);
                preparedStatement.setString(1, databaseName);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            } else {
                throw new SQLException("INVALID DATABASE NAME");
            }
        } catch (SQLException e) {
            return false;
        }
        return count == 1;
    }

    public static boolean doesTableExist(String tableName, Connection connection) {
        try {
            boolean b = Pattern.matches("[a-zA-Z0-9_.]*", tableName);
            if (b) {
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(
                        DOES_TABLE_EXIST.replace("?", tableName));
                if (rs.next()) {
                    rs.getInt(1);
                }
            } else {
                throw new SQLException("INVALID TABLE NAME");
            }
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    public static boolean doesTableConstraintExist(String constraintName, Connection connection) {
        int count = 0;
        try {
            boolean b = Pattern.matches("[a-zA-Z0-9_]*", constraintName);
            if (b) {
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(
                        DOES_TABLE_CONSTRAINT_EXIST.replace("?", constraintName));
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            } else {
                throw new SQLException("INVALID CONSTRAINT NAME");
            }
        } catch (SQLException e) {
            return false;
        }
        return count == 1;
    }

    public static void dropDatabaseByName(String databaseName, Connection connection) throws SQLException {
        boolean b = Pattern.matches("[a-zA-Z0-9_]*", databaseName);
        if (b) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(DROP_DATABASE_BY_NAME.replace("?", databaseName));
        } else {
            throw new SQLException("INVALID DATABASE NAME");
        }
    }

    public static void copyOutManagerTest(Connection connection, String tableName) throws SQLException, IOException {
        CopyManager copyManager = new CopyManager(connection.unwrap(BaseConnection.class));
        String file ="test-table-backup.sql";
        FileWriter writer = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        String sql = "COPY ? TO STDOUT".replace("?",tableName);
        copyManager.copyOut(sql, bufferedWriter);
        bufferedWriter.close();
    }

    public static void copyInManagerTest(Connection connection, String tableName) throws SQLException, IOException {
        CopyManager copyManager = new CopyManager(connection.unwrap(BaseConnection.class));
        String file ="test-table-backup.sql";
        String sql = "COPY ? FROM STDIN".replace("?",tableName);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        copyManager.copyIn(sql, reader);
        reader.close();
    }

}
