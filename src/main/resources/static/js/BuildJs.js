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
    // Mainboard & CPU fields
    const detailSocket = document.getElementById("detailSocket");
    const detailRAM = document.getElementById("detailRAM");
    const detailForm = document.getElementById("detailForm");
    const detailChipset = document.getElementById("detailChipset");
    const detailTdp = document.getElementById("detailTdp");
    const detailIGPU = document.getElementById("detailIGPU");
    // GPU fields
    const detailPCIe = document.getElementById("detailPCIe");
    const detailVRAM = document.getElementById("detailVRAM");
    // Case fields
    const detailFormFactor = document.getElementById("detailFormFactor");
    const detailGpuLength = document.getElementById("detailGpuLength");
    const detailCpuHeight = document.getElementById("detailCpuHeight");
    const detailPsuFormFactor = document.getElementById("detailPsuFormFactor");
    // Cooling fields
    const detailType = document.getElementById("detailType");
    const detailFanSize = document.getElementById("detailFanSize");
    const detailTDP = document.getElementById("detailTDP");
    // Memory & Storage fields
    const detailCapacity = document.getElementById("detailCapacity");
    const detailModules = document.getElementById("detailModules");
    const detailSpeed = document.getElementById("detailSpeed");
    // PSU fields
    const detailWattage = document.getElementById("detailWattage");
    const detailEfficiency = document.getElementById("detailEfficiency");
    const detailModular = document.getElementById("detailModular");

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

        // set hidden input in same form (preferred)
        const hiddenInput = findHiddenInputForCard(card);
        if (hiddenInput && id != null) hiddenInput.value = id;

        // parse price (data-price preferred)
        const currentProductPrice = parsePriceFromCard(card) || 0;

        // update detail panel - name is common for all
        if (detailName) detailName.innerText = name || '';

        // Mainboard & CPU specific
        if (detailSocket) {
            const socket = card.getAttribute('data-socket');
            detailSocket.innerText = socket ? ('Socket: ' + socket) : 'Socket: ...';
        }
        if (detailRAM) {
            detailRAM.innerText = 'RAM: ' + (card.getAttribute('data-ramtype') || '...');
        }
        if (detailForm) {
            detailForm.innerText = 'Form Factor: ' + (card.getAttribute('data-formfactor') || '...');
        }
        if (detailChipset) {
            detailChipset.innerText = 'Chipset: ' + (card.getAttribute('data-chipset') || '...');
        }
        if (detailTdp) {
            detailTdp.innerText = 'TDP: ' + (card.getAttribute('data-tdp') || '...');
        }
        if (detailIGPU) {
            detailIGPU.innerText = 'IGPU: ' + ((card.getAttribute('data-igpu') === 'true') ? 'Yes' : 'No');
        }

        // GPU specific
        if (detailPCIe) {
            detailPCIe.innerText = 'PCIe: ' + (card.getAttribute('data-pcie') || '...');
        }
        if (detailVRAM) {
            detailVRAM.innerText = 'VRAM: ' + (card.getAttribute('data-vram') || '...') + ' GB';
        }

        // Case specific
        if (detailFormFactor) {
            detailFormFactor.innerText = 'Form Factor: ' + (card.getAttribute('data-formfactor') || '...');
        }
        if (detailGpuLength) {
            detailGpuLength.innerText = 'GPU Max Length: ' + (card.getAttribute('data-gpumaxlength') || '...') + 'mm';
        }
        if (detailCpuHeight) {
            detailCpuHeight.innerText = 'CPU Cooler Max Height: ' + (card.getAttribute('data-cpumaxheight') || '...') + 'mm';
        }
        if (detailPsuFormFactor) {
            detailPsuFormFactor.innerText = 'PSU Form Factor: ' + (card.getAttribute('data-psuformfactor') || '...');
        }

        // Cooling specific
        if (detailType) {
            detailType.innerText = 'Type: ' + (card.getAttribute('data-type') || '...');
        }
        if (detailFanSize) {
            detailFanSize.innerText = 'Fan Size: ' + (card.getAttribute('data-fansize') || '...');
        }
        if (detailTDP) {
            detailTDP.innerText = 'TDP: ' + (card.getAttribute('data-tdp') || '...');
        }

        // Memory specific
        if (detailModules) {
            detailModules.innerText = 'Modules: ' + (card.getAttribute('data-modules') || '...');
        }
        if (detailSpeed) {
            detailSpeed.innerText = 'Speed: ' + (card.getAttribute('data-speed') || '...') + 'MHz';
        }

        // PSU specific
        if (detailWattage) {
            detailWattage.innerText = 'Wattage: ' + (card.getAttribute('data-wattage') || '...') + 'W';
        }
        if (detailEfficiency) {
            detailEfficiency.innerText = 'Efficiency: ' + (card.getAttribute('data-efficiency') || '...');
        }
        if (detailModular) {
            const isModular = card.getAttribute('data-modular') === 'true';
            detailModular.innerText = 'Modular: ' + (isModular ? 'Yes' : 'No');
        }

        // Memory & Storage specific
        if (detailCapacity) {
            detailCapacity.innerText = 'Capacity: ' + (card.getAttribute('data-capacity') || '...') + 'GB';
        }

        // PSU specific
        if (detailWattage) {
            detailWattage.innerText = 'Wattage: ' + (card.getAttribute('data-wattage') || '...') + 'W';
        }

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

    // ===============================
    // Overview Popup Handling
    // ===============================
    const openOverviewBtn = document.getElementById("openOverviewBtn");
    const overviewPopup = document.getElementById("overviewPopup");
    const closeOverviewBtn = document.getElementById("closeOverviewBtn");
    const closeOverviewFooterBtn = document.getElementById("closeOverviewFooterBtn");

    // Open overview popup
    if (openOverviewBtn && overviewPopup) {
        openOverviewBtn.addEventListener("click", function(e) {
            e.preventDefault();
            overviewPopup.style.display = "flex";
            console.log("✅ Overview popup opened");
        });
    }

    // Close overview popup - X button
    if (closeOverviewBtn && overviewPopup) {
        closeOverviewBtn.addEventListener("click", function() {
            overviewPopup.style.display = "none";
            console.log("✅ Overview popup closed");
        });
    }

    // Close overview popup - Close button in footer
    if (closeOverviewFooterBtn && overviewPopup) {
        closeOverviewFooterBtn.addEventListener("click", function() {
            overviewPopup.style.display = "none";
            console.log("✅ Overview popup closed via footer button");
        });
    }

    // Close overview popup when clicking outside
    if (overviewPopup) {
        overviewPopup.addEventListener("click", function(e) {
            if (e.target === overviewPopup) {
                overviewPopup.style.display = "none";
                console.log("✅ Overview popup closed by clicking outside");
            }
        });
    }

    // ===============================
    // Save as Image Functionality
    // ===============================
    const saveAsImageBtn = document.getElementById("saveAsImageBtn");

    if (saveAsImageBtn) {
        saveAsImageBtn.addEventListener("click", function() {
            // Lấy phần nội dung overview để chụp
            const overviewContent = document.querySelector(".overview-popup-content");

            if (!overviewContent) {
                alert("⚠️ Cannot find overview content to save.");
                return;
            }

            // Tạm ẩn các nút trong footer để không chụp vào ảnh
            const footer = overviewContent.querySelector(".overview-footer");
            const originalDisplay = footer ? footer.style.display : null;
            if (footer) footer.style.display = "none";

            // Sử dụng html2canvas để chụp
            html2canvas(overviewContent, {
                backgroundColor: '#ffffff',
                scale: 2,
                logging: false,
                useCORS: true,
                allowTaint: true
            }).then(function(canvas) {
                // Khôi phục footer
                if (footer && originalDisplay !== null) {
                    footer.style.display = originalDisplay;
                } else if (footer) {
                    footer.style.display = "";
                }

                // Tạo link download
                const link = document.createElement('a');
                const timestamp = new Date().getTime();
                link.download = 'my-pc-build-' + timestamp + '.png';
                link.href = canvas.toDataURL('image/png');
                link.click();

                console.log("✅ Build image saved successfully!");
            }).catch(function(error) {
                // Khôi phục footer nếu có lỗi
                if (footer && originalDisplay !== null) {
                    footer.style.display = originalDisplay;
                } else if (footer) {
                    footer.style.display = "";
                }

                console.error('❌ Error generating image:', error);
                alert('Failed to save image. Please try again.');
            });
        });
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