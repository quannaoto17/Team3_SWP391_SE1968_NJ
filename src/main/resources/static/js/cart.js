document.addEventListener("DOMContentLoaded", function () {

    // Lấy token CSRF (nếu bạn dùng Spring Security)
    const csrfToken = document.querySelector('meta[name="_csrf"]') ? document.querySelector('meta[name="_csrf"]').getAttribute('content') : '';
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]') ? document.querySelector('meta[name="_csrf_header"]').getAttribute('content') : 'X-CSRF-TOKEN';

    const cartContainer = document.querySelector(".cart-container");
    if (!cartContainer) return;

    const grandTotalElement = document.getElementById("grand-total-value");
    const selectedItemsCountElement = document.getElementById("selected-items-count");
    const selectedItemsTotalElement = document.getElementById("selected-items-total");
    const checkoutLink = document.getElementById("checkout-link");
    const selectAllCheckbox = document.getElementById("select-all-checkbox");

    // --- HÀM CHUNG ---

    /**
     * Gửi request AJAX (POST)
     * @param {string} url - Endpoint
     * @param {FormData} body - Dữ liệu form
     * @param {function(object): void} onSuccess - Callback khi thành công
     * @param {function(string): void} onError - Callback khi thất bại
     */
    function postAjax(url, body, onSuccess, onError) {
        const headers = {
            'Accept': 'application/json',
        };
        // Thêm CSRF token nếu có
        if (csrfToken) {
            headers[csrfHeader] = csrfToken;
        }

        fetch(url, {
            method: 'POST',
            headers: headers,
            body: body
        })
            .then(response => {
                if (!response.ok) {
                    // Cố gắng đọc lỗi JSON từ server
                    return response.json().then(errData => {
                        throw new Error(errData.error || 'Unknown server error');
                    });
                }

                if (response.status === 204) {
                    return null;
                }
                return response.json();
            })
            .then(data => {
                if (onSuccess) onSuccess(data);
            })
            .catch(error => {
                console.error('AJAX Error:', error);
                if (onError) onError(error.message);
                showMessage(error.message || "An error occurred.", "danger");
            });
    }

    /**
     * Cập nhật tổng tiền và trạng thái UI
     * @param {number} newGrandTotal - Tổng tiền mới (từ server)
     */
    function updateTotals(newGrandTotal) {
        const formatter = new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND'});
        const formattedTotal = formatter.format(newGrandTotal).replace(/\s/g, ' '); // Định dạng "1.234.567 ₫"

        if (grandTotalElement) {
            grandTotalElement.textContent = formattedTotal;
        }
        if (selectedItemsTotalElement) {
            selectedItemsTotalElement.textContent = formattedTotal;
        }

        // Cập nhật số lượng item đã chọn
        const selectedCheckboxes = document.querySelectorAll(".cart-item-checkbox:checked");
        if (selectedItemsCountElement) {
            selectedItemsCountElement.textContent = selectedCheckboxes.length;
        }

        // Cập nhật trạng thái nút checkout
        if (checkoutLink) {
            if (newGrandTotal > 0 && selectedCheckboxes.length > 0) {
                checkoutLink.classList.remove("disabled");
            } else {
                checkoutLink.classList.add("disabled");
            }
        }

        // Cập nhật trạng thái "Select All"
        const allItemCheckboxes = document.querySelectorAll(".cart-item-checkbox");
        if (selectAllCheckbox) {
            selectAllCheckbox.checked = allItemCheckboxes.length > 0 && selectedCheckboxes.length === allItemCheckboxes.length;
        }
    }

    /**
     * Xử lý khi trang được tải (cập nhật tổng tiền ban đầu)
     */
    function initializeCartState() {
        // Lấy tổng tiền ban đầu (đã được tính bởi server)
        const initialTotal = parseFloat(grandTotalElement.getAttribute('data-raw-total') || 0);
        updateTotals(initialTotal);
    }

    /**
     * Hiển thị thông báo
     * @param {string} message - Nội dung
     * @param {string} type - 'success' hoặc 'danger'
     */
    function showMessage(message, type = 'success') {
        const messagesContainer = document.getElementById('cart-messages');
        if (!messagesContainer) return;

        const alert = `
            <div class="alert alert-${type} alert-dismissible fade show" role="alert">
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>`;
        messagesContainer.innerHTML = alert;
    }

    // 1. Sự kiện cho các nút (+), (-), và (Xóa)
    cartContainer.addEventListener("click", function (event) {
        const target = event.target;

        // Tìm thẻ .cart-item cha
        const cartItemElement = target.closest(".cart-item");
        if (!cartItemElement) return;


        const cartItemId = cartItemElement.getAttribute("data-cart-item-id");
        if (!cartItemId) return;

        // Lấy thẻ input số lượng
        const quantityInput = cartItemElement.querySelector(".quantity-display");
        let currentQuantity = parseInt(quantityInput.value, 10);

        // Nút (+) Tăng số lượng
        if (target.classList.contains("increase-qty-btn")) {
            const inventory = parseInt(cartItemElement.getAttribute("data-inventory"), 10);
            if (currentQuantity < inventory) {
                updateQuantity(cartItemId, currentQuantity + 1);
            }
        }

        // Nút (-) Giảm số lượng
        if (target.classList.contains("decrease-qty-btn")) {
            if (currentQuantity > 1) {
                updateQuantity(cartItemId, currentQuantity - 1);
            }
        }

        // Nút (Xóa)
        if (target.classList.contains("remove-item-btn")) {
            if (confirm("Are you sure you want to remove this item?")) {
                removeItem(cartItemId);
            }
        }
    });

    // 2. Sự kiện cho các Checkbox (Chọn/Bỏ chọn)
    cartContainer.addEventListener("change", function (event) {
        const target = event.target;


        if (target.classList.contains("cart-item-checkbox")) {
            const cartItemId = target.getAttribute("data-cart-item-id");
            const isSelected = target.checked;
            toggleSelectItem(cartItemId, isSelected); // Gọi API để cập nhật DB
        }
    });

    // 3. Sự kiện cho Checkbox "Select All"
    if (selectAllCheckbox) {
        selectAllCheckbox.addEventListener("change", function (event) {
            const isSelected = event.target.checked;
            const allCheckboxes = document.querySelectorAll(".cart-item-checkbox");
            allCheckboxes.forEach(checkbox => {
                // Chỉ gọi API nếu trạng thái thay đổi
                if (checkbox.checked !== isSelected) {
                    checkbox.checked = isSelected;
                    const cartItemId = checkbox.getAttribute("data-cart-item-id");
                    toggleSelectItem(cartItemId, isSelected); // Gọi API cho từng item
                }
            });
        });
    }

    // 4. Sự kiện cho nút "Clear Cart"
    const clearCartBtn = document.querySelector(".clear-cart-icon-btn");
    if(clearCartBtn) {
        clearCartBtn.addEventListener("click", function() {
            if (confirm("Are you sure you want to clear your entire cart?")) {
                // Submit form
                document.getElementById("clear-cart-form").submit();
            }
        });
    }


    /**
     * Gọi API /update/{cartItemId}
     * @param {string} cartItemId
     * @param {number} quantity
     */
    function updateQuantity(cartItemId, quantity) {
        const formData = new FormData();
        formData.append('quantity', quantity);

        postAjax(`/cart/update/${cartItemId}`, formData,
            (data) => {
                // Cập nhật UI ngay lập tức
                const cartItemElement = document.querySelector(`.cart-item[data-cart-item-id="${cartItemId}"]`);
                if (cartItemElement) {
                    cartItemElement.querySelector(".quantity-display").value = quantity;

                    // Cập nhật subtotal
                    const price = parseFloat(cartItemElement.getAttribute("data-price"));
                    const subtotal = price * quantity;
                    const formatter = new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND'});
                    cartItemElement.querySelector(".item-subtotal").textContent = formatter.format(subtotal).replace(/\s/g, ' ');

                    // Cập nhật nút
                    cartItemElement.querySelector(".decrease-qty-btn").disabled = (quantity <= 1);
                    const inventory = parseInt(cartItemElement.getAttribute("data-inventory"), 10);
                    cartItemElement.querySelector(".increase-qty-btn").disabled = (quantity >= inventory);
                }
                // Cập nhật tổng tiền từ server
                if (data && data.newGrandTotal !== undefined) {
                    updateTotals(data.newGrandTotal);
                }
                showMessage("Quantity updated.", "success");
            },
            (errorMsg) => {
                showMessage(errorMsg, "danger");

            }
        );
    }

    /**
     * Gọi API /remove/{cartItemId}
     * @param {string} cartItemId
     */
    function removeItem(cartItemId) {
        postAjax(`/cart/remove/${cartItemId}`, new FormData(),
            (data) => {
                // Xóa element khỏi DOM
                const cartItemElement = document.querySelector(`.cart-item[data-cart-item-id="${cartItemId}"]`);
                if (cartItemElement) {
                    cartItemElement.remove();
                }
                // Cập nhật tổng tiền từ server
                if (data && data.newGrandTotal !== undefined) {
                    updateTotals(data.newGrandTotal);
                }
                // Kiểm tra nếu giỏ hàng rỗng
                if (document.querySelectorAll(".cart-item").length === 0) {
                    location.reload(); // Tải lại trang để hiển thị "Empty Cart"
                }
                showMessage("Item removed.", "success");
            },
            (errorMsg) => {
                showMessage(errorMsg, "danger");
            }
        );
    }


    function toggleSelectItem(cartItemId, isSelected) {
        const url = isSelected ? `/cart/select/${cartItemId}` : `/cart/deselect/${cartItemId}`;

        postAjax(url, new FormData(),
            (data) => {
                // Cập nhật tổng tiền từ server (server sẽ tính lại dựa trên is_selected)
                if (data && data.newGrandTotal !== undefined) {
                    updateTotals(data.newGrandTotal);
                }
            },
            (errorMsg) => {

                const checkbox = document.querySelector(`.cart-item-checkbox[data-cart-item-id="${cartItemId}"]`);
                if (checkbox) {
                    checkbox.checked = !isSelected;
                }
                showMessage(errorMsg, "danger");
            }
        );
    }

    initializeCartState();
});