function calculateCartTotal() {
    const cartRows = document.querySelectorAll('.cart-table tbody tr');
    let subtotal = 0;


    cartRows.forEach(row => {
        const priceElement = row.querySelector('#price');
        const quantityInput = row.querySelector('.quantity-input');
        const price = parseInt(priceElement.textContent.replace(/[^0-9]/g, ''));
        const quantity = parseInt(quantityInput.value);


        const total = price * quantity;


        subtotal += total;


        const totalElement = row.querySelector('td:last-child');
        totalElement.textContent = new Intl.NumberFormat('vi-VN', {
            style: 'currency',
            currency: 'VND'
        }).format(total);
    });

    // Cập nhật phần "Tạm tính" và "Tổng" trong giỏ hàng
    document.getElementById('PriceName').textContent = new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(subtotal);

    const grandTotalElement = document.querySelector('.cart-summary-row:last-child span:last-child');
    grandTotalElement.textContent = new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(subtotal);
}


calculateCartTotal();


document.querySelectorAll('.quantity-input').forEach(input => {
    input.addEventListener('change', calculateCartTotal);
});
