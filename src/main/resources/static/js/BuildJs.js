document.addEventListener("DOMContentLoaded", function () {
    const cards = document.querySelectorAll(".product-card");
    const priceTotal = document.getElementById("priceTotal");
    const hiddenInput = document.querySelector("input[type=hidden]");

    // Lưu giá trị Total Price ban đầu từ HTML
    let originalTotalPrice = 0;
    if (priceTotal) {
        originalTotalPrice = parseFloat(priceTotal.innerText.replace(/[^\d.]/g, "")) || 0;
    }

    const detailName = document.getElementById("detailName");
    const detailSocket = document.getElementById("detailSocket");
    const detailRAM = document.getElementById("detailRAM");
    const detailForm = document.getElementById("detailForm");
    const detailChipset = document.getElementById("detailChipset");
    const detailTdp = document.getElementById("detailTdp");
    const detailIGPU = document.getElementById("detailIGPU");

    cards.forEach(card => {
        card.addEventListener("click", function () {
            cards.forEach(c => c.classList.remove("selected"));
            this.classList.add("selected");

            const id = this.getAttribute("data-id");
            const name = this.getAttribute("data-name");
            const socket = this.getAttribute("data-socket");
            const priceStr = this.querySelector("strong").innerText;

            // Lấy giá sản phẩm hiện tại
            const currentProductPrice = parseFloat(priceStr.replace(/[^\d.]/g, "")) || 0;

            hiddenInput.value = id;

            // Update detail panel (tuỳ trang)
            if (detailName) detailName.innerText = name;
            if (detailSocket) detailSocket.innerText = "Socket: " + socket;
            if (detailRAM) detailRAM.innerText = "RAM: " + this.getAttribute("data-ramtype");
            if (detailForm) detailForm.innerText = "Form Factor: " + this.getAttribute("data-formfactor");
            if (detailChipset) detailChipset.innerText = "Chipset: " + this.getAttribute("data-chipset");
            if (detailTdp) detailTdp.innerText = "TDP: " + this.getAttribute("data-tdp");
            if (detailIGPU) detailIGPU.innerText = "IGPU: " + (this.getAttribute("data-igpu") === 'true' ? 'Yes' : 'No');

            // Hiển thị tạm thời: Total Price ban đầu + giá sản phẩm đang chọn
            if (priceTotal) {
                const tempTotal = originalTotalPrice + currentProductPrice;
                priceTotal.innerText = "$" + tempTotal.toFixed(2);
            }
        });
    });

    // Popup logic
    const filterBtn = document.getElementById('openFilterPopup');
    const filterPopup = document.getElementById('filterPopup');
    const closeFilterBtn = document.getElementById('closeFilterPopup');

    if (filterBtn && filterPopup && closeFilterBtn) {
        filterBtn.onclick = () => {
            filterPopup.style.display = 'block';
        };
        closeFilterBtn.onclick = () => {
            filterPopup.style.display = 'none';
        };
    }
});

function filterProducts() {
    const query = document.getElementById("searchBox").value.toLowerCase();
    document.querySelectorAll(".product-card").forEach(card => {
        const name = card.getAttribute("data-name").toLowerCase();
        card.style.display = name.includes(query) ? "block" : "none";
    });
}

function clearFilter() {
    document.getElementById("searchBox").value = "";
    document.querySelectorAll(".product-card").forEach(card => {
        card.style.display = "block";
    });
}