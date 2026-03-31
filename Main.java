import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class Person {
    private String name;
    private String phone;

    public Person() {
    }

    public Person(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}

class Product {
    private String id;
    private String name;
    private String unit;
    private int quantity;

    public Product() {
    }

    public Product(String id, String name, String unit, int quantity) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

class ReceiptItem {
    private Product product;
    private int quantity;

    public ReceiptItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}

class IssueItem {
    private Product product;
    private int quantity;

    public IssueItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}

class ReceiptNote {
    private String noteNumber;
    private LocalDate receivedDate;
    private Person deliveredBy;
    private Clerk receivedBy;
    private List<ReceiptItem> items;

    public ReceiptNote(String noteNumber, LocalDate receivedDate, Person deliveredBy, Clerk receivedBy) {
        this.noteNumber = noteNumber;
        this.receivedDate = receivedDate;
        this.deliveredBy = deliveredBy;
        this.receivedBy = receivedBy;
        this.items = new ArrayList<>();
    }

    public void addItem(ReceiptItem item) {
        items.add(item);
    }

    public List<ReceiptItem> getItems() {
        return items;
    }

    public void printNote() {
        System.out.println("===== ORLOGIIN PADAA =====");
        System.out.println("Dugaar: " + noteNumber);
        System.out.println("Ognoo: " + receivedDate);
        System.out.println("Huleelgen ugsun hun: " + deliveredBy.getName());
        System.out.println("Huleen avsan nyarav: " + receivedBy.getName());

        for (ReceiptItem item : items) {
            System.out.println(item.getProduct().getName() + " - " + item.getQuantity() + " " + item.getProduct().getUnit());
        }
    }
}

class IssueNote {
    private String noteNumber;
    private LocalDate issuedDate;
    private Person receivedBy;
    private Clerk issuedBy;
    private List<IssueItem> items;

    public IssueNote(String noteNumber, LocalDate issuedDate, Person receivedBy, Clerk issuedBy) {
        this.noteNumber = noteNumber;
        this.issuedDate = issuedDate;
        this.receivedBy = receivedBy;
        this.issuedBy = issuedBy;
        this.items = new ArrayList<>();
    }

    public void addItem(IssueItem item) {
        items.add(item);
    }

    public List<IssueItem> getItems() {
        return items;
    }

    public void printNote() {
        System.out.println("===== ZARLAGIIN PADAA =====");
        System.out.println("Dugaar: " + noteNumber);
        System.out.println("Ognoo: " + issuedDate);
        System.out.println("Huleen avsan hun: " + receivedBy.getName());
        System.out.println("Gargsan nyarav: " + issuedBy.getName());

        for (IssueItem item : items) {
            System.out.println(item.getProduct().getName() + " - " + item.getQuantity() + " " + item.getProduct().getUnit());
        }
    }
}

class Warehouse {
    private String id;
    private String name;
    private Clerk clerk;
    private List<Product> products;
    private List<ReceiptNote> receiptNotes;
    private List<IssueNote> issueNotes;

    public Warehouse(String id, String name) {
        this.id = id;
        this.name = name;
        this.products = new ArrayList<>();
        this.receiptNotes = new ArrayList<>();
        this.issueNotes = new ArrayList<>();
    }

    public void setClerk(Clerk clerk) {
        this.clerk = clerk;
    }

    public String getName() {
        return name;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void addReceiptNote(ReceiptNote note) {
        receiptNotes.add(note);
    }

    public void addIssueNote(IssueNote note) {
        issueNotes.add(note);
    }

    public List<Product> getProducts() {
        return products;
    }

    public void showAllProducts() {
        System.out.println("===== NOOTSIIN TAILAN =====");
        for (Product p : products) {
            System.out.println(p.getName() + " - " + p.getQuantity() + " " + p.getUnit());
        }
    }
}

class Clerk {
    private String id;
    private String name;
    private Warehouse warehouse;

    public Clerk(String id, String name, Warehouse warehouse) {
        this.id = id;
        this.name = name;
        this.warehouse = warehouse;
    }

    public String getName() {
        return name;
    }

    public void receiveGoods(ReceiptNote note) {
        for (ReceiptItem item : note.getItems()) {
            Product p = item.getProduct();
            p.setQuantity(p.getQuantity() + item.getQuantity());
        }
        warehouse.addReceiptNote(note);
        note.printNote();
    }

    public void issueGoods(IssueNote note) {
        for (IssueItem item : note.getItems()) {
            Product p = item.getProduct();
            p.setQuantity(p.getQuantity() - item.getQuantity());
        }
        warehouse.addIssueNote(note);
        note.printNote();
    }

    public void viewInventoryReport() {
        warehouse.showAllProducts();
    }
}

public class Main {
    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse("W01", "Central Warehouse");

        Clerk clerk = new Clerk("C01", "Bat", warehouse);
        warehouse.setClerk(clerk);

        Product rice = new Product("P01", "Rice", "kg", 100);
        Product sugar = new Product("P02", "Sugar", "kg", 50);

        warehouse.addProduct(rice);
        warehouse.addProduct(sugar);

        Person supplier = new Person("Supplier A", "99112233");
        ReceiptNote receipt = new ReceiptNote("R001", LocalDate.now(), supplier, clerk);
        receipt.addItem(new ReceiptItem(rice, 20));
        receipt.addItem(new ReceiptItem(sugar, 10));

        clerk.receiveGoods(receipt);

        Person customer = new Person("Customer B", "88112233");
        IssueNote issue = new IssueNote("I001", LocalDate.now(), customer, clerk);
        issue.addItem(new IssueItem(rice, 30));

        clerk.issueGoods(issue);

        clerk.viewInventoryReport();
    }
}