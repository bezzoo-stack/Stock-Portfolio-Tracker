import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class DatabaseConnection {

    private static final String DB_URL = "jdbc:sqlite:stocks.db";

    public static Connection connect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver not found!");
        }
        return DriverManager.getConnection(DB_URL);
    }

    // Initialize database
    public static void Database() {
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {

            // Users table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    username TEXT PRIMARY KEY,
                    password TEXT
                )
            """);

            // Add default admin user
            stmt.execute("""
                INSERT INTO users (username, password)
                SELECT 'admin', 'admin123'
                WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin')
            """);

            // Balance table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS balance (
                    username TEXT PRIMARY KEY,
                    amount REAL
                )
            """);

            // Set admin balance to 100000 if not exists
            stmt.execute("""
                INSERT INTO balance (username, amount)
                SELECT 'admin', 100000.0
                WHERE NOT EXISTS (SELECT 1 FROM balance WHERE username = 'admin')
            """);

            // Stocks table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS stocks (
                    company TEXT,
                    industry TEXT,
                    symbol TEXT PRIMARY KEY,
                    price REAL,
                    day_change TEXT,
                    gain_loss TEXT
                )
            """);

            // Owned stocks table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS owned_stocks (
                    company TEXT,
                    industry TEXT,
                    symbol TEXT PRIMARY KEY,
                    price REAL,
                    day_change TEXT,
                    gain_loss TEXT
                )
            """);

            // History table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS history (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    company TEXT,
                    industry TEXT,
                    symbol TEXT,
                    price REAL,
                    day_change TEXT,
                    gain_loss TEXT,
                    action TEXT,
                    date TEXT DEFAULT CURRENT_TIMESTAMP
                )
            """);

            // Reset and insert sample stocks
            stmt.execute("DELETE FROM stocks");
            stmt.execute("""
                INSERT INTO stocks (company, industry, symbol, price, day_change, gain_loss) VALUES
                ('Apple Inc.', 'Technology', 'AAPL', 178.23, '+1.45%', '+$230'),
                ('Microsoft Corp.', 'Technology', 'MSFT', 319.60, '-0.22%', '-$45'),
                ('Tesla Inc.', 'Automotive', 'TSLA', 251.12, '+2.10%', '+$520'),
                ('Coca-Cola Co.', 'Beverage', 'KO', 58.43, '+0.12%', '+$15'),
                ('JPMorgan Chase', 'Finance', 'JPM', 144.56, '-0.35%', '-$90'),
                ('Amazon.com Inc.', 'E-commerce', 'AMZN', 132.87, '+0.89%', '+$340')
            """);

            System.out.println("✅ Database initialized successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ------------------------
    // STOCK OPERATIONS
    // ------------------------
    public static void buyStock(String company, String industry, String symbol, double price, String dayChange, String gainLoss) {
        String sqlOwned = "INSERT OR REPLACE INTO owned_stocks (company, industry, symbol, price, day_change, gain_loss) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlHistory = "INSERT INTO history (company, industry, symbol, price, day_change, gain_loss, action) VALUES (?, ?, ?, ?, ?, ?, 'BUY')";
        try (Connection conn = connect();
             PreparedStatement pstmt1 = conn.prepareStatement(sqlOwned);
             PreparedStatement pstmt2 = conn.prepareStatement(sqlHistory)) {

            pstmt1.setString(1, company);
            pstmt1.setString(2, industry);
            pstmt1.setString(3, symbol);
            pstmt1.setDouble(4, price);
            pstmt1.setString(5, dayChange);
            pstmt1.setString(6, gainLoss);
            pstmt1.executeUpdate();

            pstmt2.setString(1, company);
            pstmt2.setString(2, industry);
            pstmt2.setString(3, symbol);
            pstmt2.setDouble(4, price);
            pstmt2.setString(5, dayChange);
            pstmt2.setString(6, gainLoss);
            pstmt2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeOwnedStock(String company, String industry, String symbol, double price, String dayChange, String gainLoss) {
        String sqlDelete = "DELETE FROM owned_stocks WHERE symbol = ?";
        String sqlHistory = "INSERT INTO history (company, industry, symbol, price, day_change, gain_loss, action) VALUES (?, ?, ?, ?, ?, ?, 'SELL')";
        try (Connection conn = connect();
             PreparedStatement pstmt1 = conn.prepareStatement(sqlDelete);
             PreparedStatement pstmt2 = conn.prepareStatement(sqlHistory)) {

            pstmt1.setString(1, symbol);
            pstmt1.executeUpdate();

            pstmt2.setString(1, company);
            pstmt2.setString(2, industry);
            pstmt2.setString(3, symbol);
            pstmt2.setDouble(4, price);
            pstmt2.setString(5, dayChange);
            pstmt2.setString(6, gainLoss);
            pstmt2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void loadDataToTable(DefaultTableModel model) {
        model.setRowCount(0);
        String sql = "SELECT * FROM stocks";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("company"),
                        rs.getString("industry"),
                        rs.getString("symbol"),
                        rs.getDouble("price"),
                        rs.getString("day_change"),
                        rs.getString("gain_loss")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void loadOwnedStocksToTable(DefaultTableModel model) {
        model.setRowCount(0);
        String sql = "SELECT * FROM owned_stocks";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("company"),
                        rs.getString("industry"),
                        rs.getString("symbol"),
                        rs.getDouble("price"),
                        rs.getString("day_change"),
                        rs.getString("gain_loss")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ------------------------
    // HISTORY
    // ------------------------
    public static void loadHistoryToTable(DefaultTableModel model) {
        model.setRowCount(0); // clear table first
        String sql = """
            SELECT DISTINCT company, industry, symbol, price, day_change, gain_loss, action, date
            FROM history
            ORDER BY id DESC
        """;
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("company"),
                        rs.getString("industry"),
                        rs.getString("symbol"),
                        rs.getDouble("price"),
                        rs.getString("day_change"),
                        rs.getString("gain_loss"),
                        rs.getString("action"),
                        rs.getString("date")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ------------------------
    // BALANCE
    // ------------------------


    public static void updateBalance(String username, double newBalance) {
        String sql = "UPDATE balance SET amount = ? WHERE username = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, newBalance);
            pstmt.setString(2, username);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ------------------------
    // MISC
    // ------------------------
    public static void updateStockPrices() {
        String sql = "UPDATE stocks SET price = price + ((ABS(RANDOM()) % 200) - 100) / 100.0";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void clearHistory() {
        String sql = "DELETE FROM history";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error clearing history: " + e.getMessage());
        }
    }

    // ------------------------
    // LOGIN / REGISTER
    // ------------------------
    public static boolean addUser(String username, String password) {
        String sqlUser = "INSERT INTO users (username, password) VALUES (?, ?)";
        String sqlBalance = "INSERT INTO balance (username, amount) VALUES (?, 10000.0)";

        try (Connection conn = connect();
             PreparedStatement pstmtUser = conn.prepareStatement(sqlUser);
             PreparedStatement pstmtBalance = conn.prepareStatement(sqlBalance)) {

            pstmtUser.setString(1, username);
            pstmtUser.setString(2, password);
            pstmtUser.executeUpdate();

            pstmtBalance.setString(1, username);
            pstmtBalance.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
            return false;
        }
    }

    public static boolean checkLogin(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
            return false;
        }
    }

    public static double getUserBalance(String username) {
    String sql = "SELECT amount FROM balance WHERE username = ?";
    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, username);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) return rs.getDouble("amount");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0.0;
}


    public static void updateUserBalance(String username, double newBalance) {
        String sql = "UPDATE balance SET amount = ? WHERE username = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, newBalance);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
















    






