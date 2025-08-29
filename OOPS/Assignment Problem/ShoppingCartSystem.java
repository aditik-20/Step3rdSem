import java.util.*;

class Product {
    String productId;
    String productName;
    double price;
    String category;
    int stockQuantity;
    static int totalProducts = 0;
    static String[] categories = {"Electronics","Books","Fashion","Groceries","Home"};

    public Product(String productName, double price, String category, int stockQuantity) {
        this.productId = generateProductId();
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.stockQuantity = stockQuantity;
        totalProducts++;
    }

    private static String generateProductId() {
        return String.format("P%03d", totalProducts + 1);
    }

    public static Product findProductById(Product[] products, String productId) {
        for (Product p : products) if (p != null && p.productId.equals(productId)) return p;
        return null;
    }

    public static Product[] getProductsByCategory(Product[] products, String category) {
        Product[] temp = new Product[products.length];
        int k = 0;
        for (Product p : products) if (p != null && p.category.equalsIgnoreCase(category)) temp[k++] = p;
        Product[] result = new Product[k];
        for (int i = 0; i < k; i++) result[i] = temp[i];
        return result;
    }
}

class ShoppingCart {
    String cartId;
    String customerName;
    Product[] products;
    int[] quantities;
    double cartTotal;
    int count;

    public ShoppingCart(String customerName, int capacity) {
        this.cartId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.customerName = customerName;
        this.products = new Product[capacity];
        this.quantities = new int[capacity];
        this.cartTotal = 0;
        this.count = 0;
    }

    public void addProduct(Product product, int quantity) {
        if (product == null || quantity <= 0) return;
        if (product.stockQuantity < quantity) {
            System.out.println("Insufficient stock for " + product.productName);
            return;
        }
        int idx = indexOf(product.productId);
        if (idx >= 0) {
            quantities[idx] += quantity;
        } else {
            if (count == products.length) {
                System.out.println("Cart full");
                return;
            }
            products[count] = product;
            quantities[count] = quantity;
            count++;
        }
        product.stockQuantity -= quantity;
        calculateTotal();
        System.out.println(product.productName + " x" + quantity + " added");
    }

    public void removeProduct(String productId) {
        int idx = indexOf(productId);
        if (idx == -1) {
            System.out.println("Product not in cart");
            return;
        }
        products[idx].stockQuantity += quantities[idx];
        for (int i = idx; i < count - 1; i++) {
            products[i] = products[i + 1];
            quantities[i] = quantities[i + 1];
        }
        products[count - 1] = null;
        quantities[count - 1] = 0;
        count--;
        calculateTotal();
        System.out.println(productId + " removed");
    }

    private int indexOf(String productId) {
        for (int i = 0; i < count; i++) if (products[i].productId.equals(productId)) return i;
        return -1;
    }

    public void calculateTotal() {
        double t = 0;
        for (int i = 0; i < count; i++) t += products[i].price * quantities[i];
        cartTotal = t;
    }

    public void displayCart() {
        System.out.println("Cart: " + cartId + " | Customer: " + customerName);
        if (count == 0) {
            System.out.println("Cart is empty");
        } else {
            for (int i = 0; i < count; i++) {
                System.out.println(products[i].productId + " | " + products[i].productName + " | " + products[i].price + " x " + quantities[i] + " = " + (products[i].price * quantities[i]));
            }
            System.out.println("Total: " + cartTotal);
        }
        System.out.println("----------------------------");
    }

    public void checkout() {
        if (count == 0) {
            System.out.println("Cart empty");
            return;
        }
        displayCart();
        System.out.println("Checkout complete. Amount paid: " + cartTotal);
        for (int i = 0; i < count; i++) {
            products[i] = null;
            quantities[i] = 0;
        }
        count = 0;
        cartTotal = 0;
    }
}

public class ShoppingCartSystem {
    public static void main(String[] args) {
        Product[] catalog = new Product[12];
        catalog[0] = new Product("Smartphone", 15999, "Electronics", 10);
        catalog[1] = new Product("Laptop", 54999, "Electronics", 5);
        catalog[2] = new Product("Headphones", 1999, "Electronics", 20);
        catalog[3] = new Product("Novel", 399, "Books", 50);
        catalog[4] = new Product("Cookbook", 599, "Books", 30);
        catalog[5] = new Product("T-Shirt", 499, "Fashion", 40);
        catalog[6] = new Product("Jeans", 1299, "Fashion", 25);
        catalog[7] = new Product("Rice 5kg", 349, "Groceries", 60);
        catalog[8] = new Product("Olive Oil 1L", 699, "Groceries", 35);
        catalog[9] = new Product("Mixer Grinder", 2499, "Home", 15);
        catalog[10] = new Product("Bedsheet", 899, "Home", 22);
        catalog[11] = new Product("Shoes", 1999, "Fashion", 18);

        Scanner sc = new Scanner(System.in);
        ShoppingCart cart = new ShoppingCart("Aditi", 20);

        boolean running = true;
        while (running) {
            System.out.println("1 Browse All  2 Browse By Category  3 Add  4 Remove  5 View Cart  6 Checkout  0 Exit");
            int ch = -1;
            try { ch = Integer.parseInt(sc.nextLine().trim()); } catch (Exception e) { ch = -1; }
            if (ch == 1) {
                for (Product p : catalog) System.out.println(p.productId + " | " + p.productName + " | " + p.category + " | " + p.price + " | Stock: " + p.stockQuantity);
            } else if (ch == 2) {
                System.out.println("Categories:");
                for (String c : Product.categories) System.out.print(c + " ");
                System.out.println();
                String cat = sc.nextLine().trim();
                Product[] filtered = Product.getProductsByCategory(catalog, cat);
                if (filtered.length == 0) System.out.println("No products");
                for (Product p : filtered) System.out.println(p.productId + " | " + p.productName + " | " + p.price + " | Stock: " + p.stockQuantity);
            } else if (ch == 3) {
                System.out.println("Enter Product ID:");
                String pid = sc.nextLine().trim();
                Product p = Product.findProductById(catalog, pid);
                System.out.println("Enter Quantity:");
                int q = 0;
                try { q = Integer.parseInt(sc.nextLine().trim()); } catch (Exception e) { q = 0; }
                cart.addProduct(p, q);
            } else if (ch == 4) {
                System.out.println("Enter Product ID to remove:");
                String pid = sc.nextLine().trim();
                cart.removeProduct(pid);
            } else if (ch == 5) {
                cart.displayCart();
            } else if (ch == 6) {
                cart.checkout();
            } else if (ch == 0) {
                running = false;
            }
        }
        sc.close();
    }
}
