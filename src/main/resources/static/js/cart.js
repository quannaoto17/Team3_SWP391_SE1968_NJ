document.addEventListener('DOMContentLoaded', () => {
    const cartContainer = document.querySelector('.cart-container');
    // Lấy CSRF token từ meta tag (nếu bạn dùng CSRF)
    const csrfToken = document.querySelector('meta[name="_csrf"]')?.getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.getAttribute('content');
    const headers = { 'Content-Type': 'application/x-www-form-urlencoded' };
    if (csrfToken && csrfHeader) {
        headers[csrfHeader] = csrfToken;
    }

    const cartMessages = document.getElementById('cart-messages');

    // --- Hàm định dạng tiền tệ ---
    function formatCurrency(amount) {
        return Math.round(amount).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + ' ₫';
    }

    // --- Hàm cập nhật tổng tiền và UI ---
    function updateTotals() {
        let selectedTotal = 0;
        let selectedCount = 0;
        const cartItems = document.querySelectorAll('.cart-item');
        let totalItemsCount = 0;

        cartItems.forEach(item => {
            const price = parseFloat(item.dataset.price);
            const quantityInput = item.querySelector('.quantity-display');
            if (!quantityInput) return;
            const quantity = parseInt(quantityInput.value);
            const isChecked = item.querySelector('.cart-item-checkbox').checked;
            const subtotal = price * quantity;

            const subtotalElement = item.querySelector('.item-subtotal');
            if (subtotalElement) subtotalElement.textContent = formatCurrency(subtotal);

            if (isChecked) {
                selectedTotal += subtotal;
                selectedCount++;
            }
            totalItemsCount++;
        });

        const selectedItemsCountEl = document.getElementById('selected-items-count');
        const selectedItemsTotalEl = document.getElementById('selected-items-total');
        const grandTotalValueEl = document.getElementById('grand-total-value');
        const checkoutLink = document.getElementById('checkout-link');
        const totalItemsCountEl = document.getElementById('total-items-count');
        const selectAllCheckbox = document.getElementById('select-all-checkbox');

        if (selectedItemsCountEl) selectedItemsCountEl.textContent = selectedCount;
        if (selectedItemsTotalEl) selectedItemsTotalEl.textContent = formatCurrency(selectedTotal);
        if (grandTotalValueEl) grandTotalValueEl.textContent = formatCurrency(selectedTotal);
        if (totalItemsCountEl) totalItemsCountEl.textContent = totalItemsCount;

        if (checkoutLink) {
            checkoutLink.classList.toggle('disabled', selectedCount <= 0);
        }

        if (selectAllCheckbox) {
            const allItemCheckboxes = document.querySelectorAll('.cart-item-checkbox');
            selectAllCheckbox.checked = totalItemsCount > 0 && selectedCount === allItemCheckboxes.length;
        }

        // Hiển thị/ẩn giỏ hàng rỗng
        const emptyCartDiv = document.querySelector('.empty-cart');
        const cartContentDiv = document.querySelector('.cart-container > div:not(.empty-cart)'); // Div chứa nội dung giỏ hàng
        if(emptyCartDiv && cartContentDiv){
            emptyCartDiv.style.display = totalItemsCount === 0 ? 'block' : 'none';
            cartContentDiv.style.display = totalItemsCount === 0 ? 'none' : 'block'; // Ẩn header, list, summary nếu rỗng
        }
    }

    // --- Hàm gửi yêu cầu AJAX bằng Fetch API ---
    async function sendCartUpdate(url, method, formData, successCallback, errorMsgPrefix) {
        document.querySelectorAll('.quantity-change-btn, .remove-item-btn, .clear-cart-icon-btn').forEach(btn => btn.disabled = true);

        try {
            const response = await fetch(url, {
                method: method,
                headers: headers, // Gửi headers (bao gồm CSRF nếu có)
                body: formData
            });

            const data = await response.json(); // Mong đợi JSON trả về

            if (response.ok) {
                showFlashMessage('success', data.message || 'Cart updated.');
                if (successCallback) {
                    successCallback(data); // Gọi hàm callback để cập nhật UI
                }
            } else {
                showFlashMessage('danger', `${errorMsgPrefix}: ${data.error || response.statusText}`);
                console.error("Cart update error:", response.status, data);
            }
        } catch (error) {
            showFlashMessage('danger', `${errorMsgPrefix}: Network or script error.`);
            console.error("Fetch error:", error);
        } finally {
            document.querySelectorAll('.quantity-change-btn, .remove-item-btn, .clear-cart-icon-btn').forEach(btn => btn.disabled = false);
            // Cần cập nhật lại trạng thái disable của +/- dựa trên số lượng mới
            document.querySelectorAll('.cart-item').forEach(item => {
                const quantity = parseInt(item.querySelector('.quantity-display')?.value || '0');
                const inventory = parseInt(item.dataset.inventory);
                item.querySelector('.decrease-qty-btn')?.toggleAttribute('disabled', quantity <= 1);
                item.querySelector('.increase-qty-btn')?.toggleAttribute('disabled', quantity >= inventory);
            });
        }
    }

    // --- Hàm hiển thị thông báo động ---
    function showFlashMessage(type, message) {
        // ... (Giữ nguyên hàm showFlashMessage) ...
        if (!cartMessages) return;
        const alertClass = `alert-${type}`;
        const messageDiv = document.createElement('div');
        messageDiv.className = `alert ${alertClass} alert-dismissible fade show`;
        // ... (innerHTML và logic timeout)
    }

    // --- Xử lý sự kiện ---
    cartContainer.addEventListener('click', function(event) {
        const target = event.target.closest('button');
        if (!target) return;

        const cartItem = target.closest('.cart-item');
        if (!cartItem) return; // Bỏ qua nếu là nút Clear All

        const productId = cartItem.dataset.productId;
        const quantityInput = cartItem.querySelector('.quantity-display');
        const currentQuantity = parseInt(quantityInput.value);
        const inventory = parseInt(cartItem.dataset.inventory);

        // Nút giảm (-)
        if (target.classList.contains('decrease-qty-btn')) {
            const newQuantity = currentQuantity - 1;
            if (newQuantity >= 1) {
                const formData = new URLSearchParams(); formData.append('quantity', newQuantity);
                sendCartUpdate(`/cart/update/${productId}`, 'POST', formData, (data) => {
                    quantityInput.value = newQuantity; // Cập nhật số lượng hiển thị
                    updateTotals(); // Tính lại tổng
                }, 'Error updating');
            } else { /* Có thể hỏi xóa */ }
        }
        // Nút tăng (+)
        else if (target.classList.contains('increase-qty-btn')) {
            const newQuantity = currentQuantity + 1;
            if (newQuantity <= inventory) {
                const formData = new URLSearchParams(); formData.append('quantity', newQuantity);
                sendCartUpdate(`/cart/update/${productId}`, 'POST', formData, (data) => {
                    quantityInput.value = newQuantity; // Cập nhật số lượng hiển thị
                    updateTotals(); // Tính lại tổng
                }, 'Error updating');
            } else { /* Báo lỗi tồn kho */ }
        }
        // Nút xóa item (thùng rác nhỏ)
        else if (target.classList.contains('remove-item-btn')) {
            const productName = cartItem.querySelector('.product-name')?.textContent || 'Product';
            if (confirm(`Remove ${productName.trim()} from cart?`)) {
                const formData = new URLSearchParams();
                sendCartUpdate(`/cart/remove/${productId}`, 'POST', formData, (data) => {
                    cartItem.remove(); // Xóa dòng khỏi HTML
                    updateTotals(); // Tính lại tổng
                }, 'Error removing');
            }
        }
    });

    // Checkbox thay đổi
    cartContainer.addEventListener('change', function(event){
        if(event.target.matches('.cart-checkbox')) {
            if (event.target.id === 'select-all-checkbox') {
                const isChecked = event.target.checked;
                document.querySelectorAll('.cart-item-checkbox').forEach(cb => cb.checked = isChecked);
            }
            updateTotals();
        }
    });

    // Nút "Clear Entire Cart"
    const clearCartBtn = document.querySelector('.clear-cart-icon-btn');
    if (clearCartBtn) {
        clearCartBtn.addEventListener('click', function() {
            if (confirm('Are you sure you want to clear the entire cart?')) {
                const clearForm = document.getElementById('clear-cart-form');
                if (clearForm) clearForm.submit(); // Submit form truyền thống
            }
        });
    }

    // --- Khởi tạo ---
    updateTotals(); // Tính tổng tiền lần đầu

});