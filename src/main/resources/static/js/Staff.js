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
