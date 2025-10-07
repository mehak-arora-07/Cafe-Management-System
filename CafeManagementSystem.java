import java.util.Scanner;

class CafeManagementSystem {
    static class MenuItem {
        int id;
        String name;
        double price;

        MenuItem(int id, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        public String toString() {
            return id + ". " + name + " - ₹" + price;
        }
    }

    static class Order {
        int id;
        MenuItem[] items;
        int[] quantities;
        int itemCount;
        double total;

        Order(int id, int maxItems) {
            this.id = id;
            this.items = new MenuItem[maxItems];
            this.quantities = new int[maxItems];
            this.itemCount = 0;
            this.total = 0.0;
        }

        void addItem(MenuItem item, int quantity) {
            if (itemCount < items.length) {
                items[itemCount] = item;
                quantities[itemCount] = quantity;
                total += item.price * quantity;
                itemCount++;
            } else {
                System.out.println("Order is full. Cannot add more items.");
            }
        }

        double calculateDiscount() {
            if (total > 499) return total * 0.07;
            else if (total > 399) return total * 0.05;
            return 0;
        }

        void displayOrder() {
            System.out.println("Order ID: " + id);
            for (int i = 0; i < itemCount; i++) {
                System.out.println(" - " + items[i].name + " (x" + quantities[i] + ") - ₹" + items[i].price);
            }
            System.out.println("Total: ₹" + total);
            double discount = calculateDiscount();
            System.out.println("Discount: ₹" + discount);
            System.out.println("Total after Discount: ₹" + (total - discount));
        }

        void printBill() {
            double discount = calculateDiscount();
            double finalTotal = total - discount;
            System.out.println("\n--- Bill for Order ID: " + id + " ---");
            for (int i = 0; i < itemCount; i++) {
                System.out.println(items[i].name + " (x" + quantities[i] + ") - ₹" + items[i].price + " each");
            }
            System.out.println("\nSubtotal: ₹" + total);
            System.out.println("Discount: ₹" + discount);
            System.out.println("Final Total: ₹" + finalTotal);
        }
    }

    static class Cafe {
        MenuItem[] menuItems;
        int menuSize;
        Order[] allOrders;
        int orderCount;
        int nextOrderId;

        Cafe(int maxMenuSize, int maxOrders) {
            menuItems = new MenuItem[maxMenuSize];
            allOrders = new Order[maxOrders];
            menuSize = 0;
            orderCount = 0;
            nextOrderId = 1;
        }

        void addMenuItem(int id, String name, double price) {
            if (menuSize < menuItems.length) {
                menuItems[menuSize++] = new MenuItem(id, name, price);
            } else {
                System.out.println("Menu is full. Cannot add more items.");
            }
        }

        void displayMenu() {
            System.out.println("\n--- Cafe Menu ---");
            for (int i = 0; i < menuSize; i++) {
                System.out.println(menuItems[i]);
            }
        }

        MenuItem getItemById(int id) {
            for (int i = 0; i < menuSize; i++) {
                if (menuItems[i].id == id) {
                    return menuItems[i];
                }
            }
            return null;
        }

        Order createOrder(int maxItems) {
            Order newOrder = new Order(nextOrderId++, maxItems);
            allOrders[orderCount++] = newOrder;
            return newOrder;
        }

        void displayAllOrders() {
            if (orderCount == 0) {
                System.out.println("No orders yet.");
            } else {
                System.out.println("\n--- All Orders ---");
                for (int i = 0; i < orderCount; i++) {
                    allOrders[i].displayOrder();
                    System.out.println();
                }
            }
        }
    }

    public static void pause(Scanner scanner) {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine(); 
        scanner.nextLine(); 
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Cafe cafe = new Cafe(20, 10);

        // Adding menu items
        cafe.addMenuItem(1, "Iced Americano", 100.0);
        cafe.addMenuItem(2, "Espresso", 80.0);
        cafe.addMenuItem(3, "Taro Boba Tea", 150.0);
        cafe.addMenuItem(4, "CheeseCake", 120.0);
        cafe.addMenuItem(5, "Sphagetti", 200.0);
        cafe.addMenuItem(6, "Chocolate Milkshake", 180.0);
        cafe.addMenuItem(7, "Lemon Ice Tea", 90.0);
        cafe.addMenuItem(8, "Blueberry Smoothie", 130.0);
        cafe.addMenuItem(9, "Combo:Burger + Fries + Coke", 250.0);
        cafe.addMenuItem(10, "Peach Ice Tea", 100.0);

        while (true) {
            System.out.println("\n**** WELCOME TO MOONBUCKS CAFE ****");
            System.out.println("1. View Menu");
            System.out.println("2. Create Order");
            System.out.println("3. View All Orders");
            System.out.println("4. Get the Bill");
            System.out.println("5. Exit");
            System.out.print("Choose an option (1-5): ");

            int choice = scanner.nextInt();

            if (choice < 1 || choice > 5) {
                System.out.println("Invalid option. Please choose between 1 and 5.");
                pause(scanner);
                continue;
            }

            switch (choice) {
                case 1:
                    cafe.displayMenu();
                    pause(scanner);
                    break;

                case 2:
                    Order order = cafe.createOrder(5);
                    if (order != null) {
                        cafe.displayMenu();

                        while (true) {
                            System.out.print("Enter item ID to add to order (0 to finish): ");
                            int itemId = scanner.nextInt();
                            if (itemId == 0) break;

                            MenuItem selectedItem = cafe.getItemById(itemId);
                            if (selectedItem == null) {
                                System.out.println("Invalid item ID. Please try again.");
                                continue;
                            }

                            System.out.print("Enter quantity: ");
                            int quantity = scanner.nextInt();
                            if (quantity <= 0) {
                                System.out.println("Quantity must be greater than 0.");
                                continue;
                            }

                            order.addItem(selectedItem, quantity);
                            System.out.println(selectedItem.name + " (x" + quantity + ") added to order.");
                        }

                        System.out.println("\nOrder Summary:");
                        order.displayOrder();
                    }
                    pause(scanner);
                    break;

                case 3:
                    cafe.displayAllOrders();
                    pause(scanner);
                    break;

                case 4:
                    if (cafe.orderCount > 0) {
                        Order latestOrder = cafe.allOrders[cafe.orderCount - 1];
                        latestOrder.printBill();
                    } else {
                        System.out.println("No orders have been placed yet.");
                    }
                    pause(scanner);
                    break;

                case 5:
                    System.out.println("Exiting system. Goodbye!");
                    scanner.close();
                    return;
            }
        }
    }
}

