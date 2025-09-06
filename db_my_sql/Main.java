import java.util.*;
interface SQLCommand {
    void execute(Database db);
}
class Database {
    private static Database instance;
    private Map<String, Table> tables = new HashMap<>();
    private List<DBObserver> observers = new ArrayList<>();

    private Database() {}

    public static Database getInstance() {
        if (instance == null) instance = new Database();
        return instance;
    }

    public void addTable(String name, Table table) {
        tables.put(name, table);
        notifyObservers("Table " + name + " created");
    }

    public Table getTable(String name) {
        return tables.get(name);
    }

    public void registerObserver(DBObserver obs) {
        observers.add(obs);
    }

    private void notifyObservers(String event) {
        for (DBObserver obs : observers) {
            obs.onEvent(event);
        }
    }
}
interface DBObserver {
    void onEvent(String event);
}

class ConsoleObserver implements DBObserver {
    public void onEvent(String event) {
        System.out.println("[DB EVENT] " + event);
    }
}


class Table {
    private String name;
    private List<String> columns;
    private List<List<String>> rows;

    private Table(String name, List<String> cols) {
        this.name = name;
        this.columns = cols;
        this.rows = new ArrayList<>();
    }

    public void insert(List<String> values) {
        rows.add(values);
    }

    public void printAll() {
        System.out.println("Table: " + name);
        System.out.println(columns);
        for (List<String> row : rows) {
            System.out.println(row);
        }
    }

    public static class Builder {
        private String name;
        private List<String> columns = new ArrayList<>();

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder addColumn(String col) {
            columns.add(col);
            return this;
        }

        public Table build() {
            return new Table(name, columns);
        }
    }
}

class CreateTableCommand implements SQLCommand {
    private String tableName;
    private List<String> columns;

    public CreateTableCommand(String tableName, List<String> columns) {
        this.tableName = tableName;
        this.columns = columns;
    }

    public void execute(Database db) {
        Table t = new Table.Builder().setName(tableName).build();
        for (String col : columns) {
            t = new Table.Builder().setName(tableName).addColumn(col).build();
        }
        db.addTable(tableName, t);
    }
}

class InsertCommand implements SQLCommand {
    private String tableName;
    private List<String> values;

    public InsertCommand(String tableName, List<String> values) {
        this.tableName = tableName;
        this.values = values;
    }

    public void execute(Database db) {
        Table t = db.getTable(tableName);
        if (t != null) t.insert(values);
    }
}

class SelectCommand implements SQLCommand {
    private String tableName;

    public SelectCommand(String tableName) {
        this.tableName = tableName;
    }

    public void execute(Database db) {
        Table t = db.getTable(tableName);
        if (t != null) t.printAll();
    }
}

class CommandFactory {
    public static SQLCommand createCommand(String sql) {
        sql = sql.trim().toUpperCase();

        if (sql.startsWith("CREATE TABLE")) {
            String[] parts = sql.split("\\s+");
            String table = parts[2];
            return new CreateTableCommand(table, List.of("id", "name")); // simplify
        } else if (sql.startsWith("INSERT")) {
            return new InsertCommand("users", List.of("1", "Anay"));
        } else if (sql.startsWith("SELECT")) {
            return new SelectCommand("users");
        }
        throw new IllegalArgumentException("Unknown SQL: " + sql);
    }
}

public class Main {
    public static void main(String[] args) {
        Database db = Database.getInstance();
        db.registerObserver(new ConsoleObserver());

        SQLCommand c1 = CommandFactory.createCommand("CREATE TABLE users (id, name)");
        c1.execute(db);

        SQLCommand c2 = CommandFactory.createCommand("INSERT INTO users VALUES (1, 'Anay')");
        c2.execute(db);

        SQLCommand c3 = CommandFactory.createCommand("SELECT * FROM users");
        c3.execute(db);
    }
}