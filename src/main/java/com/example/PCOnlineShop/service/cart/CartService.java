package com.example.PCOnlineShop.service.cart;

import com.example.PCOnlineShop.dto.cart.CartItemDTO;
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.model.cart.Cart;
import com.example.PCOnlineShop.model.cart.CartItem;
import com.example.PCOnlineShop.model.product.Product;
import com.example.PCOnlineShop.repository.cart.CartItemRepository;
import com.example.PCOnlineShop.repository.cart.CartRepository;
import com.example.PCOnlineShop.repository.product.ProductRepository; // Cần ProductRepository
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional // Đảm bảo tính nhất quán dữ liệu
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository; // Để lấy thông tin sản phẩm

    // --- Lấy hoặc Tạo giỏ hàng cho user ---
    private Cart getOrCreateCart(Account account) {
        // Thử tìm cart active của user, nếu không có thì tạo mới
        return cartRepository.findByAccount(account)
                .orElseGet(() -> cartRepository.save(new Cart(account)));
    }

    // --- Lấy thông tin giỏ hàng để hiển thị ---
    public List<CartItemDTO> getCartItems(Account account) {
        Optional<Cart> cartOpt = cartRepository.findByAccountAndStatusWithItems(account); // Chỉ lấy cart, items, product
        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();
            cart.setUpdatedDate(new Date());

            List<CartItemDTO> dtos = new ArrayList<>();
            if (cart.getItems() != null) {
                for (CartItem item : cart.getItems()) {
                    Product product = item.getProduct();
                    if (product != null) {
                        // KHỞI TẠO (INITIALIZE) COLLECTION IMAGES
                        // Bằng cách gọi size() hoặc làm gì đó với collection images
                        // Hibernate sẽ thực hiện truy vấn riêng để tải images cho product này
                        product.getImages().size(); // Hoặc dùng Hibernate.initialize(product.getImages());

                        // Giờ đây bạn có thể tạo DTO mà không lỗi LazyInitializationException
                        dtos.add(new CartItemDTO(product, item.getQuantity()));
                    }
                }
                // Sắp xếp DTOs nếu cần
                dtos.sort(Comparator.comparing(CartItemDTO::getProductName));
            }
            return dtos;

        }
        return new ArrayList<>();
    }

    // --- Thêm sản phẩm vào giỏ ---
    public void addToCart(Account account, int productId, int quantity) {
        Cart cart = getOrCreateCart(account);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + productId));

        if (!product.isStatus()) {
            throw new IllegalArgumentException("Product is unavailable.");
        }

        int inventoryQuantity = product.getInventoryQuantity();
        Optional<CartItem> existingItemOpt = cartItemRepository.findByCartAndProduct(cart, product);

        if (existingItemOpt.isPresent()) {
            // Sản phẩm đã có -> cập nhật số lượng
            CartItem existingItem = existingItemOpt.get();
            int newQuantity = existingItem.getQuantity() + quantity;
            if (newQuantity > inventoryQuantity) {
                throw new IllegalArgumentException("Cannot add quantity. Only " + (inventoryQuantity - existingItem.getQuantity()) + " more available.");
            }
            if (newQuantity <= 0) { // Nếu thêm số âm dẫn đến <= 0 thì xóa
                cartItemRepository.delete(existingItem);
            } else {
                existingItem.setQuantity(newQuantity);
                cartItemRepository.save(existingItem);
            }
        } else {
            // Sản phẩm chưa có -> thêm mới
            if (quantity <= 0) {
                throw new IllegalArgumentException("Quantity must be positive.");
            }
            if (quantity > inventoryQuantity) {
                throw new IllegalArgumentException("Cannot add quantity. Only " + inventoryQuantity + " available.");
            }
            CartItem newItem = new CartItem(cart, product, quantity);
            cartItemRepository.save(newItem);
        }
        cart.setUpdatedDate(new Date());
        cartRepository.save(cart);
    }

    // --- Cập nhật số lượng ---
    public void updateQuantity(Account account, int productId, int quantity) {
        Cart cart = getOrCreateCart(account); // Cần cartId để tìm đúng item
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + productId));

        Optional<CartItem> itemOpt = cartItemRepository.findByCartAndProduct(cart, product);

        if (itemOpt.isPresent()) {
            CartItem item = itemOpt.get();
            if (quantity <= 0) {
                cartItemRepository.delete(item); // Xóa nếu số lượng <= 0
            } else {
                int inventoryQuantity = product.getInventoryQuantity();
                if (quantity > inventoryQuantity) {
                    throw new IllegalArgumentException("Cannot update quantity. Only " + inventoryQuantity + " available.");
                }
                item.setQuantity(quantity);
                cartItemRepository.save(item);
            }
            cart.setUpdatedDate(new Date());
            cartRepository.save(cart);
        } else {
            throw new EntityNotFoundException("Product not found in cart.");
        }
    }

    // --- Xóa sản phẩm khỏi giỏ ---
    public void removeFromCart(Account account, int productId) {
        Cart cart = getOrCreateCart(account);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + productId));

        Optional<CartItem> itemOpt = cartItemRepository.findByCartAndProduct(cart, product);
        if (itemOpt.isPresent()) {
            cartItemRepository.delete(itemOpt.get());
            cart.setUpdatedDate(new Date());
            cartRepository.save(cart);
        } else {
            throw new EntityNotFoundException("Product not found in cart.");
        }
    }

    // --- Xóa toàn bộ giỏ hàng ---
    public void clearCart(Account account) {
        Optional<Cart> cartOpt = cartRepository.findByAccount(account);
        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();
            // Xóa tất cả cart items liên quan trước (hoặc dùng orphanRemoval=true trong Cart entity)
            cartItemRepository.deleteAll(cart.getItems()); // Xóa items trước
            cart.getItems().clear(); // Clear list trong entity
            // Có thể xóa luôn cart hoặc chỉ clear items tùy logic
            // cartRepository.delete(cart); // Nếu muốn xóa luôn cart
            cart.setUpdatedDate(new Date());
            cartRepository.save(cart); // Lưu lại cart rỗng
        }
    }

    // --- Tính tổng tiền ---
    public double calculateGrandTotal(List<CartItemDTO> cartItems) {
        return cartItems.stream().mapToDouble(CartItemDTO::getSubtotal).sum();
    }

    // --- Lấy Map<Integer, Integer> cho checkout ---
    public Map<Integer, Integer> getCartMapForCheckout(Account account) {
        Map<Integer, Integer> cartMap = new HashMap<>();
        Optional<Cart> cartOpt = cartRepository.findByAccountAndStatusWithItems(account);
        if (cartOpt.isPresent()) {
            cartOpt.get().getItems().forEach(item -> cartMap.put(item.getProduct().getProductId(), item.getQuantity()));
        }
        return cartMap;
    }

}