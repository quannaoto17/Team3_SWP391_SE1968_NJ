package com.example.PCOnlineShop.service.cart;

import com.example.PCOnlineShop.dto.build.BuildItemDto;
import com.example.PCOnlineShop.dto.cart.CartItemDTO;
import com.example.PCOnlineShop.dto.cart.CartSummaryDTO;
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.model.cart.Cart;
import com.example.PCOnlineShop.model.cart.CartItem;
import com.example.PCOnlineShop.model.product.Product;
import com.example.PCOnlineShop.repository.cart.CartItemRepository;
import com.example.PCOnlineShop.repository.cart.CartRepository;
import com.example.PCOnlineShop.repository.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    private Cart getOrCreateCart(Account account) {
        return cartRepository.findByAccount(account)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setAccount(account);
                    return cartRepository.save(newCart);
                });
    }

    public List<CartItemDTO> getCartItems(Account account) {
        Cart cart = getOrCreateCart(account);
        List<CartItem> items = cartItemRepository.findByCart(cart);
        List<CartItemDTO> dtos = new ArrayList<>();
        for (CartItem item : items) {
            Product product = item.getProduct();
            if (product != null) {
                Hibernate.initialize(product.getImages());
                CartItemDTO dto = new CartItemDTO();
                dto.setCartItemId(item.getCartItemId());
                dto.setProductId(product.getProductId());
                dto.setProductName(product.getProductName());
                dto.setPrice(product.getPrice());
                dto.setQuantity(item.getQuantity());
                dto.setInventoryQuantity(product.getInventoryQuantity());
                dto.setSelected(item.isSelected());
                if(product.getImages() != null && !product.getImages().isEmpty()) {
                    dto.setImageUrl(product.getImages().get(0).getImageUrl());
                } else {
                    dto.setImageUrl("/images/no-image.png");
                }
                dtos.add(dto);
            }
        }
        dtos.sort(Comparator.comparing(CartItemDTO::getProductName));
        return dtos;
    }

    public double calculateSelectedTotal(List<CartItemDTO> cartItems) {
        return cartItems.stream()
                .filter(CartItemDTO::isSelected)
                .mapToDouble(CartItemDTO::getSubtotal)
                .sum();
    }

    public CartSummaryDTO getCartDetails(Account account) {
        List<CartItemDTO> items = getCartItems(account);
        double total = calculateSelectedTotal(items);
        return new CartSummaryDTO(items, total);
    }

    public double calculateSelectedTotalForAccount(Account account) {
        return calculateSelectedTotal(getCartItems(account));
    }

    public void addToCart(Account account, int productId, int quantity) {
        Cart cart = getOrCreateCart(account);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        if (!product.isStatus()) {
            throw new IllegalArgumentException("Product is unavailable.");
        }
        Optional<CartItem> existingItem = cartItemRepository
                .findByCartAndProduct(cart, product);
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            cartItemRepository.save(item);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setSelected(true);
            cartItemRepository.save(newItem);
        }
    }

    public void addBuildToCart(Account account, BuildItemDto buildItems) {
        // Validate input
        if (buildItems == null) {
            throw new IllegalArgumentException("Build items cannot be null");
        }

        Cart cart = getOrCreateCart(account);


        Consumer<Product> addBuildItem = (product) -> {
            if (product != null) {

                addToCart(account, product.getProductId(), 1);
            }
        };

        // Add components with null safety checks
        if (buildItems.getMainboard() != null) {
            addBuildItem.accept(buildItems.getMainboard().getProduct());
        }
        if (buildItems.getCpu() != null) {
            addBuildItem.accept(buildItems.getCpu().getProduct());
        }
        if (buildItems.getGpu() != null) {
            addBuildItem.accept(buildItems.getGpu().getProduct());
        }
        if (buildItems.getMemory() != null) {
            addBuildItem.accept(buildItems.getMemory().getProduct());
        }
        if (buildItems.getStorage() != null) {
            addBuildItem.accept(buildItems.getStorage().getProduct());
        }
        if (buildItems.getPowerSupply() != null) {
            addBuildItem.accept(buildItems.getPowerSupply().getProduct());
        }
        if (buildItems.getPcCase() != null) {
            addBuildItem.accept(buildItems.getPcCase().getProduct());
        }
        if (buildItems.getCooling() != null) {
            addBuildItem.accept(buildItems.getCooling().getProduct());
        }
        if (buildItems.getOther() != null) {
            addBuildItem.accept(buildItems.getOther());
        }
    }

    public void addListToCart(Account account, List<Integer> productIds, int quantity) {
        Cart cart = getOrCreateCart(account);
        for (Integer productId : productIds) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found: " + productId));
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setSelected(true);
            cartItemRepository.save(newItem);
        }
    }

    public void updateQuantity(Account account, int cartItemId, int quantity) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));
        if(item.getCart().getAccount().getAccountId() != account.getAccountId()) {
            throw new SecurityException("Not authorized");
        }
        if (quantity <= 0) {
            cartItemRepository.delete(item);
        } else {
            item.setQuantity(quantity);
            cartItemRepository.save(item);
        }
    }

    public void removeFromCart(Account account, int cartItemId) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));
        if(item.getCart().getAccount().getAccountId() != account.getAccountId()) {
            throw new SecurityException("Not authorized");
        }
        cartItemRepository.delete(item);
    }

    public Map<Integer, CartItem> getCartMapForCheckout(Account account) {
        Cart cart = getOrCreateCart(account);
        return cartItemRepository.findByCartAndIsSelected(cart, true)
                .stream()
                .collect(Collectors.toMap(item -> item.getProduct().getProductId(), item -> item));
    }

    public void toggleSelectItem(Account account, int cartItemId, boolean isSelected) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));
        if(item.getCart().getAccount().getAccountId() != account.getAccountId()) {
            throw new SecurityException("Not authorized");
        }
        item.setSelected(isSelected);
        cartItemRepository.save(item);
    }

    public void clearSelectedItems(Account account) {
        Cart cart = getOrCreateCart(account);
        cartItemRepository.deleteByCartAndIsSelected(cart, true);
    }

    public void clearCart(Account account) {
        Cart cart = getOrCreateCart(account);
        cartItemRepository.deleteByCart(cart);
    }
}