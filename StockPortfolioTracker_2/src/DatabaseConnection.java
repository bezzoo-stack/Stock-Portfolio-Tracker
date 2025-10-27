import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

public class DatabaseConnection {

    private static final String DB_URL = "jdbc:sqlite:stocks.db";

    // Connect to SQLite
    public static Connection connect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver not found!");
        }
        return DriverManager.getConnection(DB_URL);
    }

    // Initialize and create tables
    public static void Database() {
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {

            // Create stocks table
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

            // Create owned_stocks table
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

            // 💰 Create balance table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS balance (
                    id INTEGER PRIMARY KEY CHECK (id = 1),
                    amount REAL
                )
            """);

            // Insert default balance if not exists
            stmt.execute("""
                INSERT INTO balance (id, amount)
                SELECT 1, 10000.00
                WHERE NOT EXISTS (SELECT 1 FROM balance WHERE id = 1)
            """);

            // Reset stocks with sample data (optional)
            stmt.execute("DELETE FROM stocks");
            String insertData = """
                INSERT INTO stocks (company, industry, symbol, price, day_change, gain_loss) VALUES
                ('Apple Inc.', 'Technology', 'AAPL', 178.23, '+1.45%', '+$230'),
                ('Microsoft Corp.', 'Technology', 'MSFT', 319.60, '-0.22%', '-$45'),
                ('Tesla Inc.', 'Automotive', 'TSLA', 251.12, '+2.10%', '+$520'),
                ('Coca-Cola Co.', 'Beverage', 'KO', 58.43, '+0.12%', '+$15'),
                ('JPMorgan Chase', 'Finance', 'JPM', 144.56, '-0.35%', '-$90'),
                ('Amazon.com Inc.', 'E-commerce', 'AMZN', 132.87, '+0.89%', '+$340')
            """;
            stmt.execute(insertData);

            System.out.println("✅ Database initialized successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🟢 Get current balance
    public static double getBalance() {
        String sql = "SELECT amount FROM balance WHERE id = 1";
        try (Connection conn = connect();
             java.sql.Statement stmt = conn.createStatement();
             java.sql.ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getDouble("amount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // 🔵 Update balance
    public static void updateBalance(double newBalance) {
        String sql = "UPDATE balance SET amount = ? WHERE id = 1";
        try (Connection conn = connect();
             java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, newBalance);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Load available stocks
    public static void loadDataToTable(DefaultTableModel model) {
        model.setRowCount(0);
        String sql = "SELECT * FROM stocks";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             java.sql.ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Object[] row = {
                    rs.getString("company"),
                    rs.getString("industry"),
                    rs.getString("symbol"),
                    rs.getDouble("price"),
                    rs.getString("day_change"),
                    rs.getString("gain_loss")
                };
                model.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Load owned stocks
    public static void loadOwnedStocksToTable(DefaultTableModel model) {
        model.setRowCount(0);
        String sql = "SELECT * FROM owned_stocks";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             java.sql.ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Object[] row = {
                    rs.getString("company"),
                    rs.getString("industry"),
                    rs.getString("symbol"),
                    rs.getDouble("price"),
                    rs.getString("day_change"),
                    rs.getString("gain_loss")
                };
                model.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Randomly update prices
    public static void updateStockPrices() {
        String updateStocks = """
            UPDATE stocks
            SET price = ROUND(price + ((RANDOM() % 200 - 100) / 100.0), 2),
                day_change = (CASE WHEN RANDOM() % 2 = 0 THEN '+' ELSE '-' END)
                             || printf('%.2f', ABS(RANDOM() % 200) / 100.0) || '%',
                gain_loss = (CASE WHEN RANDOM() % 2 = 0 THEN '+$' ELSE '-$' END)
                            || CAST(ABS(RANDOM() % 500) AS TEXT)
        """;

        String updateOwned = """
            UPDATE owned_stocks
            SET price = ROUND(price + ((RANDOM() % 200 - 100) / 100.0), 2),
                day_change = (CASE WHEN RANDOM() % 2 = 0 THEN '+' ELSE '-' END)
                             || printf('%.2f', ABS(RANDOM() % 200) / 100.0) || '%',
                gain_loss = (CASE WHEN RANDOM() % 2 = 0 THEN '+$' ELSE '-$' END)
                            || CAST(ABS(RANDOM() % 500) AS TEXT)
        """;

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(updateStocks);
            stmt.executeUpdate(updateOwned);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Buy stock
    public static void buyStock(String company, String industry, String symbol, double price) {
        String sql = "INSERT OR IGNORE INTO owned_stocks VALUES (?, ?, ?, ?, '+0.00%', '+$0')";
        try (Connection conn = connect();
             java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, company);
            pstmt.setString(2, industry);
            pstmt.setString(3, symbol);
            pstmt.setDouble(4, price);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Sell stock
    public static void removeOwnedStock(String symbol) {
        String sql = "DELETE FROM owned_stocks WHERE symbol = ?";
        try (Connection conn = connect();
             java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, symbol);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
   
    }
}






    






