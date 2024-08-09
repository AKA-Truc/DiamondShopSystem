const textOptions = ["hello", "hi", "hey"];
const centerText = document.getElementById("center-text");
let index = 0;

function rotateText() {
    centerText.classList.remove('visible');
    setTimeout(() => {
        centerText.textContent = textOptions[index];
        centerText.classList.add('visible');
        index = (index + 1) % textOptions.length;
    }, 1000); // Wait for the fade-out effect before changing text
}

setInterval(rotateText, 3000); // Change text every 3 seconds (1s fade-out + 1s fade-in)

// Initialize text
rotateText();