// STAFF.JS - dùng cho Add & Edit Staff

function checkStrength() {
    const password = document.getElementById("password");
    const message = document.getElementById("strengthMessage");
    if (!password || !message) return;

    const pwd = password.value;
    let strength = 0;

    if (pwd.length >= 6) strength++;
    if (/[A-Z]/.test(pwd)) strength++;
    if (/[0-9]/.test(pwd)) strength++;
    if (/[@$!%*?&]/.test(pwd)) strength++;

    if (pwd.length === 0) {
        message.textContent = "";
        return;
    }

    switch (strength) {
        case 1:
            message.textContent = "Weak 😢";
            message.style.color = "red";
            break;
        case 2:
            message.textContent = "Medium 😐";
            message.style.color = "orange";
            break;
        case 3:
            message.textContent = "Strong 😊";
            message.style.color = "#4b7bec";
            break;
        case 4:
            message.textContent = "Very Strong 💪";
            message.style.color = "green";
            break;
    }
}

/* ==========================================================
   📍 ADDRESS API (Tỉnh / Huyện / Xã)
   ========================================================== */
document.addEventListener("DOMContentLoaded", function () {
    const provinceSelect = document.getElementById("province");
    const districtSelect = document.getElementById("district");
    const wardSelect = document.getElementById("ward");
    const addressInput = document.getElementById("address");

    // Nếu không có các phần tử này (ví dụ trang View Staff) thì bỏ qua
    if (!provinceSelect || !districtSelect || !wardSelect || !addressInput) return;

    // === 1. Load danh sách Tỉnh/Thành ===
    fetch("https://provinces.open-api.vn/api/p/")
        .then(res => res.json())
        .then(data => {
            data.forEach(province => {
                const opt = new Option(province.name, province.code);
                provinceSelect.add(opt);
            });
        })
        .catch(err => console.error("Lỗi tải danh sách tỉnh:", err));

    // === 2. Khi chọn Tỉnh -> load Huyện ===
    provinceSelect.addEventListener("change", () => {
        districtSelect.innerHTML = "<option value=''>-- Chọn Quận/Huyện --</option>";
        wardSelect.innerHTML = "<option value=''>-- Chọn Phường/Xã --</option>";

        if (provinceSelect.value) {
            fetch(`https://provinces.open-api.vn/api/p/${provinceSelect.value}?depth=2`)
                .then(res => res.json())
                .then(data => {
                    data.districts.forEach(district => {
                        const opt = new Option(district.name, district.code);
                        districtSelect.add(opt);
                    });
                })
                .catch(err => console.error("Lỗi tải danh sách huyện:", err));
        }
        updateAddress();
    });

    // === 3. Khi chọn Huyện -> load Xã ===
    districtSelect.addEventListener("change", () => {
        wardSelect.innerHTML = "<option value=''>-- Chọn Phường/Xã --</option>";

        if (districtSelect.value) {
            fetch(`https://provinces.open-api.vn/api/d/${districtSelect.value}?depth=2`)
                .then(res => res.json())
                .then(data => {
                    data.wards.forEach(ward => {
                        const opt = new Option(ward.name, ward.name);
                        wardSelect.add(opt);
                    });
                })
                .catch(err => console.error("Lỗi tải danh sách xã:", err));
        }
        updateAddress();
    });

    // === 4. Khi chọn Xã -> tự cập nhật ô Address ===
    wardSelect.addEventListener("change", updateAddress);

    function updateAddress() {
        const provinceText = provinceSelect.options[provinceSelect.selectedIndex]?.text || "";
        const districtText = districtSelect.options[districtSelect.selectedIndex]?.text || "";
        const wardText = wardSelect.options[wardSelect.selectedIndex]?.text || "";

        // Tự động ghép chuỗi vào ô input address
        addressInput.value = [wardText, districtText, provinceText, "Việt Nam"]
            .filter(Boolean)
            .join(", ");
    }
});
