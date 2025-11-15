document.addEventListener("DOMContentLoaded", function() {

    // --- Lấy các Element ---
    const shipHomeRadio = document.getElementById("ship-home");
    const shipStoreRadio = document.getElementById("ship-store");
    const shippingDetailsBlock = document.getElementById("shipping-details-block");
    const shippingMethodInput = document.getElementById("shippingMethodInput");

    // Input ẩn của form chính
    const inputFullName = document.getElementById("shippingFullNameInput");
    const inputPhone = document.getElementById("shippingPhoneInput");
    const inputAddress = document.getElementById("shippingAddressInput");

    // Hiển thị trên UI
    const displayName = document.getElementById("display-name");
    const displayPhone = document.getElementById("display-phone");
    const displayAddress = document.getElementById("display-address");

    // Modals
    const listModal = document.getElementById("address-list-modal");
    const newModal = document.getElementById("new-address-modal");

    function updateShippingMethod() {
        if (!shipHomeRadio || !shipStoreRadio) return; // Thoát nếu không tìm thấy element

        shipHomeRadio.closest('.custom-radio').classList.toggle('selected', shipHomeRadio.checked);
        shipStoreRadio.closest('.custom-radio').classList.toggle('selected', shipStoreRadio.checked);

        if (shipStoreRadio.checked) {
            shippingDetailsBlock.classList.add("hidden");
            shippingMethodInput.value = "Nhận tại cửa hàng";
        } else {
            shippingDetailsBlock.classList.remove("hidden");
            shippingMethodInput.value = "Giao hàng tận nơi";
        }
    }
    if (shipHomeRadio) shipHomeRadio.addEventListener("change", updateShippingMethod);
    if (shipStoreRadio) shipStoreRadio.addEventListener("change", updateShippingMethod);

    const btnChangeAddress = document.getElementById("btn-change-address");
    const btnCancelList = document.getElementById("btn-cancel-list");

    if (btnChangeAddress) {
        btnChangeAddress.addEventListener("click", function() {
            if (listModal) listModal.style.display = "flex";
        });
    }
    if (btnCancelList) {
        btnCancelList.addEventListener("click", function() {
            if (listModal) listModal.style.display = "none";
        });
    }

    const btnOpenNewModal = document.getElementById("btn-open-new-address-modal");
    const btnCancelNew = document.getElementById("btn-cancel-new");

    if (btnOpenNewModal) {
        btnOpenNewModal.addEventListener("click", function() {
            if (listModal) listModal.style.display = "none";
            if (newModal) newModal.style.display = "flex";
            const errorDiv = document.getElementById("new-address-error");
            if (errorDiv) errorDiv.innerText = "";
        });
    }
    if (btnCancelNew) {
        btnCancelNew.addEventListener("click", function() {
            if (newModal) newModal.style.display = "none";
            if (listModal) listModal.style.display = "flex";
        });
    }

    const btnConfirmAddress = document.getElementById("btn-confirm-address");

    if (btnConfirmAddress) {
        btnConfirmAddress.addEventListener("click", function() {
            const selectedRadio = document.querySelector('input[name="selectedAddress"]:checked');
            if (selectedRadio) {
                const name = selectedRadio.getAttribute('data-name');
                const phone = selectedRadio.getAttribute('data-phone');
                const address = selectedRadio.getAttribute('data-address');

                if(displayName) displayName.innerText = name;
                if(displayPhone) displayPhone.innerText = phone;
                if(displayAddress) displayAddress.innerText = address;

                if (inputFullName) inputFullName.value = name;
                if (inputPhone) inputPhone.value = phone;
                if (inputAddress) inputAddress.value = address;

                const addrDisplayBlock = document.getElementById("address-display-block");
                const noAddrBlock = document.getElementById("no-address-block");
                if (addrDisplayBlock) addrDisplayBlock.classList.remove("hidden");
                if (noAddrBlock) noAddrBlock.classList.add("hidden");

                if (listModal) listModal.style.display = "none";
            } else {
                alert("Bạn chưa chọn địa chỉ.");
            }
        });
    }

    const btnConfirmNewAddress = document.getElementById("btn-confirm-new-address");

    if (btnConfirmNewAddress) {
        btnConfirmNewAddress.addEventListener("click", function() {
            const fullName = document.getElementById("new-name").value;
            const phone = document.getElementById("new-phone").value;
            const address = document.getElementById("new-address").value;
            const errorDiv = document.getElementById("new-address-error");
            if (!errorDiv) return;
            errorDiv.innerText = "";

            const nameRegex = /^[a-zA-ZÀ-ỹ\s]+$/;
            const phoneRegex = /^(0[3|5|7|8|9])+([0-9]{8})\b$/;

            if (!fullName || !phone || !address) {
                errorDiv.innerText = "Vui lòng nhập đầy đủ thông tin.";
                return;
            }
            if (!nameRegex.test(fullName)) {
                errorDiv.innerText = "Họ tên chỉ được chứa chữ cái và khoảng trắng.";
                return;
            }
            if (!phoneRegex.test(phone)) {
                errorDiv.innerText = "Số điện thoại không hợp lệ (gồm 10 số, bắt đầu bằng 03, 05, 07, 08, 09).";
                return;
            }

            const formData = new FormData();
            formData.append('fullName', fullName);
            formData.append('phone', phone);
            formData.append('address', address);

            fetch('/address/add', {
                method: 'POST',
                body: formData,
            })
                .then(response => {
                    if (!response.ok) {
                        return response.json().then(errData => {
                            throw new Error(errData.error || 'Lỗi không xác định từ server.');
                        });
                    }
                    return response.json();
                })
                .then(newAddress => {
                    // 1. Thêm vào danh sách Radio (dùng hàm helper đã sửa)
                    addAddressToRadioList(newAddress);

                    // 2. Cập nhật UI chính
                    if(displayName) displayName.innerText = newAddress.fullName;
                    if(displayPhone) displayPhone.innerText = newAddress.phone;
                    if(displayAddress) displayAddress.innerText = newAddress.address;

                    // 3. Cập nhật Input ẩn
                    if (inputFullName) inputFullName.value = newAddress.fullName;
                    if (inputPhone) inputPhone.value = newAddress.phone;
                    if (inputAddress) inputAddress.value = newAddress.address;

                    // 4. Hiển thị khối địa chỉ
                    const addrDisplayBlock = document.getElementById("address-display-block");
                    const noAddrBlock = document.getElementById("no-address-block");
                    if (addrDisplayBlock) addrDisplayBlock.classList.remove("hidden");
                    if (noAddrBlock) noAddrBlock.classList.add("hidden");

                    // 5. Đóng modal thêm mới, quay lại modal list
                    if (newModal) newModal.style.display = "none";
                    if (listModal) listModal.style.display = "flex";
                })
                .catch(err => {
                    errorDiv.innerText = err.message || "Lỗi hệ thống, không thể thêm địa chỉ.";
                });
        });
    }


    function addAddressToRadioList(addr) {
        const listDiv = document.getElementById("address-radio-list");
        if (!listDiv) return;

        const noAddressMsg = document.getElementById("no-address-msg");
        if (noAddressMsg) noAddressMsg.style.display = 'none';

        const oldChecked = document.querySelector('input[name="selectedAddress"]:checked');
        if (oldChecked) oldChecked.checked = false;

        const newItem = document.createElement('div');
        newItem.className = 'address-item';

        const isDefault = addr.default;

        newItem.innerHTML = `
            <div>
                <input type="radio" name="selectedAddress"
                       id="addr-${addr.addressId}"
                       value="${addr.addressId}"
                       data-name="${addr.fullName}"
                       data-phone="${addr.phone}"
                       data-address="${addr.address}"
                       ${isDefault ? 'checked' : ''}> 
                <label for="addr-${addr.addressId}">
                    <strong class="addr-name">${addr.fullName}</strong> -
                    <span class="addr-phone">${addr.phone}</span>
                    ${isDefault ? '<span style="color: #d70018; font-weight: 600;">(Mặc định)</span>' : ''}
                    <br>
                    <span class="addr-text">${addr.address}</span>
                </label>
            </div>
            <div class="address-item-actions" style="margin-left: 28px; margin-top: 8px; display: flex; gap: 15px;">
                <button type="button" class="btn-change-address btn-edit-address"
                        data-id="${addr.addressId}"
                        data-name="${addr.fullName}"
                        data-phone="${addr.phone}"
                        data-address="${addr.address}">
                    Sửa
                </button>
                ${!isDefault ? `
                    <button type="button" class="btn-change-address btn-set-default"
                            data-id="${addr.addressId}">
                        Đặt làm mặc định
                    </button>
                ` : ''}
            </div>
        `;
        listDiv.appendChild(newItem);
    }


    const editModal = document.getElementById("edit-address-modal");
    const listModalForEdit = document.getElementById("address-list-modal");

    const inputEditId = document.getElementById("edit-address-id");
    const inputEditName = document.getElementById("edit-name");
    const inputEditPhone = document.getElementById("edit-phone");
    const inputEditAddress = document.getElementById("edit-address");
    const errorEditDiv = document.getElementById("edit-address-error");

    const btnCancelEdit = document.getElementById("btn-cancel-edit");
    if (btnCancelEdit) {
        btnCancelEdit.addEventListener("click", function() {
            if (editModal) editModal.style.display = "none";
            if (listModalForEdit) listModalForEdit.style.display = "flex";
        });
    }

    const addressListContainer = document.getElementById("address-radio-list");

    if (addressListContainer) {
        addressListContainer.addEventListener("click", function(event) {
            const target = event.target; // Element được click

            if (target.classList.contains("btn-edit-address")) {
                const id = target.getAttribute("data-id");
                const name = target.getAttribute("data-name");
                const phone = target.getAttribute("data-phone");
                const address = target.getAttribute("data-address");

                if (inputEditId) inputEditId.value = id;
                if (inputEditName) inputEditName.value = name;
                if (inputEditPhone) inputEditPhone.value = phone;
                if (inputEditAddress) inputEditAddress.value = address;
                if (errorEditDiv) errorEditDiv.innerText = "";

                if (listModalForEdit) listModalForEdit.style.display = "none";
                if (editModal) editModal.style.display = "flex";
            }

            if (target.classList.contains("btn-set-default")) {
                const addressId = target.getAttribute("data-id");
                if (!confirm("Bạn có chắc muốn đặt địa chỉ này làm mặc định?")) {
                    return;
                }

                const formData = new FormData();
                formData.append('addressId', addressId);

                fetch('/address/set-default', {
                    method: 'POST',
                    body: formData
                })
                    .then(response => response.json())
                    .then(data => {
                        if (data.error) {
                            alert("Lỗi: " + data.error);
                        } else {
                            alert(data.message); // "Đặt làm mặc định thành công."
                            location.reload(); // Tải lại trang
                        }
                    })
                    .catch(err => {
                        alert("Lỗi hệ thống khi đặt mặc định.");
                    });
            }
        });
    }

    const btnConfirmEdit = document.getElementById("btn-confirm-edit");

    if (btnConfirmEdit) {
        btnConfirmEdit.addEventListener("click", function() {
            if (!inputEditId || !inputEditName || !inputEditPhone || !inputEditAddress || !errorEditDiv) return;

            const id = inputEditId.value;
            const name = inputEditName.value;
            const phone = inputEditPhone.value;
            const address = inputEditAddress.value;

            errorEditDiv.innerText = "";

            const nameRegex = /^[a-zA-ZÀ-ỹ\s]+$/;
            const phoneRegex = /^(0[3|5|7|8|9])+([0-9]{8})\b$/;

            if (!name || !phone || !address) {
                errorEditDiv.innerText = "Vui lòng nhập đầy đủ thông tin.";
                return;
            }
            if (!nameRegex.test(name)) {
                errorEditDiv.innerText = "Họ tên chỉ được chứa chữ cái và khoảng trắng.";
                return;
            }
            if (!phoneRegex.test(phone)) {
                errorEditDiv.innerText = "Số điện thoại không hợp lệ.";
                return;
            }

            const formData = new FormData();
            formData.append('addressId', id);
            formData.append('fullName', name);
            formData.append('phone', phone);
            formData.append('address', address);

            fetch('/address/update', {
                method: 'POST',
                body: formData
            })
                .then(response => {
                    if (!response.ok) {
                        return response.json().then(errData => {
                            throw new Error(errData.error || 'Lỗi không xác định.');
                        });
                    }
                    return response.json();
                })
                .then(updatedAddress => {
                    const radio = document.getElementById('addr-' + updatedAddress.addressId);
                    if (!radio) return;

                    const addressItem = radio.closest('.address-item'); // Lấy <div> cha
                    const label = addressItem.querySelector('label');

                    radio.setAttribute('data-name', updatedAddress.fullName);
                    radio.setAttribute('data-phone', updatedAddress.phone);
                    radio.setAttribute('data-address', updatedAddress.address);

                    if (label) {
                        const nameEl = label.querySelector('.addr-name');
                        const phoneEl = label.querySelector('.addr-phone');
                        const addressEl = label.querySelector('.addr-text');

                        if (nameEl) nameEl.innerText = updatedAddress.fullName;
                        if (phoneEl) phoneEl.innerText = updatedAddress.phone;
                        if (addressEl) addressEl.innerText = updatedAddress.address;
                    }

                    const editButton = addressItem.querySelector('.btn-edit-address');
                    if (editButton) {
                        editButton.setAttribute('data-name', updatedAddress.fullName);
                        editButton.setAttribute('data-phone', updatedAddress.phone);
                        editButton.setAttribute('data-address', updatedAddress.address);
                    }


                    if (radio.checked) {
                        if(displayName) displayName.innerText = updatedAddress.fullName;
                        if(displayPhone) displayPhone.innerText = updatedAddress.phone;
                        if(displayAddress) displayAddress.innerText = updatedAddress.address;

                        if (inputFullName) inputFullName.value = updatedAddress.fullName;
                        if (inputPhone) inputPhone.value = updatedAddress.phone;
                        if (inputAddress) inputAddress.value = updatedAddress.address;
                    }

                    if (editModal) editModal.style.display = "none";
                    if (listModalForEdit) listModalForEdit.style.display = "flex";

                })
                .catch(err => {

                    errorEditDiv.innerText = err.message;
                });
        });
    }

});