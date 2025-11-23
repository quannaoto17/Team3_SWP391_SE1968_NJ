function validatePassword() {
    const password = document.getElementById("password").value;
    const confirm = document.getElementById("confirmPassword").value;
    const errorText = document.getElementById("errorText");

    if (password !== confirm) {
        errorText.textContent = "⚠️ Confirm password not true!";
        return false;
    }
    return true;
}

document.addEventListener("DOMContentLoaded", async function () {
    const provinceSelect = document.getElementById("province");
    const districtSelect = document.getElementById("district");
    const wardSelect = document.getElementById("ward");
    const addressInput = document.getElementById("address");

    if (!provinceSelect || !districtSelect || !wardSelect || !addressInput) return;

    // 1. Load Tỉnh
    const provinces = await fetch("https://provinces.open-api.vn/api/p/").then(r => r.json());
    provinces.forEach(p => provinceSelect.add(new Option(p.name, p.code)));

    // Nếu có địa chỉ cũ → tách ra để prefill
    const oldAddress = addressInput.value;  // Ví dụ: "Phường X, Quận Y, TP Z, Việt Nam"
    let provinceName = "", districtName = "", wardName = "";

    if (oldAddress) {
        const parts = oldAddress.split(",").map(p => p.trim());
        wardName = parts[0] || "";
        districtName = parts[1] || "";
        provinceName = parts[2] || "";
    }

    // 2. Nếu đang Edit → chọn Tỉnh cũ
    if (provinceName) {
        const foundProvince = provinces.find(p => p.name === provinceName);
        if (foundProvince) {
            provinceSelect.value = foundProvince.code;

            // Load Huyện tương ứng
            const provinceDetail = await fetch(`https://provinces.open-api.vn/api/p/${foundProvince.code}?depth=2`).then(r => r.json());
            provinceDetail.districts.forEach(d => districtSelect.add(new Option(d.name, d.code)));

            const foundDistrict = provinceDetail.districts.find(d => d.name === districtName);
            if (foundDistrict) {
                districtSelect.value = foundDistrict.code;

                // Load Xã tương ứng
                const districtDetail = await fetch(`https://provinces.open-api.vn/api/d/${foundDistrict.code}?depth=2`).then(r => r.json());
                districtDetail.wards.forEach(w => wardSelect.add(new Option(w.name, w.name)));

                wardSelect.value = wardName;
            }
        }
    }

    // 3. Lắng nghe thay đổi realtime
    provinceSelect.addEventListener("change", handleProvinceChange);
    districtSelect.addEventListener("change", handleDistrictChange);
    wardSelect.addEventListener("change", updateAddress);

    async function handleProvinceChange() {
        districtSelect.innerHTML = "<option value=''>-- District --</option>";
        wardSelect.innerHTML = "<option value=''>-- Ward --</option>";
        if (!provinceSelect.value) { updateAddress(); return; }

        const data = await fetch(`https://provinces.open-api.vn/api/p/${provinceSelect.value}?depth=2`).then(r => r.json());
        data.districts.forEach(d => districtSelect.add(new Option(d.name, d.code)));

        updateAddress();
    }

    async function handleDistrictChange() {
        wardSelect.innerHTML = "<option value=''>-- Ward --</option>";
        if (!districtSelect.value) { updateAddress(); return; }

        const data = await fetch(`https://provinces.open-api.vn/api/d/${districtSelect.value}?depth=2`).then(r => r.json());
        data.wards.forEach(w => wardSelect.add(new Option(w.name, w.name)));

        updateAddress();
    }

    function updateAddress() {
        const p = provinceSelect.options[provinceSelect.selectedIndex]?.text || "";
        const d = districtSelect.options[districtSelect.selectedIndex]?.text || "";
        const w = wardSelect.options[wardSelect.selectedIndex]?.text || "";

        addressInput.value = [w, d, p, "Việt Nam"].filter(Boolean).join(", ");
    }
});
