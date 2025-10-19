document.addEventListener("DOMContentLoaded", function () {
    // use event delegation: handle clicks on any .product-card across the page
    const priceTotal = document.getElementById("priceTotal");

    // known hidden input ids (fallback)
    const knownHiddenIds = [
        'selectedMainboardId','selectedCpuId','selectedGpuId','selectedCaseId',
        'selectedCoolingId','selectedMemoryId','selectedStorageId','selectedPsuId','selectedOtherId'
    ];

    // helper to find a hidden input for a clicked card: prefer input inside the same form
    function findHiddenInputForCard(card) {
        // try to find form ancestor
        const form = card.closest('form');
        if (form) {
            // prefer inputs whose name ends with 'Id' or have id starting with 'selected'
            let input = form.querySelector("input[type=hidden][id^='selected']");
            if (!input) input = form.querySelector("input[type=hidden]");
            if (input) return input;
        }
        // fallback: global known ids
        for (const id of knownHiddenIds) {
            const el = document.getElementById(id);
            if (el) return el;
        }
        // last resort: first hidden input on document
        return document.querySelector("input[type=hidden]");
    }

    // helper to parse a price from element: prefer data-price attribute (numeric), else parse innerText
    function parsePriceFromCard(card) {
        const dp = card.getAttribute('data-price');
        if (dp) {
            const n = parseFloat(dp);
            if (!isNaN(n)) return n;
        }
        const strong = card.querySelector('strong') || card.querySelector('span') || card.querySelector('p');
        if (strong) {
            const txt = strong.innerText || '';
            const cleaned = txt.replace(/[^\d.\-]/g, '');
            const num = parseFloat(cleaned);
            return isNaN(num) ? 0 : num;
        }
        return 0;
    }

    // get initial total displayed (if any)
    function readDisplayedTotal() {
        if (!priceTotal) return 0;
        const txt = priceTotal.innerText || '';
        const cleaned = txt.replace(/[^\d.\-]/g, '');
        const num = parseFloat(cleaned);
        return isNaN(num) ? 0 : num;
    }

    let originalTotalPrice = readDisplayedTotal();

    // detail fields (optional on some pages)
    const detailName = document.getElementById("detailName");
    const detailSocket = document.getElementById("detailSocket");
    const detailRAM = document.getElementById("detailRAM");
    const detailForm = document.getElementById("detailForm");
    const detailChipset = document.getElementById("detailChipset");
    const detailTdp = document.getElementById("detailTdp");
    const detailIGPU = document.getElementById("detailIGPU");

    // delegate clicks from document level for product-card
    document.addEventListener('click', function (e) {
        const card = e.target.closest('.product-card');
        if (!card) return;

        // visual selection
        document.querySelectorAll('.product-card.selected').forEach(c => c.classList.remove('selected'));
        card.classList.add('selected');

        // id and attributes
        const id = card.getAttribute('data-id');
        const name = card.getAttribute('data-name');
        const socket = card.getAttribute('data-socket');

        // set hidden input in same form (preferred)
        const hiddenInput = findHiddenInputForCard(card);
        if (hiddenInput && id != null) hiddenInput.value = id;

        // parse price (data-price preferred)
        const currentProductPrice = parsePriceFromCard(card) || 0;

        // update detail panel if present
        if (detailName) detailName.innerText = name || '';
        if (detailSocket) detailSocket.innerText = socket ? ('Socket: ' + socket) : '';
        if (detailRAM) detailRAM.innerText = 'RAM: ' + (card.getAttribute('data-ramtype') || '');
        if (detailForm) detailForm.innerText = 'Form Factor: ' + (card.getAttribute('data-formfactor') || '');
        if (detailChipset) detailChipset.innerText = 'Chipset: ' + (card.getAttribute('data-chipset') || '');
        if (detailTdp) detailTdp.innerText = 'TDP: ' + (card.getAttribute('data-tdp') || '');
        if (detailIGPU) detailIGPU.innerText = 'IGPU: ' + ((card.getAttribute('data-igpu') === 'true') ? 'Yes' : 'No');

        // update temporary total display (originalTotalPrice + currentProductPrice)
        if (priceTotal) {
            const tempTotal = originalTotalPrice + currentProductPrice;
            priceTotal.innerText = "$" + (Number.isInteger(tempTotal) ? tempTotal.toFixed(0) : tempTotal.toFixed(2));
        }
    });

    // Validate forms that require a selection before submit
    document.querySelectorAll('form[data-require-selection="true"]').forEach(form => {
        form.addEventListener('submit', function (e) {
            // find hidden selected input inside the form
            const hidden = form.querySelector("input[type=hidden][id^='selected']") || form.querySelector('input[type=hidden]');
            const val = hidden ? (hidden.value || '') : '';
            if (!val || val.trim() === '') {
                e.preventDefault();
                alert('Please select a component before continuing.');
                return false;
            }
            return true;
        });
    });

    // Popup logic (unchanged)
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

    // If needed, recalc originalTotalPrice whenever server re-renders or other changes appear. We keep one-time read for now.
});

function filterProducts() {
    const sb = document.getElementById("searchBox");
    const query = sb ? sb.value.toLowerCase() : '';
    document.querySelectorAll(".product-card").forEach(card => {
        const name = (card.getAttribute("data-name") || "").toLowerCase();
        card.style.display = name.includes(query) ? "block" : "none";
    });
}
function clearFilter() {
    const sb = document.getElementById("searchBox");
    if (sb) sb.value = "";
    document.querySelectorAll(".product-card").forEach(card => {
        card.style.display = "block";
    });
}