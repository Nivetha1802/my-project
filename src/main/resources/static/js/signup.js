function showPopup(message) {
    if (message) {
        alert(message);
    }
}
function showMessage(message, type) {
    var div = document.createElement('div');
    div.className = type === 'error' ? 'error-popup' : 'success-popup';
    div.innerText = message;
    document.body.appendChild(div);
    setTimeout(function() {
        document.body.removeChild(div);
    }, 3000);
}