function validatePassword() {
    var password = document.getElementById("password");
    var confirmPassword = document.getElementById("confirm_password");
    var error = document.getElementById("password_error");

    if (password.value !== confirmPassword.value) {
        error.textContent = "Passwords do not match";
        confirmPassword.setCustomValidity("Passwords do not match");
    } else {
        error.textContent = "";
        confirmPassword.setCustomValidity("");
    }
}

document.getElementById("password").oninput = validatePassword;
document.getElementById("confirm_password").oninput = validatePassword;
