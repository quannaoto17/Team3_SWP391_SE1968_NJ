// Xử lý click chọn sản phẩm -> hiển thị chi tiết bên phải
function showDetails(id, name, socket, formFactor, ram, wifi, bluetooth, description, price) {
    const detailsPanel = document.getElementById("product-details");

    detailsPanel.innerHTML = `
    <h2>${name}</h2>
    <p><strong>Socket:</strong> ${socket}</p>
    <p><strong>Form Factor:</strong> ${formFactor}</p>
    <p><strong>RAM:</strong> ${ram}</p>
    <p><strong>WiFi:</strong> ${wifi ? "Yes" : "No"}</p>
    <p><strong>Bluetooth:</strong> ${bluetooth ? "Yes" : "No"}</p>
    <p><strong>Description:</strong> ${description}</p>
    <p><strong>Price:</strong> $${price}</p>
    <button class="select-btn" onclick="selectProduct(${id})">Chọn sản phẩm này</button>
  `;

    detailsPanel.classList.add("active");
}

// Giả lập chọn sản phẩm
function selectProduct(id) {
    alert("Bạn đã chọn sản phẩm ID: " + id);
    // TODO: Gửi request về server lưu vào build hiện tại
}

// Tìm kiếm sản phẩm theo tên
function searchProducts() {
    const keyword = document.getElementById("searchInput").value.toLowerCase();
    const cards = document.querySelectorAll(".product-card");

    cards.forEach(card => {
        const name = card.querySelector("h3").innerText.toLowerCase();
        card.style.display = name.includes(keyword) ? "block" : "none";
    });
}

// Lọc sản phẩm theo socket
function filterProducts() {
    const selectedSocket = document.getElementById("filterSocket").value;
    const cards = document.querySelectorAll(".product-card");

    cards.forEach(card => {
        const socket = card.getAttribute("data-socket");
        card.style.display = (selectedSocket === "" || socket === selectedSocket) ? "block" : "none";
    });
}
