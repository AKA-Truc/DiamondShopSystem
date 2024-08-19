//package goldiounes.com.vn.models;
//
//import goldiounes.com.vn.models.entities.*;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class CartItemTest {
//
//    @Test
//    void testGetterAndSetter() {
//        User user = new User("Nhi", "098", "len@gmail.com", "527 dbp", "nhan vien");
//        Cart cart = new Cart(user);
//        Category category = new Category("AAA");
//        Product product = new Product(category,"BBB","URL",
//                1.2,500,24);
//        CartItem cartItem = new CartItem(cart, product, 2);
//
//        assertEquals(cart,cartItem.getCart());
//        assertEquals(product,cartItem.getProduct());
//        assertEquals(2,cartItem.getQuantity());
//
//        User user1 = new User("Nhi", "098", "len@gmail.com", "527 dbp", "nhan vien");
//        Cart cart1 = new Cart(user1);
//        Category category1 = new Category("AAA");
//        Product product1 = new Product(category1,"BBB","URL",
//                1.2,500,24);
//        cartItem.setCart(cart1);
//        cartItem.setProduct(product1);
//        cartItem.setQuantity(5);
//
//        assertEquals(cart1, cartItem.getCart());
//        assertEquals(product1, cartItem.getProduct());
//        assertEquals(5, cartItem.getQuantity());
//    }
//
//    @Test
//    void testConstructor() {
//        Cart cart = new Cart();
//        Product product = new Product();
//        CartItem cartItem = new CartItem(cart, product, 3);
//
//        assertNotNull(cartItem);
//        assertEquals(cart, cartItem.getCart());
//        assertEquals(product, cartItem.getProduct());
//        assertEquals(3, cartItem.getQuantity());
//    }
//
//    @Test
//    void testDefaultConstructor() {
//        CartItem cartItem = new CartItem();
//
//        assertNotNull(cartItem);
//        assertNull(cartItem.getCart());
//        assertNull(cartItem.getProduct());
//        assertEquals(0, cartItem.getQuantity());
//    }
//}